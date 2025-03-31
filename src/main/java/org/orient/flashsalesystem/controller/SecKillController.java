package org.orient.flashsalesystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rabbitmq.tools.json.JSONUtil;
import jakarta.annotation.Resource;
import org.orient.flashsalesystem.pojo.FlashOrder;
import org.orient.flashsalesystem.pojo.Order;
import org.orient.flashsalesystem.pojo.SeckillMessage;
import org.orient.flashsalesystem.pojo.User;
import org.orient.flashsalesystem.rabbitmq.MQSendder;
import org.orient.flashsalesystem.service.IFlashOrderService;
import org.orient.flashsalesystem.service.IGoodsService;
import org.orient.flashsalesystem.service.IOrderService;
import org.orient.flashsalesystem.utils.JsonUtil;
import org.orient.flashsalesystem.vo.GoodsVo;
import org.orient.flashsalesystem.vo.RespBean;
import org.orient.flashsalesystem.vo.RespBeanEnum;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 秒杀功能
 * windows 优化前:QPS: 2369.7 20个商品 秒杀订单835条
 * windows 优化后:QPS: 8443   20个商品 秒杀订单133条 剩余0件
 * 9797.5 20个商品 秒杀订单20条  剩余0件
 * 14492  20个商品 秒杀订单20条  剩余0
 */
@Controller
@RequestMapping("/secKill")
public class SecKillController implements InitializingBean {
    @Resource
    private IGoodsService goodsService;
    @Resource
    private IFlashOrderService flashOrderService;
    @Resource
    private IOrderService orderService;
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private MQSendder mqSendder;

    private Map<Long, Boolean> EmptyStockMap = new ConcurrentHashMap<>();

    @RequestMapping("/doSecKill2")
    public String doSecKill2(Model model, User user, Long goodsId) {
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(goodsId);
        if (goodsVo.getStockCount() < 1) {
            model.addAttribute("errmsg", RespBeanEnum.EMPTY_STOCK);
            return "secKillFail";
        }
        // 判断是否重复抢购
        FlashOrder flashOrder = flashOrderService.getOne(new QueryWrapper<FlashOrder>().eq("goods_id", goodsId).eq("user_id", user.getId()));
        if (flashOrder != null) {
            model.addAttribute("errmsg", RespBeanEnum.REPEAT_ERROR);
            return "secKillFail";
        }
        // 秒杀
        Order order = orderService.seckill(user, goodsVo);
        model.addAttribute("order", order);
        model.addAttribute("goodsVo", goodsVo);
        return "orderDetail";
    }

    @PostMapping("/doSecKill")
    @ResponseBody
    public RespBean doSecKill(User user, Long goodsId) {
        if (user == null) {
            return RespBean.error(RespBeanEnum.SESSION_ERROR);
        }
        ValueOperations valueOperations = redisTemplate.opsForValue();
        // 判断是否重复抢购
        FlashOrder flashOrder = (FlashOrder) valueOperations.get("order:" + user.getId() + ":" + goodsId);
        if (flashOrder != null) {
            return RespBean.error(RespBeanEnum.REPEAT_ERROR);
        }
        // 通过标志, 减少对redis的访问
        if (EmptyStockMap.get(goodsId)) {
            return RespBean.error(RespBeanEnum.EMPTY_STOCK);

        }

        // 在redis中提前减少库存
        Long stock = valueOperations.decrement("seckillGoods:" + goodsId);
        if (stock < 0) {
            EmptyStockMap.put(goodsId, true);
            valueOperations.increment("seckillGoods:" + goodsId);
            return RespBean.error(RespBeanEnum.EMPTY_STOCK);
        }

        // 下单 秒杀
        SeckillMessage message = new SeckillMessage(user, goodsId);
        mqSendder.sendSeckillMessage(JsonUtil.object2JsonStr(message));
        return RespBean.success(0);
    }

    /**
     * 系统初始化, 把商品库存数量加载到redis中
     *
     * @throws Exception
     */

    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> list = goodsService.findGoodsVo();
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        list.forEach(goodsVo -> {
            redisTemplate.opsForValue().set("seckillGoods:" + goodsVo.getId(), goodsVo.getStockCount());
            EmptyStockMap.put(goodsVo.getId(), false);
        });
    }

    /**
     * 获取秒杀结果
     * @param user
     * @param goodsId orderId:成功,  -1失败  0派对中
     * @return
     */
    @GetMapping("/result")
    @ResponseBody
    public RespBean getResult(User user, Long goodsId) {
        if(user==null){
            return RespBean.error(RespBeanEnum.SESSION_ERROR);
        }
        Long orderId = flashOrderService.getResult(user, goodsId);
        return RespBean.success(orderId);
    }
}

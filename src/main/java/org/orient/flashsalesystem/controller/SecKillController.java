package org.orient.flashsalesystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import org.orient.flashsalesystem.pojo.FlashOrder;
import org.orient.flashsalesystem.pojo.Order;
import org.orient.flashsalesystem.pojo.User;
import org.orient.flashsalesystem.service.IFlashOrderService;
import org.orient.flashsalesystem.service.IGoodsService;
import org.orient.flashsalesystem.service.IOrderService;
import org.orient.flashsalesystem.vo.GoodsVo;
import org.orient.flashsalesystem.vo.RespBean;
import org.orient.flashsalesystem.vo.RespBeanEnum;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 秒杀功能
 * windows 优化前:QPS: 2369.7 20个商品 秒杀订单835条
 * windows 优化后:QPS: 8443   20个商品 秒杀订单133条 剩余0件
 *                    9797.5 20个商品 秒杀订单20条  剩余0件
 *                    14492  20个商品 秒杀订单20条  剩余0
 */
@Controller
@RequestMapping("/secKill")
public class SecKillController {
    @Resource
    private IGoodsService goodsService;
    @Resource
    private IFlashOrderService flashOrderService;
    @Resource
    private IOrderService orderService;
    @Resource
    private RedisTemplate redisTemplate;

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
        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(goodsId);
        if (goodsVo.getStockCount() < 1) {
            return RespBean.error(RespBeanEnum.EMPTY_STOCK);
        }
        // 判断是否重复抢购
//        FlashOrder flashOrder = flashOrderService.getOne(new QueryWrapper<FlashOrder>().eq("goods_id", goodsId).eq("user_id", user.getId()));
        FlashOrder flashOrder = (FlashOrder)redisTemplate.opsForValue().get("order:" + user.getId() + ":" + goodsId);
        if (flashOrder != null) {
            return RespBean.error(RespBeanEnum.REPEAT_ERROR);
        }
        // 秒杀
        Order order = orderService.seckill(user, goodsVo);
        return RespBean.success(order);
    }
}

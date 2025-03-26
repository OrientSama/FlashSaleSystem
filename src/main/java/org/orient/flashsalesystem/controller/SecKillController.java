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
import org.orient.flashsalesystem.vo.RespBeanEnum;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 秒杀功能
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

    @RequestMapping("/doSecKill")
    public String doSecKill(Model model, User user, Long goodsId) {
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
}

package org.orient.flashsalesystem.controller;

import jakarta.annotation.Resource;
import org.orient.flashsalesystem.pojo.User;
import org.orient.flashsalesystem.service.IGoodsService;
import org.orient.flashsalesystem.service.IUserService;
import org.orient.flashsalesystem.vo.GoodsVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

/**
 * 商品页面
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {
    @Resource
    private IUserService userService;
    @Resource
    private IGoodsService goodsService;

    /**
     * 跳转到商品页面
     * windows first: QPS 2719
     */
    @RequestMapping("/toList")
    public String toList(Model model, User user) {
        model.addAttribute("user", user);
        model.addAttribute("goodsList", goodsService.findGoodsVo());
        return "goodsList";
    }

    /**
     * 跳转到商品详情页
     *
     * @param goodsId
     * @param user
     * @return
     */
    @RequestMapping("/toDetail/{goodsId}")
    public String toDetail(@PathVariable Long goodsId, Model model, User user) {
        model.addAttribute("user", user);
        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(goodsId);
        // 计算flashStatus
        Date startDate = goodsVo.getStartDate();
        Date endDate = goodsVo.getEndDate();
        Date now = new Date();
        int remainSeconds = 0;
        int flashStatus = 0;
        if (now.before(startDate)) {
            remainSeconds = (int) (startDate.getTime() - now.getTime()) / 1000;
        } else if (now.after(endDate)) {
            // 秒杀已结束
            flashStatus = 2;
            remainSeconds = -1;
        } else {
            flashStatus = 1;
        }
        model.addAttribute("remainSeconds", remainSeconds);
        model.addAttribute("flashStatus", flashStatus);
        model.addAttribute("goods", goodsVo);
        return "goodsDetail";
    }
}

package org.orient.flashsalesystem.controller;

import jakarta.annotation.Resource;
import org.orient.flashsalesystem.pojo.User;
import org.orient.flashsalesystem.service.IGoodsService;
import org.orient.flashsalesystem.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
     */
    @RequestMapping("/toList")
    public String toList(Model model, User user) {

        model.addAttribute("user", user);
        model.addAttribute("goodsList", goodsService.findGoodsVo());
        return "goodsList";
    }
}

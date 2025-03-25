package org.orient.flashsalesystem.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.orient.flashsalesystem.pojo.User;
import org.orient.flashsalesystem.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 商品页面
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {
    @Resource
    private IUserService userService;

    /**
     * 跳转到商品页面
     */
    @RequestMapping("/toList")
    public String toList(HttpServletRequest request, HttpServletResponse response,
                         Model model,
                         @CookieValue("userTicket") String ticket) {
        if (StringUtils.isEmpty(ticket)) {
            return "redirect:/login/toLogin";
        }
//        User user = (User) session.getAttribute(ticket);
        User user = userService.getUserByCookie(ticket, request, response);

        if (user == null) {
            return "redirect:/login/toLogin";
        }
        model.addAttribute("user", user);
        return "goodsList";
    }
}

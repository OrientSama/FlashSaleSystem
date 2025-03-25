package org.orient.flashsalesystem.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import jakarta.servlet.http.HttpSession;
import org.orient.flashsalesystem.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 商品页面
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

    /**
     * 跳转到商品页面
     */
    @RequestMapping("/toList")
    public String toList(HttpSession session,
                         Model model,
                         @CookieValue("userTicket") String ticket) {
        if(StringUtils.isEmpty(ticket)) {
            return "redirect:/login/toLogin";
        }
        User user = (User) session.getAttribute(ticket);
        if(user == null) {
            return "redirect:/login/toLogin";
        }
        model.addAttribute("user", user);
        return "goodsList";
    }
}

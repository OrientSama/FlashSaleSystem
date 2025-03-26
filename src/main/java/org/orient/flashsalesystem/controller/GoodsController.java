package org.orient.flashsalesystem.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.orient.flashsalesystem.pojo.User;
import org.orient.flashsalesystem.service.IGoodsService;
import org.orient.flashsalesystem.service.IUserService;
import org.orient.flashsalesystem.vo.GoodsVo;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;
import org.thymeleaf.web.servlet.IServletWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.time.Duration;
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
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private ThymeleafViewResolver thymeleafViewResolver;

    /**
     * 跳转到商品页面
     * 如果redis中有该页面,直接返回;
     * 如果redis中没有该页面, 那么生成页面, 放入redis, 再返回
     * windows first: QPS 2719
     */
    @ResponseBody
    @RequestMapping(value = "/toList", produces = "text/html;charset=utf-8")
    public String toList(Model model, User user,
                         HttpServletRequest request, HttpServletResponse response) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String html = (String) valueOperations.get("goodsListHtml");
        if (!StringUtils.isEmpty(html)) {
            return html;
        }
        model.addAttribute("user", user);
        model.addAttribute("goodsList", goodsService.findGoodsVo());

        // 如果为空, 手动渲染
        JakartaServletWebApplication application = JakartaServletWebApplication.buildApplication(request.getServletContext());
        IServletWebExchange exchange = application.buildExchange(request, response);
        WebContext webContext = new WebContext(exchange, exchange.getLocale(), model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goodsList", webContext);
        if (!StringUtils.isEmpty(html)) {
            valueOperations.set("goodsListHtml", html, Duration.ofMinutes(3));
        }
        return html;
    }

    /**
     * 跳转到商品详情页
     *
     * @param goodsId
     * @param user
     * @return
     */
    @RequestMapping(value = "/toDetail/{goodsId}", produces = "text/html;charset=utf-8")
    @ResponseBody
    public String toDetail(@PathVariable Long goodsId, Model model, User user,
                           HttpServletRequest request, HttpServletResponse response) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String html = (String) valueOperations.get("goodsDetailHtml:" + goodsId);
        if (!StringUtils.isEmpty(html)) {
            return html;
        }
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

        // 如果为空, 手动渲染
        JakartaServletWebApplication application = JakartaServletWebApplication.buildApplication(request.getServletContext());
        IServletWebExchange exchange = application.buildExchange(request, response);
        WebContext webContext = new WebContext(exchange, exchange.getLocale(), model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goodsDetail", webContext);
        if (!StringUtils.isEmpty(html)) {
            valueOperations.set("goodsDetailHtml:" + goodsId, html, Duration.ofMinutes(3));
        }
        return html;
    }
}

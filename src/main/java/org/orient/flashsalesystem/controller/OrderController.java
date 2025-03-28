package org.orient.flashsalesystem.controller;

import jakarta.annotation.Resource;
import org.orient.flashsalesystem.pojo.User;
import org.orient.flashsalesystem.service.IOrderService;
import org.orient.flashsalesystem.vo.OrderVo;
import org.orient.flashsalesystem.vo.RespBean;
import org.orient.flashsalesystem.vo.RespBeanEnum;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author orient
 * @since 2025-03-25
 */
@Controller
@RequestMapping("/order")
public class OrderController {
    @Resource
    private IOrderService orderService;

    @RequestMapping("/detail")
    @ResponseBody
    public RespBean detail(User user, Long orderId) {
        if(user == null) {
            return RespBean.error(RespBeanEnum.SESSION_ERROR);
        }
        OrderVo orderVo =  orderService.detail(orderId);
        return RespBean.success(orderVo);
    }

}

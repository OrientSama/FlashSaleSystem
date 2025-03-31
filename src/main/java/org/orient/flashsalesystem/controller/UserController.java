package org.orient.flashsalesystem.controller;

import org.orient.flashsalesystem.pojo.User;
import org.orient.flashsalesystem.rabbitmq.MQSendder;
import org.orient.flashsalesystem.vo.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author orient
 * @since 2025-03-24
 */
@Controller
@RequestMapping("/user")
public class UserController {

    /**
     * 查看用户信息
     * windows first: QPS: 4633/单用户  4397/双用户
     *
     * @param user
     * @return
     */
    @RequestMapping("/info")
    @ResponseBody
    public RespBean info(User user) {
        return RespBean.success(user);
    }
}

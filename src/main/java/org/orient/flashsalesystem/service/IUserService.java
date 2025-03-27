package org.orient.flashsalesystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.orient.flashsalesystem.pojo.User;
import org.orient.flashsalesystem.vo.LoginVo;
import org.orient.flashsalesystem.vo.RespBean;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author orient
 * @since 2025-03-24
 */
public interface IUserService extends IService<User> {

    /**
     * 登录校验
     * @param loginVo
     * @return
     */
    RespBean doLogin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response);

    /**
     * 通过cookie获得user
     */
    User getUserByCookie(String userTicket, HttpServletRequest request, HttpServletResponse response);
    /**
     * 用户修改密码
     */
    RespBean updatePassword(String userTicket, String newPassword,  HttpServletRequest request, HttpServletResponse response);
}

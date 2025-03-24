package org.orient.flashsalesystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
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
    RespBean doLogin(LoginVo loginVo);
}

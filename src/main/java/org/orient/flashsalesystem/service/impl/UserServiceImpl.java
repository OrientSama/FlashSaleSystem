package org.orient.flashsalesystem.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.val;
import org.orient.flashsalesystem.pojo.User;
import org.orient.flashsalesystem.mapper.UserMapper;
import org.orient.flashsalesystem.service.IUserService;
import org.orient.flashsalesystem.utils.MD5Util;
import org.orient.flashsalesystem.utils.ValidatorUtil;
import org.orient.flashsalesystem.vo.LoginVo;
import org.orient.flashsalesystem.vo.RespBean;
import org.orient.flashsalesystem.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author orient
 * @since 2025-03-24
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;
    /**
     * 登录
     * @param loginVo
     * @return
     */
    @Override
    public RespBean doLogin(LoginVo loginVo) {
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            return RespBean.error(RespBeanEnum.LOGIN_ERROR);
        }
        if (!ValidatorUtil.isMobile(mobile)) {
            return RespBean.error(RespBeanEnum.MOBILE_ERROR);
        }
        val user = userMapper.selectById(mobile);
        if(null == user){
            return RespBean.error(RespBeanEnum.LOGIN_ERROR);
        }
        // 判断密码是否正确
        if (!MD5Util.formPassToDBPass(password, user.getSalt()).equals(user.getPassword())) {
            return RespBean.error(RespBeanEnum.LOGIN_ERROR);
        }
        return RespBean.success();
    }
}

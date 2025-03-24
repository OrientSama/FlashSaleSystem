package org.orient.flashsalesystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.orient.flashsalesystem.pojo.User;
import org.orient.flashsalesystem.mapper.UserMapper;
import org.orient.flashsalesystem.service.IUserService;
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

}

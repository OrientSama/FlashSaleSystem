package org.orient.flashsalesystem.service.impl;

import org.orient.flashsalesystem.pojo.Order;
import org.orient.flashsalesystem.mapper.OrderMapper;
import org.orient.flashsalesystem.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author orient
 * @since 2025-03-25
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

}

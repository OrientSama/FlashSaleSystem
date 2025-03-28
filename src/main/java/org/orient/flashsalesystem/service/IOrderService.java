package org.orient.flashsalesystem.service;

import org.orient.flashsalesystem.pojo.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import org.orient.flashsalesystem.pojo.User;
import org.orient.flashsalesystem.vo.GoodsVo;
import org.orient.flashsalesystem.vo.OrderVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author orient
 * @since 2025-03-25
 */
public interface IOrderService extends IService<Order> {

    /**
     * 秒杀
     * @param user 用户
     * @param goodsVo 商品
     * @return
     */
    Order seckill(User user, GoodsVo goodsVo);

    /**
     * 订单详情
     * @param orderId 订单Id
     * @return
     */
    OrderVo detail(Long orderId);
}

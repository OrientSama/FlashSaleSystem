package org.orient.flashsalesystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import jakarta.annotation.Resource;
import org.orient.flashsalesystem.pojo.FlashGoods;
import org.orient.flashsalesystem.pojo.FlashOrder;
import org.orient.flashsalesystem.pojo.Order;
import org.orient.flashsalesystem.mapper.OrderMapper;
import org.orient.flashsalesystem.pojo.User;
import org.orient.flashsalesystem.service.IFlashGoodsService;
import org.orient.flashsalesystem.service.IFlashOrderService;
import org.orient.flashsalesystem.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.orient.flashsalesystem.vo.GoodsVo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

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
    @Resource
    private IFlashGoodsService flashGoodsService;
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private IFlashOrderService flashOrderService;

    @Override
    public Order seckill(User user, GoodsVo goodsVo) {
        // 获取 秒杀商品表里的实际库存 并减一
        FlashGoods flashGoods = flashGoodsService.getOne(new QueryWrapper<FlashGoods>().eq("goods_id", goodsVo.getId()));
        flashGoods.setStockCount(flashGoods.getStockCount() - 1);
        flashGoodsService.updateById(flashGoods);
        // 生成订单
        Order order = new Order();
        order.setUserId(user.getId());
        order.setGoodsId(goodsVo.getId());
        order.setDeliveryAddrId(0L);
        order.setGoodsName(goodsVo.getGoodsName());
        order.setGoodsPrice(goodsVo.getSeckillPrice());
        order.setGoodsCount(1);
        order.setStatus((byte)0);
        order.setCreateDate(new Date());
        order.setOrderChannel((byte)1);
        orderMapper.insert(order);
        // 生成秒杀订单
        FlashOrder flashOrder = new FlashOrder();
        flashOrder.setOrderId(order.getId());
        flashOrder.setGoodsId(goodsVo.getId());
        flashOrder.setUserId(user.getId());
        flashOrderService.save(flashOrder);
        return order;
    }
}

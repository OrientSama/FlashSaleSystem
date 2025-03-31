package org.orient.flashsalesystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.orient.flashsalesystem.mapper.FlashOrderMapper;
import org.orient.flashsalesystem.pojo.FlashOrder;
import org.orient.flashsalesystem.pojo.User;
import org.orient.flashsalesystem.service.IFlashOrderService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author orient
 * @since 2025-03-25
 */
@Service
public class FlashOrderServiceImpl extends ServiceImpl<FlashOrderMapper, FlashOrder> implements IFlashOrderService {
    @Resource
    private FlashOrderMapper flashOrderMapper;
    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public Long getResult(User user, Long goodsId) {
        FlashOrder flashOrder = flashOrderMapper.selectOne(new QueryWrapper<FlashOrder>().eq("user_id", user.getId())
                .eq("goods_id", goodsId));
        if (flashOrder != null) {
            return flashOrder.getOrderId();
        } else if (redisTemplate.hasKey("isStockEmpty:" + goodsId)) {
            return -1L;
        } else {
            return 0L;
        }
    }
}

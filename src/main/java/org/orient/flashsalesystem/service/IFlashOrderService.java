package org.orient.flashsalesystem.service;

import org.orient.flashsalesystem.pojo.FlashOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import org.orient.flashsalesystem.pojo.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author orient
 * @since 2025-03-25
 */
public interface IFlashOrderService extends IService<FlashOrder> {

    /**
     * 获取秒杀结果
     * @param user
     * @param goodsId
     * @return
     */
    Long getResult(User user, Long goodsId);
}

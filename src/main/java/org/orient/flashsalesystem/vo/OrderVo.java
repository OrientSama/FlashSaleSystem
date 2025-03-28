package org.orient.flashsalesystem.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.orient.flashsalesystem.pojo.Order;

/**
 * 订单详情返回对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderVo {
    private Order order;
    private GoodsVo goodsVo;
}

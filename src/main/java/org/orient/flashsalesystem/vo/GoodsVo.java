package org.orient.flashsalesystem.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.orient.flashsalesystem.pojo.Goods;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品返回对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsVo extends Goods {
    private BigDecimal flashPrice;
    private Integer stockCount;
    private Date startDate;
    private Date endDate;
}

package org.orient.flashsalesystem.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
/**
 * <p>
 * 
 * </p>
 *
 * @author orient
 * @since 2025-03-25
 */
@Getter
@Setter
@ToString
@TableName("t_flash_goods")
@ApiModel(value = "FlashGoods对象", description = "")
public class FlashGoods implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 秒杀主键ID
     */
    @ApiModelProperty("秒杀主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 商品ID
     */
    @ApiModelProperty("商品ID")
    private Long goodsId;

    /**
     * 秒杀价
     */
    @ApiModelProperty("秒杀价")
    private BigDecimal seckillPrice;

    /**
     * 库存数量
     */
    @ApiModelProperty("库存数量")
    private Integer stockCount;

    /**
     * 秒杀开始时间
     */
    @ApiModelProperty("秒杀开始时间")
    private LocalDateTime startDate;

    /**
     * 秒杀结束时间
     */
    @ApiModelProperty("秒杀结束时间")
    private LocalDateTime endDate;
}

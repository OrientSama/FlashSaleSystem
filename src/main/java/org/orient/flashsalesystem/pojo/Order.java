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
import java.util.Date;

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
@TableName("t_order")
@ApiModel(value = "Order对象", description = "")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单ID
     */
    @ApiModelProperty("订单ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    @ApiModelProperty("用户ID")
    private Long userId;

    /**
     * 商品ID
     */
    @ApiModelProperty("商品ID")
    private Long goodsId;

    /**
     * 收货地址ID
     */
    @ApiModelProperty("收货地址ID")
    private Long deliveryAddrId;

    /**
     * 商品名称
     */
    @ApiModelProperty("商品名称")
    private String goodsName;

    /**
     * 商品数量
     */
    @ApiModelProperty("商品数量")
    private Integer goodsCount;

    /**
     * 商品价格
     */
    @ApiModelProperty("商品价格")
    private BigDecimal goodsPrice;

    /**
     * 1pc, 2android,3ios
     */
    @ApiModelProperty("1pc, 2android,3ios")
    private Byte orderChannel;

    /**
     * 订单状态, 0未支付, 1已支付, 2已发货, 3已收货, 4已退款, 5已完成
     */
    @ApiModelProperty("订单状态, 0未支付, 1已支付, 2已发货, 3已收货, 4已退款, 5已完成")
    private Byte status;

    /**
     * 订单创建时间
     */
    @ApiModelProperty("订单创建时间")
    private Date createDate;

    /**
     * 支付时间
     */
    @ApiModelProperty("支付时间")
    private Date payDate;
}

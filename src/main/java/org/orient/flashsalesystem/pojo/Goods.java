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
@TableName("t_goods")
@ApiModel(value = "Goods对象", description = "")
public class Goods implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品ID
     */
    @ApiModelProperty("商品ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 商品名称
     */
    @ApiModelProperty("商品名称")
    private String goodsName;

    /**
     * 商品标题
     */
    @ApiModelProperty("商品标题")
    private String goodsTitle;

    /**
     * 商品图片
     */
    @ApiModelProperty("商品图片")
    private String goodsImg;

    /**
     * 商品详情
     */
    @ApiModelProperty("商品详情")
    private String goodsDetail;

    /**
     * 商品价格
     */
    @ApiModelProperty("商品价格")
    private BigDecimal goodsPrice;

    /**
     * 商品库存, -1代表无限制
     */
    @ApiModelProperty("商品库存, -1代表无限制")
    private Integer goodsStock;
}

package org.orient.flashsalesystem.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.orient.flashsalesystem.pojo.User;

/**
 * 详情返回对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailVo {
    private User User;
    private GoodsVo goodsVo;
    private int flashStatus;
    private int remainSeconds;
}

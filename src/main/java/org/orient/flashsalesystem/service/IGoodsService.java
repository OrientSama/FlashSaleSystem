package org.orient.flashsalesystem.service;

import org.orient.flashsalesystem.pojo.Goods;
import com.baomidou.mybatisplus.extension.service.IService;
import org.orient.flashsalesystem.vo.GoodsVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author orient
 * @since 2025-03-25
 */
public interface IGoodsService extends IService<Goods> {

    /**
     * 获取商品列表
     * @return 商品列表
     */
    List<GoodsVo> findGoodsVo();

    /**
     * 根据goodsId获取商品详情
     * @param goodsId
     * @return
     */
    GoodsVo findGoodsVoByGoodsId(Long goodsId);
}

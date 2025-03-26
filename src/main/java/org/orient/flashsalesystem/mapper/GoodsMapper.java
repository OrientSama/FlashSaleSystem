package org.orient.flashsalesystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.orient.flashsalesystem.pojo.Goods;
import org.orient.flashsalesystem.vo.GoodsVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author orient
 * @since 2025-03-25
 */
public interface GoodsMapper extends BaseMapper<Goods> {
    /**
     * 获取商品列表
     * @return 商品列表
     */
    List<GoodsVo> findGoodsVo();
}


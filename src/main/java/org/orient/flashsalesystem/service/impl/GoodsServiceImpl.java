package org.orient.flashsalesystem.service.impl;

import jakarta.annotation.Resource;
import org.orient.flashsalesystem.pojo.Goods;
import org.orient.flashsalesystem.mapper.GoodsMapper;
import org.orient.flashsalesystem.service.IGoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.orient.flashsalesystem.vo.GoodsVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author orient
 * @since 2025-03-25
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {

    @Resource
    private GoodsMapper goodsMapper;

    @Override
    public List<GoodsVo> findGoodsVo() {
        return goodsMapper.findGoodsVo();
    }
}

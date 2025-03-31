package org.orient.flashsalesystem.rabbitmq;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.orient.flashsalesystem.pojo.FlashOrder;
import org.orient.flashsalesystem.pojo.Order;
import org.orient.flashsalesystem.pojo.SeckillMessage;
import org.orient.flashsalesystem.pojo.User;
import org.orient.flashsalesystem.service.IGoodsService;
import org.orient.flashsalesystem.service.IOrderService;
import org.orient.flashsalesystem.utils.JsonUtil;
import org.orient.flashsalesystem.vo.GoodsVo;
import org.orient.flashsalesystem.vo.RespBean;
import org.orient.flashsalesystem.vo.RespBeanEnum;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MQRecevier {
    @Resource
    private IGoodsService goodsService;
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private IOrderService orderService;

    /**
     * 下单操作, 将redis中预先减掉的 库存, 实际在sql中减去
     * @param msg
     */
    @RabbitListener(queues = "SecKillQueue")
    public void receiveSecKill(String msg) {
        log.info("接收到消息:" + msg);
        SeckillMessage message = JsonUtil.jsonStr2Object(msg, SeckillMessage.class);
        Long goodsId = message.getGoodsId();
        User user = message.getUser();
        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(goodsId);
        if(goodsVo.getStockCount()<=0){
            return;
        }
        // 判断是否重复抢购
        FlashOrder flashOrder = (FlashOrder) redisTemplate.opsForValue().get("order:" + user.getId() + ":" + goodsId);
        if (flashOrder != null) {
            return;
        }
        // 下单
        orderService.seckill(user, goodsVo);

    }
}

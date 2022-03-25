package org.example.rabbitmq.consumer.delay;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * desc: 订单延迟消息监听
 *
 * @author create 2022/3/8 by rao
 */
@Slf4j
@Component
public class OrderDelayMsgListener {

    /**
     * 基于死信队列实现延迟消息消费， 本质思想是  消息设置过期时间，达到过期时间后流转到 死信队列
     *
     *    --->  默认的消息生效？
     * @param msg
     */
    @RabbitListener(queuesToDeclare = @Queue("dlxMsgQueue"))
    public void handleOrderDelayMsg(String msg){
        log.info("收到消息：{}",msg);
    }

}

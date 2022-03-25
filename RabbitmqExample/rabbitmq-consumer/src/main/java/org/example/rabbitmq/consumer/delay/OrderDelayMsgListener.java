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

    @RabbitListener(queuesToDeclare = @Queue("dlxMsgQueue"))
    public void handleOrderDelayMsg(String msg){
        log.info("收到消息：{}",msg);
    }

}

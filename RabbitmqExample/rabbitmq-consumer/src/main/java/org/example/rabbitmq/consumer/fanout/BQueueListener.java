package org.example.rabbitmq.consumer.fanout;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * desc:
 *
 * @author create 2022/3/7 by rao
 */
@Profile("dev")
@Slf4j
@Component
public class BQueueListener {

    /**
     * 处理bQueue队列消息
     * @param msg
     */
    @RabbitListener(queues = "bQueue")
    public void handleBQueueMsg(String msg){
        log.info("bQueue msg:{}",msg);
    }

}

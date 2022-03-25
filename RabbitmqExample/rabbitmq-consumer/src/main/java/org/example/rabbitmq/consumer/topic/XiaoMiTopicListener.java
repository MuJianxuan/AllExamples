package org.example.rabbitmq.consumer.topic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * desc:
 *
 * @author create 2022/3/7 by rao
 */
@Slf4j
@Profile("dev")
@Component
public class XiaoMiTopicListener {

    @RabbitListener(queuesToDeclare = @Queue("xiaomi"))
    public void handleXiaoMiMsg(String msg){
        log.info("xiaomi msg:{}",msg);
    }

}

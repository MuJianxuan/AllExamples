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
@Profile("test")
@Component
public class HuaWeiTopicListener {

    @RabbitListener(queuesToDeclare = @Queue("huawei"))
    public void handleHuaWeiMsg(String msg){
        log.info("huawei msg:{}",msg);
    }

}

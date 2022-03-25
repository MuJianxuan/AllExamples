package org.example.rabbitmq.consumer.headers;

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
@Profile("test")
@Slf4j
@Component
public class HuaweiHeaderListener {

    @RabbitListener(queuesToDeclare = @Queue("huaweiHeader"))
    public void xiaomiMsg(byte[] msgArr){
        log.info("HuaweiHeaderListener msg:{}",new String(msgArr) );
    }

}

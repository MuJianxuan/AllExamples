package org.example.rabbitmq.consumer.ack;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * desc: 手动Ack
 *
 * @author create 2022/3/8 by rao
 */
@Profile("ack")
@Slf4j
@Component
public class AckListener {

    /**
     * 1、如果消息正常消费成功，则执行 basicAck 完成确认。
     * 如果消息消费失败，则执行 basicNack 方法，告诉 RabbitMQ 消息消费失败。
     *
     * 2、消息ID，幂等性人如何保证
     *
     * @param channel
     * @param message
     */
    @RabbitListener(queuesToDeclare = @Queue("ackQueue"))
    public void ackMessage(Channel channel, Message message){
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            //消息消费的代码写到这里
            String msg = new String(message.getBody());
            log.info("msg:{}",msg );
            // 消费完成后，手动 ack   如果我不ack 会与重试有关吗 ? 不会负载到这个服务了，控制台显示消息未确认 当重启服务的时候还是会重新消费
            // 怎么样保证幂等呢？
//            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            //手动 nack
            try {
                channel.basicNack(deliveryTag, false, true);
            } catch (IOException ex) {
                log.error("[消息拒绝] error!",ex);
            }
        }
    }

}

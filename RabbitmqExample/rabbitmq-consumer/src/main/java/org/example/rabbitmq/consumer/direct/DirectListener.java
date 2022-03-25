package org.example.rabbitmq.consumer.direct;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * desc: @RabbitListener 注解来标记一个消息消费方法，
 * 默认情况下，消息消费方法自带事务，即如果该方法在执行过程中抛出异常，
 * 那么被消费的消息会重新回到队列中等待下一次被消费，
 * 如果该方法正常执行完没有抛出异常，则这条消息就算是被消费了。
 *
 * create 2022/3/6 by rao
 */
@Slf4j
@Component
public class DirectListener {

    /**
     * rabbitmq 启动不会先注册新的 队列，会导致这个启动不起来。 wdf
     *   情况1: 生产者发送对象 user ，接收者只用string
     *
     * @param msg
     * @param channel
     */
    @RabbitListener(queues = "defaultExchangeQueue")
    public void consumerMsg(String msg, Channel channel){
        log.info("msg:{}",msg);
    }

    /**
     * 测试不存在队列自动创建 (已验证，会自动创建队列)
     * 参考: https://blog.csdn.net/wnn654321/article/details/122383000
     * @param msg
     * @param channel
     */
    @RabbitListener(queuesToDeclare = {@Queue("test.autoQueue")})
    public void testAutoCreateNotExistQueue(String msg,Channel channel){
        log.info("msg，生产端没有创建队列，消费端不存在的队列会不会自动创建");
    }

    /**
     * 消费自定义的队列
     * @param msg
     * @param channel
     */
    @RabbitListener(queues = "directQueue")
    public void consumerDirectQueue(String msg,Channel channel){
        log.info("msg:{}",msg);
    }

}

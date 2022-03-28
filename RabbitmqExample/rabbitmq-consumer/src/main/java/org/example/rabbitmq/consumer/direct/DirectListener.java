package org.example.rabbitmq.consumer.direct;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.concurrent.atomic.AtomicInteger;

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

    private final AtomicInteger atomic  = new AtomicInteger(0);

    /**
     * rabbitmq 启动不会先注册新的 队列，会导致这个启动不起来。 wdf
     *   情况1: 生产者发送对象 user ，接收者只用string  会抛出异常：  ClassNotFoundException: org.example.rabbitmq.producer.entity.User  且无重试，会认为是消费成功。
     *   情况2：消费抛异常  --> 无限重试？
     *       listener:
     *       simple:
     *         acknowledge-mode: auto  # 自动ack
     *         retry:
     *           enabled: true
     *           # 重试次数
     *           max-attempts: 2
     *           max-interval: 10000   # 重试最大间隔时间
     *           initial-interval: 2000  # 重试初始间隔时间
     *           multiplier: 2 # 间隔时间乘子，间隔时间*乘子=下一次的间隔时间，最大不能超过设置的最大间隔时间
     *   设置了之后，重试消费变慢了！！！ 重试之后，消息会被抛弃。  说明这里走的是 Reject
     *   情况3：设置死信队列后  重试后被抛弃是否会走到死信队列 确实会走到死信队列
     *
     * @param msg
     * @param channel
     */
    @RabbitListener(queues = "defaultExchangeQueue")
    public void consumerMsg(String msg, Channel channel){

        int andGet = atomic.addAndGet(1);
        if( andGet == 100){
            log.info("msg:{}",msg);
            return;
        }

        throw new RuntimeException("11111");

//        log.info("msg:{}",msg);
    }


    /**
     *  处理这个队列的消息
     * @param msg
     */
    @RabbitListener(queues = "defaultExchangeDlQueue")
    public void handleDefaultExchangeDlQueue(String msg){
        log.info("收到 defaultExchangeQueue队列的死信消息：{}",msg);
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
    public void consumerDirectQueue(String msg, Channel channel, Message message){
        log.info("msg:{}",msg);

        log.info("message: {}",new String( message.getBody(), Charset.defaultCharset() ));

    }

}

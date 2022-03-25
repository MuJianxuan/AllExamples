package org.example.rabbitmq.producer;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * desc: 死信队列 基于DLX
 * 消息变成死信消息有如下几种情况：
 * 1、消息被拒绝(Basic.Reject/Basic.Nack) ，井且设置requeue 参数为false
 * 2、消息过期 message对象
 * 3、队列达到最大长度
 * DLX 本质上也是一个普普通通的交换机，我们可以为任意队列指定 DLX，当该队列中存在死信时，
 * RabbitMQ 就会自动的将这个死信发布到 DLX 上去，进而被路由到另一个绑定了 DLX 的队列上（即死信队列）
 *
 * @author create 2022/3/7 by rao
 */
@Configuration
public class DelayQueueConfig {

    /**
     * 死信交换机
     * @return
     */
    @Bean
    public DirectExchange dlxExchange(){
        return new DirectExchange("dlxExchange");
    }

    /**
     * 死信消息队列
     * @return
     */
    @Bean
    public Queue dlxMsgQueue(){
        return new Queue("dlxMsgQueue");
    }

    /**
     * 绑定交换机和死信消息队列
     * @return
     */
    @Bean
    public Binding dlxBinding(){
        return BindingBuilder.bind(dlxMsgQueue()).to(dlxExchange()).withQueueName();
    }

    /**
     *  需要注意的是  死信队列绑定的对象是 队列！
     * @return
     */
    @Bean
    public Queue orderQueue(){

        Map<String, Object> args = new HashMap<>(2);
        //设置消息过期时间  一开始设置成 10， 再修改成 1000就出问题了 因此可能需要使用 message来实现  ,这个值优先
        // 需要留意的是 这个值一旦设定，这边不可以再修改,否则启动会报错，控制台未找到可修改的操作
        args.put("x-message-ttl", 100000);
        //设置死信交换机
        args.put("x-dead-letter-exchange", "dlxExchange");
        //设置死信 routing_key
        args.put("x-dead-letter-routing-key", "dlxMsgQueue");
        return new Queue("orderQueue", true, false, false, args);
    }


}

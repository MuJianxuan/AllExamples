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
 * desc: rabbitmq 中有默认的交换机，各模式都有默认的交换机
 * create 2022/3/6 by rao
 */
@Configuration
public class DirectConfig  {

    /**
     * 添加死信队列 场景
     */
    @Bean
    public Queue defaultExchangeDlQueue(){
        return new Queue("defaultExchangeDlQueue");
    }

    @Bean
    public DirectExchange defaultExchangeDlQueueExchange(){
        return new DirectExchange("dlQueueExchange");
    }

    @Bean
    public Binding defaultExchangeDlQueueExchangeBinding(){
        return BindingBuilder.bind( defaultExchangeDlQueue() ).to( defaultExchangeDlQueueExchange() ).withQueueName();
    }

    /**
     * 使用默认的直连交换机队列
     *   先创建队列 再给队列添加 死信队列 启动会抛异常...
     *   1、在控制台直接删除 队列
     *   2、自动ACK
     * @return
     */
    @Bean
    public Queue defaultExchangeQueue() {
        // 默认是持久化的
        Map<String, Object> args = new HashMap<>(2);
        //设置消息过期时间  一开始设置成 10， 再修改成 1000就出问题了 因此可能需要使用 message来实现  ,这个值优先
        // 需要留意的是 这个值一旦设定，这边不可以再修改,否则启动会报错，控制台未找到可修改的操作
        args.put("x-message-ttl", 10000);
        //设置死信交换机
        args.put("x-dead-letter-exchange", "dlQueueExchange");
        //设置死信 routing_key
        args.put("x-dead-letter-routing-key", "defaultExchangeDlQueue");
        return new Queue("defaultExchangeQueue",true,false,false,args);
    }





    /**
     * 过期消息队列
     * @return
     */
    @Bean
    public Queue expireMsgQueue(){
        return new Queue("expireMsgQueue");
    }




    // 使用自定义的

    /**
     * 创建直连队列
     * @return
     */
    @Bean
    public Queue directQueue(){
        return new Queue("directQueue");
    }

    /**
     * 创建直连交换机
     * @return
     */
    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange("directExchange");
    }

    /**
     * 绑定交换机和队列
     * @return
     */
    @Bean
    public Binding directBinding(){
        return BindingBuilder.bind(directQueue()).to(directExchange()).withQueueName();
    }


}

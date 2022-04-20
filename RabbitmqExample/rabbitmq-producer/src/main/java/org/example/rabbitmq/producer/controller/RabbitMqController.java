package org.example.rabbitmq.producer.controller;

import org.example.rabbitmq.producer.entity.User;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

/**
 * desc: rabbitmq controller
 * create 2022/3/6 by rao
 */
@RestController
@RequestMapping("rabbitmq")
public class RabbitMqController {

    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     * 发送消息到队列中
     * @return
     */
    @PostMapping("sendUserToDefaultExchangeQueue")
    public String  sendMsgToDefaultExchangeQueue() {
        //这样发送是错误的， 因为接收端使用的是 msg对象
        amqpTemplate.convertAndSend("defaultExchangeQueue",new User("Rao"));
        return "ok";
    }

    @PostMapping("sendMsgToDefaultExchangeQueue")
    public String  sendMsgToDefaultExchangeQueue(String msg) {
        amqpTemplate.convertAndSend("defaultExchangeQueue",msg);
        return "ok";
    }


    @PostMapping("sendMsgToDirectExchangeQueue")
    public String  sendMsgToDirectExchangeQueue(String msg) {
        // 直接往队列里发
        amqpTemplate.convertAndSend("directQueue",msg);
        return "ok";
    }

    @PostMapping("sendMsgToFanoutExchangeQueue")
    public String sendMsgToFanoutExchangeQueue(String msg){
        // 往交换机发送消息，交换机会依据消息初始化
        amqpTemplate.convertAndSend("fanoutExchange",null,msg);
        return "ok";
    }

    @PostMapping("sendMsgToTopicExchangeQueue")
    public String sendMsgToTopicExchangeQueue(String msg){
        // 往交换机发
        amqpTemplate.convertAndSend("topicExchange",msg,msg);
        return "ok";
    }

    @PostMapping("sendMsgToHeadersExchangeQueue")
    public String sendMsgToHeadersExchangeQueue(String key,String value){
        Message message = MessageBuilder
                .withBody( value.getBytes(StandardCharsets.UTF_8))
                .setHeader(key, value)
                .build();
        // 往交换机发消息
        amqpTemplate.convertAndSend("headersExchange",null,message);
        return "ok";
    }

    /**
     * 会过期的消息
     * @param msg
     * @return
     */
    @PostMapping("sendMsgToDefaultExchangeQueueWillExpire")
    public String  sendMsgToDefaultExchangeQueueWillExpire(String msg) {
        Message message = MessageBuilder.withBody(msg.getBytes(StandardCharsets.UTF_8))
                // 10s
                .setExpiration("10000").build();
        // 别消费，去观察 控制台这个消息会消失
        amqpTemplate.convertAndSend("expireMsgQueue", message);
        return "ok";
    }

    /**
     * 发送延迟消息
     * @param msg
     * @return
     */
    @PostMapping("sendDelayMsgToDelayExchangeQueue")
    public String sendDelayMsgToDelayExchangeQueue(String msg){
        // 往订单队列里发消息
        //amqpTemplate.convertAndSend("orderQueue", msg);

        Message message = MessageBuilder.withBody(msg.getBytes(StandardCharsets.UTF_8)).setExpiration("10000").build();
        amqpTemplate.convertAndSend( "orderQueue", message);

        return "ok";
    }

    /**
     * 发送消息到 ack队列
     * @param msg
     * @return
     */
    @PostMapping("sendMsgToAckQueue")
    public String sendMsgToAckQueue(String msg) {
        amqpTemplate.convertAndSend("ackQueue",msg);
        return "ok";
    }


    /**
     * 发送消息到 user队列
     * @return
     */
    @PostMapping("sendUserMsgToAckQueue")
    public String sendUserMsgToAckQueue() {
        amqpTemplate.convertAndSend("userQueue",new User("rao"));
//        amqpTemplate.convertAndSend("userQueue",new User());
        return "ok";
    }

}

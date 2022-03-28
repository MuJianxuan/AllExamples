package org.example.rabbitmq.consumer.user;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.example.rabbitmq.consumer.entity.User;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * desc: 用户消息监听
 *
 * @author Rao
 * @Date 2022/03/28
 **/
@Slf4j
@Component
public class UserListener {

    /**
     * 1、测试对象序列换，包路径不同是否正常   Caused by: java.lang.ClassNotFoundException: org.example.rabbitmq.producer.entity.User  序列化有问题  --> 需要自定义序列化
     * 2、
     */
    @RabbitListener(queuesToDeclare = {@Queue("userQueue")})
    public void userMsgListener(User user){
        log.info("user:{}", JSON.toJSONString( user ) );
    }

}

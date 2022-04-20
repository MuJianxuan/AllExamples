package org.example.rabbitmq.producer;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * desc:
 * {@link org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration } 直接集成的...
 * {@link SimpleMessageConverter#createMessage(java.lang.Object, org.springframework.amqp.core.MessageProperties)} 使用的是 jdk序列化
 *
 * @author create 2022/3/7 by rao
 */
@Slf4j
@Configuration
public class RabbitMqConfig implements RabbitTemplate.ReturnCallback, RabbitTemplate.ConfirmCallback, InitializingBean {

    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * 需要添加初始化 RabbitAdmin#initialize 会注册新写的队列在rabbitmq中创建。
     * @param defaultConnectionFactory
     * @return
     */
    @Bean(initMethod = "initialize")
    public RabbitAdmin rabbitAdmin(ConnectionFactory defaultConnectionFactory){
        return new RabbitAdmin(defaultConnectionFactory);
    }



    /**
     *
     * @param correlationData 当ack为true时，这个值为null
     * @param ack
     * @param cause 当ack为true时，这个值也为null
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        // ack 失败!
        if( !ack){
            log.error("[消息确认] 确认失败， 严重，correlationData:{},cause:{}", correlationData, cause );
        }
        else {

            // 消息序列化失败  此时我使用的是 自动ack！ 也会 ack 是成功 ，是否 开启手动ack可以避免这种情况？
            log.info("[消息确认] 确认成功,correlationData:{}",correlationData);
        }
    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        // 执行了此回调表示消息未路由到 实际队列中
        log.error("[消息路由失败] 严重，message:{},replyCode:{},,replyText:{},exchange:{},routingKey:{}",message,replyCode,replyText,exchange,routingKey);
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        // 处理消息序列化  默认的不行
        Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
        rabbitTemplate.setMessageConverter( jackson2JsonMessageConverter );

        // 发送方消息确认
        rabbitTemplate.setConfirmCallback( this);
        //
        rabbitTemplate.setReturnCallback( this);


    }

    /**
     * Fastjson
     * @see {https://www.jianshu.com/p/a8403b1cb58e}
     */
//    @Bean
//    public RabbitTemplate fastjsonRabbitTemplate(ConnectionFactory connectionFactory){
//        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//        rabbitTemplate.setMessageConverter(new AbstractMessageConverter() {
//            @Override
//            protected Message createMessage(Object object, MessageProperties messageProperties) {
//                messageProperties.setContentType("application/json");
//                return new Message(JSON.toJSONBytes(object), messageProperties);
//            }
//
//            @Override
//            public Object fromMessage(Message message) throws MessageConversionException {
//                return JSON.parse(message.getBody());
//            }
//        });
//        return rabbitTemplate;
//    }

}

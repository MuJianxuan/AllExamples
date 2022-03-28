package org.example.rabbitmq.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

/**
 * desc: rabbitmq 配置
 *
 * @author Rao
 * @Date 2022/03/28
 **/
@Configuration
public class RabbitMqConfig implements RabbitListenerConfigurer {

    /**
     * 处理消费者的消息监听
     * @param registrar
     */
    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {

        DefaultMessageHandlerMethodFactory defaultMessageHandlerMethodFactory = new DefaultMessageHandlerMethodFactory();
        defaultMessageHandlerMethodFactory.setMessageConverter( new MappingJackson2MessageConverter());

        registrar.setMessageHandlerMethodFactory( defaultMessageHandlerMethodFactory );
    }



}

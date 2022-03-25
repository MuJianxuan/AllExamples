package org.example.rabbitmq.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * desc:
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
        if (ack) {
            // 当 发送到不存在的消息队列时，ack会为ture  但同时会执行  returnedMessage 回调
            log.info("消息成功到达交换器");
        }else{
            log.error("{}:消息发送失败", correlationData.getId());
        }
    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        // 执行了此回调表示消息未路由到 实际队列中
        log.error("{}:消息未成功路由到队列",message.getMessageProperties().getMessageId());
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        // 发送方消息确认
        rabbitTemplate.setConfirmCallback( this);
        //
        rabbitTemplate.setReturnCallback( this);
    }
}

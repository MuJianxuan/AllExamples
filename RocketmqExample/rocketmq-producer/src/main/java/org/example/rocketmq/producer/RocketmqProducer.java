package org.example.rocketmq.producer;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * desc:
 *
 * @author Rao
 * @Date 2022/04/20
 **/
@Slf4j
@SpringBootApplication
public class RocketmqProducer implements CommandLineRunner {

    @Autowired
    private RocketMQTemplate rocketMqTemplate;

    public static void main(String[] args) {
        SpringApplication.run( RocketmqProducer.class,args);
    }

    @Override
    public void run(String... args) throws Exception {
        //study();

        // 发送事务消息
        Message<String> message = MessageBuilder.withPayload("事务消息").build();
        TransactionSendResult transactionSendResult = rocketMqTemplate.sendMessageInTransaction("tran", message, null);
        log.info("transactionSendResult:{}", JSON.toJSONString(transactionSendResult));

    }

    /**
     * rocketmq事务消息会查监听器
     */
    @Slf4j
    @RocketMQTransactionListener
    public static class RocketMqTransactionMessageListener implements RocketMQLocalTransactionListener {

        @Override
        public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
            return RocketMQLocalTransactionState.COMMIT;
        }

        @Override
        public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
            return RocketMQLocalTransactionState.COMMIT;
        }
    }

    /**
     * 学习
     */
    private void study() {
        //send message synchronously 发送简单消息
        rocketMqTemplate.convertAndSend("test-topic-1", "Hello, World!");

        //send spring message 发送Spring消息
        rocketMqTemplate.send("test-topic-1", MessageBuilder.withPayload("Hello, World! I'm from spring message").build());

        //send messgae asynchronously 发送异步消息
        rocketMqTemplate.asyncSend("test-topic-2", new OrderPaidEvent("T_001", new BigDecimal("88.00")), new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.printf("async onSucess SendResult=%s %n", sendResult);
            }

            @Override
            public void onException(Throwable var1) {
                System.out.printf("async onException Throwable=%s %n", var1);
            }

        });

        //Send messages orderly
        rocketMqTemplate.syncSendOrderly("orderly_topic",MessageBuilder.withPayload("Hello, World").build(),"hashkey");

        rocketMqTemplate.destroy(); // notes:  once rocketMqTemplate be destroyed, you can not send any message again with this rocketMqTemplate
    }

    @Data
    @AllArgsConstructor
    public static class OrderPaidEvent implements Serializable {

        private String orderId;

        private BigDecimal paidMoney;
    }
}

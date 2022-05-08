package org.example.rocketmq.consumer.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * desc: RocketMq事务消息实践
 *
 * @author create 2022/5/8 by rao
 */
@Slf4j
@Component
@RocketMQMessageListener(topic = "tran",consumerGroup = "consumer1-tran-group")
public class TransactionMqListener implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        log.info("tran msg:{}",message);
    }
}

package org.example.rocketmq.consumer.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQListener;

/**
 * desc:
 *  默认的消息是什么？  group ，topic ， tag
 *
 * @author create 2022/5/1 by rao
 */
@Slf4j
//@Service
//@RocketMQMessageListener(consumerGroup = "", topic = "")
public class RaoTopicListener implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {

        log.info("message:{}",message);

    }
}

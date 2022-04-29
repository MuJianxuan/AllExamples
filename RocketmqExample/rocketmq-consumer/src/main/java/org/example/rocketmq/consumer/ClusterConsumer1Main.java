package org.example.rocketmq.consumer;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

/**
 * desc: 集群消费
 *
 * @author create 2022/4/26 by rao
 */
@Slf4j
public class ClusterConsumer1Main {
    public static void main(String[] args) throws Exception {
        DefaultMQPushConsumer defaultMqPushConsumer = new DefaultMQPushConsumer("demo-group1");
        defaultMqPushConsumer.setNamesrvAddr("http://117.50.174.141:9876");

        //这里设置的是一个consumer的消费策略  所以需要防重
        //CONSUME_FROM_LAST_OFFSET 默认策略，从该队列最尾开始消费，即跳过历史消息
        //CONSUME_FROM_FIRST_OFFSET 从队列最开始开始消费，即历史消息（还储存在broker的）全部消费一遍
        //CONSUME_FROM_TIMESTAMP 从某个时间点开始消费，和setConsumeTimestamp()配合使用，默认是半个小时以前
        defaultMqPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        // 集群消费
        defaultMqPushConsumer.setMessageModel( MessageModel.CLUSTERING);

        // 表示订阅的 Topic 和 tag * 表示All
        defaultMqPushConsumer.subscribe("test-main","tag1");
        //
        defaultMqPushConsumer.registerMessageListener((MessageListenerConcurrently) (list, consumeConcurrentlyContext) -> {

            log.info("Receive New Messages size：{}",list.size() );
            list.forEach(messageExt -> {
                String msg = new String(messageExt.getBody());
                log.info("msg:" + msg);
                log.info(JSON.toJSONString(msg));
            });

            // 返回消费状态
            // RECONSUME_LATER 返回失败则 需要稍后重新消费
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });

        try {
            defaultMqPushConsumer.start();
            log.info("MQPushConsumer has started!");

            System.in.read();
        } finally {
            defaultMqPushConsumer.shutdown();
        }

    }
}

package org.example.rocketmq.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * desc:
 *
 * @author create 2022/4/25 by rao
 */
@Slf4j
public class ProducerMain {
    public static void main(String[] args) {

        // 指定生产者组名称
        DefaultMQProducer producer = new DefaultMQProducer("demo-group");
        producer.setNamesrvAddr("http://117.50.174.141:9876");
        try {
            producer.start();


            //  普通无序消息
            Message message1 = new Message(// topic
                    "Rao", // tag
                    "TagA", // keys
                    "hello word!".getBytes());
            // 向broker发送消息============================> 同步发送
            SendResult sendResult = producer.send(message1);
            log.info("普通无序消息:{}",sendResult);

            // 有序消息 分为 全局有序和局部有序
            // 全局有序消息 本质思想是 Tpoic 对应 1个队列

            String[] tags = {"tagA","tagB","tagC"};
            for (int i = 0; i < 6; i++) {
                int orderId = i % 10;
                Message message = new Message("TopicOrderGlobalOrdered", tags[i % tags.length], ("订单信息："+i).getBytes(StandardCharsets.UTF_8));
                producer.send( message,new MessageQueueSelector(){
                    @Override
                    public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                        return mqs.get(0);
                    }
                },orderId );
            }

            // 局部有序，那就是不同的订单ID根据取余获取位置
            for (int i = 0; i < 6; i++) {
                int orderId = i % 10;
                Message message = new Message("TopicOrderGlobalOrdered", tags[i % tags.length], ("订单信息："+i).getBytes(StandardCharsets.UTF_8));
                producer.send( message,new MessageQueueSelector(){
                    @Override
                    public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                        Integer orderId = (Integer) arg;
                        // 这样的话，后续都是这个订单的消息都会只发送在这个队列中。
                        int index = orderId % mqs.size();
                        return mqs.get(index);
                    }
                },orderId );
            }

            // 延迟消息  固定的，直接设置等级即可。 topic是延迟的？
            Message delayMsg = new Message("OrderDelay", "TagD", "hello delay".getBytes(StandardCharsets.UTF_8));
            // 具体看延迟等级 延迟10s
            delayMsg.setDelayTimeLevel(3);
            SendResult delayMsgSendResult = producer.send(delayMsg);
            log.info("delayMsgSendResult:{}",delayMsgSendResult);


        } catch (Exception ex){
            log.error("我异常了",ex);
        } finally {
            producer.shutdown();
        }




    }
}

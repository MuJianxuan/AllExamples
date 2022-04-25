package org.example.rocketmq.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

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
            for (int i = 0; i < 5; i++) {
                Message message = new Message(// topic
                        "Rao", // tag
                        "TagA", // keys
                        "key" + i, ("Hello world RocketMQ Demo01" + i).getBytes());
                // 向broker发送消息============================> 同步发送
                SendResult sendResult = producer.send(message);
                System.out.printf("%s%n", sendResult);
            }

            // 有序消息 分为 全局有序和局部有序




        } catch (Exception ex){
            log.error("我异常了",ex);
        } finally {
            producer.shutdown();
        }




    }
}

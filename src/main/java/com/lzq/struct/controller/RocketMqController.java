package com.lzq.struct.controller;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author lzq
 */
@RestController
public class RocketMqController {

    @GetMapping("/createTopic")
    public String createTopic() throws MQClientException{

        //分组名可以任意设置
        DefaultMQProducer producer = new DefaultMQProducer("proc_group2");

        //设置nameserver的地址
        producer.setNamesrvAddr("10.20.2.70:9876");

        //启动生产者
        producer.start();

        /**
         * 创建topic，参数分别是：borker的名称，topic的名称，queue的数量
         * broker要和虚拟机broker.conf配置文件中brokername的名字一致
         * newTopic的名字随便起，queueNum8的意思是新建的消息队列数为8个
         */
        producer.createTopic("broker-a", "TopicTest-hello", 4);
        System.out.println("topic创建成功！");
        producer.shutdown();

        return "success";
    }


    @GetMapping("/producer")
    public String producer() throws Exception {

        // 实例化消息生产者Producer
        DefaultMQProducer producer = new DefaultMQProducer("producer_group");

        // 设置NameServer的地址
        producer.setNamesrvAddr("10.20.2.70:9876");

        // 启动Producer实例
        producer.start();
        for (int i = 0; i < 100; i++) {

            // 创建消息，并指定Topic，Tag和消息体

            String topic = "TopicTest";
            String tag = "TagA";
            byte[] messageBody = ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET);
            Message msg = new Message(topic, tag, messageBody);

            // 发送消息到一个Broker
            SendResult sendResult = producer.send(msg);
            // 通过sendResult返回消息是否成功送达
            System.out.printf("%s%n", sendResult);
        }
        // 如果不再发送消息，关闭Producer实例。
        producer.shutdown();

        return "success";
    }


    @GetMapping("/consumer")
    public String consumer() throws InterruptedException, MQClientException {

        // 实例化消费者
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("consumer_group");

        // 设置NameServer的地址
        consumer.setNamesrvAddr("10.20.2.70:9876");

        // 订阅一个或者多个Topic，以及Tag来过滤需要消费的消息
        consumer.subscribe("TopicTest", "*");

        // 注册回调实现类来处理从broker拉取回来的消息
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);
                // 标记该消息已经被成功消费
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        // 启动消费者实例
        consumer.start();

        System.out.printf("Consumer Started.%n");
        return "Consumer Started";
    }
}

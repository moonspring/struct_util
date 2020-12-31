package com.lzq.struct.util.rocketmq.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 *
 * 同步地发送方式使用的比较广泛，比如：重要的消息通知，短信通知
 * @author lzq
 */
public class SyncProducer {

	public static void main(String[] args) throws Exception {

    	// 实例化消息生产者Producer
        DefaultMQProducer producer = new DefaultMQProducer("proc_group");

        // 设置NameServer的地址
    	producer.setNamesrvAddr("10.20.2.70:9876");

    	// 启动Producer实例
        producer.start();
    	for (int i = 0; i < 100; i++) {

    	    // 创建消息，并指定Topic，Tag和消息体

			String topic = "TopicTest";
			String tag = "TagA";
			byte[] messageBody = ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET);
			Message msg = new Message(topic, tag,messageBody);

        	// 发送消息到一个Broker
            SendResult sendResult = producer.send(msg);
            // 通过sendResult返回消息是否成功送达
            System.out.printf("%s%n", sendResult);
    	}
    	// 如果不再发送消息，关闭Producer实例。
    	producer.shutdown();
    }
}
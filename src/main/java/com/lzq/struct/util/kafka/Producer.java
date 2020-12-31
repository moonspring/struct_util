//package com.lzq.struct.util.kafka;
//
//import org.apache.kafka.clients.producer.KafkaProducer;
//import org.apache.kafka.clients.producer.ProducerConfig;
//import org.apache.kafka.clients.producer.ProducerRecord;
//import org.apache.kafka.clients.producer.RecordMetadata;
//import org.apache.kafka.common.serialization.StringSerializer;
//
//import java.util.Properties;
//
///**
// * @author lzq
// */
//public class Producer {
//
//    public static void main(String[] _args) throws Exception {
//
//        Properties p = new Properties();
//
//        /** Broker的IP地址和端口 */
//        p.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.142.128:9092");
//
//        /** key序列化器 */
//        p.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
//
//        /** value序列化器 */
//        p.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
//
//        p.put(ProducerConfig.CLIENT_ID_CONFIG, "producer.client.id.demo");
//
//        KafkaProducer<String, String> producer = new KafkaProducer<>(p);
//
//        /** 发送100条消息 */
//        for(int i = 0; i < 100; i++) {
//
//            ProducerRecord<String, String> record = new ProducerRecord<>("topic-test", "message-" + i);
//            RecordMetadata meta = producer.send(record).get();
//            System.out.println(meta.topic() + " " + meta.partition() + " " + meta.offset());
//        }
//
//        producer.close();
//    }
//}
//
//package com.lzq.struct.util.kafka;
//
//import org.apache.kafka.clients.producer.KafkaProducer;
//import org.apache.kafka.clients.producer.ProducerConfig;
//import org.apache.kafka.clients.producer.ProducerRecord;
//import org.apache.kafka.clients.producer.RecordMetadata;
//import org.apache.kafka.common.serialization.StringSerializer;
//
//import java.util.Properties;
//
///**
// * @author lzq
// */
//public class Producer {
//
//    public static void main(String[] _args) throws Exception {
//
//        Properties p = new Properties();
//
//        /** Broker的IP地址和端口 */
//        p.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.142.128:9092");
//
//        /** key序列化器 */
//        p.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
//
//        /** value序列化器 */
//        p.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
//
//        p.put(ProducerConfig.CLIENT_ID_CONFIG, "producer.client.id.demo");
//
//        KafkaProducer<String, String> producer = new KafkaProducer<>(p);
//
//        /** 发送100条消息 */
//        for(int i = 0; i < 100; i++) {
//
//            ProducerRecord<String, String> record = new ProducerRecord<>("topic-test", "message-" + i);
//            RecordMetadata meta = producer.send(record).get();
//            System.out.println(meta.topic() + " " + meta.partition() + " " + meta.offset());
//        }
//
//        producer.close();
//    }
//}
//

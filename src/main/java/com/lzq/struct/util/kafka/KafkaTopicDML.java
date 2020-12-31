//package com.lzq.struct.util.kafka;
//
//import java.util.Arrays;
//import java.util.Properties;
//import java.util.Set;
//import java.util.concurrent.ExecutionException;
//
//public class KafkaTopicDML {
//
//   public static void main(String[] args) throws ExecutionException, InterruptedException {
//       //创建KafkaAdminClient
//       Properties props = new Properties();
//       //配置kafka的服务连接
//       props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG,
//               "你自己的kafka-ip:9092");
//
//       KafkaAdminClient adminClient = (KafkaAdminClient)KafkaAdminClient.create(props);
//
//       //创建topic
//       adminClient.createTopics(Arrays.asList(
//               new NewTopic("topic01",2,(short)1),//(名称，分区数，副本因子)
//               new NewTopic("topic02",2,(short)1),
//               new NewTopic("topic03",2,(short)1))
//       );
//
//       //删除topic
//       //adminClient.deleteTopics(Arrays.asList("topic01","topic02"));
//
//       //查看topic列表
//       ListTopicsResult topicsResult = adminClient.listTopics();
//       Set<String> names = topicsResult.names().get();
//       names.stream().forEach(name -> System.out.println(name));
//
//       //查看topic详细信息
//       /*DescribeTopicsResult topic = adminClient.describeTopics(Arrays.asList("topic01"));
//       Map<String, TopicDescription> map = topic.all().get();*/
//
//       //关闭AdminClient
//       adminClient.close();
//   }
//}
//

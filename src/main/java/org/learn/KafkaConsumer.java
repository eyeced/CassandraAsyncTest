package org.learn;

import akka.actor.ActorSystem;
import akka.kafka.ConsumerSettings;
import akka.kafka.scaladsl.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by abhiso on 7/5/16.
 */
public class KafkaConsumer {

//    public static void main(String[] args) {
//        final ActorSystem system = ActorSystem.create("example");
//        final ConsumerSettings<byte[], String> consumerSettings =
//                ConsumerSettings.create(system, new ByteArrayDeserializer(), new StringDeserializer(),
//                        new HashSet<String>(Arrays.asList("topic1")))
//                        .withBootstrapServers("localhost:9092")
//                        .withGroupId("group1")
//                        .withProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
//    }
}

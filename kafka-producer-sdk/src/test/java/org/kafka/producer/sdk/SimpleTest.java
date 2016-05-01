package org.kafka.producer.sdk;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class SimpleTest {
	public static void main(String [] args){
		Properties props = new Properties();
		 props.put("bootstrap.servers", "192.168.1.113:9092");
		 props.put("acks", "all");
		 props.put("retries", 0);
		 props.put("batch.size", 16384);
		 props.put("linger.ms", 1);
		 props.put("buffer.memory", 33554432);
		 props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		 props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

		 Producer<String, String> producer = new KafkaProducer(props);
		 for(int i = 0; i < 100; i++)
		     producer.send(new ProducerRecord("my-topic", Integer.toString(i), Integer.toString(i)));

		 producer.close();
	}
}

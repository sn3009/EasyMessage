package org.emsg.kafka_sdk.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.emsg.kafka_sdk.config.ConsumerInit;
import org.emsg.kafka_sdk.worker.ConsumerWorker;

import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

public class ConsumerEngine<T> {
	
	private final ConsumerConnector consumer;
	private final String topic;
	private final int threadNum;
	private  ExecutorService executor;
	
	public ConsumerEngine(String topic, int threadNum){
		ConsumerConfig config = new ConsumerConfig(ConsumerInit.CONSUMER_PROP);
		consumer = kafka.consumer.Consumer.createJavaConsumerConnector(config);
		this.topic = topic;
		this.threadNum = threadNum;
	}
	
	public void startDefault(){
		Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        topicCountMap.put(topic, threadNum);
        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);
        
        List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(topic);
        
        executor = Executors.newFixedThreadPool(threadNum);
        // now create an object to consume the messages
	    
        int threadNo = 0;
		for (final KafkaStream stream : streams) {
        	ConsumerWorker<T> worker = new ConsumerWorker<T>(stream, threadNo);
        	executor.submit(worker);
        	threadNo++;
        }
	}
}

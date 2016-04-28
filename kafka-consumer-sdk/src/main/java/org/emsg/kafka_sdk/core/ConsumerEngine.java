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
import kafka.serializer.Decoder;

public class ConsumerEngine<K, V> {
	
	private final ConsumerConnector consumer;
	private final String topic;
	private final int threadNum;
	private ExecutorService executor;
	
	public ConsumerEngine(String topic, int threadNum){
		ConsumerConfig config = new ConsumerConfig(ConsumerInit.CONSUMER_PROP);
		consumer = kafka.consumer.Consumer.createJavaConsumerConnector(config);
		this.topic = topic;
		this.threadNum = threadNum;
	}
	
	public void run(Decoder<K> keyDecoder, Decoder<V> valueDecoder){
		Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        topicCountMap.put(topic, threadNum);
        Map<String, List<KafkaStream<K, V>>> consumerMap = consumer.createMessageStreams(topicCountMap, keyDecoder, valueDecoder);
        
        List<KafkaStream<K, V>> streams = consumerMap.get(topic);
        
        executor = Executors.newFixedThreadPool(threadNum);
	    
        int threadNo = 0;
		for (final KafkaStream<K, V> stream : streams) {
        	ConsumerWorker<K, V> worker = new ConsumerWorker<K, V>(stream, threadNo);
        	executor.submit(worker);
        	threadNo++;
        }
	}
}

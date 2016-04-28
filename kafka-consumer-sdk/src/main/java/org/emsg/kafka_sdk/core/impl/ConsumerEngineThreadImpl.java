package org.emsg.kafka_sdk.core.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.common.TopicPartition;
import org.emsg.kafka_sdk.core.ConsumerEngineBootServer;
import org.emsg.kafka_sdk.handler.MessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsumerEngineThreadImpl<K, V> extends ConsumerEngineBootServer<K, V> {
	
	public ConsumerEngineThreadImpl(Consumer<K, V> consumer, MessageHandler<V> handler) {
		super(consumer, handler);
	}

	private final static Logger LOGGER = LoggerFactory.getLogger(ConsumerEngineBootStrapImpl.class);
	
	public void subscribeTopic(List<String> topicList){
	    consumer.subscribe(topicList);
	}
	
	/**
	 * Subsrcibe some partitions of a topic 
	 * @param topic topic name
	 * @param pNumArray the partition number eg:0 1 2 3
	 */
	public void subscribePartition(String topic, int ...pNumArray){
		List<TopicPartition> list = new ArrayList();
		for(int pNum : pNumArray){
			TopicPartition tpartition = new TopicPartition(topic, pNum);
			list.add(tpartition);
		}
		consumer.assign(list);
	}
}

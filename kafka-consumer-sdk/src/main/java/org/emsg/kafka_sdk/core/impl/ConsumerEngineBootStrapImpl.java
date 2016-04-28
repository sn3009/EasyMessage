package org.emsg.kafka_sdk.core.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.common.TopicPartition;
import org.emsg.kafka_sdk.core.ConsumerEngineBootServer;
import org.emsg.kafka_sdk.handler.MessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ConsumerEngine new SDK
 * <p>Title: ConsumerEngineForBootStrap</p>
 * <p>Description:
 * This consumer engine is developed by kafka0.9.0.1's new consumer api
 * it's different from the high-level api, it poll message from brokers
 * and you can control the offset by Automatic or Manual,
 * if you want to make the consistency, you should use this sdk. 
 * You can control the transaction by the message offset</p>
 * <p>Company:</p>
 * @author Shuttle
 * @date 2016年4月28日
 *
 */
public class ConsumerEngineBootStrapImpl<K, V> extends ConsumerEngineBootServer<K, V> {
	
	public ConsumerEngineBootStrapImpl(Consumer<K, V> consumer, MessageHandler<V> handler) {
		super(consumer, handler);
	}

	private final static Logger LOGGER = LoggerFactory.getLogger(ConsumerEngineBootStrapImpl.class);
	
	public void subscribeTopic(List<String> topicList){
	    consumer.subscribe(topicList);
	    pollMessages();
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
		pollMessages();
	}
	
}

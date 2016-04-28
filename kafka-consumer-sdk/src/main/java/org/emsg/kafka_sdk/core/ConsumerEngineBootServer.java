package org.emsg.kafka_sdk.core;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.errors.WakeupException;
import org.emsg.kafka_sdk.config.ConsumerInit;
import org.emsg.kafka_sdk.handler.MessageHandler;

public abstract class ConsumerEngineBootServer<K, V> {
	
	protected final Consumer<K, V> consumer;
	protected final MessageHandler<V> handler;
	
	public ConsumerEngineBootServer(Consumer<K, V> consumer, MessageHandler<V> handler){
		this.consumer = consumer;
		this.handler = handler;
	}
	
	/**
	 * Subscribe some topics
	 * @param topicList
	 */
	public abstract void subscribeTopic(List<String> topicList);
	
	/**
	 * Subsrcibe some partitions of a topic 
	 * @param topic topic name
	 * @param pNumArray the partition number eg:0 1 2 3
	 */
	public abstract void subscribePartition(String topic, int ...pNumArray);
	
	/**
	 * Start to poll message from broker
	 */
	protected synchronized void pollMessages(){
		while (true) {
	         ConsumerRecords<K, V> records = consumer.poll(ConsumerInit.POLL_TIMEOUT);
	         for (ConsumerRecord<K, V> record : records) {
	        	 handler.dealMessage(record.value());
	        	 consumer.commitSync();
	         }
	    }
	}
	
	/**
	 * Start to poll message from broker
	 */
	public synchronized void pollMessages(AtomicBoolean closed){
		try {
			while (!closed.get()) {
				ConsumerRecords<K, V> records = consumer.poll(ConsumerInit.POLL_TIMEOUT);
				for (ConsumerRecord<K, V> record : records) {
					handler.dealMessage(record.value());
					consumer.commitSync();
				}
			}
		} catch (WakeupException e) {
			if (!closed.get()){
				throw e;
			}
		} finally {
            consumer.close();
        }
	}
}

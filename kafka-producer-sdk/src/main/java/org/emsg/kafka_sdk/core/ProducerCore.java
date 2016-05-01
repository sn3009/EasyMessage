package org.emsg.kafka_sdk.core;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;;

/**
 * use this class to send message to broker
 * @author shuttle
 *
 * @param <K> key type
 * @param <V> value type
 */
public class ProducerCore<K, V> {
	
	private final Producer<K, V> producer;
	private ProducerRecord<K, V> record;
	
	public ProducerCore(Producer<K, V> producer){
		this.producer = producer;
	}
	
	/**
	 * send message (the partition which would be used will be decided by the borker)
	 * @param topic topic name
	 * @param value the message you want to send
	 */
	public void sendMessage(String topic, V value){
		record = new ProducerRecord<K, V>(topic, value);
		sendMessage(record);
	}
	
	
	/**
	 * send message
	 * @param topic topic name
	 * @param a key to generate which partition will be send to.
	 * @param value the message you want to send
	 */
	public void sendMessage(String topic, K key, V value){
		record = new ProducerRecord<K, V>(topic, key, value);
		sendMessage(record);
	}
	
	/**
	 * send message
	 * when you give the partition and key at the same time,
	 * the partition will be used to send and key will not be used
	 * @param topic topic name
	 * @param partition which partition
	 * @param key will not be used.
	 * @param value the message you want to send
	 */
	public void sendMessage(String topic, int partition, K key, V value){
		record = new ProducerRecord<K, V>(topic, partition, key, value);
		sendMessage(record);
	}
	
	private void sendMessage(ProducerRecord<K, V> record){
		producer.send(record);
	}
}

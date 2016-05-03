package org.emsg.kafka_sdk.core;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.emsg.kafka_sdk.handler.MessageCallBackHandler;;

/**
 * use this class to send message to broker
 * 
 * @author shuttle
 *
 * @param <K>
 *            key type
 * @param <V>
 *            value type
 */
public class ProducerCore<K, V> {

	private final Producer<K, V> producer;
	private ProducerRecord<K, V> record;

	public ProducerCore(Producer<K, V> producer) {
		this.producer = producer;
	}

	/**
	 * send message (the partition which would be used will be decided by the
	 * borker)
	 * 
	 * @param topic
	 *            topic name
	 * @param value
	 *            the message you want to send
	 */
	public void sendMessage(String topic, V value, MessageCallBackHandler callBackHandler) {
		record = new ProducerRecord<K, V>(topic, value);
		sendMessage(record, callBackHandler);
	}

	/**
	 * send message
	 * 
	 * @param topic
	 *            topic name
	 * @param a
	 *            key to generate which partition will be send to.
	 * @param value
	 *            the message you want to send
	 */
	public void sendMessage(String topic, K key, V value, MessageCallBackHandler callBackHandler) {
		record = new ProducerRecord<K, V>(topic, key, value);
		sendMessage(record, callBackHandler);
	}

	/**
	 * send message when you give the partition and key at the same time, the
	 * partition will be used to send and key will not be used
	 * 
	 * @param topic
	 *            topic name
	 * @param partition
	 *            which partition
	 * @param key
	 *            will not be used.
	 * @param value
	 *            the message you want to send
	 */
	public void sendMessage(String topic, int partition, K key, V value, MessageCallBackHandler callBackHandler) {
		record = new ProducerRecord<K, V>(topic, partition, key, value);
		sendMessage(record, callBackHandler);
	}

	private void sendMessage(ProducerRecord<K, V> record, MessageCallBackHandler callBackHandler) {
		Future<RecordMetadata> future = producer.send(record);
		producer.close();
		while (future.isDone()) {
			try {
				if (callBackHandler != null) {
					callBackHandler.callBack(future.get());
					break;
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				break;
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				break;
			}
		}
	}
}

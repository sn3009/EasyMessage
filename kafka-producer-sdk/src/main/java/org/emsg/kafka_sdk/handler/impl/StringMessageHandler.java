package org.emsg.kafka_sdk.handler.impl;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.emsg.kafka_sdk.config.ProducerInit;
import org.emsg.kafka_sdk.core.ProducerCore;
import org.emsg.kafka_sdk.handler.SendMessageHandlerInterface;

public class StringMessageHandler implements SendMessageHandlerInterface {

	private String topic;

	private String key;

	private String value;

	private int partition;
	

	public StringMessageHandler(String topic, int partition, String key, String value) {
		this.topic = topic;
		this.partition = partition;
		this.key = key;
		this.value = value;
	}

	public void send() {
		Producer<String, String> producer = new KafkaProducer<>(ProducerInit.PRODUCER_PROP);
		ProducerCore sendEngine = new ProducerCore(producer);
		sendEngine.sendMessage(topic, partition, key, value);
	}

}

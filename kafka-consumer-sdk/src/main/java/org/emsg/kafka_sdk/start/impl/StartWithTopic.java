package org.emsg.kafka_sdk.start.impl;

import java.util.List;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.emsg.kafka_sdk.config.ConsumerInit;
import org.emsg.kafka_sdk.core.ConsumerEngineBootServer;
import org.emsg.kafka_sdk.core.impl.ConsumerEngineBootStrapImpl;
import org.emsg.kafka_sdk.handler.MessageHandler;
import org.emsg.kafka_sdk.handler.impl.DefaultMessageHandlerImpl;

public class StartWithTopic {

	private ConsumerEngineBootServer<String, String> booServer;
	private MessageHandler<String> handler;
	private Consumer<String, String> consumer;
	
	public void start(List<String> listTopic) {
		handler = new DefaultMessageHandlerImpl<>();
		consumer = new KafkaConsumer<>(ConsumerInit.CONSUMER_PROP);
		booServer = new ConsumerEngineBootStrapImpl<>(consumer, handler);
		booServer.subscribeTopic(listTopic);
		
		//topic with partition
//		String topic = "sdknew2";
//		booServer.subscribePartition(topic, 0);
	}
}

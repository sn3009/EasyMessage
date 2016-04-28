package org.emsg.kafka_sdk.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.emsg.kafka_sdk.config.ConsumerInit;
import org.emsg.kafka_sdk.core.ConsumerEngineBootServer;
import org.emsg.kafka_sdk.core.impl.ConsumerEngineBootStrapImpl;
import org.emsg.kafka_sdk.handler.MessageHandler;
import org.emsg.kafka_sdk.handler.impl.DefaultMessageHandlerImpl;
import org.emsg.kafka_sdk.start.StartInterface;

public class StartWithTopic implements StartInterface  {

	private ConsumerEngineBootServer<String, String> booServer;
	private MessageHandler<String> handler;
	private Consumer<String, String> consumer;
	
	@Override
	public void start() {
		handler = new DefaultMessageHandlerImpl<>();
		consumer = new KafkaConsumer<>(ConsumerInit.CONSUMER_PROP);
		booServer = new ConsumerEngineBootStrapImpl<>(consumer, handler);
		List<String> listTopic = new ArrayList<>();
		//Just topic
		listTopic.add("sdk-new-1");
		booServer.subscribeTopic(listTopic);
		
		//topic with partition
		String topic = "sdk-new-2";
		booServer.subscribePartition(topic, 1);
	}
}

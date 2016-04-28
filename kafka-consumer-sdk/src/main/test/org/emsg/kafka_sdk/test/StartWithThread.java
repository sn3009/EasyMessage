package org.emsg.kafka_sdk.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.emsg.kafka_sdk.config.ConsumerInit;
import org.emsg.kafka_sdk.core.ConsumerEngineBootServer;
import org.emsg.kafka_sdk.core.impl.ConsumerEngineThreadImpl;
import org.emsg.kafka_sdk.handler.MessageHandler;
import org.emsg.kafka_sdk.handler.impl.DefaultMessageHandlerImpl;
import org.emsg.kafka_sdk.start.StartInterface;
import org.emsg.kafka_sdk.worker.MutilSubscribePartitions;
import org.emsg.kafka_sdk.worker.MutilSubscribeTopic;

public class StartWithThread implements StartInterface  {

	private ConsumerEngineBootServer<String, String> booServer;
	private MessageHandler<String> handler;
	private Consumer<String, String> consumer;
	
	@Override
	public void start() {
		handler = new DefaultMessageHandlerImpl<>();
		consumer = new KafkaConsumer<>(ConsumerInit.CONSUMER_PROP);
		booServer = new ConsumerEngineThreadImpl<>(consumer, handler);
		
		List<String> listTopic = new ArrayList<>();
		listTopic.add("sdk-new-3");
		MutilSubscribeTopic threadTopic = new MutilSubscribeTopic(consumer, handler, listTopic);
		
		MutilSubscribePartitions threadPartition = new MutilSubscribePartitions(consumer, handler, "sdk-4", 1);
		threadPartition.run();
	}
}

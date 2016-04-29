package org.emsg.kafka_sdk.start.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.emsg.kafka_sdk.config.ConsumerInit;
import org.emsg.kafka_sdk.core.ConsumerEngineBootServer;
import org.emsg.kafka_sdk.core.impl.ConsumerEngineThreadImpl;
import org.emsg.kafka_sdk.handler.MessageHandler;
import org.emsg.kafka_sdk.handler.impl.DefaultMessageHandlerImpl;
import org.emsg.kafka_sdk.start.StartWithTopicInterface;
import org.emsg.kafka_sdk.worker.MutilSubscribePartitions;
import org.emsg.kafka_sdk.worker.MutilSubscribeTopic;

public class StartWithThreadTopic<K, V> implements StartWithTopicInterface  {

	private ConsumerEngineBootServer<K, V> booServer;
	private MessageHandler<V> handler;
	private Consumer<K, V> consumer;
	
	@Override
	public void start(List<String> topicList) {
		handler = new DefaultMessageHandlerImpl<>();
		consumer = new KafkaConsumer<>(ConsumerInit.CONSUMER_PROP);
		booServer = new ConsumerEngineThreadImpl<>(consumer, handler);
		
		MutilSubscribeTopic threadTopic = new MutilSubscribeTopic(consumer, handler, topicList);
		threadTopic.run();
	}
}

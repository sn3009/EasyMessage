package org.emsg.kafka_sdk.start.impl;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.emsg.kafka_sdk.config.ConsumerInit;
import org.emsg.kafka_sdk.core.ConsumerEngineBootServer;
import org.emsg.kafka_sdk.core.impl.ConsumerEngineBootStrapImpl;
import org.emsg.kafka_sdk.handler.MessageHandler;
import org.emsg.kafka_sdk.handler.impl.DefaultMessageHandlerImpl;
import org.emsg.kafka_sdk.start.StartWithPartitionInterface;

public class StartWithPartitions<K, V> implements StartWithPartitionInterface {
	
	private ConsumerEngineBootServer<K, V> booServer;
	private MessageHandler<V> handler;
	private Consumer<K, V> consumer;
	
	public StartWithPartitions(){
		handler = new DefaultMessageHandlerImpl<>();
	}
	
	public StartWithPartitions(MessageHandler<V> handler){
		this.handler = handler;
		if(handler == null)
			handler = new DefaultMessageHandlerImpl<>();
	}
	
	public void start(String topic, int ...partitons) {
		handler = new DefaultMessageHandlerImpl<>();
		consumer = new KafkaConsumer<>(ConsumerInit.CONSUMER_PROP);
		booServer = new ConsumerEngineBootStrapImpl<>(consumer, handler);
		
		booServer.subscribePartition(topic, partitons);
	}
}

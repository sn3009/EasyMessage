package org.emsg.kafka_sdk.start.impl;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.emsg.kafka_sdk.config.ConsumerInit;
import org.emsg.kafka_sdk.core.ConsumerEngineBootServer;
import org.emsg.kafka_sdk.core.impl.ConsumerEngineThreadImpl;
import org.emsg.kafka_sdk.handler.MessageHandler;
import org.emsg.kafka_sdk.handler.impl.DefaultMessageHandlerImpl;
import org.emsg.kafka_sdk.start.StartWithPartitionInterface;
import org.emsg.kafka_sdk.worker.MutilSubscribePartitions;

public class StartWithThreadPartition<K, V> implements StartWithPartitionInterface  {

	private ConsumerEngineBootServer<K, V> booServer;
	private MessageHandler<V> handler;
	private Consumer<K, V> consumer;
	
	public StartWithThreadPartition(){
		handler = new DefaultMessageHandlerImpl<>();
	}
	
	public StartWithThreadPartition(MessageHandler<V> handler){
		this.handler = handler;
		if(handler == null)
			handler = new DefaultMessageHandlerImpl<>();
	}
	
	public void start(String topic, int... partition) {
		consumer = new KafkaConsumer<>(ConsumerInit.CONSUMER_PROP);
		booServer = new ConsumerEngineThreadImpl<>(consumer, handler);
		
		MutilSubscribePartitions threadPartition = new MutilSubscribePartitions(consumer, handler, topic, partition);
		threadPartition.run();
	}
}

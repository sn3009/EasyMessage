package org.emsg.kafka_sdk.worker;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.kafka.clients.consumer.Consumer;
import org.emsg.kafka_sdk.core.ConsumerEngineBootServer;
import org.emsg.kafka_sdk.core.impl.ConsumerEngineBootStrapImpl;
import org.emsg.kafka_sdk.handler.MessageHandler;

public class MutilSubscribeTopic implements Runnable {
	private final AtomicBoolean closed = new AtomicBoolean(false);
	private final List<String> topicList;
    private final Consumer consumer;
    private final MessageHandler handler;
    private final ConsumerEngineBootServer bootServer;
    
    public MutilSubscribeTopic(Consumer consumer, MessageHandler handler, List<String> topicList){
    	this.consumer = consumer;
    	this.handler = handler;
    	this.topicList = topicList;
    	this.bootServer = new ConsumerEngineBootStrapImpl(consumer, handler);
    }

    public void run() {
        bootServer.subscribeTopic(topicList);
        bootServer.pollMessages(closed);
    }

    // Shutdown hook which can be called from a separate thread
    public void shutdown() {
        closed.set(true);
        consumer.wakeup();
    }
}

package org.emsg.kafka_sdk.worker;

import org.emsg.kafka_sdk.handler.MessageHandler;
import org.emsg.kafka_sdk.handler.impl.DefaultMessageHandlerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;

public class ConsumerWorker<K, V> implements Runnable {

	private KafkaStream<K, V> mStream;
	private int threadNo;

	private final static Logger log = LoggerFactory.getLogger(ConsumerWorker.class);

	public ConsumerWorker(KafkaStream mStream, int threadNo) {
		this.mStream = mStream;
		this.threadNo = threadNo;
	}

	public void run() {
		// TODO Auto-generated method stub
		MessageHandler<V> msgHandler = new DefaultMessageHandlerImpl();
		ConsumerIterator it = mStream.iterator();
		while (it.hasNext()) {
			Object o = it.next().message();
			msgHandler.dealMessage((V) o);
			log.info("thread:" + threadNo + " get message:" + o);
		}
	}

}

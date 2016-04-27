package org.emsg.kafka_sdk.handler;

public interface MessageHandler<T> {
	public void dealMessage(T t);
}

package org.emsg.kafka_sdk.service;

import org.emsg.kafka_sdk.handler.SendMessageHandlerInterface;

public interface MessageSenderInteface {
	public void sendMessage(SendMessageHandlerInterface handler);
}

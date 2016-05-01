package org.emsg.kafka_sdk.service.impl;

import org.emsg.kafka_sdk.handler.SendMessageHandlerInterface;
import org.emsg.kafka_sdk.service.MessageSenderInteface;

public class StringMessageSender implements MessageSenderInteface {

	@Override
	public void sendMessage(SendMessageHandlerInterface handler) {
		// TODO Auto-generated method stub
		handler.send();
	}

}

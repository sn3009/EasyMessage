package org.emsg.kafka_sdk.handler.impl;

import org.emsg.kafka_sdk.handler.MessageHandler;

public class DefaultMessageHandlerImpl<String> implements MessageHandler<String> {

	public void dealMessage(String msg) {
		// TODO Auto-generated method stub
		System.out.println(msg);
	}

}

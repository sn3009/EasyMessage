package org.emsg.kafka_sdk.handler.impl;

import org.emsg.kafka_sdk.handler.MessageHandler;

public class ByteMessageHandler implements MessageHandler<byte[]> {

	public void dealMessage(byte[] t) {
		// TODO Auto-generated method stub
		System.out.println(new String(t));
	}

}

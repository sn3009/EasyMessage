package org.kafka.producer.sdk;

import org.emsg.kafka_sdk.handler.SendMessageHandlerInterface;
import org.emsg.kafka_sdk.handler.impl.StringMessageHandler;
import org.emsg.kafka_sdk.service.MessageSenderInteface;
import org.emsg.kafka_sdk.service.impl.StringMessageSender;

public class SendTest {
	public static void main(String[] args) {
		MessageSenderInteface msgSender = new StringMessageSender();
		SendMessageHandlerInterface handler = new StringMessageHandler("test", 0, "1", "this is a very long test!");
		
		msgSender.sendMessage(handler);
	}
}

package org.kafka.producer.sdk;

import org.emsg.kafka_sdk.handler.SendMessageHandlerInterface;
import org.emsg.kafka_sdk.handler.impl.StringMessageHandler;

public class SendTest {
	public static void main(String[] args) {
		SendMessageHandlerInterface handler = new StringMessageHandler("sdk", 0, "1", "this is a very long test!");
		handler.send();
	}
}

package org.kafka.producer.sdk;

import org.emsg.kafka_sdk.handler.MessageCallBackHandler;
import org.emsg.kafka_sdk.handler.SendMessageHandlerInterface;
import org.emsg.kafka_sdk.handler.impl.ShowMessageCallBackHandler;
import org.emsg.kafka_sdk.handler.impl.StringMessageHandler;

public class SendTest {
	public static void main(String[] args) {
		SendMessageHandlerInterface sender = new StringMessageHandler("sdk", 0, "1", "this is a very long test!");
		MessageCallBackHandler handler = new ShowMessageCallBackHandler();
		sender.send(handler);
	}
}

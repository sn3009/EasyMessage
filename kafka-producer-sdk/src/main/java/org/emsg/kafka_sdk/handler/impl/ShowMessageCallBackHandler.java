package org.emsg.kafka_sdk.handler.impl;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.emsg.kafka_sdk.handler.MessageCallBackHandler;

public class ShowMessageCallBackHandler implements MessageCallBackHandler {

	@Override
	public void callBack(Object o) {
		// TODO Auto-generated method stub
		RecordMetadata meta = (RecordMetadata)o;
		System.out.println(meta.offset() + " " + meta.partition() + " " + meta.topic());
		System.out.println(meta.toString());
	}

}

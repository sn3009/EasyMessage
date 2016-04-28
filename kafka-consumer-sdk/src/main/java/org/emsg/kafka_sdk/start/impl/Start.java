package org.emsg.kafka_sdk.start.impl;

import org.emsg.kafka_sdk.config.ConsumerInit;
import org.emsg.kafka_sdk.core.ConsumerEngine;
import org.emsg.kafka_sdk.start.StartInterface;

import kafka.serializer.Decoder;
import kafka.serializer.StringDecoder;

public class Start implements StartInterface {
	
	public static void main(String [] args){
		Start s = new Start();
		s.start();
	}

	public void start() {
		// TODO Auto-generated method stub
		ConsumerEngine<String, String> ce = new ConsumerEngine<String, String>("sdk", Integer.parseInt(ConsumerInit.PARTITION_STATEGY));
		Decoder<String> stringDecoder = new StringDecoder(null);
		ce.start(stringDecoder, stringDecoder);
	}
}

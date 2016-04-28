package org.emsg.kafka_sdk.start.impl;

import org.emsg.kafka_sdk.config.ConsumerInit;
import org.emsg.kafka_sdk.core.ConsumerEngine;
import org.emsg.kafka_sdk.start.StartInterface;

import kafka.serializer.Decoder;
import kafka.serializer.DefaultDecoder;

public class StartByte implements StartInterface {

	public static void main(String [] args){
		Start s = new Start();
		s.start();
	}

	public void start() {
		// TODO Auto-generated method stub
		ConsumerEngine<byte[], byte[]> ce = new ConsumerEngine<byte[], byte[]>("sdk", Integer.parseInt(ConsumerInit.PARTITION_STATEGY));
		Decoder<byte[]> decoder = new DefaultDecoder(null);
		ce.run(decoder, decoder);
	}

}

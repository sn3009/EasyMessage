package org.emsg.kafka_sdk.start.impl;

import java.util.List;

import org.emsg.kafka_sdk.config.ConsumerInit;
import org.emsg.kafka_sdk.core.ConsumerEngine;
import org.emsg.kafka_sdk.start.StartWithTopicInterface;

import kafka.serializer.Decoder;
import kafka.serializer.DefaultDecoder;

public class StartOldByte implements StartWithTopicInterface {

	public void start(List<String> topicList) {
		for(String topic : topicList){
			ConsumerEngine<byte[], byte[]> ce = new ConsumerEngine<byte[], byte[]>(topic, Integer.parseInt(ConsumerInit.PARTITION_STATEGY));
			Decoder<byte[]> decoder = new DefaultDecoder(null);
			ce.run(decoder, decoder);
		}
	}

}

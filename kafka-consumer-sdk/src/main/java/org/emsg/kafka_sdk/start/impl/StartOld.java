package org.emsg.kafka_sdk.start.impl;

import java.util.List;

import org.emsg.kafka_sdk.config.ConsumerInit;
import org.emsg.kafka_sdk.core.ConsumerEngine;
import org.emsg.kafka_sdk.start.StartWithTopicInterface;

import kafka.serializer.Decoder;
import kafka.serializer.StringDecoder;

public class StartOld implements StartWithTopicInterface {
	public void start(List<String> topicList) {
		// TODO Auto-generated method stub
		for (String topic : topicList) {
			ConsumerEngine<String, String> ce = new ConsumerEngine<String, String>(topic,
					Integer.parseInt(ConsumerInit.PARTITION_STATEGY));
			Decoder<String> stringDecoder = new StringDecoder(null);
			ce.run(stringDecoder, stringDecoder);
		}
	}
}

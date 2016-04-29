package org.emsg.kafka_sdk.test;

import org.emsg.kafka_sdk.start.StartWithPartitionInterface;
import org.emsg.kafka_sdk.start.impl.StartWithThreadPartition;

public class Test {
	public static void main(String [] args){
		StartWithPartitionInterface start = new StartWithThreadPartition<String, String>();
		start.start("sdknew2", 0);
	}
}

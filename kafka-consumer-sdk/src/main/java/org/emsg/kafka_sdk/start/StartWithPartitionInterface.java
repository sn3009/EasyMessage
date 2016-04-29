package org.emsg.kafka_sdk.start;

public interface StartWithPartitionInterface {
	public void start(String topic, int ...partitions);
}

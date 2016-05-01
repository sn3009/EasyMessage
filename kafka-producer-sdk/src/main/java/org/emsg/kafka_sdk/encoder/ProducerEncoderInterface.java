package org.emsg.kafka_sdk.encoder;

import org.apache.kafka.common.serialization.Deserializer;

public interface ProducerEncoderInterface<T> extends Deserializer<T>  {
	
}

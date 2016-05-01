package org.emsg.kafka_sdk.consts;

/**
 * kafka config settings.
 * 
 * @author Ning
 *
 */
public class ProducerConsts {

	public final static String PRODUCER_CONFIG_FILE_PATH = "/producer.properties";
	public final static String PRODUCER_SDK_SETTINGS = "/producer-sdk-settings.properties";

	public final static String BOOTSTRAP_SERVERS = "bootstrap.servers";
	public final static String ACK = "acks";
	public final static String RETRIES = "retries";
	public final static String BATCH_SIZE = "batch.size";

	public final static String LINGER = "linger.ms";
	public final static String BUFFER_MEMORY = "buffer.memory";
	public final static String KEY_ENCODER = "key.serializer";
	public final static String VALUE_ENCODER = "value.deserializer";
}

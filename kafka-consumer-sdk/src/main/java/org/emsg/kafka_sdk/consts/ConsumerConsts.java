package org.emsg.kafka_sdk.consts;

/**
 * kafka config settings.
 * @author Ning
 *
 */
public class ConsumerConsts {
	
	public final static String CONSUMER_CONFIG_FILE_PATH = "/consumer.properties";
	public final static String CONSUMER_SDK_SETTINGS = "/consumer-sdk-settings.properties";
	
    public final static String BOOTSTRAP_SERVERS = "bootstrap.servers";
    public final static String GROUP_ID = "group.id";
    public final static String ENABLE_AUTO_COMMIT = "enable.auto.commit";
    public final static String AUTO_COMMIT_INTERVAL = "auto.commit.interval.ms";
    public final static String SESSION_TIMEOUT = "session.timeout.ms";
    public final static String KEY_DESERIALIZER = "key.deserializer";
    public final static String VALUE_DESERIALIZER = "value.deserializer";
    
    public final static String MIN_THREAD = "worker_min_thread_num";
    public final static String MAX_THREAD = "worker_max_thread_num";
    public final static String DEFAULT_THREAD = "worker_default_thread_num";
    public final static String PARTITION_STATEGY = "partition_strategy";
    public final static String POLL_TIMEOUT = "poll_timeout";
}

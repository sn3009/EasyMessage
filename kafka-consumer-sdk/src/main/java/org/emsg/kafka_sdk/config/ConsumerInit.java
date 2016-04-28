package org.emsg.kafka_sdk.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.ems.utils.PropertiesFileInit;
import org.emsg.kafka_sdk.consts.ConsumerConsts;

public class ConsumerInit {
	
	public static Properties CONSUMER_PROP;
	
	public static Properties CONFIG_PROP;
	
	public static String WORKER_MIN_THREAD_NUM;
	
	public static String WORKER_MAX_THREAD_NUM;
	
	public static String WORKER_DEFAULT_THREAD_NUM;
	
	public static String PARTITION_STATEGY;
	
	/**
	 * Pool message time out time.
	 */
	public static Long POLL_TIMEOUT;
	
	static{
		PropertiesFileInit property = new PropertiesFileInit();
		InputStream in = ConsumerInit.class.getResourceAsStream(ConsumerConsts.CONSUMER_CONFIG_FILE_PATH);
		try {
			CONSUMER_PROP = property.loadProperties(in);
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		InputStream configIn = ConsumerInit.class.getResourceAsStream(ConsumerConsts.CONSUMER_SDK_SETTINGS);
		try {
			CONFIG_PROP = property.loadProperties(configIn);
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WORKER_MIN_THREAD_NUM = CONFIG_PROP.getProperty(ConsumerConsts.MIN_THREAD);
		WORKER_MAX_THREAD_NUM = CONFIG_PROP.getProperty(ConsumerConsts.MAX_THREAD);
		WORKER_DEFAULT_THREAD_NUM = CONFIG_PROP.getProperty(ConsumerConsts.DEFAULT_THREAD);
		PARTITION_STATEGY = CONFIG_PROP.getProperty(ConsumerConsts.PARTITION_STATEGY);
		POLL_TIMEOUT = Long.parseLong(CONFIG_PROP.getProperty(ConsumerConsts.POLL_TIMEOUT));
	}
	
}

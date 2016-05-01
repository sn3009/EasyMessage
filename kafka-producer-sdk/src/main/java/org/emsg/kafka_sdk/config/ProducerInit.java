package org.emsg.kafka_sdk.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.ems.utils.PropertiesFileInit;
import org.emsg.kafka_sdk.consts.ProducerConsts;

public class ProducerInit {
	
	public static Properties PRODUCER_PROP;
	
	public static Properties CONFIG_PROP;
	
	/**
	 * Pool message time out time.
	 */
	public static Long POLL_TIMEOUT;
	
	static{
		PropertiesFileInit property = new PropertiesFileInit();
		InputStream in = ProducerInit.class.getResourceAsStream(ProducerConsts.PRODUCER_CONFIG_FILE_PATH);
		try {
			PRODUCER_PROP = property.loadProperties(in);
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		InputStream configIn = ProducerInit.class.getResourceAsStream(ProducerConsts.PRODUCER_SDK_SETTINGS);
		try {
			CONFIG_PROP = property.loadProperties(configIn);
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

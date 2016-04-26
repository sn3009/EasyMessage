package org.emsg.kafka_sdk.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.ems.utils.PropertiesFileInit;
import org.emsg.kafka_sdk.consts.KafkaConsts;

public class ConsumerInit { 
	
	public static Properties InitConfig(){
		InputStream in = ConsumerInit.class.getResourceAsStream(KafkaConsts.CONFIG_FILE_PATH);
		PropertiesFileInit property = new PropertiesFileInit();
		try {
			return property.loadProperties(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static Properties InitConfig(String path){
		InputStream in = ConsumerInit.class.getResourceAsStream(path);
		PropertiesFileInit property = new PropertiesFileInit();
		try {
			return property.loadProperties(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}

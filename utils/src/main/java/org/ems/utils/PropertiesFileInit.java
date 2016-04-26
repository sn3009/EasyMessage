package org.ems.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesFileInit {
	
	public Properties loadProperties(String filePath) throws IOException{
		Properties pro = new Properties();
		FileInputStream in = null;
		try {
			in = new FileInputStream(filePath);
			pro.load(in);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			in.close();
		}
		
		return pro;
	}
	
	public Properties loadProperties(InputStream in) throws IOException{
		Properties pro = new Properties();
		try {
			pro.load(in);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			in.close();
		}
		
		return pro;
	}
}

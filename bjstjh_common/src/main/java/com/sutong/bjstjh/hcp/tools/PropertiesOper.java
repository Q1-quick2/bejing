package com.sutong.bjstjh.hcp.tools;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesOper {

	// private static HashMap<String,String> hmKV=null;
	private static Properties props = null;// new Properties();
	public static String configPath = "";

	public static String get(String key) {
		if (props == null) {
			props = new Properties();
			try {
				// System.out.println(configPath);
				InputStream in = new FileInputStream(configPath); // PropertiesOper.class.getResourceAsStream(configPath);
				props.load(in);
				String value = props.getProperty(key);
				// return value;
			} catch (Exception e) {
				e.printStackTrace();
				// return null;
			}
		}
		return props.getProperty(key);

	}
}

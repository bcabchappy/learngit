package com.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * 读取系统配置文件config.properties
 * @author zzy
 *
 */
@SuppressWarnings("unchecked")
public class ConfigProperties {
	
	public static Map<String, String> CONFIGMAP = null;

	/**
	 * 从配置文件中获取配置的路径信息
	 * @return
	 */
	public static void initConfigProperties() {
		CONFIGMAP = new HashMap<String, String>();
		Properties properties = new Properties();
		ConfigProperties rsfp = new ConfigProperties();
		InputStream in = rsfp.getClass().getResourceAsStream("/application.properties");
		if (in == null) {
			System.out.println("没有找到配置文件!");
		} else {
			try {
				properties.load(in);
				Set propertiesSet = properties.keySet();
				Iterator ite = propertiesSet.iterator();
				while (ite.hasNext()) {
					String key = ite.next().toString();
					CONFIGMAP.put(key, properties.getProperty(key));
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (in != null) {
						in.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 通过KEY获取到具体的配置信息
	 * @param key
	 * @return
	 */
	public static String getByKey(String key) {
		if (ConfigProperties.CONFIGMAP == null) {
			initConfigProperties();
		}
		if (ConfigProperties.CONFIGMAP.get(key) != null) {
			return ConfigProperties.CONFIGMAP.get(key);
		}
		return null;
	}
	/**
	 * 根据配置文件(application.properties)的配置 判断是否使用ftp上传下载
	 * @return
	 */
	public static boolean useFtp(){
		String useFtp = ConfigProperties.getByKey("ftp.config.flag");
		if("true".equals(useFtp)){//使用ftp下载
				return true;
			}else{
				return false;
			}
	}
}

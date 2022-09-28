package com.jackson.seal2022.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 配置文件处理类
 * 
 * @author yanglijun
 * 
 */
public class SystemConfig {
	
	private static Properties configProperties = new Properties();
	
	/**
	 * 静态代码块，加载config.properties文件
	 * 
	 */
	static {
		InputStream is = null;
		try {
			is = SystemConfig.class.getResourceAsStream("/config.properties");
			configProperties.load(is);
			//logger.info("加载配置文件config.properties完成......");
		} catch (IOException e) {
			//logger.info("加载properties配置文件发生异常，请重启服务再次尝试......");
			e.printStackTrace();
		} finally {
			try {
				if (null != is) {
					is.close();
				}
			} catch (IOException e) {
				//logger.info("加载properties配置文件发生异常，请重启服务再次尝试......");
				e.printStackTrace();
			}
		}
	}

	/**
	 * 根据config.properties配置文件的key获取对应的值
	 * 
	 * @param key
	 * @return
	 */
	public static String getConfigProperty(String key){
		return configProperties.getProperty(key);
	}
}

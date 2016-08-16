package com.koal.tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;

/**
 * 加载配置文件，并获取对应的key值
 */
public class PropertiesUtils {
	
	//产生一个操作配置文件的对象
	private static Properties prop = new Properties();
	
	/**
	 * 加载配置文件
	 * @param fileName 需要加载的配置文件的名称，此文件需要放在src目录下
	 * @return 是否加载成功
	 */
	public static boolean loadFile(String fileName){
		try {
//			prop.load(Properties.class.getClassLoader().getResourceAsStream(fileName));
//			prop.load(FrameConfig.class.getResourceAsStream(fileName));
			// 获取当前类加载的根目录，如：/C:/Program Files/Apache/Tomcat 6.0/webapps/fee/WEB-INF/classes/ 
			String path = PropertiesUtils.class.getClassLoader().getResource("").toURI().getPath(); 
			// 把文件读入文件输入流，存入内存中 
			FileInputStream fis = new FileInputStream(new File(path + fileName)); 
			//加载文件流的属性 
			prop.load(fis);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * 获取对应key的值
	 * @param key 配置文件中键的名称
	 * @return key对应的值
	 */
	public static String getValue(String key){
		return prop.getProperty(key);
	}

}

package com.koal.tool;

import java.util.ArrayList;
import java.util.List;
/**
 * 初始化，模拟加载所有的配置文件
 */
public class DBInitInfo {
	public  static List<DBbean>  beans = null;
	static{
		beans = new ArrayList<DBbean>();
		
		DBbean bean = new DBbean();
		
		PropertiesUtils.loadFile("dbconfig.properties");
		bean.setDriverName(PropertiesUtils.getValue("driverClass"));
		bean.setUrl(PropertiesUtils.getValue("url"));
		bean.setUserName(PropertiesUtils.getValue("user"));
		bean.setPassword(PropertiesUtils.getValue("password"));
		
		bean.setMinConnections(5);
		bean.setMaxConnections(100);
		
		bean.setPoolName("testPool");
		beans.add(bean);
	}
}

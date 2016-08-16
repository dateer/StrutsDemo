package com.koal.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.koal.tool.ConnectionPoolManager;
import com.koal.tool.IConnectionPool;
import com.koal.tool.PropertiesUtils;

/**
 * 数据库连接
 */
public class Dal {
	
	IConnectionPool pool = ConnectionPoolManager.getInstance().getPool("testPool");
	
	private Logger logger = Logger.getLogger(Dal.class);
	
	private Connection dbConnection;
	private Statement sta;

	/**
	 * 取得数据库连接
	 * @return
	 */
	private Connection getConnection(){
		try{
			dbConnection = pool.getConnection(); //获取一个连接
			sta = dbConnection.createStatement();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return dbConnection;
	}
	
	/**
	 * 关闭连接
	 */
	private void closeConn(){
		try {
			sta.close();
			pool.releaseConn(dbConnection); //将连接回收
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取班级信息
	 * @return
	 */
	public JSONObject QueryAllStudentInfo(){
		String sql = "SELECT * FROM stu_complete_info ORDER BY stu_id";
		JSONObject jsobj = ExecuteQueryStudent(sql);
		return jsobj;
	}
	
	/**
	 * 模糊查询学生信息
	 * @return
	 */
	public JSONObject fuzzyQuery(int specialty_id, String SearchInputNum, String SearchInputChar){
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM stu_complete_info WHERE 1=1 ");
		if(specialty_id > 0){
			sql.append(" and specialty_id = " + specialty_id);
		}
		if(!SearchInputNum.equals("")){
			sql.append(" and stu_id = " + SearchInputNum);
		}
		if(!SearchInputChar.equals("")){
			sql.append(" and  (NAME LIKE '%" + SearchInputChar + "%' OR info LIKE '%" + SearchInputChar + "%') ");
		}
		sql.append(" ORDER BY stu_id");
		JSONObject jsobj = ExecuteQueryStudent(sql.toString());
		return jsobj;
	}
	
	private JSONObject ExecuteQueryStudent(String sql){
		JSONObject jsobj = null;
		JSONArray jsarr = null;
		try{
			this.getConnection();			
			
			ResultSet rs = sta.executeQuery(sql);
			jsobj = new JSONObject();
			jsarr = new JSONArray();
			while(rs.next()){
//				stu_id, specialty_id, specialty_name, name, sex, age, birthday, info
				jsobj.put("stu_id", rs.getInt("stu_id"));
				jsobj.put("specialty_id", rs.getInt("specialty_id"));
				jsobj.put("specialty_name", rs.getString("specialty_name"));
				jsobj.put("name", rs.getString("name"));
				jsobj.put("sex", rs.getInt("sex") == 0 ? "男" : "女");
				jsobj.put("age", rs.getInt("age"));
				jsobj.put("birthday", rs.getString("birthday"));
				
				//因为数据库存储blob，所以需要经过转换才不会导致乱码
				Blob infoBlob = rs.getBlob("info");
				String info = null;
				if(infoBlob != null){
					InputStream is = infoBlob.getBinaryStream();
					ByteArrayInputStream bais = (ByteArrayInputStream)is;
					byte[] byte_data = new byte[bais.available()]; //bais.available()返回此输入流的字节数
					bais.read(byte_data, 0,byte_data.length);//将输入流中的内容读到指定的数组
					info = new String(byte_data,"utf-8"); //再转为String，并使用指定的编码方式
					is.close();
				}
				jsobj.put("info", info);
				jsarr.add(jsobj);
				jsobj.clear();
			}
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("rows", jsarr);
			jsobj = JSONObject.fromObject(map);
			this.closeConn();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("查询学生信息出错：\n" + e);
		}
		return jsobj;
	}
	
	/**
	 * 查询班级信息
	 * @param sql 待执行的sql语句
	 * @param flag 是否有“显示全部”
	 * @return
	 */
	public JSONArray QuerySpecialty(boolean flag){
		JSONObject jsobj = null;
		JSONArray jsarr = null;
		try{
			this.getConnection();
			
			String sql = "SELECT * FROM specialty";
			ResultSet rs = sta.executeQuery(sql);
			jsobj = new JSONObject();
			jsarr = new JSONArray();
			
			if(flag){
				jsobj.put("specialty_id", 0);
				jsobj.put("specialty_name", "显示全部");
				jsarr.add(jsobj);
			}
			
			while(rs.next()){
				jsobj.clear();
				jsobj.put("specialty_id", rs.getInt("specialty_id"));
				jsobj.put("specialty_name", rs.getString("specialty_name"));
				jsarr.add(jsobj);
			}
			
			this.closeConn();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("查询班级表出错：\n" + e);
		}
		
		return jsarr;
	}
	
	/**
	 * 新增学生
	 * @return
	 */
	public int AddStudent(String stu_info){
		String[] arr = stu_info.split(",");
		String sql = null;
//		var stu_info = specialty_id + "-,-" + name + "-,-" + sex + "-,-" + age + "-,-" + birthday + "-,-" +  info;
		if(arr.length == 5){
			sql = "INSERT INTO student(specialty_id, NAME, sex, age, birthday) "
					+ "VALUES (" + arr[0] + ", '" + arr[1] + "', " + arr[2] + ", " + arr[3] + ", '" + arr[4] + "')";
		}else{
			sql = "INSERT INTO student(specialty_id, NAME, sex, age, birthday, info) "
					+ "VALUES (" + arr[0] + ", '" + arr[1] + "', " + arr[2] + ", " + arr[3] + ", '" + arr[4] + "', '" + arr[5] + "')";
		}
		
		int result = DB_OperateData(sql);
		logger.info("执行插入：" + sql); //写入日志
		
		return result;
	}
	
	/**
	 * 修改学生信息
	 * @return
	 */
	public int EditStudent(String stu_info){
		String[] arr = stu_info.split(",");
		String sql = null;
//		var stu_info = stu_id + "," + specialty_id + "," + name + "," + sex + "," + age + "," + birthday + "," +  info;
		if(arr.length == 6){
			sql = "UPDATE student "
					+ "SET specialty_id='" + arr[1] + "', NAME='" + arr[2] + "', sex=" + arr[3] + ", age=" + arr[4] + ", birthday='" + arr[5] + "', info='' "
					+ "WHERE stu_id=" + arr[0];
		}else{
			sql = "UPDATE student "
					+ "SET specialty_id='" + arr[1] + "', NAME='" + arr[2] + "', sex=" + arr[3] + ", age=" + arr[4] + ", birthday='" + arr[5] + "', info='" + arr[6] + "' "
					+ "WHERE stu_id=" + arr[0];
		}
		int result = DB_OperateData(sql);
		logger.info("执行更新：" + sql);
		
		return result;
	}
	
	/**
	 * 删除学生
	 * @return
	 */
	public int deleteStudent(int stu_id){
		String sql = "DELETE FROM student WHERE stu_id = " + stu_id;
		int result = DB_OperateData(sql);

		return result;
	}
	
	/**
	 * 更新数据。
	 * 包括新增、修改、删除等操作
	 * @param sql 待执行的sql语句
	 * @return 执行结果
	 */
	public int DB_OperateData(String sql){
		int result = 0;
		try{
			this.getConnection();			
			result = sta.executeUpdate(sql);
			this.closeConn();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("更新出错：\n" + e);
		}
		return result;
	}
	

	
}

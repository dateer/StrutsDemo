package com.koal.action;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.koal.service.Dal;

public class Actions extends BaseAction {
	
	private JSONObject jsObj;
	private JSONArray jsArr;
	private String msg;
	private String stu_info;
	private int stu_id;
	private Dal dal = new Dal();
	
	/**
	 * 获取学生信息，绑定dg
	 * @return
	 */
	public String getStudentInfo(){
		jsObj = dal.QueryAllStudentInfo();
		return SUCCESS;
	}
	
	/**
	 * 获取有 “显示全部” 的班级信息
	 * @return
	 */
	public String getSearch_specialty(){
		jsArr = dal.QuerySpecialty(true);
		return SUCCESS;
	}
	
	/**
	 * 获取班级信息，绑定combobox
	 * @return
	 */
	public String getSpecialty(){
		jsArr = dal.QuerySpecialty(false);
		return SUCCESS;
	}
	
	/**
	 * 新增学生
	 * @return
	 */
	public String AddStudent(){
		int result = dal.AddStudent(stu_info);
		msg = result > 0 ? "OK" : "NO";
		return SUCCESS;
	}
	
	/**
	 * 修改学生信息
	 * @return
	 */
	public String EditStudent(){
		int result = dal.EditStudent(stu_info);
		msg = result > 0 ? "OK" : "NO";
		return SUCCESS;
	}
	
	/**
	 * 删除学生信息
	 * @return
	 */
	public String deleteStudent(){
		int result = dal.deleteStudent(stu_id);
		msg = result > 0 ? "OK" : "NO";
		return SUCCESS;
	}
	
	/**
	 * 模糊查询学生信息
	 * @return
	 */
	public String fuzzyQuery(){
		int specialty_id = Integer.parseInt(this.getRequest().getParameter("specialty_id"));
		String SearchInputNum = this.getRequest().getParameter("SearchInputNum");
		String SearchInputChar = this.getRequest().getParameter("SearchInputChar");
		jsObj = dal.fuzzyQuery(specialty_id, SearchInputNum, SearchInputChar);
		return SUCCESS;
	}
	
	public JSONObject getJsObj() {
		return jsObj;
	}
	public void setJsObj(JSONObject jsObj) {
		this.jsObj = jsObj;
	}
	public JSONArray getJsArr() {
		return jsArr;
	}
	public void setJsArr(JSONArray jsArr) {
		this.jsArr = jsArr;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getStu_info() {
		return stu_info;
	}
	public void setStu_info(String stu_info) {
		this.stu_info = stu_info;
	}
	public int getStu_id() {
		return stu_id;
	}
	public void setStu_id(int stu_id) {
		this.stu_id = stu_id;
	}

}

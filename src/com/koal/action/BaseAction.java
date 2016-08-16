package com.koal.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

public  abstract  class BaseAction extends ActionSupport implements ServletRequestAware {

	private HttpServletRequest request;
	private HttpSession session;
	
	public void setServletRequest(HttpServletRequest arg0){
		this.setRequest(arg0);
		this.setSession(arg0.getSession());
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}
	
}
package com.yunwenlong.model;

import java.util.List;

public class ResponseBean {

	private boolean flag = false;
	
	private String msg = "网络传输异常,请稍后再试！";
	
	private String message = null;
	
	private Integer index;
	
	private List list;

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public ResponseBean(boolean flag) {
		super();
		this.flag = flag;
	}
	
}

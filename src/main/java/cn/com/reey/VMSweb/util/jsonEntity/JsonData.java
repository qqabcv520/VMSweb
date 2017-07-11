/**   
 * filename：JsonData.java
 *   
 * date：2016年4月24日
 * Copyright reey Corporation 2016 
 *   
 */
package cn.com.reey.VMSweb.util.jsonEntity;

import cn.com.reey.VMSweb.util.MyStatus;

public class JsonData {
	private int statusCode = -1;
	private String status;
	private Object data;
	
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
		status = MyStatus.codeToDescription(statusCode);
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}

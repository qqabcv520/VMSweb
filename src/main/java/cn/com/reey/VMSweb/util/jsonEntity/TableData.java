/**   
 * filename：JsonData.java
 *   
 * date：2016年4月18日
 * Copyright reey Corporation 2016 
 *   
 */
package cn.com.reey.VMSweb.util.jsonEntity;

import java.util.List;

public class TableData {
	private int total;
	private List<Object> rows;
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List<Object> getRows() {
		return rows;
	}
	public void setRows(List<Object> rows) {
		this.rows = rows;
	}
	
	

}

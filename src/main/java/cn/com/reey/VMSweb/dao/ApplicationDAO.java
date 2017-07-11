/**   
 * filename：UserDAO.java
 *   
 * date：2016年4月14日
 * Copyright reey Corporation 2016 
 *   
 */
package cn.com.reey.VMSweb.dao;

import java.util.Date;
import java.util.List;

import cn.com.reey.VMSweb.entity.Application;


public interface ApplicationDAO extends BaseDAO<Application> {
	
	List<Application> getExportData(Date startTime, Date endTime, Integer type);
	List<Application> getExportData(Date startTime, Date endTime, Integer type, String order, String sort);
}

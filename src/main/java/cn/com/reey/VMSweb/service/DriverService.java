/**   
 * filename：DriverService.java
 *   
 * date：2016年5月2日
 * Copyright reey Corporation 2016 
 *   
 */
package cn.com.reey.VMSweb.service;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import cn.com.reey.VMSweb.entity.Driver;
import cn.com.reey.VMSweb.util.jsonEntity.JsonData;

public interface DriverService extends BaseEntityService<Driver> {
	
	
	
	
	
	
	/**   
	 * 获取司机的数据，JSON形式
	 * @author 范子才
	 * @param limit 每页的个数
	 * @param offset 第几页
	 * @return JSON字符串
	 * @version 2016年4月25日 下午17:23:40
	 */
	String getJsonDataString(int limit, int offset, String order, String sort);

	/**   
	 * 添加司机
	 * @author 范子才
	 * @param drivername
	 * @param drivernote
	 * @return
	 * @version 2016年5月3日 下午6:37:36
	 */
	JsonData addDriver(String drivername, String drivernote);
	
	
	
	/**   
	 * 编辑司机信息
	 * @author 范子才
	 * @param idList
	 * @param drivername
	 * @param drivernote
	 * @return
	 * @version 2016年5月3日 下午6:41:37
	 */
	JsonData editDriver(Collection<Integer> idList, String drivername, String drivernote);
	
	
}

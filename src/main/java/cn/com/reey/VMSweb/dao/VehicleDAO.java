/**   
 * filename：UserDAO.java
 *   
 * date：2016年4月14日
 * Copyright reey Corporation 2016 
 *   
 */
package cn.com.reey.VMSweb.dao;

import cn.com.reey.VMSweb.entity.Vehicle;


public interface VehicleDAO extends BaseDAO<Vehicle> {
	/**   
	 * platenumber查找车辆
	 * @author 范子才
	 * @param platenumber
	 * @return
	 * @version 2016年5月2日 下午10:58:46
	 */
	Vehicle findByPlatenumber(String platenumber);
}

/**   
 * filename：VehicleService.java
 *   
 * date：2016年5月2日
 * Copyright reey Corporation 2016 
 *   
 */
package cn.com.reey.VMSweb.service;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import cn.com.reey.VMSweb.entity.Application;
import cn.com.reey.VMSweb.util.jsonEntity.JsonData;

public interface ApplicationService extends BaseEntityService<Application> {
	
	
	
	
	
	
	/**   
	 * 获取车辆的数据，JSON形式
	 * @author 范子才
	 * @param limit 每页的个数
	 * @param offset 第几页
	 * @param order 升降序
	 * @param sort 排序字段
	 * @return JSON字符串
	 * @version 2016年4月25日 下午17:23:40
	 */
	String getJsonDataString(int limit, int offset, String order, String sort);

	/**   
	 * 按照开始用车时间导出指定时间段数据，并排序
	 * @author 范子才
	 * @param startTime 导出时间段的开始时间
	 * @param endTime 导出时间段的结束时间
	 * @param type 
	 * @param order
	 * @param sort
	 * @return
	 * @version 2016年7月14日 下午11:40:54
	 */
	HSSFWorkbook export(Date startTime, Date endTime, Integer type, String order, String sort);
	
	
	
	/**   
	 * 获取当前用户
	 * @author 范子才
	 * @return
	 * @version 2016年5月7日 下午8:59:17
	 */
	String getUserApplication(String username, int limit, int offset);

	/**   
	 * 获取当前用户的待审批申请
	 * @author 范子才
	 * @param username
	 * @param limit
	 * @param offset
	 * @return
	 * @version 2016年5月8日 上午9:41:04
	 */
	String getUserReviewApplication(String username, Integer limit,
			Integer offset);

	/**   
	 * 新建申请
	 * @author 范子才
	 * @param username
	 * @param sendpeoplename
	 * @param vehicleid
	 * @param driverid
	 * @param applicationreason
	 * @param applicationstartposition
	 * @param applicationendposition
	 * @param applicationstarttime
	 * @param applicationendtime
	 * @param applicationdistance
	 * @param applicationnote
	 * @return
	 * @version 2016年5月9日 上午11:35:45
	 */
	String addApplication(String username,
			String sendpeoplename, Integer vehicleid, Integer driverid,
			String applicationreason, String applicationstartposition,
			String applicationendposition, Date applicationstarttime,
			Date applicationendtime, Float applicationdistance,
			String applicationnote);

	/**   
	 * 取消申请
	 * @author 范子才
	 * @param username
	 * @param applicationid
	 * @return
	 * @version 2016年5月9日 下午9:02:26
	 */
	String cancelApplication(String username, Integer applicationid);

	/**   
	 * 审核
	 * @author 范子才
	 * @param session
	 * @param applicationid
	 * @param result
	 * @return
	 * @version 2016年5月10日 下午3:45:24
	 */
	String review(HttpSession session, Integer applicationid, Integer result);

	/**   
	 * 归还申请车辆
	 * @author 范子才
	 * @param session
	 * @param applicationid
	 * @return
	 * @version 2016年5月11日 上午11:42:02
	 */
	String returnVehicle(HttpSession session, Integer applicationid);
	
}

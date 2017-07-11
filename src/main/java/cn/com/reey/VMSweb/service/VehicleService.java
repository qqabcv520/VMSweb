/**   
 * filename：VehicleService.java
 *   
 * date：2016年5月2日
 * Copyright reey Corporation 2016 
 *   
 */
package cn.com.reey.VMSweb.service;

import java.io.Serializable;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.multipart.MultipartFile;

import cn.com.reey.VMSweb.entity.Vehicle;
import cn.com.reey.VMSweb.util.jsonEntity.JsonData;

public interface VehicleService extends BaseEntityService<Vehicle> {
	
	
	
	
	
	
	/**   
	 * 获取车辆的数据，JSON形式
	 * @author 范子才
	 * @param limit 每页的个数
	 * @param offset 第几页
	 * @return JSON字符串
	 * @version 2016年4月25日 下午17:23:40
	 */
	String getJsonDataString(int limit, int offset, String order, String sort);
	
	
	/**   
	 * 通过车牌号查找车辆
	 * @author 范子才
	 * @param platenumber
	 * @return
	 * @version 2016年5月2日 下午10:58:46
	 */
	JsonData findByPlatenumber(String platenumber);
	
	
	/**   
	 * 添加车辆
	 * @author 范子才
	 * @param request
	 * @param img 表单图片文件
	 * @param platenumber 车牌号
	 * @param info 车辆信息
	 * @param state 车辆状态
	 * @param note 
	 * @return
	 * @version 2016年5月2日 下午10:11:46
	 */
	JsonData addVehicle(HttpServletRequest request, MultipartFile img, String platenumber, String info, Integer state, String note);
	
	
	/**   
	 * 编辑车辆信息
	 * @author 范子才
	 * @param request
	 * @param idList 
	 * @param img 表单图片文件
	 * @param info 车辆信息
	 * @param state 车辆状态
	 * @param note
	 * @return
	 * @version 2016年5月3日 上午10:44:16
	 */
	JsonData editVehicle(HttpServletRequest request, Collection<Integer> idList, MultipartFile img, String info, Integer state, String note);

	
	
}

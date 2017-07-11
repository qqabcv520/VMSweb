/**   
 * filename：Constant.java
 *   
 * date：2016年4月22日
 * Copyright reey Corporation 2016 
 *   
 */
package cn.com.reey.VMSweb.util;
public class Constant {

	public static final String LOGIN_USER_KEY = "LOGIN_USER_KEY";
	public static final String TOKEN = "token";
	

	/**   
	 * EXPORT_TYPE_ALL:导出所有
	 */
	public static final Integer EXPORT_TYPE_ALL = 0;
	/**   
	 * EXPORT_TYPE_PASS:导出已通过的
	 */
	public static final Integer EXPORT_TYPE_PASS = 1;

	/**   
	 * PASS_STATE_NOT_AUDITED:未审核
	 */
	public static final Integer PASS_STATE_NOT_AUDITED = 0;
	/**   
	 * PASS_STATE_YES:已审核通过
	 */
	public static final Integer PASS_STATE_YES = 1;
	/**   
	 * PASS_STATE_NO:审核未通过
	 */
	public static final Integer PASS_STATE_NO = 2;
	/**   
	 * PASS_STATE_NO:申请还车完成
	 */
	public static final Integer PASS_IS_OVER = 3;
	
	/**   
	 * PASS_STATE_NO:车辆可使用
	 */
	public static final Integer VEHICLE_CAN_USE = 0;
	

	/**   
	 * PASS_STATE_NO:车辆使用中
	 */
	public static final Integer VEHICLE_USEING = 1;

	/**   
	 * PASS_STATE_NO:车辆不可用
	 */
	public static final Integer VEHICLE_CAN_NOT_USE = 2;
	
	
	
	//public static final String LOGISTICS_COMPONENT = "LOGISTICS-COMPONENT";
	
}

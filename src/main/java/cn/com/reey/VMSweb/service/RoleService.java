/**   
 * filename：BaseService.java
 *   
 * date：2016年4月19日
 * Copyright reey Corporation 2016 
 *   
 */
package cn.com.reey.VMSweb.service;


import java.util.List;

import cn.com.reey.VMSweb.entity.Role;
import cn.com.reey.VMSweb.util.jsonEntity.JsonData;


public interface RoleService extends BaseEntityService<Role> {
	
	
	/**   
	 * 获取role数据，JSON形式
	 * @author 范子才
	 * @param limit 每页的个数
	 * @param offset 第几页
	 * @return JSON字符串
	 * @version 2016年4月25日 下午17:23:40
	 */
	String getJsonDataString(int limit, int offset, String order, String sort);

	/**   
	 * 添加role
	 * @author 范子才
	 * @param rolename
	 * @param rolenote
	 * @param permission 权限ID数组
	 * @return
	 * @version 2016年5月5日 下午5:05:33
	 */
	JsonData addRole(String rolename, String rolenote, Integer[] permission);

	/**   
	 * 编辑角色
	 * @author 范子才
	 * @param idList
	 * @param rolename
	 * @param rolenote
	 * @param permission
	 * @return
	 * @version 2016年5月5日 下午5:27:58
	 */
	JsonData editRole(List<Integer> idList, String rolename, String rolenote,
			Integer[] permission);
	
}

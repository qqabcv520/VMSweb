/**   
 * filename：BaseService.java
 *   
 * date：2016年4月19日
 * Copyright reey Corporation 2016 
 *   
 */
package cn.com.reey.VMSweb.service;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.multipart.MultipartFile;

import cn.com.reey.VMSweb.entity.Users;
import cn.com.reey.VMSweb.util.jsonEntity.JsonData;


public interface UserService extends BaseEntityService<Users>{
	
	
	/**   
	 * 获取用户的数据，JSON形式
	 * @author 范子才
	 * @param limit 每页的个数
	 * @param offset 第几页
	 * @return JSON字符串
	 * @version 2016年4月25日 下午17:23:40
	 */
	String getJsonDataString(int limit, int offset, String order, String sort);
	
	/**   
	 * 登录
	 * @author 范子才
	 * @param username 用户名
	 * @param password 用户的未加密密码
	 * @param rememberMe
	 * @param session
	 * @return 登陆JSON信息
	 * @version 2016年4月21日 下午3:58:46
	 */
	JsonData login(String username, String password, boolean rememberMe, HttpSession session);
	
	/**   
	 * 登录
	 * @author 范子才
	 * @param username 用户名
	 * @param password 用户的未加密密码
	 * @param rememberMe
	 * @param cid 个推设备码
	 * @param session
	 * @return 登陆JSON信息
	 * @version 2016年4月21日 下午3:58:46
	 */
	JsonData login(String username, String password, boolean rememberMe, String cid, HttpSession session);
	
	/**   
	 * token登录
	 * @author 范子才
	 * @param token
	 * @param session
	 * @return 登陆JSON信息
	 * @version 2016年4月21日 下午3:58:46
	 */
	JsonData loginByToken(String token, HttpSession session);
	
	
	/**   
	 * 获取用户
	 * @author 范子才
	 * @param username
	 * @return Users实体
	 * @version 2016年4月22日 下午7:47:54
	 */
	JsonData findByUsername(String username);

	/**   
	 * 添加用户
	 * @author 范子才
	 * @param request
	 * @param img
	 * @param username
	 * @param workid
	 * @param realname
	 * @param department
	 * @param duty
	 * @param phonenumber
	 * @param roleid
	 * @param note
	 * @return
	 * @version 2016年4月28日 下午6:01:45
	 */
	JsonData addUser(HttpServletRequest request,
			MultipartFile img,
    		String username,
    		String workid,
    		String realname,
    		String department,
    		String duty,
    		String phonenumber,
    		Integer roleid,
    		String note);

	/**   
	 * 编辑用户
	 * @author 范子才
	 * @param request
	 * @param id
	 * @param img
	 * @param pwd
	 * @param workid
	 * @param realname
	 * @param department
	 * @param duty
	 * @param phonenumber
	 * @param roleid
	 * @param note
	 * @return
	 * @version 2016年4月30日 下午9:14:18
	 */
	JsonData editUser(HttpServletRequest request, Collection<Integer> id, MultipartFile img,
			String pwd, String workid, String realname, String department,
			String duty, String phonenumber, Integer roleid,
    		String note);

	/**   
	 * 注销登录
	 * @author 范子才
	 * @param session
	 * @return
	 * @version 2016年5月10日 上午10:59:58
	 */
	String logout(HttpSession session);

	/**   
	 * 编辑手机号
	 * @author 范子才
	 * @param session
	 * @param newPhonenumber
	 * @return
	 * @version 2016年5月10日 下午3:06:02
	 */
	String editUserphonenumber(HttpSession session, String newPhonenumber);

	/**   
	 * 修改密码
	 * @author 范子才
	 * @param username
	 * @param oldUserpwd
	 * @param newUserpwd
	 * @return
	 * @version 2016年5月10日 下午3:08:01
	 */
	String editUserpwd(String username, String oldUserpwd, String newUserpwd);

	/**   
	 * 获取审核人列表
	 * @author 范子才
	 * @param limit
	 * @param offset
	 * @param order
	 * @return
	 * @version 2016年5月10日 下午6:08:37
	 */
	String getSendpeopleList(Integer limit, Integer offset, String order, String sort);

	

	
}

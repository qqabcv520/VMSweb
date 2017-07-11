/**   
 * filename：UserDAO.java
 *   
 * date：2016年4月14日
 * Copyright reey Corporation 2016 
 *   
 */
package cn.com.reey.VMSweb.dao;

import cn.com.reey.VMSweb.entity.Users;


public interface UserDAO extends BaseDAO<Users> {
	Users findByUsername(String username);
	Users findByUsersignid(String usersignid);
}

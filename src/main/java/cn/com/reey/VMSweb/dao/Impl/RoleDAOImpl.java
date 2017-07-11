/**   
 * filename：UserDAOImpl.java
 *   
 * date：2016年4月14日
 * Copyright reey Corporation 2016 
 *   
 */
package cn.com.reey.VMSweb.dao.Impl;

import org.springframework.stereotype.Repository;

import cn.com.reey.VMSweb.dao.RoleDAO;
import cn.com.reey.VMSweb.entity.Role;

@Repository("roleDao")
public class RoleDAOImpl extends BaseDAOImpl<Role> implements RoleDAO {


}
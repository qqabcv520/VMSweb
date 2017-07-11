/**   
 * filename：UserDAOImpl.java
 *   
 * date：2016年4月14日
 * Copyright reey Corporation 2016 
 *   
 */
package cn.com.reey.VMSweb.dao.Impl;

import org.springframework.stereotype.Repository;

import cn.com.reey.VMSweb.dao.PermissionDAO;
import cn.com.reey.VMSweb.dao.RoleDAO;
import cn.com.reey.VMSweb.entity.Permission;
import cn.com.reey.VMSweb.entity.Role;

@Repository("permissionDao")
public class PermissionDAOImpl extends BaseDAOImpl<Permission> implements PermissionDAO {


}
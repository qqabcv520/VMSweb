/**   
 * filename：UserService.java
 *   
 * date：2016年4月12日
 * Copyright reey Corporation 2016 
 *   
 */
package cn.com.reey.VMSweb.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;















import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;

import cn.com.reey.VMSweb.dao.PermissionDAO;
import cn.com.reey.VMSweb.dao.RoleDAO;
import cn.com.reey.VMSweb.entity.Permission;
import cn.com.reey.VMSweb.entity.Role;
import cn.com.reey.VMSweb.entity.Users;
import cn.com.reey.VMSweb.service.RoleService;
import cn.com.reey.VMSweb.util.MyStatus;
import cn.com.reey.VMSweb.util.jsonEntity.JsonData;

@Service
public class RoleServiceImpl extends BaseEntityServiceImpl<Role> implements RoleService {

	@Resource
	private RoleDAO roleDao;
	@Resource
	private PermissionDAO permissionDao;
	
	private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);


	@Override
	public String getJsonDataString(int limit, int offset, String order, String sort) {

		JsonData jsonData = getJsonData(limit, offset, order, sort);
		
		SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
		
		return JSON.toJSONString(jsonData, filter, SerializerFeature.DisableCircularReferenceDetect);
		
	}

	@Transactional
	@Override
	public JsonData addRole(String rolename, String rolenote,
			Integer[] permission) {


		Role role = new Role();
		if(permission != null){
			List<Permission> permissionList;
			DetachedCriteria criteria = DetachedCriteria.forClass(Permission.class);
			criteria.add(Restrictions.in("permissionid", permission));
			permissionList = permissionDao.findEntitysByDetachedCriteria(criteria, Integer.MAX_VALUE, 0);
			role.getPermissions().addAll(permissionList);
			
		}
		
		role.setRolename(rolename);
		role.setRolenote(rolenote);
		roleDao.save(role);
		
		JsonData jsonData = new JsonData();
		jsonData.setStatusCode(MyStatus.OK);
		
		return jsonData;
	}

	@Transactional
	@Override
	public JsonData editRole(List<Integer> idList, String rolename,
			String rolenote, Integer[] permission) {
		
		
		for (Integer id : idList) {
			Role role = roleDao.get(id);
			
			role.getPermissions().clear();
			if(permission != null) {
				DetachedCriteria criteria = DetachedCriteria.forClass(Permission.class);
				criteria.add(Restrictions.in("permissionid", permission));
				List<Permission> permissionList = permissionDao.findEntitysByDetachedCriteria(criteria, Integer.MAX_VALUE, 0);
				
				role.getPermissions().addAll(permissionList);
			}
			if(rolename != null) {
				role.setRolename(rolename);
			}
			if(rolenote != null) {
				role.setRolenote(rolenote);
			}
			roleDao.update(role);
			
		}
		
		JsonData jsonData = new JsonData();
		jsonData.setStatusCode(MyStatus.OK);
		
		return jsonData;
	}
	
	
	
	
	
	

}

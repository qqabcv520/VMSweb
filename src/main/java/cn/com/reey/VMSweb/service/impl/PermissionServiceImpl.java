/**   
 * filename：UserService.java
 *   
 * date：2016年4月12日
 * Copyright reey Corporation 2016 
 *   
 */
package cn.com.reey.VMSweb.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;

import cn.com.reey.VMSweb.entity.Permission;
import cn.com.reey.VMSweb.service.PermissionService;
import cn.com.reey.VMSweb.util.jsonEntity.JsonData;

@Service
public class PermissionServiceImpl extends BaseEntityServiceImpl<Permission> implements PermissionService {

//	@Resource
//	private RoleDAO roleDao;
//	@Resource
//	private PermissionDAO permissionDao;
//	
	private static final Logger logger = LoggerFactory.getLogger(PermissionServiceImpl.class);


	@Override
	public String getJsonDataString(int limit, int offset, String order, String sort) {

		JsonData jsonData = getJsonData(limit, offset, order, sort);
		
		SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
		
		return JSON.toJSONString(jsonData, filter, SerializerFeature.DisableCircularReferenceDetect);
		
	}

	
	
	
	

}

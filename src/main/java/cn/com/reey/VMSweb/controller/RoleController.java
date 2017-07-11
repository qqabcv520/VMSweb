/**   
 * filename：UserController.java
 *   
 * date：2016年4月13日
 * Copyright reey Corporation 2016 
 *   
 */
package cn.com.reey.VMSweb.controller;



import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.reey.VMSweb.entity.Permission;
import cn.com.reey.VMSweb.service.PermissionService;
import cn.com.reey.VMSweb.service.RoleService;
import cn.com.reey.VMSweb.util.AuthPassport;
import cn.com.reey.VMSweb.util.jsonEntity.JsonData;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;


@AuthPassport("role_info")
@Controller   
@RequestMapping("/main/role")
public class RoleController {

	@Resource
    private RoleService roleService;
	@Resource
    private PermissionService permissionService;

	@ResponseBody
	@RequestMapping(value = "/data.do", produces="application/json;charset=UTF-8")
    public String data(@RequestParam Integer limit, 
    		@RequestParam Integer offset, 
    		@RequestParam(defaultValue="asc")String order, 
    		String sort){
        return roleService.getJsonDataString(limit, offset, order, sort);
    }

	@AuthPassport("role_edit")
	@ResponseBody
	@RequestMapping(value = "/delete.do", produces="application/json;charset=UTF-8")
    public String delete(@RequestParam("idArray[]")Integer[] idArray){
		JsonData jsondata =  roleService.deleteByIdArray(idArray);
        return JSON.toJSONString(jsondata);
    }
	

	@AuthPassport("role_edit")
	@ResponseBody
	@RequestMapping(value = "/add.do", produces="text/plain;charset=UTF-8")
    public String add(@RequestParam String rolename,
    		@RequestParam String rolenote,
    		@RequestParam(required = false) Integer[] permission){
		
        JsonData jsonData = roleService.addRole(rolename, rolenote, permission);
		return JSON.toJSONString(jsonData);
    }

	@AuthPassport("role_edit")
	@ResponseBody
	@RequestMapping(value = "/edit.do", produces="text/plain;charset=UTF-8")
    public String edit(@RequestParam String idArray,
    		@RequestParam String rolename, String rolenote,
    		@RequestParam(required = false) Integer[] permission){
		System.out.println(permission);
		List<Integer> idList = JSONArray.parseArray(idArray, Integer.class);
		JsonData jsonData = roleService.editRole(idList, rolename, rolenote, permission);
		return JSON.toJSONString(jsonData);
    }
	

	@RequestMapping(value = "/management.do")
    public String managementContent(ModelMap model){
    	model.addAttribute("content", "role-management");
		JsonData permission = permissionService.getAll();
    	model.addAttribute("permissionList", permission.getData());
        return "index";
    }
	
    @RequestMapping(value = "/management.do", params="isOnlyContent=true")
    public String management(ModelMap model){
		JsonData permission = permissionService.getAll();
    	model.addAttribute("permissionList", permission.getData());
        return "include/role-management";
    }
    
 
}

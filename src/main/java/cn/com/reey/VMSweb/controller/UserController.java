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
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.com.reey.VMSweb.service.RoleService;
import cn.com.reey.VMSweb.service.UserService;
import cn.com.reey.VMSweb.util.AuthPassport;
import cn.com.reey.VMSweb.util.jsonEntity.JsonData;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;


@AuthPassport("user_info")
@Controller   
@RequestMapping("/main/user")
public class UserController {

	@Resource
    private UserService userService;
	@Resource
    private RoleService roleService;


	@ResponseBody
	@RequestMapping(value = "/data.do", produces="application/json;charset=UTF-8")
    public String data(@RequestParam Integer limit, 
    		@RequestParam Integer offset, 
    		@RequestParam(defaultValue="asc")String order, 
    		String sort){
        return userService.getJsonDataString(limit, offset, order, sort);
    }

	@AuthPassport("user_edit")
	@ResponseBody
	@RequestMapping(value = "/delete.do", produces="application/json;charset=UTF-8")
    public String delete(@RequestParam("idArray[]")Integer[] idArray){
		JsonData jsondata =  userService.deleteByIdArray(idArray);
        return JSON.toJSONString(jsondata);
    }
	
	
	

	@AuthPassport("user_edit")
	@ResponseBody
	@RequestMapping(value = "/add.do", consumes="multipart/form-data", produces="text/plain;charset=UTF-8")
    public String add(
    		HttpServletRequest request,
    		@RequestParam MultipartFile img,
    		@RequestParam String username,
    		@RequestParam String workid,
    		@RequestParam String realname,
    		@RequestParam String department,
    		@RequestParam String duty,
    		@RequestParam String phonenumber,
    		@RequestParam Integer roleid,
    		String note){
		
		
		userService.addUser(request, img, username, workid, realname, 
				department, duty, phonenumber, roleid, note);
		
        JsonData jsonData = new JsonData();
        jsonData.setStatusCode(0);
		return JSON.toJSONString(jsonData);
    }

	@AuthPassport("user_edit")
	@ResponseBody
	@RequestMapping(value = "/edit.do", consumes="multipart/form-data", produces="text/plain;charset=UTF-8")
    public String edit(
    		HttpServletRequest request,
    		@RequestParam String idArray,
    		MultipartFile img,
    		String pwd,
    		String workid,
    		String realname,
    		String department,
    		String duty,
    		String phonenumber,
    		Integer roleid,
    		String note){
		
		List<Integer> idList = JSONArray.parseArray(idArray, Integer.class);
		JsonData jsonData = userService.editUser(request, idList, img, pwd, workid, realname, 
				department, duty, phonenumber, roleid, note);
		return JSON.toJSONString(jsonData);
    }
	
	
	
	@RequestMapping(value = "/management.do")
    public String managementContent(ModelMap model){
    	model.addAttribute("content", "user-management");
    	JsonData role = roleService.getAll();
    	model.addAttribute("roleList", role.getData());
        return "index";
    }
	
    @RequestMapping(value = "/management.do", params="isOnlyContent=true")
    public String management(ModelMap model){
    	
    	JsonData role = roleService.getAll();
    	
    	model.addAttribute("roleList", role.getData());
        return "include/user-management";
    }
    
 
}

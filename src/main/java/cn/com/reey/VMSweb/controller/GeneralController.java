/**   
 * filename：UserController.java
 *   
 * date：2016年4月13日
 * Copyright reey Corporation 2016 
 *   
 */
package cn.com.reey.VMSweb.controller;


import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.reey.VMSweb.service.ApplicationService;
import cn.com.reey.VMSweb.service.DriverService;
import cn.com.reey.VMSweb.service.UserService;
import cn.com.reey.VMSweb.service.VehicleService;
import cn.com.reey.VMSweb.util.AuthPassport;
import cn.com.reey.VMSweb.util.Constant;
import cn.com.reey.VMSweb.util.GeTuiUtils;
import cn.com.reey.VMSweb.util.UserSession;
import cn.com.reey.VMSweb.util.jsonEntity.JsonData;

import com.alibaba.fastjson.JSON;



@Controller  
public class GeneralController {
   
	@Resource
	UserService userService;
	@Resource
	ApplicationService applicationService;
	@Resource 
	VehicleService vehicleService;
	@Resource 
	DriverService driverService;
	@Resource
	private GeTuiUtils geTuiUtils;
	
	@AuthPassport
	@RequestMapping(value="/main/index.do")
    public String index(){
		return "index";
    }
	
	
	@RequestMapping(value="/loginpage.do", method = RequestMethod.GET)
    public String loginpage(){
		
		return "loginpage";
    }
	
//	@ResponseBody
//	@RequestMapping(value = "/login.do", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
//    public String login(HttpSession session, @RequestParam String username, @RequestParam String password, 
//    		@RequestParam(required = false,  defaultValue = "false") boolean rememberMe){
//		JsonData jsondata = userService.login(username, password, rememberMe, session);
//        return JSON.toJSONString(jsondata);
//    }
	@ResponseBody
	@RequestMapping(value = "/login.do", produces="application/json;charset=UTF-8")
    public String login(HttpSession session, @RequestParam String username, @RequestParam String password, 
    		@RequestParam(required = false,  defaultValue = "false") boolean rememberMe,
    		@RequestParam(required = false,  defaultValue = "") String cid){
		//geTuiUtils.sendSingle("9a732e83cdccc8af779f58d30ec9d6b5", "消息提示", "您的申请已被处理", "您的申请已被处理");
		JsonData jsondata = userService.login(username, password, rememberMe, cid, session);
        return JSON.toJSONString(jsondata);
    }

	@AuthPassport
	@ResponseBody
	@RequestMapping(value = "/usercenter.do", produces="application/json;charset=UTF-8")
    public String usercenter(HttpSession session){
		UserSession us = (UserSession) session.getAttribute(Constant.LOGIN_USER_KEY);
		JsonData jsondata = userService.findByUsername(us.getUsername());
        return JSON.toJSONString(jsondata);
    }
	
	@AuthPassport
	@ResponseBody
	@RequestMapping(value = "/userapplication.do", produces="application/json;charset=UTF-8")
    public String userapplication(HttpSession session,
    		@RequestParam Integer limit, 
    		@RequestParam Integer offset){
		UserSession us = (UserSession) session.getAttribute(Constant.LOGIN_USER_KEY);
        return applicationService.getUserApplication(us.getUsername(), limit, offset);
    }

	@AuthPassport
	@ResponseBody
	@RequestMapping(value = "/userreviewapplication.do", produces="application/json;charset=UTF-8")
    public String userreviewapplication(HttpSession session,
    		@RequestParam Integer limit, 
    		@RequestParam Integer offset){
		UserSession us = (UserSession) session.getAttribute(Constant.LOGIN_USER_KEY);
        return applicationService.getUserReviewApplication(us.getUsername(), limit, offset);
    }
	
	
	@AuthPassport
	@ResponseBody
	@RequestMapping(value = "/addApplication.do", produces="application/json;charset=UTF-8")
    public String addApplication(HttpSession session, 
    		@RequestParam String sendpeoplename, 
    		@RequestParam Integer vehicleid, 
    		@RequestParam Integer driverid, 
    		@RequestParam String applicationreason, 
    		@RequestParam String applicationstartposition, 
    		@RequestParam String applicationendposition, 
    		@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")Date applicationstarttime,
    		@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")Date applicationendtime,
    		@RequestParam Float applicationdistance, 
    		String applicationnote) {
		UserSession us = (UserSession) session.getAttribute(Constant.LOGIN_USER_KEY);
        return applicationService.addApplication(us.getUsername(), sendpeoplename, vehicleid, driverid, 
    			applicationreason, applicationstartposition, applicationendposition, applicationstarttime,
    			applicationendtime, applicationdistance, applicationnote);
    }
	
	@AuthPassport
	@ResponseBody
	@RequestMapping(value = "/cancelApplication.do", produces="application/json;charset=UTF-8")
    public String cancelApplication(HttpSession session, 
    		@RequestParam Integer applicationid) {
		UserSession us = (UserSession) session.getAttribute(Constant.LOGIN_USER_KEY);
        return applicationService.cancelApplication(us.getUsername(), applicationid);
    }
	

	@AuthPassport
	@ResponseBody
	@RequestMapping(value = "/vehicleList.do", produces="application/json;charset=UTF-8")
    public String vehicleList(@RequestParam Integer limit, 
    		@RequestParam Integer offset, 
    		@RequestParam(defaultValue="asc")String order, 
    		String sort){
        return vehicleService.getJsonDataString(limit, offset, order, sort);
    }
	
	@AuthPassport
	@ResponseBody
	@RequestMapping(value = "/driverList.do", produces="application/json;charset=UTF-8")
    public String driverList(@RequestParam Integer limit, 
    		@RequestParam Integer offset, 
    		@RequestParam(defaultValue="asc")String order, 
    		String sort){
        return driverService.getJsonDataString(limit, offset, order, sort);
    }
	
	@AuthPassport
	@ResponseBody
	@RequestMapping(value = "/logout.do", produces="application/json;charset=UTF-8")
    public String logout(HttpSession session){
        return userService.logout(session);
    }
	
	@AuthPassport
	@ResponseBody
	@RequestMapping(value = "/editUserphonenumber.do", produces="application/json;charset=UTF-8")
    public String editUserphonenumber(HttpSession session, @RequestParam String newUserphonenumber){
        return userService.editUserphonenumber(session, newUserphonenumber);
    }
	
	@ResponseBody
	@RequestMapping(value = "/editUserpwd.do", produces="application/json;charset=UTF-8")
    public String editUserpwd(@RequestParam String username, @RequestParam String oldUserpwd, @RequestParam String newUserpwd){
        return userService.editUserpwd(username, oldUserpwd, newUserpwd);
    }
	
	
	@AuthPassport("review")
	@ResponseBody
	@RequestMapping(value = "/review.do", produces="application/json;charset=UTF-8")
    public String review(HttpSession session, @RequestParam Integer applicationid, @RequestParam Integer result){
        return applicationService.review(session, applicationid, result);
    }
	
	
	@AuthPassport
	@ResponseBody
	@RequestMapping(value = "/sendpeopleList.do", produces="application/json;charset=UTF-8")
    public String sendpeopleList(@RequestParam Integer limit, 
    		@RequestParam Integer offset, 
    		@RequestParam(defaultValue="asc")String order,
    		String sort){
        return userService.getSendpeopleList(limit, offset, order, sort);
    }
	
	
	@AuthPassport
	@ResponseBody
	@RequestMapping(value = "/returnVehicle.do", produces="application/json;charset=UTF-8")
    public String returnVehicle(HttpSession session, @RequestParam Integer applicationid){
        return applicationService.returnVehicle(session, applicationid);
    }
	
}

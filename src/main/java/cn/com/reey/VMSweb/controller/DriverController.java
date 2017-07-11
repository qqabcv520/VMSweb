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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import cn.com.reey.VMSweb.service.DriverService;
import cn.com.reey.VMSweb.util.AuthPassport;
import cn.com.reey.VMSweb.util.jsonEntity.JsonData;


@AuthPassport
@Controller    
@RequestMapping("/main/driver")
public class DriverController {

	@Resource
    private DriverService driverService;

	@ResponseBody
	@RequestMapping(value = "/data.do", produces="application/json;charset=UTF-8")
    public String data(@RequestParam Integer limit, 
    		@RequestParam Integer offset, 
    		@RequestParam(defaultValue="asc")String order, 
    		String sort){
        return driverService.getJsonDataString(limit, offset, order, sort);
    }

	@AuthPassport("driver_edit")
	@ResponseBody
	@RequestMapping(value = "/delete.do", produces="application/json;charset=UTF-8")
    public String delete(@RequestParam("idArray[]")Integer[] idArray){
		JsonData jsondata =  driverService.deleteByIdArray(idArray);
        return JSON.toJSONString(jsondata);
    }
	

	@AuthPassport("driver_edit")
	@ResponseBody
	@RequestMapping(value = "/add.do", produces="text/plain;charset=UTF-8")
    public String add(@RequestParam String drivername, String drivernote){
		
        JsonData jsonData = driverService.addDriver(drivername, drivernote);
		return JSON.toJSONString(jsonData);
    }

	@AuthPassport("driver_edit")
	@ResponseBody
	@RequestMapping(value = "/edit.do", produces="text/plain;charset=UTF-8")
    public String edit(@RequestParam String idArray,
    		@RequestParam String drivername, String drivernote){
		
		List<Integer> idList = JSONArray.parseArray(idArray, Integer.class);
		JsonData jsonData = driverService.editDriver(idList, drivername, drivernote);
		return JSON.toJSONString(jsonData);
    }
	

	@RequestMapping(value = "/management.do")
    public String managementContent(ModelMap model){
    	model.addAttribute("content", "driver-management");
        return "index";
    }
	
    @RequestMapping(value = "/management.do", params="isOnlyContent=true")
    public String management(ModelMap model){
        return "include/driver-management";
    }
    
 
}

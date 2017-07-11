/**   
 * filename：UserController.java
 *   
 * date：2016年4月13日
 * Copyright reey Corporation 2016 
 *   
 */
package cn.com.reey.VMSweb.controller;



import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.reey.VMSweb.service.ApplicationService;
import cn.com.reey.VMSweb.service.DriverService;
import cn.com.reey.VMSweb.util.AuthPassport;
import cn.com.reey.VMSweb.util.jsonEntity.JsonData;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;


@AuthPassport("application_info")
@Controller
@RequestMapping("/main/application")
public class ApplicationController {

	@Resource
    private ApplicationService applicationService;

	@ResponseBody
	@RequestMapping(value = "/data.do", produces="application/json;charset=UTF-8")
    public String data(@RequestParam Integer limit, 
    		@RequestParam Integer offset, 
    		@RequestParam(defaultValue="asc")String order,
    		String sort){
        return applicationService.getJsonDataString(limit, offset, order, sort);
    }
	
	@ResponseBody
	@RequestMapping(value = "/delete.do", produces="application/json;charset=UTF-8")
    public String delete(@RequestParam("idArray[]")Integer[] idArray){
		JsonData jsondata =  applicationService.deleteByIdArray(idArray);
        return JSON.toJSONString(jsondata);
    }
	
	
	@ResponseBody
	@RequestMapping(value = "/export.do", produces="application/json;charset=UTF-8")
    public void export(@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date startTime,
    		@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date endTime,
    		@RequestParam(defaultValue="0") Integer type,
    		HttpServletResponse response,
    		@RequestParam(defaultValue="asc")String order,
    		String sort){
		
		HSSFWorkbook workbook =  applicationService.export(startTime, endTime, type, order, sort);
		response.setContentType("application/x-msdownload");
		response.setHeader("Content-Disposition", "attachment;filename=export.xls");
		
		try {
			workbook.write(response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	

//	@ResponseBody
//	@RequestMapping(value = "/add.do", produces="text/plain;charset=UTF-8")
//    public String add(@RequestParam String drivername, String drivernote){
//		
//        JsonData jsonData = applicationService.addApplication(drivername, drivernote);
//		return JSON.toJSONString(jsonData);
//    }
//	
//	@ResponseBody
//	@RequestMapping(value = "/edit.do", produces="text/plain;charset=UTF-8")
//    public String edit(@RequestParam String idArray,
//    		@RequestParam String drivername, String drivernote){
//		
//		List<Integer> idList = JSONArray.parseArray(idArray, Integer.class);
//		JsonData jsonData = applicationService.editApplication(idList, drivername, drivernote);
//		return JSON.toJSONString(jsonData);
//    }
//	

	@RequestMapping(value = "/management.do")
    public String managementContent(ModelMap model){
    	model.addAttribute("content", "application-management");
        return "index";
    }
	
    @RequestMapping(value = "/management.do", params="isOnlyContent=true")
    public String management(ModelMap model){
        return "include/application-management";
    }
    
 
}

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import cn.com.reey.VMSweb.service.VehicleService;
import cn.com.reey.VMSweb.util.AuthPassport;
import cn.com.reey.VMSweb.util.jsonEntity.JsonData;

@AuthPassport
@Controller
@RequestMapping("/main/vehicle")
public class VehicleController {

	@Resource
    private VehicleService vehicleService;

	
	@ResponseBody
	@RequestMapping(value = "/data.do", produces="application/json;charset=UTF-8")
    public String data(@RequestParam Integer limit, 
    		@RequestParam Integer offset, 
    		@RequestParam(defaultValue="asc")String order, 
    		String sort){
        return vehicleService.getJsonDataString(limit, offset, order, sort);
    }

	@AuthPassport("vehicle_edit")
	@ResponseBody
	@RequestMapping(value = "/delete.do", produces="application/json;charset=UTF-8")
    public String delete(@RequestParam("idArray[]")Integer[] idArray){
		JsonData jsondata =  vehicleService.deleteByIdArray(idArray);
        return JSON.toJSONString(jsondata);
    }
	

	@AuthPassport("vehicle_edit")
	@ResponseBody
	@RequestMapping(value = "/add.do", consumes="multipart/form-data", produces="text/plain;charset=UTF-8")
    public String add(
    		HttpServletRequest request,
    		@RequestParam MultipartFile img,
    		@RequestParam String platenumber,
    		@RequestParam String info,
    		@RequestParam(defaultValue="0")Integer state,
    		String note){
		
		
		vehicleService.addVehicle(request, img, platenumber, info, state, note);
		
        JsonData jsonData = new JsonData();
        jsonData.setStatusCode(0);
		return JSON.toJSONString(jsonData);
    }

	@AuthPassport("vehicle_edit")
	@ResponseBody
	@RequestMapping(value = "/edit.do", consumes="multipart/form-data", produces="text/plain;charset=UTF-8")
    public String edit(
    		HttpServletRequest request,
    		@RequestParam String idArray,
    		MultipartFile img,
    		@RequestParam String info,
    		Integer state,
    		String note){
		
		List<Integer> idList = JSONArray.parseArray(idArray, Integer.class);
		JsonData jsonData = vehicleService.editVehicle(request, idList, img, info, state, note);
		return JSON.toJSONString(jsonData);
    }
	
	@RequestMapping(value = "/management.do")
    public String managementContent(ModelMap model){
    	model.addAttribute("content", "vehicle-management");
        return "index";
    }
	
    @RequestMapping(value = "/management.do", params="isOnlyContent=true")
    public String management(ModelMap model){
        return "include/vehicle-management";
    }
    
 
}

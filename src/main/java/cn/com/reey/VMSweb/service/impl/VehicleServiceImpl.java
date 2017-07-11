/**   
 * filename：UserService.java
 *   
 * date：2016年4月12日
 * Copyright reey Corporation 2016 
 *   
 */
package cn.com.reey.VMSweb.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import cn.com.reey.VMSweb.dao.VehicleDAO;
import cn.com.reey.VMSweb.entity.Vehicle;
import cn.com.reey.VMSweb.service.VehicleService;
import cn.com.reey.VMSweb.util.Constant;
import cn.com.reey.VMSweb.util.MyStatus;
import cn.com.reey.VMSweb.util.MyUtils;
import cn.com.reey.VMSweb.util.UserSession;
import cn.com.reey.VMSweb.util.jsonEntity.JsonData;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;

@Service
public class VehicleServiceImpl extends BaseEntityServiceImpl<Vehicle> implements VehicleService {

	@Resource
	private VehicleDAO vehicleDao;
	@Resource
	private String uploadPath = "/upload";
	@Resource
	private String vehicleImgFolder = "/vehicleImg";
	
	
	private static final Logger logger = LoggerFactory.getLogger(VehicleServiceImpl.class);
	
	
	
	
	
	
	@Override
	public String getJsonDataString(int limit, int offset, String order, String sort){
		
		JsonData jsonData =  getJsonData(limit, offset, order, sort);

		SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
		
		return JSON.toJSONString(jsonData, filter, SerializerFeature.DisableCircularReferenceDetect);
	}





	@Transactional
	@Override
	public JsonData addVehicle(HttpServletRequest request, MultipartFile img,
			String platenumber, String info, Integer state, String note) {


		JsonData jsonData = new JsonData();
		Vehicle vehicle = new Vehicle();
		
		
		if(vehicleDao.findByPlatenumber(platenumber) != null) {
			jsonData.setStatusCode(MyStatus.VEHICLE_PLATE_NUMBER_EXISTED);
			logger.info(MyStatus.codeToDescription(MyStatus.VEHICLE_PLATE_NUMBER_EXISTED));
			return jsonData;
        }
		vehicle.setVehicleplatenumber(platenumber);
		
		
		String of = img.getOriginalFilename();
		String Suffix = of.substring(of.lastIndexOf(".")+1, of.length());

		if((!"jpg".equalsIgnoreCase(Suffix)) && (!"jpeg".equalsIgnoreCase(Suffix)) && (!"gif".equalsIgnoreCase(Suffix)) && (!"png".equalsIgnoreCase(Suffix)) && (!"bmp".equalsIgnoreCase(Suffix))) {
			jsonData.setStatusCode(MyStatus.FILE_SUFFIX_EXCEPTION);
			UserSession us = (UserSession) request.getSession().getAttribute(Constant.LOGIN_USER_KEY);
			logger.info(us.getUsername()+MyStatus.codeToDescription(MyStatus.FILE_SUFFIX_EXCEPTION));
			return jsonData;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String webappDir = uploadPath + "/" + dateFormat.format(new Date()) + vehicleImgFolder;
		String randName = "" + System.nanoTime() + new Random().nextInt() + "." + Suffix;
        String realDir = request.getServletContext().getRealPath(webappDir);
		
		File dirFile = new File(realDir);  
        if(!dirFile.exists()){  
        	dirFile.mkdirs();  
        }  
		File targetFile = new File(realDir, randName);
        //保存  
        try {  
        	img.transferTo(targetFile);
        } catch (Exception e) {
        	logger.error("文件上传异常：" + e.toString());
        	jsonData.setStatusCode(MyStatus.SERVICE_ERROR);
			return jsonData;
        }  
        
        
        vehicle.setVehiclephoto(webappDir + "/" + randName);
        
        vehicle.setVehicleinfo(info);
        vehicle.setVehiclestate(state);
        vehicle.setVehiclenote(note);  
        
        vehicleDao.save(vehicle);
        
        jsonData.setStatusCode(MyStatus.OK);
        logger.info("添加车辆：" + platenumber);
		return jsonData;
	}






	@Override
	public JsonData findByPlatenumber(String platenumber) {
		JsonData jsonData = new JsonData();
		jsonData.setData(vehicleDao.findByPlatenumber(platenumber));
		jsonData.setStatusCode(MyStatus.OK);
		
		return jsonData;
	}




	@Transactional
	@Override
	public JsonData editVehicle(HttpServletRequest request,
			Collection<Integer> idArr, MultipartFile img, 
			String info, Integer state, String note) {

		JsonData jsonData = new JsonData();

		String webappDir = null;
		String randName = null;
		
		if(img != null) {
			String of = img.getOriginalFilename();
			
			if(!MyUtils.isEmptyOrNull(of)) {
				String Suffix = of.substring(of.lastIndexOf(".")+1, of.length());

				
				if((!"jpg".equalsIgnoreCase(Suffix)) && (!"jpeg".equalsIgnoreCase(Suffix)) && (!"gif".equalsIgnoreCase(Suffix)) && (!"png".equalsIgnoreCase(Suffix)) && (!"bmp".equalsIgnoreCase(Suffix))) {
					jsonData.setStatusCode(MyStatus.FILE_SUFFIX_EXCEPTION);
					UserSession us = (UserSession) request.getSession().getAttribute(Constant.LOGIN_USER_KEY);
					logger.info(us.getUsername()+MyStatus.codeToDescription(MyStatus.FILE_SUFFIX_EXCEPTION));
					return jsonData;
				} else {
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					webappDir = uploadPath + "/" + dateFormat.format(new Date()) + vehicleImgFolder;
					randName = "" + System.nanoTime() + new Random().nextInt() + "." + Suffix;
			        String realDir = request.getServletContext().getRealPath(webappDir);
					
					File dirFile = new File(realDir);  
			        if(!dirFile.exists()){  
			        	dirFile.mkdirs();  
			        }  
					
					
					File targetFile = new File(realDir, randName);
			        //保存  
			        try {  
			        	img.transferTo(targetFile);
			        } catch (Exception e) {
			        	logger.error("文件上传异常：" + e.toString());
			        	jsonData.setStatusCode(MyStatus.SERVICE_ERROR);
						return jsonData;
			        }  
				}
			}
		}
		
		
		for (Integer id : idArr) {
			
			Vehicle vehicle = vehicleDao.get(id);
			
			
			if(webappDir != null && randName != null) {
				vehicle.setVehiclephoto(webappDir + "/" + randName);
			}
			
			
	        if(!MyUtils.isEmptyOrNull(info)) {
	        	vehicle.setVehicleinfo(info);
	        }
	        if(state != null) {
	        	vehicle.setVehiclestate(state);
	        }
	        if(!MyUtils.isEmptyOrNull(note)) {
	        	vehicle.setVehiclenote(note);
	        }
	        vehicleDao.update(vehicle);
		}
        jsonData.setStatusCode(MyStatus.OK);
        logger.info("编辑车辆完成");
		return jsonData;
		
		
	}





	
	
	
	
	

}

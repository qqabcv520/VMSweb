/**   
 * filename：UserService.java
 *   
 * date：2016年4月12日
 * Copyright reey Corporation 2016 
 *   
 */
package cn.com.reey.VMSweb.service.impl;

import java.util.Collection;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.reey.VMSweb.dao.DriverDAO;
import cn.com.reey.VMSweb.entity.Driver;
import cn.com.reey.VMSweb.service.DriverService;
import cn.com.reey.VMSweb.util.MyStatus;
import cn.com.reey.VMSweb.util.jsonEntity.JsonData;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;

@Service
public class DriverServiceImpl extends BaseEntityServiceImpl<Driver> implements DriverService {

	@Resource
	private DriverDAO driverDao;
	
	
	private static final Logger logger = LoggerFactory.getLogger(DriverServiceImpl.class);
	
	
	
	@Override
	public String getJsonDataString(int limit, int offset, String order, String sort){
		
		JsonData jsonData =  getJsonData(limit, offset, order, sort);

		SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
		
		filter.getExcludes().add("applications");
		
		return JSON.toJSONString(jsonData, filter, SerializerFeature.DisableCircularReferenceDetect);
	}





	@Transactional
	@Override
	public JsonData addDriver(String drivername, String drivernote) {


		JsonData jsonData = new JsonData();
		Driver driver = new Driver();
		
		driver.setDrivername(drivername);
		driver.setDrivernote(drivernote);
        
        driverDao.save(driver);
        
        jsonData.setStatusCode(MyStatus.OK);
        logger.info("添加司机：" + drivername);
		return jsonData;
	}

	@Transactional
	@Override
	public JsonData editDriver(Collection<Integer> idArr, String drivername, String drivernote) {
		JsonData jsonData = new JsonData();
		
		for (Integer id : idArr) {
			Driver driver = driverDao.get(id);
			driver.setDrivername(drivername);
			driver.setDrivernote(drivernote);
	        
	        driverDao.update(driver);
		}
        jsonData.setStatusCode(MyStatus.OK);
        logger.info("编辑司机完成");
		return jsonData;
	}

}

/**   
 * filename：UserService.java
 *   
 * date：2016年4月12日
 * Copyright reey Corporation 2016 
 *   
 */
package cn.com.reey.VMSweb.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.util.CellRangeAddress;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.reey.VMSweb.dao.ApplicationDAO;
import cn.com.reey.VMSweb.dao.UserDAO;
import cn.com.reey.VMSweb.dao.VehicleDAO;
import cn.com.reey.VMSweb.entity.Application;
import cn.com.reey.VMSweb.entity.Driver;
import cn.com.reey.VMSweb.entity.Users;
import cn.com.reey.VMSweb.entity.Vehicle;
import cn.com.reey.VMSweb.service.ApplicationService;
import cn.com.reey.VMSweb.util.Constant;
import cn.com.reey.VMSweb.util.GeTuiUtils;
import cn.com.reey.VMSweb.util.MyStatus;
import cn.com.reey.VMSweb.util.MyUtils;
import cn.com.reey.VMSweb.util.UserSession;
import cn.com.reey.VMSweb.util.jsonEntity.JsonData;
import cn.com.reey.VMSweb.util.jsonEntity.TableData;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;

@Service
public class ApplicationServiceImpl extends BaseEntityServiceImpl<Application> implements ApplicationService {

	@Resource
	private UserDAO userDao;
	@Resource
	private ApplicationDAO applicationDao;
	@Resource
	private VehicleDAO vehicleDAO;
	@Resource
	private GeTuiUtils geTuiUtils;
	
	
	private static final Logger logger = LoggerFactory.getLogger(ApplicationServiceImpl.class);
	
	
	
	
	
	
	@Override
	public String getJsonDataString(int limit, int offset, String order, String sort){
		
		JsonData jsonData =  getJsonData(limit, offset, order, sort);

		SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
		
		return JSON.toJSONString(jsonData, filter, SerializerFeature.DisableCircularReferenceDetect);
	}



	@Override
	public HSSFWorkbook export(Date startTime, Date endTime, Integer type, String order, String sort) {
		
		// 第一步，创建一个webbook，对应一个Excel文件  
        HSSFWorkbook workbook = new HSSFWorkbook();  
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
        HSSFSheet sheet = workbook.createSheet("学生表一");  
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
        // 第四步，创建单元格，并设置值表头 设置表头居中  
        HSSFCellStyle style = workbook.createCellStyle();  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个左右居中格式  
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//上下居中    
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);//下边框     
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框    
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框    
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框 
        style.setWrapText(true);//自动换行
        
        sheet.setColumnWidth(0, 2600); //设置列宽
        sheet.setColumnWidth(1, 2700); //设置列宽
        sheet.setColumnWidth(2, 3000); //设置列宽
        sheet.setColumnWidth(3, 3000); //设置列宽
        sheet.setColumnWidth(4, 4000); //设置列宽
        sheet.setColumnWidth(5, 1800); //设置列宽
        sheet.setColumnWidth(6, 1800); //设置列宽
        sheet.setColumnWidth(7, 1800); //设置列宽
        sheet.setColumnWidth(8, 2300); //设置列宽

        sheet.setRepeatingRows(new CellRangeAddress(0, 1, 0, 8));//打印时每页自动添加表头
        
        writeTableHead(sheet, 0, style);
        
        
        
        // 第五步，写入实体数据 实际应用中这些数据从数据库得到，  
        List<Application> list = applicationDao.getExportData(startTime, endTime, type, order, sort);  
        for (int i = 0; i < list.size(); i++)  
        {  
        	HSSFRow row = sheet.createRow((int) i+2);
            Application application = list.get(i);
            // 第四步，创建单元格，并设置值  
            HSSFCell cell = row.createCell(0);   
            cell.setCellStyle(style); 
            cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(application.getApplicationstarttime()));  
            cell = row.createCell(1); 
            cell.setCellStyle(style); 
            cell.setCellValue(application.getVehicle().getVehicleplatenumber());
            cell = row.createCell(2); 
            cell.setCellStyle(style); 
            cell.setCellValue(application.getApplicationstartposition());
            cell = row.createCell(3); 
            cell.setCellStyle(style); 
            cell.setCellValue(application.getApplicationendposition());
            cell = row.createCell(4); 
            cell.setCellStyle(style); 
            cell.setCellValue(application.getApplicationreason());
            cell = row.createCell(5); 
            cell.setCellStyle(style); 
            cell.setCellValue(application.getApplicationdistance());
            cell = row.createCell(6); 
            cell.setCellStyle(style); 
            cell.setCellValue(application.getUsersByApplicationuser().getUserrealname());
            cell = row.createCell(7); 
            cell.setCellStyle(style); 
            cell.setCellValue(application.getUsersByApplicationsendpeople().getUserrealname());
            cell = row.createCell(8); 
            cell.setCellStyle(style); 
            cell.setCellValue(application.getApplicationnote());
            
        }  
        // 第六步，将文件存到指定位置  
//        try  
//        {  
//            FileOutputStream fout = new FileOutputStream("E:/students.xls");  
//            workbook.write(fout);
//            fout.close();
//        }  
//        catch (Exception e)  
//        {  
//            e.printStackTrace();  
//        }  
	    
		return workbook;
	}


	
	/**   
	 * 写从指定行开始写表头
	 * @author 范子才
	 * @param sheet
	 * @param rowNum
	 * @param style
	 * @return 返回表头高度
	 * @version 2016年6月29日 下午10:42:34
	 */
	private int writeTableHead(HSSFSheet sheet, int rowNum, HSSFCellStyle style) {
		HSSFRow row = sheet.createRow(rowNum); 
        HSSFRow row1 = sheet.createRow(rowNum+1);
        HSSFCell cell;
        cell = row.createCell(0);
        cell.setCellValue("日期");  
        cell.setCellStyle(style);
        cell = row1.createCell(0);
        cell.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum+1, 0, 0));
        cell = row.createCell(1);
        cell.setCellValue("车牌");
        cell.setCellStyle(style); 
        cell = row1.createCell(1);
        cell.setCellStyle(style);  
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum+1, 1, 1));
        cell = row.createCell(2);  
        cell.setCellValue("地点");  
        cell.setCellStyle(style); 
        cell = row.createCell(3);
        cell.setCellStyle(style);   
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 2, 3));
        
        cell = row1.createCell(2);
        cell.setCellValue("行驶路线"); 
        cell.setCellStyle(style);  
        cell = row1.createCell(3);
        cell.setCellStyle(style);  
        sheet.addMergedRegion(new CellRangeAddress(rowNum+1, rowNum+1, 2, 3));
        cell = row.createCell(4);  
        cell.setCellValue("用车事由");  
        cell.setCellStyle(style); 
        cell = row1.createCell(4);
        cell.setCellStyle(style); 
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum+1, 4, 4));
        cell = row.createCell(5);  
        cell.setCellValue("公里数");  
        cell.setCellStyle(style); 
        cell = row1.createCell(5);
        cell.setCellStyle(style); 
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum+1, 5, 5));
        cell = row.createCell(6);  
        cell.setCellValue("用车人");  
        cell.setCellStyle(style); 
        cell = row1.createCell(6);
        cell.setCellStyle(style); 
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum+1, 6, 6));
        cell = row.createCell(7);  
        cell.setCellValue("派车人");  
        cell.setCellStyle(style); 
        cell = row1.createCell(7);
        cell.setCellStyle(style); 
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum+1, 7, 7));
        cell = row.createCell(8);  
        cell.setCellValue("备注");
        cell.setCellStyle(style);
        cell = row1.createCell(8);
        cell.setCellStyle(style); 
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum+1, 8, 8));

        return row.getHeight() + row1.getHeight();//累加行高
	}

	
	
	
	@Override
	public String getUserApplication(String username, int limit, int offset){
		DetachedCriteria criteria = DetachedCriteria.forClass(Application.class);
		Users user = userDao.findByUsername(username);
		criteria.add(Restrictions.eq("usersByApplicationuser", user));
		List<Application> list = applicationDao.findEntitysByDetachedCriteria(criteria, limit, offset);
		

		TableData tableData = new TableData();
		JSONArray jsonArray = new JSONArray();
		jsonArray.addAll(list);
		tableData.setRows(jsonArray);
		
		
		//获取总数据数
		int count = userDao.getRowCountByDetachedCriteria(criteria);
		tableData.setTotal(count);
		
		
		JsonData jsonData = new JsonData();
		jsonData.setStatusCode(MyStatus.OK);
		jsonData.setData(tableData);
		
		//y用户字段只序列化username和userid
		SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
		
		return JSON.toJSONString(jsonData, filter, SerializerFeature.DisableCircularReferenceDetect);
	}



	@Override
	public String getUserReviewApplication(String username, Integer limit,
			Integer offset) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Application.class);
		Users user = userDao.findByUsername(username);
		criteria.add(Restrictions.eq("usersByApplicationsendpeople", user));
		List<Application> list = applicationDao.findEntitysByDetachedCriteria(criteria, limit, offset);
		
		TableData tableData = new TableData();
		JSONArray jsonArray = new JSONArray();
		jsonArray.addAll(list);
		tableData.setRows(jsonArray);
		
		
		//获取总数据数
		int count = userDao.getRowCountByDetachedCriteria(criteria);
		tableData.setTotal(count);
		
		
		JsonData jsonData = new JsonData();
		jsonData.setStatusCode(MyStatus.OK);
		jsonData.setData(tableData);
		
		//用户字段只序列化username和userid
		SimplePropertyPreFilter filter = new SimplePropertyPreFilter(Users.class);
		
		
		return JSON.toJSONString(jsonData, filter, SerializerFeature.DisableCircularReferenceDetect);
	}  
	



	@Transactional
	@Override
	public String addApplication(String username, String sendpeoplename, Integer vehicleid, Integer driverid, 
			String applicationreason, String applicationstartposition, String applicationendposition, Date applicationstarttime,
			Date applicationendtime, Float applicationdistance, String applicationnote) {


		JsonData jsonData = new JsonData();
		Application application = new Application();
		
		application.setUsersByApplicationuser(userDao.findByUsername(username));
		application.setUsersByApplicationsendpeople(userDao.findByUsername(sendpeoplename));
		Vehicle vehicle = new Vehicle();
		vehicle.setVehicleid(vehicleid);
		application.setVehicle(vehicle);
		Driver driver = new Driver();
		driver.setDriverid(driverid);
		application.setDriver(driver);
		application.setApplicationreason(applicationreason);
		application.setApplicationstartposition(applicationstartposition);
		application.setApplicationendposition(applicationendposition);
		application.setApplicationstarttime(applicationstarttime);
		application.setApplicationendtime(applicationendtime);
		application.setApplicationdistance(applicationdistance);
		application.setApplicationnote(applicationnote);
		application.setApplicationispass(0);
		applicationDao.save(application);
        
		String cid = application.getUsersByApplicationsendpeople().getCid();
		geTuiUtils.sendSingle(cid, "消息提示", "您有新的审批信息", "您有新的审批信息");
		
        jsonData.setStatusCode(MyStatus.OK);
        
		return JSON.toJSONString(jsonData);
	}
//
//	@Transactional
//	@Override
//	public JsonData editApplication(Collection<Integer> idArr, String drivername, String drivernote) {
//		JsonData jsonData = new JsonData();
//		
//		for (Integer id : idArr) {
//			Driver driver = driverDao.get(id);
//			driver.setDrivername(drivername);
//			driver.setDrivernote(drivernote);
//	        
//	        driverDao.update(driver);
//		}
//        jsonData.setStatusCode(MyStatus.OK);
//        logger.info("编辑司机完成");
//		return jsonData;
//	}


	@Transactional	
	@Override
	public String cancelApplication(String username, Integer applicationid) {
		
		JsonData jsonData = new JsonData();
		
		Application application = applicationDao.get(applicationid);
		if(application != null) {

			Users user = application.getUsersByApplicationuser();
			if(user != null && username != null && username.equals(user.getUsername())) {
				if(application.getApplicationispass() != 0) {
					jsonData.setStatusCode(MyStatus.REVIEWED);
				} else {
					applicationDao.deleteById(applicationid);
					jsonData.setStatusCode(MyStatus.OK);
				}
			} else {
				jsonData.setStatusCode(MyStatus.NOT_PERMISSION);
			}
		} else {
			jsonData.setStatusCode(MyStatus.PARAMETER_EXCEPTION);
		}
		
		
		return JSON.toJSONString(jsonData);
	}



	@Transactional
	@Override
	public String review(HttpSession session, Integer applicationid,
			Integer result) {
		JsonData jsonData = new JsonData();
		UserSession us = (UserSession) session.getAttribute(Constant.LOGIN_USER_KEY);
		
		
		Application application = applicationDao.get(applicationid);
		if(application != null) {
			Users user = application.getUsersByApplicationsendpeople();
			if(user != null && us.getUsername() != null && us.getUsername().equals(user.getUsername())) {
				if(application.getApplicationispass() != Constant.PASS_STATE_NOT_AUDITED) {
					jsonData.setStatusCode(MyStatus.REVIEWED);
				} else {
					
					if(result == Constant.PASS_STATE_YES) {
						application.setApplicationispass(Constant.PASS_STATE_YES);
						Vehicle vehicle = application.getVehicle();
						if(vehicle != null) {
							application.getVehicle().setVehiclestate(Constant.VEHICLE_USEING);
						}
						applicationDao.update(application);
					} else if(result == Constant.PASS_STATE_NO) {
						application.setApplicationispass(Constant.PASS_STATE_NO);
						applicationDao.update(application);
					} else {
						jsonData.setStatusCode(MyStatus.PARAMETER_EXCEPTION);
					}
					Users applicationuser = application.getUsersByApplicationuser();
					if(applicationuser != null) {
						String cid = applicationuser.getCid();
						geTuiUtils.sendSingle(cid, "消息提示", "您的申请已被处理", "您的申请已被处理");
					}
					
					jsonData.setStatusCode(MyStatus.OK);
				}
			} else {
				jsonData.setStatusCode(MyStatus.NOT_PERMISSION);
			}
		} else {
			jsonData.setStatusCode(MyStatus.PARAMETER_EXCEPTION);
		}
		return JSON.toJSONString(jsonData);
	}


	@Transactional
	@Override
	public String returnVehicle(HttpSession session, Integer applicationid) {
		Application application =  applicationDao.get(applicationid);

		JsonData jsonData = new JsonData();
		if(application != null) {
			Vehicle vehicle = application.getVehicle();
			application.setApplicationispass(Constant.PASS_IS_OVER);
			if(vehicle != null) {
				vehicle.setVehiclestate(Constant.VEHICLE_CAN_USE);
			}
			applicationDao.update(application);
			
			jsonData.setStatusCode(MyStatus.OK);
		} else {
			jsonData.setStatusCode(MyStatus.PARAMETER_EXCEPTION);
		}
		
		
		
		return JSON.toJSONString(jsonData);
	}

}

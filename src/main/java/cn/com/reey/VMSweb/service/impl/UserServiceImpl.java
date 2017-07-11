/**   
 * filename锛歎serService.java
 *   
 * date锛�2016骞�4鏈�12鏃�
 * Copyright reey Corporation 2016 
 *   
 */
package cn.com.reey.VMSweb.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import cn.com.reey.VMSweb.dao.ApplicationDAO;
import cn.com.reey.VMSweb.dao.UserDAO;
import cn.com.reey.VMSweb.entity.Application;
import cn.com.reey.VMSweb.entity.Role;
import cn.com.reey.VMSweb.entity.Users;
import cn.com.reey.VMSweb.service.UserService;
import cn.com.reey.VMSweb.util.Constant;
import cn.com.reey.VMSweb.util.GeTuiUtils;
import cn.com.reey.VMSweb.util.MyStatus;
import cn.com.reey.VMSweb.util.MyUtils;
import cn.com.reey.VMSweb.util.UserSession;
import cn.com.reey.VMSweb.util.encryption.PasswordService;
import cn.com.reey.VMSweb.util.jsonEntity.JsonData;
import cn.com.reey.VMSweb.util.jsonEntity.TableData;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;

@Service
public class UserServiceImpl extends BaseEntityServiceImpl<Users> implements UserService {

	@Resource
	private UserDAO userDao;
	@Resource
	private ApplicationDAO applicationDao;
	@Resource
	private PasswordService passwordService;
	@Resource
	private PasswordService tokenService;
	@Resource
	private Long tokenValidity = 15L;
	@Resource
	private String uploadPath = "/upload";
	@Resource
	private String userImgFolder = "/userImg";
	@Resource
	private String defaultPassword = "123456";
	@Resource
	private GeTuiUtils geTuiUtils;
	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	
	@Override
	public JsonData findByUsername(String username) {
		
		JsonData jsonData = new JsonData();
		jsonData.setData(userDao.findByUsername(username));
		jsonData.setStatusCode(MyStatus.OK);
		
		return jsonData;
	}
	
	
	
	
	@Override
	public String getJsonDataString(int limit, int offset, String order, String sort){
		
		JsonData jsonData =  getJsonData(limit, offset, order, sort);
		
		SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
		
		return JSON.toJSONString(jsonData, filter, SerializerFeature.DisableCircularReferenceDetect);
	}
	
	
	
	
	@Transactional
	@Override
	public JsonData login(String username, String password, boolean rememberMe, HttpSession session) {
		
		JsonData jsonData = new JsonData();
		
		Users user = userDao.findByUsername(username);
		
		if(user != null){
			
			String encryptPwd = passwordService.encrypt(password, username);
			logger.debug("鍔犲瘑瀵嗙爜锛�" + encryptPwd);
			if(encryptPwd.equals(user.getUserpwd())){
				
				if(defaultPassword.equals(password)){
					//鏈洿鏀瑰垵濮嬪瘑鐮�
					jsonData.setStatusCode(MyStatus.PASSWORD_IS_INIT);
					logger.info("鐢ㄦ埛" + username + "鐧诲綍锛岄渶淇敼鍒濆瀵嗙爜");
				} else {
					UserSession us = new UserSession(user);
					if(us.getPermissionsNames().contains("login")) {
						
					
					
						//鐧婚檰鎴愬姛
						session.setAttribute(Constant.LOGIN_USER_KEY, new UserSession(user));
						jsonData.setStatusCode(MyStatus.OK);
						String token = "";
						if(rememberMe){
							token = tokenService.encrypt(password, username+new Date().getTime());
							logger.debug("鍔犲瘑token锛�" + token);
							JSONObject json = new JSONObject();
							json.put("token", token);
							jsonData.setData(json);
						}
						try {
							user.setUsersignid(token);
							user.setUserlastlogintime(new Date());
							userDao.update(user);
							logger.info("鐢ㄦ埛" + username + "鐧诲綍锛岀櫥褰曟垚鍔�");
						} catch (Exception e) {
							logger.error(user.getUsername() + "鐧诲綍寮傚父锛�" + e.toString());
						}
					} else {
						//鏃犵櫥褰曟潈闄�
						jsonData.setStatusCode(MyStatus.NOT_PERMISSION);
						logger.info("鐢ㄦ埛" + username + "鐧诲綍澶辫触锛屾棤鏉冮檺");
					}
				}
				
				
			} else {
				//瀵嗙爜閿欒
				jsonData.setStatusCode(MyStatus.PASSWORD_ERROR);
				logger.info("鐢ㄦ埛" + username + "鐧诲綍锛屽瘑鐮侀敊璇�");
			}
			
			
			
		} else {//鐢ㄦ埛鍚嶄笉瀛樺湪
			jsonData.setStatusCode(MyStatus.USER_UNKNOW);
			logger.info("鐢ㄦ埛" + username + "鐧诲綍锛岀敤鎴峰悕涓嶅瓨鍦�");
		}
		
		
		return jsonData;
	}


	


	@Transactional
	@Override
	public JsonData loginByToken(String token, HttpSession session) {
		
		Users user = userDao.findByUsersignid(token);
		
		if(user != null){
			long interval = new Date().getTime() - user.getUserlastlogintime().getTime();
			if(interval < tokenValidity*24*60*60*1000){
				//鐧婚檰鎴愬姛
				session.setAttribute(Constant.LOGIN_USER_KEY, new UserSession(user));
				try {
					user.setUserlastlogintime(new Date());
					userDao.update(user);
					logger.info("鐢ㄦ埛" + user.getUsername() + "鐧诲綍锛岀櫥褰曟垚鍔�");
				} catch (Exception e) {
					logger.error(user.getUsername() + "鐧诲綍寮傚父锛�" + e.toString());
				}
			}
		} else {
			//token鏃犳晥
			logger.info("token锛�" + token + "鏃犳晥");
			
			JsonData jsonData = new JsonData();
			jsonData.setStatusCode(MyStatus.TOKEN_UNKNOW);
			return jsonData;
		}
		
		
		return null;
	}




	@Transactional
	@Override
	public JsonData addUser(HttpServletRequest request, MultipartFile img, 
			String username, String workid,
			String realname, String department, String duty,
			String phonenumber, Integer roleid, String note) {
		
		JsonData jsonData = new JsonData();
		Users user = new Users();
		
		
		if(userDao.findByUsername(username) != null) {
			jsonData.setStatusCode(MyStatus.USERNAME_EXISTED);
			logger.info(MyStatus.codeToDescription(MyStatus.USERNAME_EXISTED));
			return jsonData;
        }
		user.setUsername(username);
		
		
		String of = img.getOriginalFilename();
		String Suffix = of.substring(of.lastIndexOf(".")+1, of.length());

		if((!"jpg".equalsIgnoreCase(Suffix)) && (!"jpeg".equalsIgnoreCase(Suffix)) && (!"gif".equalsIgnoreCase(Suffix)) && (!"png".equalsIgnoreCase(Suffix)) && (!"bmp".equalsIgnoreCase(Suffix))) {
			jsonData.setStatusCode(MyStatus.FILE_SUFFIX_EXCEPTION);
			UserSession us = (UserSession) request.getSession().getAttribute(Constant.LOGIN_USER_KEY);
			logger.info(us.getUsername()+MyStatus.codeToDescription(MyStatus.FILE_SUFFIX_EXCEPTION));
			return jsonData;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String webappDir = uploadPath + "/" + dateFormat.format(new Date()) + userImgFolder;
		String randName = "" + System.nanoTime() + new Random().nextInt() + "." + Suffix;
        String realDir = request.getServletContext().getRealPath(webappDir);
		
		File dirFile = new File(realDir);  
        if(!dirFile.exists()){  
        	dirFile.mkdirs();  
        }  
		
		
		File targetFile = new File(realDir, randName);
        //淇濆瓨  
        try {  
        	img.transferTo(targetFile);
        } catch (Exception e) {
        	logger.error("鏂囦欢涓婁紶寮傚父锛�" + e.toString());
        	jsonData.setStatusCode(MyStatus.SERVICE_ERROR);
			return jsonData;
        }  
        user.setUserimg(webappDir + "/" + randName);
        
        user.setUserworkid(workid);
        user.setUserrealname(realname);
        user.setUserdepartment(department);
        user.setUserduty(duty);
        user.setUserphonenumber(phonenumber);
        
        Role role = new Role();
        role.setRoleid(roleid);
        user.setRole(role);
        user.setUsernote(note);
        
        user.setUserpwd(passwordService.encrypt(defaultPassword, user.getUsername()));
        
        userDao.save(user);
        
        jsonData.setStatusCode(MyStatus.OK);
        logger.info("娣诲姞鐢ㄦ埛锛�" + username);
		return jsonData;
	}



	@Transactional
	@Override
	public JsonData editUser(HttpServletRequest request, Collection<Integer> idArr, MultipartFile img,
			String pwd, String workid, String realname, String department,
			String duty, String phonenumber, Integer roleid, String note) {

		
		
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
					webappDir = uploadPath + "/" + dateFormat.format(new Date()) + userImgFolder;
					randName = "" + System.nanoTime() + new Random().nextInt() + "." + Suffix;
			        String realDir = request.getServletContext().getRealPath(webappDir);
					
					File dirFile = new File(realDir);  
			        if(!dirFile.exists()){  
			        	dirFile.mkdirs();  
			        }  
					
					
					File targetFile = new File(realDir, randName);
			        //淇濆瓨  
			        try {  
			        	img.transferTo(targetFile);
			        } catch (Exception e) {
			        	logger.error("鏂囦欢涓婁紶寮傚父锛�" + e.toString());
			        	jsonData.setStatusCode(MyStatus.SERVICE_ERROR);
						return jsonData;
			        }  
				}
			}
		}
		
		
		for (Integer id : idArr) {
			
			Users user = userDao.get(id);
			
			if(!MyUtils.isEmptyOrNull(pwd)) {
				String encryptPwd = passwordService.encrypt(pwd, user.getUsername());
				user.setUserpwd(encryptPwd);
			}
			
			if(webappDir != null && randName != null) {
		        user.setUserimg(webappDir + "/" + randName);
			}
			
			
	        if(!MyUtils.isEmptyOrNull(workid)) {
	            user.setUserworkid(workid);
	        }
	        if(!MyUtils.isEmptyOrNull(realname)) {
	        	user.setUserrealname(realname);
	        }
	        if(!MyUtils.isEmptyOrNull(department)) {
	        	user.setUserdepartment(department);
	        }
	        if(!MyUtils.isEmptyOrNull(duty)) {
	        	user.setUserduty(duty);
	        }
	        if(!MyUtils.isEmptyOrNull(phonenumber)) {
	        	user.setUserphonenumber(phonenumber);
	        }
	        if(roleid != null) {
	        	 Role role = new Role();
	             role.setRoleid(roleid);
	             user.setRole(role);
	        }
	        if(!MyUtils.isEmptyOrNull(note)) {
	        	user.setUsernote(note);
	        }
	        userDao.update(user);
		}
        jsonData.setStatusCode(MyStatus.OK);
        logger.info("缂栬緫鐢ㄦ埛瀹屾垚");
		return jsonData;
		
		
	}

	@Transactional
	@Override
	public String logout(HttpSession session) {
		UserSession us = (UserSession) session.getAttribute(Constant.LOGIN_USER_KEY);
		session.removeAttribute(Constant.LOGIN_USER_KEY);
		
		Users user = userDao.findByUsername(us.getUsername());
		user.setUsersignid(null);
		userDao.update(user);
		JsonData jd = new JsonData();
		jd.setStatusCode(MyStatus.OK);
		return JSON.toJSONString(jd);
	}




	@Transactional
	@Override
	public String editUserphonenumber(HttpSession session, String newPhonenumber) {
		UserSession us = (UserSession) session.getAttribute(Constant.LOGIN_USER_KEY);
		Users user = userDao.findByUsername(us.getUsername());
		user.setUserphonenumber(newPhonenumber);
		userDao.update(user);
		JsonData jd = new JsonData();
		jd.setStatusCode(MyStatus.OK);
		return JSON.toJSONString(jd);
	}




	@Transactional
	@Override
	public String editUserpwd(String username, String oldUserpwd,
			String newUserpwd) {
		
		Users user = userDao.findByUsername(username);
		
		String encryptPwd = passwordService.encrypt(oldUserpwd, username);

		JsonData jd = new JsonData();
		if(encryptPwd.equals(user.getUserpwd())) {
			user.setUserpwd(passwordService.encrypt(newUserpwd, username));
			userDao.update(user);
			jd.setStatusCode(MyStatus.OK);
			return JSON.toJSONString(jd);
		}
		jd.setStatusCode(MyStatus.PASSWORD_ERROR);
		
		return JSON.toJSONString(jd);
	}




	@Override
	public String getSendpeopleList(Integer limit, Integer offset, String order, String sort) {
		TableData tableData = new TableData();
		
		//鑾峰彇鏁版嵁
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Users.class);
		
		if(sort != null){
			String[] sorts = sort.split("\\.");
			String tempName = "";
			for(int i = 0; i < sorts.length-1; i++) {
				if(sorts[i] != null){
					detachedCriteria.createAlias(tempName + sorts[i], sorts[i]);
					tempName = sorts[i] + ".";
				}
			}
			if("desc".equalsIgnoreCase(order)) {
				detachedCriteria.addOrder(Order.desc(sort));
			} else {
				detachedCriteria.addOrder(Order.asc(sort));
			}
		}

		detachedCriteria.createAlias("role", "r");
		detachedCriteria.createAlias("r.permissions", "p");
		List<Users> list = userDao.findEntitysByDetachedCriteria(detachedCriteria, limit, offset);
		JSONArray jsonArray = new JSONArray();
		jsonArray.addAll(list);
		tableData.setRows(jsonArray);
		
		
		//鑾峰彇鎬绘暟鎹暟
		int count = userDao.getRowCountByDetachedCriteria(detachedCriteria);
		tableData.setTotal(count);
		
		
		JsonData jsonData = new JsonData();
		jsonData.setStatusCode(MyStatus.OK);
		jsonData.setData(tableData);
		

		SimplePropertyPreFilter filter = new SimplePropertyPreFilter(Users.class);
		
		filter.getExcludes().add("role");
		
		return JSON.toJSONString(jsonData, filter);
	}




	@Transactional
	@Override
	public JsonData login(String username, String password, boolean rememberMe,
			String cid, HttpSession session) {
		
		JsonData jsonData = new JsonData();
		
		Users user = userDao.findByUsername(username);
		
		if(user != null){
			
			String encryptPwd = passwordService.encrypt(password, username);
			logger.debug("鍔犲瘑瀵嗙爜锛�" + encryptPwd);
			if(encryptPwd.equals(user.getUserpwd())){
				
				if(defaultPassword.equals(password)){
					//鏈洿鏀瑰垵濮嬪瘑鐮�
					jsonData.setStatusCode(MyStatus.PASSWORD_IS_INIT);
					logger.info("鐢ㄦ埛" + username + "鐧诲綍锛岄渶淇敼鍒濆瀵嗙爜");
				} else {
					UserSession us = new UserSession(user);
					if(us.getPermissionsNames().contains("login")) {
						
					
					
						//鐧婚檰鎴愬姛
						session.setAttribute(Constant.LOGIN_USER_KEY, new UserSession(user));
						jsonData.setStatusCode(MyStatus.OK);
						String token = "";
						JSONObject json = new JSONObject();
						if(rememberMe){
							token = tokenService.encrypt(password, username+new Date().getTime());
							json.put("token", token);
							user.setUsersignid(token);
							logger.debug("鍔犲瘑token锛�" + token);
						} 
						user.setUserlastlogintime(new Date());
						if(MyUtils.isEmptyOrNull(user.getCid())) {
							DetachedCriteria criteria = DetachedCriteria.forClass(Application.class);
							criteria.add(Restrictions.eq("usersByApplicationsendpeople", user));
							int cnt = applicationDao.getRowCountByDetachedCriteria(criteria);
							if(cnt > 0) {
								geTuiUtils.sendSingle(cid, "娑堟伅鎻愮ず", "鎮ㄦ湁鏂扮殑瀹℃壒淇℃伅", "鎮ㄦ湁鏂扮殑瀹℃壒淇℃伅");
							}
						}
						user.setCid(cid);
						try {
							
							userDao.update(user);
							jsonData.setData(json);
							logger.info("鐢ㄦ埛" + username + "鐧诲綍锛岀櫥褰曟垚鍔�");
						} catch (Exception e) {
							logger.error(user.getUsername() + "鐧诲綍寮傚父锛�" + e.toString());
						}
					} else {
						//鏃犵櫥褰曟潈闄�
						jsonData.setStatusCode(MyStatus.NOT_PERMISSION);
						logger.info("鐢ㄦ埛" + username + "鐧诲綍澶辫触锛屾棤鏉冮檺");
					}
				}
				
				
			} else {
				//瀵嗙爜閿欒
				jsonData.setStatusCode(MyStatus.PASSWORD_ERROR);
				logger.info("鐢ㄦ埛" + username + "鐧诲綍锛屽瘑鐮侀敊璇�");
			}
			
			
			
		} else {//鐢ㄦ埛鍚嶄笉瀛樺湪
			jsonData.setStatusCode(MyStatus.USER_UNKNOW);
			logger.info("鐢ㄦ埛" + username + "鐧诲綍锛岀敤鎴峰悕涓嶅瓨鍦�");
		}
		
		
		return jsonData;
	}



}

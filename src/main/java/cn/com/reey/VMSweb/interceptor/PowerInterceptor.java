/**   
 * 文件名：SecurityInterceptor.java
 *   
 * 版本信息：TODO
 * 日期：2015年7月9日
 * Copyright QiHang Corporation 2015 
 *   
 */
package cn.com.reey.VMSweb.interceptor;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import cn.com.reey.VMSweb.dao.UserDAO;
import cn.com.reey.VMSweb.entity.Users;
import cn.com.reey.VMSweb.service.UserService;
import cn.com.reey.VMSweb.util.AuthPassport;
import cn.com.reey.VMSweb.util.Constant;
import cn.com.reey.VMSweb.util.MyStatus;
import cn.com.reey.VMSweb.util.MyUtils;
import cn.com.reey.VMSweb.util.UserSession;
import cn.com.reey.VMSweb.util.jsonEntity.JsonData;


public class PowerInterceptor implements HandlerInterceptor{

	@Resource
    private UserDAO userDao;
	
	
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}

	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {
		
		HandlerMethod method = (HandlerMethod)handler;  
		
		AuthPassport authPassportByClass = method.getBeanType().getAnnotation(AuthPassport.class);
		AuthPassport authPassportByMethod = method.getMethodAnnotation(AuthPassport.class);  
		
		//如果为空在表示该方法不需要进行权限验证  
        if (authPassportByMethod != null) {
	        //验证是否具有权限  
	        if (!hasPower(request, authPassportByMethod.value())) {
	        	if(request.getHeader("Accept").indexOf("text/html") != -1) {
        			response.sendRedirect(request.getContextPath());
        		} else {
	        		JsonData jd = new JsonData();
	        		jd.setStatusCode(MyStatus.NOT_PERMISSION);
	        		response.setContentType("application/json;charset=UTF-8");
	        		response.getWriter().print(JSON.toJSONString(jd));
        		}
	            return false;
	        }
        }
		
        //如果为空在表示该类不需要进行权限验证  
        if (authPassportByClass != null) {
        	if (!hasPower(request, authPassportByClass.value())) {
        		if(request.getHeader("Accept").indexOf("text/html") != -1) {
        			response.sendRedirect(request.getContextPath());
        		} else {
        			JsonData jd = new JsonData();
            		jd.setStatusCode(MyStatus.NOT_PERMISSION);
            		response.setContentType("application/json;charset=UTF-8");
            		response.getWriter().print(JSON.toJSONString(jd));
        		}
        		
                return false;
            }
        }  
        
        return true;  
	}

	
	/**   
	 * 判断请求是否具有权限
	 * @author 范子才
	 * @return
	 * @version 2016年4月17日 下午3:16:28
	 */
	public boolean hasPower(HttpServletRequest request, String authPassport){
		HttpSession session = request.getSession();
		UserSession usersession = (UserSession) session.getAttribute(Constant.LOGIN_USER_KEY);
		
		
		
		
		if(usersession == null) {
			return false;
		}
		
		//更新权限
		Users user = userDao.findByUsername(usersession.getUsername());
		usersession = new UserSession(user);
		session.setAttribute(Constant.LOGIN_USER_KEY, usersession);
		
		if(MyUtils.isEmptyOrNull(authPassport)) {
			return true;
		}
		
		return usersession.getPermissionsNames().contains(authPassport);
	}
	
}

/**   
 * 文件名：SecurityInterceptor.java
 *   
 * 版本信息：TODO
 * 日期：2015年7月9日
 * Copyright QiHang Corporation 2015 
 *   
 */
package cn.com.reey.VMSweb.interceptor;


import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import cn.com.reey.VMSweb.service.UserService;
import cn.com.reey.VMSweb.service.impl.UserServiceImpl;
import cn.com.reey.VMSweb.util.Constant;
import cn.com.reey.VMSweb.util.jsonEntity.JsonData;


public class AutoLoginInterceptor implements HandlerInterceptor{


	private static final Logger logger = LoggerFactory.getLogger(AutoLoginInterceptor.class);
	
	@Resource
	private UserService userService;
	
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}

	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {
	
		response.setContentType("text/plain;charset=UTF-8");
		
		HttpSession session = request.getSession();
		
		String token = request.getParameter(Constant.TOKEN);
		if(token == null){
			return true;
		}
		
		JsonData json = userService.loginByToken(token, session);

		if(json != null){
			PrintWriter pw = response.getWriter();
			pw.print(JSON.toJSONString(json));
			return false;
		}
        
        return true;  
	}

	
}

/**   
 * filename：ExceptionHandler.java
 *   
 * date：2016年5月11日
 * Copyright reey Corporation 2016 
 *   
 */
package cn.com.reey.VMSweb.resolver;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import cn.com.reey.VMSweb.service.impl.ApplicationServiceImpl;
import cn.com.reey.VMSweb.util.MyStatus;
import cn.com.reey.VMSweb.util.jsonEntity.JsonData;

public class ExceptionHandler implements HandlerExceptionResolver {

	
	private static final Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);
	
	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception e) {
		HandlerMethod method = (HandlerMethod)handler;  
		
		logger.error(method.toString() + " - " + e.toString());
		
		JsonData jd = new JsonData();
		jd.setStatusCode(MyStatus.SERVICE_ERROR);
		response.setContentType("application/json;charset=UTF-8");
		try {
			response.getWriter().print(JSON.toJSONString(jd));
		} catch (IOException e1) {
			logger.error(e1.toString());
		}
		
		return null;  
	}

}

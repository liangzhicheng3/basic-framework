package com.liangzhicheng.config.mvc.interceptor;

import com.liangzhicheng.common.basic.WebResult;
import com.liangzhicheng.common.constant.ApiConstant;
import com.liangzhicheng.common.utils.SysCacheUtil;
import com.liangzhicheng.common.utils.SysToolUtil;
import com.liangzhicheng.config.mvc.interceptor.annotation.DDOSValidate;
import net.sf.json.JSONObject;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description DDOS攻击拦截器，凡是方法头部加了注解@DDOSValidate的controller，执行前都会先执行下面的preHandle()方法
 * @author liangzhicheng
 * @since 2020-08-07
 */
public class DDOSInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if(handler.getClass().isAssignableFrom(HandlerMethod.class)){
			SysToolUtil.info("--- DDOSValidate come start ///");
			HandlerMethod handlerMethod = (HandlerMethod)handler;
			DDOSValidate ddosValidate = handlerMethod.getMethodAnnotation(DDOSValidate.class);
			if(ddosValidate != null && ddosValidate.validate() == true){
				String userId = request.getParameter("userId");
				if(SysToolUtil.isBlank(userId)){
					WebResult result = new WebResult(ApiConstant.PARAM_IS_NULL, ApiConstant.getMessage(ApiConstant.PARAM_IS_NULL), null);
					JSONObject json = JSONObject.fromObject(result);
					response.setCharacterEncoding("UTF-8");
					response.setContentType("application/json");
					response.getWriter().print(json.toString());
					return false;
				}
				//获取函数名
				String methodName = handlerMethod.getMethod().getName();
				int second = ddosValidate.second();
				if(second > 0){
					//String ip = request.getRemoteAddr();
					String cacheName = userId + "-" + methodName;
					String cacheValue = (String) SysCacheUtil.get(cacheName);
					if(SysToolUtil.isNotBlank(cacheValue)){
						WebResult result = new WebResult(ApiConstant.REQUEST_BUSY, ApiConstant.getMessage(ApiConstant.REQUEST_BUSY), null);
						JSONObject json = JSONObject.fromObject(result);
						response.setCharacterEncoding("UTF-8");
						response.setContentType("application/json");
						response.getWriter().print(json.toString());
						return false;
					}
					SysCacheUtil.set(cacheName, cacheName, second);
				}
			}
		}
		return true;
	}

}

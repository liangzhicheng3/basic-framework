package com.liangzhicheng.config.mvc.interceptor;

import com.liangzhicheng.common.basic.WebResult;
import com.liangzhicheng.common.constant.ApiConstant;
import com.liangzhicheng.common.utils.SysToolUtil;
import com.liangzhicheng.common.utils.SysServerUtil;
import com.liangzhicheng.config.mvc.interceptor.annotation.LoginServerValidate;
import net.sf.json.JSONObject;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description 服务端登录校验拦截器，凡是方法头部加了注解@LoginServerValidate的controller，执行前都会先执行下面的preHandle()方法
 * @author liangzhicheng
 * @since 2020-08-14
 */
public class LoginServerInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if(handler.getClass().isAssignableFrom(HandlerMethod.class)){
			SysToolUtil.info("--- LoginServerValidate come start ///");
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			LoginServerValidate loginServerValidate = handlerMethod.getMethodAnnotation(LoginServerValidate.class);
			if(loginServerValidate != null && loginServerValidate.validate() == true){
				boolean isLogin = false;
				String userId = request.getParameter("userId");
				SysToolUtil.info("--- server request userId : " + userId);
				if(SysToolUtil.isNotBlank(userId)){
					isLogin = SysServerUtil.isLogin(userId, request);
				}
				/*
				 * 如果没有登录，返回false拦截请求
				 */
				if(isLogin){
					int code = ApiConstant.NO_LOGIN;
					if(SysToolUtil.isBlank(userId)){
						code = ApiConstant.PARAM_IS_NULL;
					}
					WebResult result = new WebResult(ApiConstant.NO_LOGIN, ApiConstant.getMessage(code), null);
					JSONObject json = JSONObject.fromObject(result);
					response.setCharacterEncoding("UTF-8");
					response.setContentType("application/json");
					response.getWriter().print(json.toString());
					return false;
				}
			}
		}
		return true;
	}

}

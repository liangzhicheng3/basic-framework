package com.liangzhicheng.config.mvc.interceptor;

import com.liangzhicheng.common.basic.WebResult;
import com.liangzhicheng.common.constant.ApiConstant;
import com.liangzhicheng.common.utils.SysTokenUtil;
import com.liangzhicheng.common.utils.SysToolUtil;
import com.liangzhicheng.config.mvc.interceptor.annotation.LoginClientValidate;
import net.sf.json.JSONObject;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description 客户端登录校验拦截器，凡是方法头部加了注解@LoginClientValidate的controller，执行前都会先执行下面的preHandle()方法
 * @author liangzhicheng
 * @since 2020-08-11
 */
public class LoginClientInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if(handler.getClass().isAssignableFrom(HandlerMethod.class)){
			SysToolUtil.info("--- LoginClientValidate come start ///");
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			LoginClientValidate loginClientValidate = handlerMethod.getMethodAnnotation(LoginClientValidate.class);
			if(loginClientValidate != null && loginClientValidate.validate() == true){
				boolean isLogin = false;
				String tokenMINI = request.getParameter("tokenMINI");
				String tokenAPP = request.getParameter("tokenAPP");
				String userId = request.getParameter("userId");
				SysToolUtil.info("--- client request tokenMINI : " + tokenMINI);
				SysToolUtil.info("--- client request tokenAPP : " + tokenAPP);
				SysToolUtil.info("--- client request userId : " + userId);
				if(SysToolUtil.isNotBlank(userId)){
					if(SysToolUtil.isNotBlank(tokenMINI)){
						isLogin = SysTokenUtil.isLoginMINI(userId, tokenMINI);
					}
					if(SysToolUtil.isNotBlank(tokenAPP)){
						isLogin = SysTokenUtil.isLoginAPP(userId, tokenAPP);
					}
				}
				/*
				 * 如果没有登录，返回false拦截请求
				 */
				if(isLogin){
					/*Map<String,Object> map = new HashMap<>();
					map.put("mini",TokenUtil.isLoginMINI(userId,tokenMINI));
					map.put("app",TokenUtil.isLoginAPP(userId,tokenAPP));
					Tool.info("--- noLogin:"+JSONObject.fromObject(map).toString(),LoginClientInterceptor.class);*/
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

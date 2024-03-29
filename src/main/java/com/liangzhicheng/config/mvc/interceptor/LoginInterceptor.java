package com.liangzhicheng.config.mvc.interceptor;

import com.liangzhicheng.common.basic.ResponseResult;
import com.liangzhicheng.common.constant.ApiConstant;
import com.liangzhicheng.common.utils.SysTokenUtil;
import com.liangzhicheng.common.utils.SysToolUtil;
import com.liangzhicheng.config.mvc.interceptor.annotation.LoginValidate;
import net.sf.json.JSONObject;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @description 登录校验拦截器，凡是方法头部加了注解@LoginValidate的controller，执行前都会先执行下面的preHandle()方法
 * @author liangzhicheng
 * @since 2020-08-11
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if(handler.getClass().isAssignableFrom(HandlerMethod.class)){
			SysToolUtil.info("--- LoginValidate come start ///");
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			LoginValidate loginValidate = handlerMethod.getMethodAnnotation(LoginValidate.class);
			if(loginValidate != null && loginValidate.validate() == true){
				boolean isLogin = false;
				String tokenMINI = request.getHeader("tokenMINI");
				String tokenAPP = request.getHeader("tokenAPP");
				String tokenWEB = request.getHeader("tokenWEB");
				String userId = request.getHeader("userId");
				SysToolUtil.info("--- request tokenMINI : " + tokenMINI);
				SysToolUtil.info("--- request tokenAPP : " + tokenAPP);
				SysToolUtil.info("--- request tokenWEB : " + tokenWEB);
				SysToolUtil.info("--- request userId : " + userId);
				if(SysToolUtil.isNotBlank(userId)){
					if(SysToolUtil.isNotBlank(tokenMINI)){
						isLogin = SysTokenUtil.isLoginMINI(userId, tokenMINI);
					}
					if(SysToolUtil.isNotBlank(tokenAPP)){
						isLogin = SysTokenUtil.isLoginAPP(userId, tokenAPP);
					}
					if(SysToolUtil.isNotBlank(tokenWEB)){
						isLogin = SysTokenUtil.isLoginWEB(userId, tokenWEB);
					}
				}
				/*
				 * 如果没有登录，返回false拦截请求
				 */
				if(!isLogin){
					ResponseResult result = null;
					PrintWriter out = null;
					try{
						result = new ResponseResult(ApiConstant.NO_LOGIN, ApiConstant.getMessage(ApiConstant.NO_LOGIN), null);
						response.setContentType("application/json;charset=UTF-8");
						response.setCharacterEncoding("UTF-8");
						out = response.getWriter();
						out.println(JSONObject.fromObject(result).toString());
					}catch(Exception e){
						SysToolUtil.error("JSON异常 : " + e.getMessage(), LoginInterceptor.class);
					}finally{
						if(SysToolUtil.isNotNull(out)){
							out.flush();
							out.close();
						}
					}
					return false;
				}
			}
		}
		return true;
	}

}

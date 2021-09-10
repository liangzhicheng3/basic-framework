package com.liangzhicheng.config.mvc.interceptor;

import com.liangzhicheng.common.basic.ResponseResult;
import com.liangzhicheng.common.constant.ApiConstant;
import com.liangzhicheng.common.utils.SysCacheUtil;
import com.liangzhicheng.common.utils.SysToolUtil;
import com.liangzhicheng.config.mvc.interceptor.annotation.AccessLimitValidate;
import net.sf.json.JSONObject;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @description 访问限制拦截器，凡是方法头部加了注解@AccessLimitValidate的controller，执行前都会先执行下面的preHandle()方法
 * @author liangzhicheng
 * @since 2020-08-07
 */
public class AccessLimitInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if(handler.getClass().isAssignableFrom(HandlerMethod.class)){
			SysToolUtil.info("--- AccessLimitValidate come start ///");
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			AccessLimitValidate accessLimitValidate = handlerMethod.getMethodAnnotation(AccessLimitValidate.class);
			if(accessLimitValidate != null && accessLimitValidate.validate() == true){
				int second = accessLimitValidate.second(); //请求时间范围
				int times = accessLimitValidate.times(); //请求次数
				//根据IP + API限流
				String key = SysToolUtil.getAccessUrl(request) + request.getRequestURI();
				//根据key获取已请求次数
				Integer maxTimes = (Integer) SysCacheUtil.get(key);
				if(maxTimes == null){
					SysCacheUtil.set(key, 1, second);
				}else if(maxTimes < times){
					SysCacheUtil.set(key, maxTimes + 1, second);
				}else{
					render(response);
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * @description 异常返回参数输出
	 * @param response
	 */
	private void render(HttpServletResponse response) {
		PrintWriter out = null;
		ResponseResult result = null;
		try {
			result = new ResponseResult(ApiConstant.REQUEST_BUSY, ApiConstant.getMessage(ApiConstant.REQUEST_BUSY), null);
			response.setContentType("application/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			out = response.getWriter();
			out.println(JSONObject.fromObject(result).toString());
		} catch (Exception e) {
			SysToolUtil.error("JSON异常 : " + e.getMessage(), AccessLimitInterceptor.class);
		}finally{
			if(out != null){
				out.flush();
				out.close();
			}
		}
	}

}

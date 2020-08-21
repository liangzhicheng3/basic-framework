package com.liangzhicheng.config.mvc.interceptor;

import com.liangzhicheng.common.basic.WebResult;
import com.liangzhicheng.common.constant.ApiConstant;
import com.liangzhicheng.common.utils.SysToolUtil;
import net.sf.json.JSONObject;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.Map;

/**
 * @description XSS攻击拦截器
 * @author liangzhicheng
 * @since 2020-08-07
 */
public class XSSInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		SysToolUtil.info("--- XSSInterceptor come start ///");
		Map<String, String[]> requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
				String newStr = SysToolUtil.replaceXSS(valueStr);
				if(!valueStr.equals(newStr)) {
					SysToolUtil.info("--- XSS come start ///");
					SysToolUtil.info("--- valueStr : " + valueStr);
					SysToolUtil.info("--- newStr : " + newStr);
					WebResult result = new WebResult(ApiConstant.BASE_FAIL_CODE, "含有XSS字符", null);
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

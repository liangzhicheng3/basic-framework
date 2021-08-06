package com.liangzhicheng.config.mvc.interceptor;

import com.liangzhicheng.common.basic.WebResult;
import com.liangzhicheng.common.constant.ApiConstant;
import com.liangzhicheng.common.utils.SysCacheUtil;
import com.liangzhicheng.common.utils.SysToolUtil;
import com.liangzhicheng.config.mvc.interceptor.annotation.PermissionsValidate;
import net.sf.json.JSONObject;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Set;

/**
 * @description 权限校验拦截器，凡是方法头部加了注解@PermissionsValidate的controller，执行前都会先执行下面的preHandle()方法
 * @author liangzhicheng
 * @since 2021-08-06
 */
public class PermissionsInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler.getClass().isAssignableFrom(HandlerMethod.class)){
            SysToolUtil.info("--- PermissionsValidate come start ///");
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            PermissionsValidate permissionsValidate = handlerMethod.getMethodAnnotation(PermissionsValidate.class);
            if(permissionsValidate != null && permissionsValidate.validate() == true){
                String expression = permissionsValidate.expression();
                String accountId = request.getHeader("accountId");
                SysToolUtil.info("--- request expression : " + expression);
                SysToolUtil.info("--- request accountId : " + accountId);
                if(SysToolUtil.isNotBlank(expression, accountId)){
                    Map<String, Object> permMap = SysCacheUtil.getPermMap();
                    Set<String> perms = (Set<String>) permMap.get(accountId);
                    if(SysToolUtil.isNotNull(perms)){
                        Integer totalPermNum = perms.size();
                        Integer permNum = totalPermNum;
                        for(String permExpression : perms){
                            if(expression.equals(permExpression)){
                                permNum -= 1;
                            }
                        }
                        if(totalPermNum == permNum){
                            render(response);
                            return false;
                        }
                    }
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
        WebResult result = null;
        try {
            result = new WebResult(ApiConstant.NO_AUTHORIZATION, ApiConstant.getMessage(ApiConstant.NO_AUTHORIZATION), null);
            response.setContentType("application/json;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            out = response.getWriter();
            out.println(JSONObject.fromObject(result).toString());
        } catch (Exception e) {
            SysToolUtil.error("JSON异常 : " + e.getMessage(), PermissionsInterceptor.class);
        }finally{
            if(out != null){
                out.flush();
                out.close();
            }
        }
    }

}

package com.liangzhicheng.config.aop;

import com.liangzhicheng.common.basic.BaseController;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 * @description 针对Controller层RequestBody的Spring Validator校验统一返回格式
 * @author liangzhicheng
 * @since 2020-07-31
 */
@Aspect
@Component
public class RequestBodyValidateAspect extends BaseController {

    /**
     * @description 统一错误返回信息为参数名加错误信息
     * @param point
     * @param bindingResult
     * @return Object
     */
    @Around("execution (* com..*.*Controller.*(..)) && args(..,bindingResult)")
    public Object validatorAround(ProceedingJoinPoint point, BindingResult bindingResult) throws Throwable {
        if(bindingResult.hasErrors()){
            FieldError fieldError = bindingResult.getFieldError();
            return buildFailedInfo(fieldError.getField()+fieldError.getDefaultMessage());
        }else{
            return point.proceed();
        }
    }

}

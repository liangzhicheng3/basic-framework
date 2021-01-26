package com.liangzhicheng.config.advice;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.liangzhicheng.common.basic.BaseController;
import com.liangzhicheng.common.basic.WebResult;
import com.liangzhicheng.common.constant.ApiConstant;
import com.liangzhicheng.common.exception.BusinessException;
import com.liangzhicheng.common.exception.TransactionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description 全局异常捕获处理类
 * @author liangzhicheng
 * @since 2020-07-31
 */
@ControllerAdvice(basePackages = {"com"})
public class SysControllerAdvice extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(SysControllerAdvice.class);

    /**
     * @description 服务器异常信息
     * @param ex
     * @return WebResult
     */
    @ExceptionHandler({RuntimeException.class})
    @ResponseBody
    public WebResult runtimeException(RuntimeException ex){
        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw,true));
        logger.error("Exception Output : " + sw.toString(), this.getClass());
        //logger.error("Exception Output 2 : " + ex.getClass().getName(), this.getClass());
        return buildFailedInfo("服务器发生异常 : " + ex.getMessage());
    }

    /**
     * @description 400错误->参数缺失异常信息
     * @param ex
     * @return WebResult
     */
    @ExceptionHandler({MissingServletRequestParameterException.class})
    @ResponseBody
    public WebResult missingServletRequestParameterException(MissingServletRequestParameterException ex){
        return buildFailedInfo(ApiConstant.PARAM_IS_NULL," : " + ex.getParameterName());
    }

    /**
     * @description 400错误->参数类型异常信息
     * @param ex
     * @return WebResult
     */
    @ExceptionHandler({TypeMismatchException.class})
    @ResponseBody
    public WebResult typeMismatchException(TypeMismatchException ex){
        return buildFailedInfo(ApiConstant.PARAM_TYPE_ERROR," : " + ex.getValue()+"，需要 : " + ex.getRequiredType().getName());
    }

    /**
     * @description 400错误->参数格式有误异常信息
     * @param ex
     * @return WebResult
     */
    @ExceptionHandler({InvalidFormatException.class})
    @ResponseBody
    public WebResult invalidFormatException(InvalidFormatException ex){
        return buildFailedInfo(ApiConstant.PARAM_FORMAT_ERROR);
    }

    /**
     * @description 400错误->json参数格式有误异常信息
     * @param ex
     * @return WebResult
     */
    @ExceptionHandler({JsonParseException.class})
    @ResponseBody
    public WebResult jsonParseException1(JsonParseException ex){
        return buildFailedInfo(ApiConstant.PARAM_JSON_ERROR);
    }

    /**
     * @description 400错误->json参数格式有误异常信息
     * @param ex
     * @return WebResult
     */
    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseBody
    public WebResult jsonParseException2(HttpMessageNotReadableException ex){
        String message = ex.getMessage();
        String rgex = "Unrecognized field \"(.*?)\" ";
        Pattern pattern = Pattern.compile(rgex); //匹配的模式
        Matcher m = pattern.matcher(ex.getMessage());
        if(m.find()){
            message = "非法参数 -> " + m.group(1);
        }
        return buildFailedInfo(ApiConstant.getMessage(ApiConstant.PARAM_JSON_ERROR) + " : " + message);
    }

    /**
     * @description 事务异常信息
     * @param ex
     * @return WebResult
     */
    @ExceptionHandler({TransactionException.class})
    @ResponseBody
    public WebResult transactionException(TransactionException ex){
        logger.error("TransactionException Output : " + ex.getMessage());
        return buildFailedInfo(ex.getCode());
    }

    /**
     * @description 自定义异常信息
     * @param ex
     * @return WebResult
     */
    @ExceptionHandler({BusinessException.class})
    @ResponseBody
    public WebResult businessException(BusinessException ex){
        logger.error("BusinessException Output : " + ex.getMessage());
        return buildFailedInfo(ex.getCode(), ex.getMessage());
    }

}

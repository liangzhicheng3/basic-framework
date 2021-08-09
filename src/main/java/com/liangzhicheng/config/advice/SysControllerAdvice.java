package com.liangzhicheng.config.advice;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.liangzhicheng.common.basic.BaseController;
import com.liangzhicheng.common.basic.WebResult;
import com.liangzhicheng.common.constant.ApiConstant;
import com.liangzhicheng.common.exception.CustomizeException;
import com.liangzhicheng.common.exception.TransactionException;
import com.liangzhicheng.common.utils.SysToolUtil;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @description 全局异常捕获处理类
 * @author liangzhicheng
 * @since 2020-07-31
 */
@ControllerAdvice(basePackages = {"com"})
public class SysControllerAdvice extends BaseController {

    @Resource
    private MessageSource messageSource;

    /**
     * @description 服务器异常信息
     * @param ex
     * @return ResponseResult
     */
    @ExceptionHandler({RuntimeException.class})
    @ResponseBody
    public WebResult runtimeException(RuntimeException ex){
        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw,true));
        SysToolUtil.error("Exception Output : " + sw.toString(), getClass());
        return buildFailedInfo("服务器发生异常 : " + ex.getMessage());
    }

    /**
     * @description 400错误->参数缺失异常信息
     * @param ex
     * @return ResponseResult
     */
    @ExceptionHandler({MissingServletRequestParameterException.class})
    @ResponseBody
    public WebResult missingServletRequestParameterException(
            MissingServletRequestParameterException ex){
        return buildFailedInfo(ApiConstant.PARAM_IS_NULL," : "
                + ex.getParameterName());
    }

    /**
     * @description 400错误->参数类型异常信息
     * @param ex
     * @return ResponseResult
     */
    @ExceptionHandler({TypeMismatchException.class})
    @ResponseBody
    public WebResult typeMismatchException(TypeMismatchException ex){
        return buildFailedInfo(ApiConstant.PARAM_TYPE_ERROR," : "
                + ex.getValue()+"，需要 : " + ex.getRequiredType().getName());
    }

    /**
     * @description 400错误->参数格式有误异常信息
     * @param ex
     * @return ResponseResult
     */
    @ExceptionHandler({InvalidFormatException.class})
    @ResponseBody
    public WebResult invalidFormatException(InvalidFormatException ex){
        return buildFailedInfo(ApiConstant.PARAM_FORMAT_ERROR);
    }

    /**
     * @description 400错误->json参数格式有误异常信息
     * @param ex
     * @return ResponseResult
     */
    @ExceptionHandler({JsonParseException.class})
    @ResponseBody
    public WebResult jsonParseException1(JsonParseException ex){
        return buildFailedInfo(ApiConstant.PARAM_JSON_ERROR);
    }

    /**
     * @description 400错误->json参数格式有误异常信息
     * @param ex
     * @return ResponseResult
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
        return buildFailedInfo(ApiConstant.getMessage(
                ApiConstant.PARAM_JSON_ERROR) + " : " + message);
    }

    /**
     * @description 参数贴有@NotBlank注解拦截异常信息
     * @param ex
     * @return ResponseResult
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public WebResult handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<FieldError> errorList = ex.getBindingResult().getFieldErrors();
        List<String> errorMessages = errorList.stream().map(x->{
            String itemMessage= messageSource.getMessage(x.getDefaultMessage(), null,
                    x.getDefaultMessage(), LocaleContextHolder.getLocale());
            return String.format("%s", itemMessage);
        }).collect(Collectors.toList());
        SysToolUtil.error("参数验证失败:" + errorMessages.get(0), getClass());
        return buildFailedInfo(errorMessages.get(0));
    }

    /**
     * @description 参数保存有误异常
     * @param ex
     * @return WebResult
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    public WebResult dataIntegrityViolationException(DataIntegrityViolationException ex) {
        SysToolUtil.error("参数保存有误异常输出 : " + ex.getMessage(), getClass());
        return buildFailedInfo("保存失败");
    }

    /**
     * @description 业务逻辑异常信息
     * @param ex
     * @return ResponseResult
     */
    @ExceptionHandler({TransactionException.class})
    @ResponseBody
    public WebResult transactionException(TransactionException ex){
        SysToolUtil.error("业务逻辑异常输出 : " + ex.getMessage(), getClass());
        return buildFailedInfo(ex.getCode());
    }

    /**
     * @description 自定义异常信息
     * @param ex
     * @return ResponseResult
     */
    @ExceptionHandler({CustomizeException.class})
    @ResponseBody
    public WebResult businessException(CustomizeException ex){
        SysToolUtil.error("自定义异常输出 : " + ex.getMessage(), getClass());
        return buildFailedInfo(ex.getCode(), ex.getMessage());
    }

    /**
     * @description Shiro异常信息
     * @param ex
     * @return ResponseResult
     */
    @ExceptionHandler(AuthorizationException.class)
    @ResponseBody
    public WebResult notAuthException(AuthorizationException ex) {
        SysToolUtil.error("没有通过权限验证", getClass());
        return buildFailedInfo(ApiConstant.NO_AUTHORIZATION);
    }

}

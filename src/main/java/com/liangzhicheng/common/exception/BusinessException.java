package com.liangzhicheng.common.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description 自定义异常
 * @author lee
 * @since 2021-01-26
 */
@Data
@NoArgsConstructor
public class BusinessException extends IllegalArgumentException {

    private int code;
    private String message;

    public BusinessException(int code, String message){
        this.code = code;
        this.message = message;
    }

}

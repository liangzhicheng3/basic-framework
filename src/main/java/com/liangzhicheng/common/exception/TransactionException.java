package com.liangzhicheng.common.exception;

import com.liangzhicheng.common.constant.ApiConstant;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description 业务逻辑异常
 * @author liangzhicheng
 * @since 2021-01-26
 */
@Data
@NoArgsConstructor
public class TransactionException extends IllegalArgumentException {

    /**
     * 状态码
     */
    private int code;

    /**
     * 具体信息
     */
    private String message;

    public TransactionException(int code){
        this.code = code;
        this.message = ApiConstant.getMessage(code);
    }

}

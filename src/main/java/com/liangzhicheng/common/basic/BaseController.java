package com.liangzhicheng.common.basic;

import com.liangzhicheng.common.constant.ApiConstant;

/**
 * @description 接口请求结果信息返回基础控制器
 * @author liangzhicheng
 * @since 2020-07-28
 */
public abstract class BaseController {

	protected WebResult buildSuccessInfo(Object resultData) {
		return new WebResult(ApiConstant.BASE_SUCCESS_CODE, ApiConstant.getMessage(ApiConstant.BASE_SUCCESS_CODE), resultData);
	}

    protected WebResult buildFailedInfo(int errorCode) {
        return new WebResult(errorCode, ApiConstant.getMessage(errorCode), null);
	}

    protected WebResult buildFailedInfo(int errorCode, String appendMsg) {
        return new WebResult(errorCode, appendMsg, null);
	}

    protected WebResult buildFailedInfo(String errorMsg) {
        return new WebResult(ApiConstant.BASE_FAIL_CODE, errorMsg, null);
	}
	
}

package com.liangzhicheng.common.basic;

import com.liangzhicheng.common.constant.ApiConstant;

/**
 * @description 接口请求结果信息返回基础控制器
 * @author liangzhicheng
 * @since 2020-07-28
 */
public abstract class BaseController {

	protected ResponseResult buildSuccessInfo(Object resultData) {
		return new ResponseResult(ApiConstant.BASE_SUCCESS_CODE, ApiConstant.getMessage(ApiConstant.BASE_SUCCESS_CODE), resultData);
	}

    protected ResponseResult buildFailedInfo(int errorCode) {
        return new ResponseResult(errorCode, ApiConstant.getMessage(errorCode), null);
	}

    protected ResponseResult buildFailedInfo(int errorCode, String appendMsg) {
        return new ResponseResult(errorCode, appendMsg, null);
	}

    protected ResponseResult buildFailedInfo(String errorMsg) {
        return new ResponseResult(ApiConstant.BASE_FAIL_CODE, errorMsg, null);
	}

}

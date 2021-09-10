package com.liangzhicheng.common.basic;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @description 响应参数
 * @author liangzhicheng
 * @since 2020-07-28
 */
@ApiModel(value = "ResponseResult", description = "响应结果集合")
public class ResponseResult {

	@ApiModelProperty(notes = "返回码，10000表示成功，非10000表示失败")
	private Integer code;

	@ApiModelProperty(notes = "返回消息，成功为\"success\"，失败为具体失败信息")
	private String message;

	@ApiModelProperty(notes = "返回的具体数据")
	private Object data;

	public ResponseResult() {
		super();
	}

	public ResponseResult(Integer resultCode) {
		super();
		this.code = resultCode;
	}

	public ResponseResult(Object data) {
		super();
		this.data = data;
	}

	public ResponseResult(Integer code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public ResponseResult(Integer code, String message, Object data) {
		super();
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}

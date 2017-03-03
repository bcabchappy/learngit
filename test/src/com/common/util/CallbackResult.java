package com.common.util;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONObject;

/**
 * 回调结果（一般转为json字符再返回）
 * 
 * @author cannel
 * 
 */
public class CallbackResult {

	/**
	 * 回调是否成功
	 */
	private Boolean success = true;

	/**
	 * 给前端的提示信息
	 */
	private String message = "";

	/**
	 * 返回的值，复杂对象序列化成json字符串
	 */
	private String returnValue = "";

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getReturnValue() {
		return returnValue;
	}

	public void setReturnValue(String returnValue) {
		this.returnValue = returnValue;
	}

	/**
	 * 把本对象输出为json
	 * 
	 * @return
	 */
	public String toJson() {
		return JSONObject.fromObject(this).toString();
	}

	/**
	 * 设为错误状态，设置提示信息，并输出json
	 * @param msg
	 * @return
	 */
	public String setErrorToJson(String msg) {
		success = false;
		if (StringUtils.isNotEmpty(msg))
			message = msg;

		return toJson();
	}
}

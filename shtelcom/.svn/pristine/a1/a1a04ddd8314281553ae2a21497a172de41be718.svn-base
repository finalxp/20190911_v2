package cn.productivetech.shtelcom.enrol.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class RspApi {
	private int retCode;
	private String retMsg;

	private Object data;

	public int getRetCode() {
		return retCode;
	}

	public void setRetCode(int retCode) {
		this.retCode = retCode;
	}
	public void setRetCode(String retCode) {
		this.retCode = Integer.parseInt(retCode);
	}

	public String getRetMsg() {
		return retMsg;
	}

	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public static RspApi error(int code, String message) {
		RspApi result = new RspApi();
		result.setRetCode(code);
		result.setRetMsg(message);
		return result;
	}

	public static RspApi error(String message) {
		return error(1, message);
	}

	public static RspApi unAuthen() {
		return error(403, "账户未登录");
	}

	public static RspApi success(String message, Object extraData) {
		RspApi result = new RspApi();
		result.setRetCode(0);
		result.setRetMsg(message);
		result.setData(extraData);
		return result;
	}

	public static RspApi success(String message) {
		return success(message, null);
	}

	public static RspApi success(Object extraData) {
		return success("success", extraData);
	}

	public static RspApi success() {
		return success("success");
	}

}

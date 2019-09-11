package cn.xs.erp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class RspResultDto {
	protected String retCode;
	protected String retMsg;

	public RspResultDto(){}
	public RspResultDto(String code,String msg){
		this.retCode = code;
		this.retMsg = msg;
	}
	public static RspResultDto createSuccess(){
		return new RspResultDto("0",null);
	}
	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getRetMsg() {
		return retMsg;
	}

	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}

}

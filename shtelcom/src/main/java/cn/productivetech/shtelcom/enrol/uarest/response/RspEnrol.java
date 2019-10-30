package cn.productivetech.shtelcom.enrol.uarest.response;

import cn.productivetech.shtelcom.enrol.uarest.response.result.EnrolResult;

public class RspEnrol extends RspBase {

	public EnrolResult result;

	public EnrolResult getResult() {
		return result;
	}

	public void setResult(EnrolResult result) {
		this.result = result;
	}
}

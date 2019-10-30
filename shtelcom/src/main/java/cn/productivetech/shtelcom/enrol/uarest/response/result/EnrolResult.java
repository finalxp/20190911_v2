package cn.productivetech.shtelcom.enrol.uarest.response.result;

import cn.productivetech.shtelcom.enrol.uarest.payload.UtteranceInformation;

public class EnrolResult extends BaseResult {
	private UtteranceInformation utteranceInformation;

	private String numberModelUpdates;

	public String getNumberModelUpdates() {
		return numberModelUpdates;
	}

	public void setNumberModelUpdates(String numberModelUpdates) {
		this.numberModelUpdates = numberModelUpdates;
	}

	public UtteranceInformation getUtteranceInformation() {
		return utteranceInformation;
	}

	public void setUtteranceInformation(UtteranceInformation utteranceInformation) {
		this.utteranceInformation = utteranceInformation;
	}

}

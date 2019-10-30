package cn.productivetech.shtelcom.enrol.uarest.response;

import java.util.List;

import cn.productivetech.shtelcom.enrol.uarest.payload.EnrolItem;

public class RspIsEnrolled extends RspBase {

	private String outcome;

	public String getOutcome() {
		return outcome;
	}

	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}

	private List<EnrolItem> model;

	public List<EnrolItem> getModel() {
		return model;
	}

	public void setModel(List<EnrolItem> model) {
		this.model = model;
	}

	public String getNumberOfUpdate(String biometricType) {

		if (model == null || model.size() == 0)
			return null;
		for (EnrolItem ei : model) {
			if (biometricType.equals(ei.getType())) {
				return ei.getNumberModelUpdates();
			}
		}
		return null;
	}

	public boolean isExist() {
		return "KNOWN_USER_ACTIVE".equals(outcome);
	}
}

package cn.productivetech.shtelcom.enrol.uarest.response.result;

import java.util.List;

import cn.productivetech.shtelcom.enrol.uarest.payload.MetaInformation;

public class BaseResult {

	private List<MetaInformation> metaInformation;

	public List<MetaInformation> getMetaInformation() {
		return metaInformation;
	}

	public void setMetaInformation(List<MetaInformation> metaInformation) {
		this.metaInformation = metaInformation;
	}

	public String getMetaInfoScore(String key) {

		if (key == null || key.length() == 0 || this.getMetaInformation() == null
				|| this.getMetaInformation().size() == 0)
			return "";

		for (MetaInformation item : this.getMetaInformation()) {
			if (key.equals(item.getKey())) {
				return item.getValue().getValue();
			}
		}
		return "";
	}
}

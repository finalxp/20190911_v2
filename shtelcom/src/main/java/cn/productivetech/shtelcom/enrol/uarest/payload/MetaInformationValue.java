package cn.productivetech.shtelcom.enrol.uarest.payload;

public class MetaInformationValue {

	private String	value;
	private String	encrypted;

	public MetaInformationValue() {
		this.value = "default";
		this.encrypted = "false";
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getEncrypted() {
		return encrypted;
	}

	public void setEncrypted(String encrypted) {
		this.encrypted = encrypted;
	}

}

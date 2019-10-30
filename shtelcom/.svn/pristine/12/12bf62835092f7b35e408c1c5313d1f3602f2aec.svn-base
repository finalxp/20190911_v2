package cn.productivetech.shtelcom.enrol.uarest.core;

public class UAException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9160737706060605085L;

	public UAException() {
		this(null);
	}

	public UAException(String message) {
		this("0", message);
	}

	public UAException(int code, String message) {
		super(message);
		this.code = String.valueOf(code);
	}

	public UAException(String code, String message) {
		super(message);
		this.code = code;
	}

	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}

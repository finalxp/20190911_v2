package cn.productivetech.shtelcom.enrol.api.response;

import java.util.List;

import cn.productivetech.shtelcom.enrol.model.DepAndUserBean;

public class UserDto {
	List<DepAndUserBean> depList;
	private String token;
	private int privilege;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public UserDto() {
	}

	public UserDto(List<DepAndUserBean> depList, String token, int privilege) {
		this.depList = depList;
		this.token = token;
		this.privilege = privilege;
	}

	public List<DepAndUserBean> getDepList() {
		return depList;
	}

	public void setDepList(List<DepAndUserBean> depList) {
		this.depList = depList;
	}

	public int getPrivilege() {
		return privilege;
	}

	public void setPrivilege(int privilege) {
		this.privilege = privilege;
	}

}

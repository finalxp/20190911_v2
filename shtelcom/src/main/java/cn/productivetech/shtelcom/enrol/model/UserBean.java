package cn.productivetech.shtelcom.enrol.model;

import java.util.Date;

public class UserBean {
	private Short userId;

	private String userLoginName;

	private String name;

	private String textNumber;

	private Integer administratorPrivileges;

	private String userDepId;

	private Date createTime;

	private Date updateTime;

	private String createUser;

	private String updateUser;

	private String unitDep;

	public Short getUserId() {
		return userId;
	}

	public void setUserId(Short userId) {
		this.userId = userId;
	}

	public String getUserLoginName() {
		return userLoginName;
	}

	public void setUserLoginName(String userLoginName) {
		this.userLoginName = userLoginName == null ? null : userLoginName.trim();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getTextNumber() {
		return textNumber;
	}

	public void setTextNumber(String textNumber) {
		this.textNumber = textNumber == null ? null : textNumber.trim();
	}

	public Integer getAdministratorPrivileges() {
		return administratorPrivileges;
	}

	public void setAdministratorPrivileges(Integer administratorPrivileges) {
		this.administratorPrivileges = administratorPrivileges;
	}

	public String getUserDepId() {
		return userDepId;
	}

	public void setUserDepId(String userDepId) {
		this.userDepId = userDepId == null ? null : userDepId.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser == null ? null : createUser.trim();
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser == null ? null : updateUser.trim();
	}

	public boolean isSystemAdmin() {
		return 2 == administratorPrivileges;
	}

	public boolean isUnitAdmin() {
		return 1 == administratorPrivileges;
	}

	public String getUnitDep() {
		return unitDep;
	}

	public void setUnitDep(String unitDep) {
		this.unitDep = unitDep;
	}

}
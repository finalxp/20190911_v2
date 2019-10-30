package cn.productivetech.shtelcom.enrol.service;

import java.util.List;

import cn.productivetech.shtelcom.enrol.model.DepAndUserBean;
import cn.productivetech.shtelcom.enrol.model.TextBean;
import cn.productivetech.shtelcom.enrol.model.UserBean;

public interface IAppService {

	UserBean findByName(String userName);

	List<TextBean> getTextBeanList();
	
	List<DepAndUserBean> selectUnitDeps(String depId);
	
	List<DepAndUserBean> selectRootDeps();
	
	List<DepAndUserBean> selectUnitAdminUsers();
	
	List<DepAndUserBean> getDepAndUserList(String depId);

	List<DepAndUserBean> searchUser(String keyWord, int privilege, String depId);

	void updateTextNumber(Short userId);

	void updatePrivilege(Short userId, int privilege);
}

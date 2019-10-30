package cn.productivetech.shtelcom.enrol.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.productivetech.shtelcom.enrol.dao.IDepartmentDao;
import cn.productivetech.shtelcom.enrol.dao.ITextDao;
import cn.productivetech.shtelcom.enrol.dao.IUserDao;
import cn.productivetech.shtelcom.enrol.model.DepAndUserBean;
import cn.productivetech.shtelcom.enrol.model.TextBean;
import cn.productivetech.shtelcom.enrol.model.UserBean;
import cn.productivetech.shtelcom.enrol.service.IAppService;

@Service("userService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class AppServiceImpl implements IAppService {

	@Autowired
	private IUserDao userDao;

	@Autowired
	private ITextDao textDao;

	@Autowired
	private IDepartmentDao departmentDao;

	@Override
	public UserBean findByName(String userName) {
		return userDao.selectByLoginName(userName);
	}

	@Override
	public List<TextBean> getTextBeanList() {
		return textDao.selectByStatus(1);
	}

	@Override
	public List<DepAndUserBean> getDepAndUserList(String depId) {
		return userDao.selectDepAndUser(depId);
	}

	@Override
	public List<DepAndUserBean> searchUser(String keyWord, int privilege, String depId) {
		if (privilege == 2) {
			return userDao.searchUserByAdmin(keyWord);
		}
		return userDao.searchUserByUnitAdmin(keyWord, depId);
	}

	@Override
	public void updateTextNumber(Short userId) {
		userDao.updateTextNumber(userId);
	}

	@Override
	public void updatePrivilege(Short userId, int privilege) {
		userDao.updatePrivilege(userId, privilege);
	}

	@Override
	public List<DepAndUserBean> selectUnitDeps(String depId) {
		
		return userDao.selectUnitDeps(depId);
	}


	@Override
	public List<DepAndUserBean> selectRootDeps() {
		return userDao.selectRootDeps();
	}

	@Override
	public List<DepAndUserBean> selectUnitAdminUsers() {
		return userDao.selectUnitAdminUsers();
	}
}

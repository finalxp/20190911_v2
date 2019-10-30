package cn.productivetech.shtelcom.enrol.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.productivetech.shtelcom.enrol.model.DepAndUserBean;
import cn.productivetech.shtelcom.enrol.model.UserBean;

@Mapper
public interface IUserDao extends IBaseDao<UserBean, Short> {
	UserBean selectByLoginName(String loginName);

	List<DepAndUserBean> searchUserByAdmin(String keyWord);

	List<DepAndUserBean> selectDepAndUser(String depId);

	List<DepAndUserBean> selectUnitDeps(String depId);

	List<DepAndUserBean> selectRootDeps();
	
	List<DepAndUserBean> selectUnitAdminUsers();

	List<DepAndUserBean> searchUserByUnitAdmin(@Param("keyWord") String keyWord,
			@Param("depId") String depId);

	Integer updateTextNumber(Short userId);

	Integer updatePrivilege(@Param("userId") Short userId, @Param("privilege") Integer privilege);
}
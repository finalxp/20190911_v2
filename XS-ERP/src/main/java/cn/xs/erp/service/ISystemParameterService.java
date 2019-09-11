package cn.xs.erp.service;

import cn.xs.erp.dto.RspResultDto;
import cn.xs.erp.model.PageInfo;
import cn.xs.erp.model.SystemParameterItem;

public interface ISystemParameterService {
	PageInfo<SystemParameterItem> listSystemParameterByPage(Integer page, Integer rows);

	SystemParameterItem selectSystemParameterByID(int id);

	RspResultDto addSystemParameter(SystemParameterItem systemParameterItem,int userID);

	RspResultDto updateSystemParameter(SystemParameterItem systemParameterItem,	int userID);

	RspResultDto deleteSystemParameter(int id);

}

package cn.xs.erp.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import cn.xs.erp.dao.ISystemParameterDao;
import cn.xs.erp.dto.RspResultDto;
import cn.xs.erp.dto.RspResultMessage;
import cn.xs.erp.model.PageInfo;
import cn.xs.erp.model.SystemParameterItem;
import cn.xs.erp.service.ISystemParameterService;
@Service
public class SystemParameterServiceImpl implements ISystemParameterService{

	@Autowired
	private ISystemParameterDao systemParameterDao;
	
	@Override
	public PageInfo<SystemParameterItem> listSystemParameterByPage(Integer page, Integer rows) {
		PageHelper.startPage(page, rows);
		List<SystemParameterItem> list = systemParameterDao.selectAll();
		PageInfo<SystemParameterItem> info = new PageInfo<SystemParameterItem>(list);
		
		return info;
	}

	@Override
	public SystemParameterItem selectSystemParameterByID(int id) {
		return systemParameterDao.selectByPrimaryKey(id);
	}

	@Override
	public RspResultDto addSystemParameter(SystemParameterItem systemParameterItem, int userID) {
		systemParameterItem.setCreateBy(userID);
		systemParameterItem.setCreateTime(new Date());
		if (systemParameterDao.insertSelective(systemParameterItem)<1) {
			return new RspResultDto(RspResultMessage.AddSystemParameterFailed);
		}
		return new RspResultDto(RspResultMessage.AddSystemParameterSuccess);
	}

	@Override
	public RspResultDto updateSystemParameter(SystemParameterItem systemParameterItem, int userID) {
		systemParameterItem.setUpdateBy(userID);
		systemParameterItem.setUpdateTime(new Date());
		if (systemParameterDao.updateByPrimaryKeySelective(systemParameterItem)<1) {
			return new RspResultDto(RspResultMessage.EditSystemParameterFailed);
		}
		return new RspResultDto(RspResultMessage.EditSystemParameterSuccess);
	}

	@Override
	public RspResultDto deleteSystemParameter(int id) {
		if (systemParameterDao.deleteByPrimaryKey(id)<1) {
			return new RspResultDto(RspResultMessage.DeleteSystemParameterFailed);
		}
		return new RspResultDto(RspResultMessage.DeleteSystemParameterSuccess);
	}

}

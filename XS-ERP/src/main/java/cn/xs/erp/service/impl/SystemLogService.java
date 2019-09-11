package cn.xs.erp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import cn.xs.erp.dao.ISystemLogDao;
import cn.xs.erp.model.PageInfo;
import cn.xs.erp.model.SystemLogItem;
import cn.xs.erp.service.ISystemLogService;
@Service
public class SystemLogService implements ISystemLogService{
	@Autowired
	private ISystemLogDao systemLogDao;
	@Override
	public PageInfo<SystemLogItem> selectAllLogByPage(int page, int rows,String startTime, String endTime) {
		PageHelper.startPage(page, rows);
		if (endTime!=null && endTime!="") {
			endTime+=" 23:59:59";
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("startTime", startTime);
		map.put("endTime", endTime);

		List<SystemLogItem> list = systemLogDao.selectAllInfo(map);
		PageInfo<SystemLogItem> pageinfo = new PageInfo<SystemLogItem>(list);
		return pageinfo;
	}
	
	@Override
	public int addLog(SystemLogItem sysItem) {
		return systemLogDao.insertSelective(sysItem);
	}

}

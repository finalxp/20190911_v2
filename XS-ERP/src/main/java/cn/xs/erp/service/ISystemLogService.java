package cn.xs.erp.service;

import cn.xs.erp.model.PageInfo;
import cn.xs.erp.model.SystemLogItem;

public interface ISystemLogService {

	PageInfo<SystemLogItem> selectAllLogByPage(int page, int rows, String startTime, String endTime);

	int addLog(SystemLogItem sysItem);

}

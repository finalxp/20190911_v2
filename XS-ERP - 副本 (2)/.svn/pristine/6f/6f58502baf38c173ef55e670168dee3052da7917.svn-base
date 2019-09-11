package cn.xs.erp.service;

import java.util.List;

import cn.xs.erp.dto.RspResultDto;
import cn.xs.erp.model.CheckinRecordItem;
import cn.xs.erp.model.PageInfo;

public interface ICheckinRecordService {
	RspResultDto addCheckinRecord(CheckinRecordItem checkinRecordItem);
	int deleteCheckinRecord(int id);
	RspResultDto updateCheckinRecord(int id,int userID);
	
	CheckinRecordItem getCheckinRecordById(int id);
	List<CheckinRecordItem> selectAllCheckinRecord(int loginUser);

	PageInfo<CheckinRecordItem> selectToolbarList(Integer page,Integer rows,String startTime,String endTime,int id,String ifApply);
	PageInfo<CheckinRecordItem> selectAllManagerCheckinByPage(Integer page,Integer rows,int loginUser,String startTime,String endTime);
	
	
	
}

package cn.xs.erp.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import cn.xs.erp.dao.ICheckinRecordDao;
import cn.xs.erp.dao.IEmployeeDao;
import cn.xs.erp.dto.RspResultDto;
import cn.xs.erp.dto.RspResultMessage;
import cn.xs.erp.model.CheckinRecordItem;
import cn.xs.erp.model.EmployeeItem;
import cn.xs.erp.model.PageInfo;
import cn.xs.erp.service.ICheckinRecordService;
@Service
public class CheckinRecordService implements ICheckinRecordService {
	@Autowired
	private ICheckinRecordDao crDao;
	@Autowired
	private IEmployeeDao empDao;
	@Override
	public RspResultDto addCheckinRecord(CheckinRecordItem checkinRecordItem) {
		Date d = checkinRecordItem.getCheckinTime();
		String timeInOut = checkinRecordItem.getTimeInOut();
		Calendar c = Calendar.getInstance();
		if (timeInOut.equals("timeIn")) {
			c.setTime(d);
			c.set(Calendar.HOUR, 8);
			c.set(Calendar.MINUTE, 30);
		} else if (timeInOut.equals("timeOut")) {
			c.setTime(d);
			c.set(Calendar.HOUR_OF_DAY, 17);
			c.set(Calendar.MINUTE, 30);
		}
		EmployeeItem eItem = empDao.getEmployeeByNo(checkinRecordItem.getEmpNo());
		checkinRecordItem.setEmpId(eItem.getId());
		checkinRecordItem.setCheckinTime(c.getTime());
		checkinRecordItem.setCheckinCategory((short) 2);
		checkinRecordItem.setIsEnable((short) 0);
		checkinRecordItem.setCreateTime(new Date());
		if(crDao.insertSelective(checkinRecordItem)>0) {
			return new RspResultDto(RspResultMessage.AddCheckinRecordSuccess);
		}else {
			return new RspResultDto(RspResultMessage.AddCheckinRecordFailed);
		}
	}

	@Override
	public int deleteCheckinRecord(int id) {
		return crDao.deleteByPrimaryKey(id);
	}

	@Override
	public List<CheckinRecordItem> selectAllCheckinRecord(int loginUser) {
		return crDao.selectLoginUser(loginUser);
	}

	@Override
	public CheckinRecordItem getCheckinRecordById(int id) {
		return crDao.selectByPrimaryKey(id);
	}

	@Override
	public PageInfo<CheckinRecordItem> selectToolbarList(Integer page, Integer rows, String startTime, String endTime,int userID,String ifApply) {
		PageHelper.startPage(page, rows);
		Map<String, String> hasMap =new HashMap<String, String>();
		hasMap.put("startTime", startTime);
		hasMap.put("endTime", endTime);
		hasMap.put("id", userID+"");
		List<CheckinRecordItem> list;
		if (ifApply=="" || ifApply==null) {
			if ((startTime==null || startTime=="") && (endTime==null || endTime=="")) {
				list = crDao.selectLoginUser(userID);
			}else {
				list=crDao.selectCRToolbarList(hasMap);
			}
		}else {
			list = crDao.selectToolbarAppplyList(hasMap);
		}
		PageInfo<CheckinRecordItem> pageInfo =  new PageInfo<CheckinRecordItem>(list);
		return pageInfo;
	}

	@Override
	public PageInfo<CheckinRecordItem> selectAllManagerCheckinByPage(Integer page, Integer rows, int loginUser,String startTime,String endTime) {
		PageHelper.startPage(page, rows);
		Map<String, String> hasMap =new HashMap<String, String>();
		if (endTime!=null && endTime!="") {
			endTime+=" 23:59:00";
		}
		hasMap.put("startTime", startTime);
		hasMap.put("endTime", endTime);
		hasMap.put("loginUser", loginUser+"");
		List<CheckinRecordItem> list;
		if ((startTime==null || startTime=="") && (endTime==null || endTime=="")) {
			list=crDao.selectManagerCheckinList(loginUser);
		}else {
			list=crDao.selectManagerCheckinListByDate(hasMap);
		}
		PageInfo<CheckinRecordItem> pageInfo =  new PageInfo<CheckinRecordItem>(list);
		return pageInfo;
	}

	@Override
	public RspResultDto updateCheckinRecord(int id,int userID) {
		
		CheckinRecordItem crItem = crDao.selectByPrimaryKey(id);
		crItem.setIsEnable((short) 1);
		crItem.setAuditBy(userID);
		crItem.setAuditTime(new Date());
		
		int i = crDao.updateByPrimaryKeySelective(crItem);
		if (i > 0) {
			return new RspResultDto(RspResultMessage.EditCheckinRecordSuccess);
		} else {
			return new RspResultDto(RspResultMessage.EditCheckinRecordFailed);
		}
	}

}

package cn.xs.erp.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import cn.xs.erp.dao.IJobDao;
import cn.xs.erp.dto.RspResultDto;
import cn.xs.erp.dto.RspResultMessage;
import cn.xs.erp.model.JobItem;
import cn.xs.erp.model.PageInfo;
import cn.xs.erp.service.IJobService;
@Service
public class JobServiceImpl implements IJobService{
	@Autowired
	private IJobDao jobDao;
	@Override
	public PageInfo<JobItem> selectAllByPage(int page, int rows,String rankFrom,String rankTo,String jobName) {
		PageHelper.startPage(page, rows);
		Map<String, String> map = new HashMap<String, String>();
		map.put("rankFrom", rankFrom);
		map.put("rankTo", rankTo);
		map.put("jobName", jobName);
		List<JobItem> list = jobDao.selectByInfo(map);
		return new PageInfo<JobItem>(list);
	}
	@Override
	public JobItem selectJobByID(int id) {
		return jobDao.selectByPrimaryKey(id);
	}
	@Override
	public RspResultDto insertJobItem(JobItem jobItem,int userID) {
		jobItem.setCreateBy(userID);
		jobItem.setCreateTime(new Date());
		if (jobDao.insertSelective(jobItem)<1) {
			return new RspResultDto(RspResultMessage.AddJobItemFailed);
		}
		return new RspResultDto(RspResultMessage.AddJobItemSuccess);
	}
	@Override
	public RspResultDto updateJobItem(JobItem jobItem,int userID) {
		jobItem.setUpdateBy(userID);
		jobItem.setUpdateTime(new Date());
		if (jobDao.updateByPrimaryKeySelective(jobItem)<1) {
			return new RspResultDto(RspResultMessage.EditJobItemFailed);
		}
		return new RspResultDto(RspResultMessage.EditJobItemSuccess);
	}
	@Override
	public RspResultDto deleteJobItemByID(int id) {
		if (jobDao.deleteByPrimaryKey(id)<1) {
			return new RspResultDto(RspResultMessage.DeleteJobItemFailed);
		}
		return new RspResultDto(RspResultMessage.DeleteJobItemSuccess);
	}

}

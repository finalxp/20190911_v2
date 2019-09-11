package cn.xs.erp.service;

import cn.xs.erp.dto.RspResultDto;
import cn.xs.erp.model.JobItem;
import cn.xs.erp.model.PageInfo;

public interface IJobService {

	PageInfo<JobItem> selectAllByPage(int page, int rows, String rankFrom, String rankTo, String jobName);

	JobItem selectJobByID(int id);

	RspResultDto insertJobItem(JobItem jobItem, int i);

	RspResultDto updateJobItem(JobItem jobItem, int i);

	RspResultDto deleteJobItemByID(int id);

}

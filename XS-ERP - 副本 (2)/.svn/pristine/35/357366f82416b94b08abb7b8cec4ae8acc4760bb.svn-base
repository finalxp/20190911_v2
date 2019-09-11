package cn.xs.erp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.xs.erp.model.JobItem;
@Mapper
public interface IJobDao extends IBaseDao<JobItem, Integer>{

	List<JobItem> selectAll();

	List<JobItem> selectByInfo(Map<String, String> map);

}

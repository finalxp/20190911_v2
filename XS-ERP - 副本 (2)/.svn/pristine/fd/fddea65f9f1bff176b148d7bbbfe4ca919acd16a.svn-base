package cn.xs.erp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.xs.erp.model.EmployeeDepartmentItem;
@Mapper
public interface IEmployeeDepartmentDao extends IBaseDao<EmployeeDepartmentItem, Integer> {

	List<EmployeeDepartmentItem> selectByEmpID(int empID);

	int deleteByEmpIdAndDepId(Map<String, Integer> map);
	
}

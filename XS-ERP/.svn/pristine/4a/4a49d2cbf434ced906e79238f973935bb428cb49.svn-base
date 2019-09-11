package cn.xs.erp.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import cn.xs.erp.dto.RspResultDto;
import cn.xs.erp.model.EmployeeItem;
import cn.xs.erp.model.PageInfo;

public interface IEmployeeService {

	EmployeeItem getEmployeeById(int Id);
	PageInfo<EmployeeItem> selectEmployeeByPage(int page, int pageSize);
	//根据toolbar信息，查询员工信息
	PageInfo<EmployeeItem> selectEmployeeByPage(int page,int pagesize,String empNo,String depID,String startTime,String endTime);
	int addEmployee(EmployeeItem employeeItem);
	//根据员工No查询员工信息
	EmployeeItem getEmployeeByNo(String empNo);
	List<EmployeeItem> selectAllEmpInfo();
	//修改员工信息
	int updateEmployeeById(Map<String, Object> map);
	
	RspResultDto deleteByPrimaryKey(int id);
	
	RspResultDto updatePrimaryInfo(EmployeeItem employeeItem,int userID,int[] depID);
	RspResultDto updatePwd(EmployeeItem employeeItem,int userID);
	
	RspResultDto uploadPicture(MultipartFile file);
	
	@Transactional
	RspResultDto addEmployeeDepartment(int[] depID,EmployeeItem eItem,int userID);
	RspResultDto updateEmployeeDepartment(EmployeeItem employeeItem, int userID, int[] depID);
	
	
}

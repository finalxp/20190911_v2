package cn.xs.erp.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import cn.xs.erp.dao.IEmployeeDao;
import cn.xs.erp.model.EmployeeItem;
import cn.xs.erp.model.PageInfo;
import cn.xs.erp.service.IEmployeeService;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

	@Autowired
	private IEmployeeDao empDao;

	@Override
	public EmployeeItem getEmployeeById(int Id) {

		return empDao.selectByPrimaryKey(Id);
	}

	@Override
	public PageInfo<EmployeeItem> selectEmployeeByPage(int page, int pageSize) {
		PageHelper.startPage(page, pageSize);
		List<EmployeeItem> docs = empDao.selectAll();
		PageInfo<EmployeeItem> pageInfo = new PageInfo<>(docs);
		return pageInfo;
	}

	@Override
	public int addEmployee(EmployeeItem employee) {
		return empDao.insert(employee);
	}

	@Override
	public EmployeeItem getEmployeeByNo(String empNo) {
		return empDao.getEmployeeByNo(empNo);
	}

	@Override
	public List<EmployeeItem> selectAllEmpInfo() {
		return empDao.selectAllEmpInfo();
	}

	@Override
	public int updateEmployeeById(Map<String, Object> map) {
		return empDao.updateEmployeeById(map);
	}

	@Override
	public int deleteByPrimaryKey(int id) {
		return empDao.deleteByPrimaryKey(id);
	}
}

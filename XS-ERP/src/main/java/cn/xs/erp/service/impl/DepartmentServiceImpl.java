package cn.xs.erp.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import cn.xs.erp.dao.IDepartmentDao;
import cn.xs.erp.dto.RspResultDto;
import cn.xs.erp.dto.RspResultMessage;
import cn.xs.erp.model.DepartmentItem;
import cn.xs.erp.model.PageInfo;
import cn.xs.erp.service.IDepartmentService;


@Service
public class DepartmentServiceImpl implements IDepartmentService {

	@Autowired
	private IDepartmentDao depDao;

	@Override
	public DepartmentItem getDepartmentById(int Id) {

		return depDao.selectByPrimaryKey(Id);
	}

	@Override
	public PageInfo<DepartmentItem> selectDepartmentByPage(Integer page,Integer pageSize, String depInfo) {
		PageHelper.startPage(page, pageSize);
		List<DepartmentItem> docs;
		if (depInfo==null || depInfo=="") {
			docs = depDao.selectAllDepInfo();
		}else {
			docs=depDao.selectAllDepInfoByDepInfo(depInfo);
		}
		PageInfo<DepartmentItem> pageInfo = new PageInfo<>(docs);
		return pageInfo;
	}

	@Override
	public RspResultDto addDepartment(DepartmentItem department,int userID) {
		
		department.setCreateBy(userID);		
		department.setCreateTime(new Date());	
		
		if (depDao.insert(department)<1) {
			return new RspResultDto(RspResultMessage.AddDepartmentFailed);
		}
		
		return new RspResultDto(RspResultMessage.AddDepartmentSuccess);
	}

	@Override
	public PageInfo<DepartmentItem> selectAllDepInfo(Integer page,Integer rows) {

		PageHelper.startPage(page, rows);
		List<DepartmentItem> list = depDao.selectAllDepInfo();
		PageInfo<DepartmentItem> pageInfo = new PageInfo<DepartmentItem>(list);
		return pageInfo;
	}

	@Override
	public List<DepartmentItem> selectAllDepInfo() {
		return depDao.selectAllDepInfo();
	}

	@Override
	public List<DepartmentItem> selectAllDepInfoByEmpID(int empID) {
		return depDao.selectAllDepInfoByEmpID(empID);
	}



	@Override
	public RspResultDto updateDepartment(DepartmentItem departmentItem, int userID) {
		departmentItem.setUpdateBy(userID);
		departmentItem.setUpdateTime(new Date());
		if (depDao.updateByPrimaryKeySelective(departmentItem)<1) {
			return new RspResultDto(RspResultMessage.EditDepartmentFailed);
		}
		return new RspResultDto(RspResultMessage.EditDepartmentSuccess);
	}

	@Override
	public RspResultDto deleteDepartment(int id) {
		if (depDao.deleteByPrimaryKey(id)<1) {
			return new RspResultDto(RspResultMessage.DeleteDepartmentFailed);
		}
		return new RspResultDto(RspResultMessage.DeleteDepartmentSuccess);
	}

}

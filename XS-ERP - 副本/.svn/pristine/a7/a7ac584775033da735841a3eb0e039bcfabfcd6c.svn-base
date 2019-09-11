package cn.xs.erp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import cn.xs.erp.dao.IDepartmentDao;
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
	public PageInfo<DepartmentItem> selectDepartmentByPage(int page, int pageSize) {
		PageHelper.startPage(page, pageSize);
		List<DepartmentItem> docs = depDao.selectAll();
		PageInfo<DepartmentItem> pageInfo = new PageInfo<>(docs);
		return pageInfo;
	}

	@Override
	public int AddDepartment(DepartmentItem department) {
		return depDao.insert(department);
	}

	@Override
	public List<DepartmentItem> selectAllDepInfo() {
		// TODO Auto-generated method stub
		return depDao.selectAllDepInfo();
	}


	

	
}

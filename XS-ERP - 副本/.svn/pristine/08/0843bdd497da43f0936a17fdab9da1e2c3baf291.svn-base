package cn.xs.erp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.xs.erp.dao.IDoorDao;
import cn.xs.erp.model.DoorItem;
import cn.xs.erp.model.PageInfo;
import cn.xs.erp.service.IDoorService;

import com.github.pagehelper.PageHelper;

@Service
public class DoorServiceImpl implements IDoorService {

	@Autowired
	private IDoorDao doorDao;

	@Override
	public DoorItem getDoorItemById(int Id) {
		return doorDao.selectByPrimaryKey(Id);
	}

	@Override
	public PageInfo<DoorItem> selectDoorByPage(int page, int pageSize) {
		PageHelper.startPage(page, pageSize);
		List<DoorItem> docs = doorDao.selectAll();
		PageInfo<DoorItem> pageInfo = new PageInfo<>(docs);
		return pageInfo;
	}

	@Override
	public int AddEmployee(DoorItem door) {

		return doorDao.insert(door);
	}
}

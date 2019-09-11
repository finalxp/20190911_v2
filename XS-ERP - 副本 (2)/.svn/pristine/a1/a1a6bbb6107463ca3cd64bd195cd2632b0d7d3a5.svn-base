package cn.xs.erp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.xs.erp.model.MenuItem;

@Mapper
public interface IMenuDao extends IBaseDao<MenuItem, Integer> {
	List<MenuItem> selectAll();
	
	//查询一级菜单（parent_id=0）
	List<MenuItem> getFirstLevelMenus();

	List<MenuItem> getTreeMenus();
	
}
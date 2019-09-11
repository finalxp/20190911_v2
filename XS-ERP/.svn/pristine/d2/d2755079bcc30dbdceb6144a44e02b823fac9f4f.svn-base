package cn.xs.erp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.xs.erp.model.DoorItem;

@Mapper
public interface IDoorDao extends IBaseDao<DoorItem, Integer> {
	List<DoorItem> selectAll();

	List<DoorItem> selectByDoorNoAndDoorName(Map<String, String> map);

	List<DoorItem> selectByEmpID(int id);
}
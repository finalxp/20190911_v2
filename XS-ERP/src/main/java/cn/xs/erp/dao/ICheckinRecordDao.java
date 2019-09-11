package cn.xs.erp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.xs.erp.model.CheckinRecordItem;
@Mapper
public interface ICheckinRecordDao extends IBaseDao<CheckinRecordItem, Integer> {
	List<CheckinRecordItem> selectLoginUser(int loginUser);
	List<CheckinRecordItem> selectCRToolbarList(Map<String, String> map);
	List<CheckinRecordItem> selectToolbarAppplyList(Map<String, String> map);
	List<CheckinRecordItem> selectManagerCheckinList(int loginUser);
	List<CheckinRecordItem> selectManagerCheckinListByDate(Map<String, String> hasMap);
}

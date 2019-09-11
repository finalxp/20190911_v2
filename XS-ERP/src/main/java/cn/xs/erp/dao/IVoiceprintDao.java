package cn.xs.erp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.xs.erp.model.VoiceprintItem;

@Mapper
public interface IVoiceprintDao extends IBaseDao<VoiceprintItem, Integer> {
	List<VoiceprintItem> selectAll();
	
}
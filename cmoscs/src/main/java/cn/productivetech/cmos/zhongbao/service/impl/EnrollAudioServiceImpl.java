package cn.productivetech.cmos.zhongbao.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.productivetech.cmos.zhongbao.config.Const;
import cn.productivetech.cmos.zhongbao.dao.IEnrollAudioDao;
import cn.productivetech.cmos.zhongbao.model.EnrollAudioItem;
import cn.productivetech.cmos.zhongbao.service.IEnrollAudioService;
import cn.productivetech.cmos.zhongbao.utils.GetTxtInfoUtils;

/**
 * 注册声纹模型处理类
 * @author   kenny_peng
 * @created  2019年4月29日
 */
@Service
public class EnrollAudioServiceImpl extends BaseService implements IEnrollAudioService{

	@Autowired
	private IEnrollAudioDao eAudioDao;
	
	@Override
	public void downloadAudio(List<EnrollAudioItem> date) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void processAudio(EnrollAudioItem audioEntity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addAudioEntitys(List<EnrollAudioItem> data) {
		//临时存放EnrollAudioItem实体的集合
		List<EnrollAudioItem> list = new ArrayList<EnrollAudioItem>();
		
		int count = 0;					//计数器，没当count到1000就执行保存命令，
		int sum = 0;					//计算总数
		int dataSize = data.size();
		
		Iterator<EnrollAudioItem> iterator = data.iterator();
		while (iterator.hasNext()) {
			count++;
			sum++;
			EnrollAudioItem enrollAudioItem = iterator.next();
			if (count < Const.UPDATE_SIZE_EVERY_TIME && sum < dataSize) {
				list.add(enrollAudioItem);
			}else if (count >= Const.UPDATE_SIZE_EVERY_TIME && sum < dataSize){
				
				//count大于1000,执行一次list.add(processAudioItem)后，执行保存命令，保存成功后，,count置为1，清空list
				addEnrollAudioItem(enrollAudioItem,list);
				count = 0;
				list.clear();
			}else {
				addEnrollAudioItem(enrollAudioItem,list);
				count = 0;
				list.clear();
			}
		}
	}
	
	/**
	 * 保存ProcessAudioItem实体到数据库
	 * @param processAudioItem	
	 * @param list	要批量保存到数据库的ProcessAudioItem集合
	 */
	private void addEnrollAudioItem(EnrollAudioItem enrollAudioItem, List<EnrollAudioItem> list) {
		list.add(enrollAudioItem);
		int insertResult = eAudioDao.insertList(list);
		throwException(insertResult, "处理待检测音频时，保存待检测音频数据异常");
	}

	@Override
	public void updateDownloadedStatus(List<EnrollAudioItem> date) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateDownloadingStatus(List<EnrollAudioItem> data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateProcessStatus(List<EnrollAudioItem> date) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<EnrollAudioItem> parseAudio(String path) {
		List<EnrollAudioItem> list = new ArrayList<EnrollAudioItem>();
		Map<String, String> readTxtFile = GetTxtInfoUtils.readTxtFile(path);
		
		/* 
		 * 迭代器的遍历速度要比增强for循环快很多，是增强for循环的2倍左右。
		 * 使用entrySet遍历的速度要比keySet快很多，是keySet的1.5倍左右。
		 */
		Iterator<Entry<String, String>> iterable = readTxtFile.entrySet().iterator();
		while (iterable.hasNext()) {
			Entry<String, String> entry = iterable.next();
			EnrollAudioItem enrollAudioItem = new EnrollAudioItem();
			enrollAudioItem.setAgentId(entry.getKey());
			enrollAudioItem.setVoiceLocation(entry.getValue());
			enrollAudioItem.setDownloadStatus((byte) 0);
			enrollAudioItem.setCreateDate(new Date());
			list.add(enrollAudioItem);
		}
		return list;
	}

	@Override
	public List<EnrollAudioItem> queryAudioItemsByUnHandle() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}

package cn.productivetech.cmos.zhongbao.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.productivetech.cmos.zhongbao.config.Const;
import cn.productivetech.cmos.zhongbao.core.IQueue;
import cn.productivetech.cmos.zhongbao.dao.IProcessAudioDao;
import cn.productivetech.cmos.zhongbao.model.ProcessAudioItem;
import cn.productivetech.cmos.zhongbao.service.IProcessAudioService;
import cn.productivetech.cmos.zhongbao.utils.GetTxtInfoUtils;

/**
 * 验证声纹处理类实现
 * @author Administrator
 *
 */
@Service
public class ProcessAudioServiceImpl extends BaseService implements IProcessAudioService{

	@Autowired
	private IProcessAudioDao pAudioDao;
	@Autowired
	private IQueue<ProcessAudioItem> queue;
	
	@Override
	public void downloadAudio(List<ProcessAudioItem> data) {
		List<ProcessAudioItem> listDownloaded = new ArrayList<ProcessAudioItem>();			//记录更新下载状态到下载完成
		List<ProcessAudioItem> listDownloadedSuccess = new ArrayList<ProcessAudioItem>();	//下载成功的音频
//		List<ProcessAudioItem> listProcessAudio = new ArrayList<ProcessAudioItem>();		//处理成功音频
		
		//1、开始下载，如果下载失败，将下载状态、下载日期、下载失败原因记录到实体ProcessAudioItem中，存储到listDownloaded集合中
		for (ProcessAudioItem processAudioItem : data) {
			
			/*
			 * 下载音频方法
			 */
			boolean isDownloadSuccess = false;
			if (isDownloadSuccess) {
				listDownloadedSuccess.add(processAudioItem);
			}
			
			processAudioItem.setDownloadStatus((byte) 2);
			listDownloaded.add(processAudioItem);
		}
		
		//2、将记录经过下载后的音频信息集合listDownloaded,批量更新到数据库中，更新失败，抛出异常
		updateDownloadedStatus(listDownloaded);
		
		//3、将下载完成的音频文件，进行文件解码、预处理
		
		
		
		//4、样本比对
		
		
		
		//5、对比结果输出
//		updateProcessStatus(listProcessAudio);
		
		
	}

	@Override
	public void processAudio(ProcessAudioItem audioEntity) {
		
	}

	@Override
	public void addAudioEntitys(List<ProcessAudioItem> data) {
		
		//临时存放ProcessAudioItem实体的集合
		List<ProcessAudioItem> list = new ArrayList<ProcessAudioItem>();
		
		int count = 0;					//计数器，没当count到1000就执行保存命令，
		int sum = 0;					//计算总数
		int dataSize = data.size();
		
		Iterator<ProcessAudioItem> iterator = data.iterator();
		while (iterator.hasNext()) {
			count++;
			sum++;
			ProcessAudioItem processAudioItem = iterator.next();
			if (count < Const.UPDATE_SIZE_EVERY_TIME && sum < dataSize) {
				list.add(processAudioItem);
			}else if (count >= Const.UPDATE_SIZE_EVERY_TIME && sum < dataSize){
				
				//count大于1000,执行一次list.add(processAudioItem)后，执行保存命令，保存成功后，,count置为1，清空list
				addProcessAudioItem(processAudioItem,list);
				count = 0;
				list.clear();
			}else {
				addProcessAudioItem(processAudioItem,list);
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
	private void addProcessAudioItem(ProcessAudioItem processAudioItem, List<ProcessAudioItem> list) {
		list.add(processAudioItem);
		int insertResult = pAudioDao.insertList(list);
		throwException(insertResult, "处理待检测音频时，保存待检测音频数据异常");
	}
	
	/**
	 * 获取数据库表名，如果数据库中没有该表名，则创建，创建失败抛出异常
	 * @return
	 */
	@SuppressWarnings("unused")
	private String getTableName() {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
		String nowDate = df.format(new Date());
		String tableName = "t_process_audio"+nowDate;
		
		//判断数据库表名是否存在,不存在就创建，创建失败跑出异常
		int isExistTableName = pAudioDao.isTableExists(tableName);
		if (isExistTableName < 1) {
			int isCreateTableSuccess = pAudioDao.createNewTable(tableName);
			throwException(isCreateTableSuccess, "创建数据库表名" + tableName + "失败！");
		}
		return tableName;
	}

	@Override
	public void updateProcessStatus(List<ProcessAudioItem> data) {
		int isUpdateSuccess = pAudioDao.updateProcessStatus(data);
		throwException(isUpdateSuccess, "更新验证声纹处理状态到处理完成失败!");
	}

	@Override
	public List<ProcessAudioItem> parseAudio(String path){
		List<ProcessAudioItem> list = new ArrayList<ProcessAudioItem>();
		Map<String, String> readTxtFile = GetTxtInfoUtils.readTxtFile(path);
		
		/* 
		 * 迭代器的遍历速度要比增强for循环快很多，是增强for循环的2倍左右。
		 * 使用entrySet遍历的速度要比keySet快很多，是keySet的1.5倍左右。
		 */
		Iterator<Entry<String, String>> iterable = readTxtFile.entrySet().iterator();
		while (iterable.hasNext()) {
			Entry<String, String> entry = iterable.next();
			ProcessAudioItem processAudioItem = new ProcessAudioItem();
			processAudioItem.setAgentId(entry.getKey());
			processAudioItem.setVoiceLocation(entry.getValue());
			processAudioItem.setDownloadStatus((byte) 0);
			processAudioItem.setCreateDate(new Date());
			list.add(processAudioItem);
		}
		return list;
	}

	@Override
	public List<ProcessAudioItem> queryAudioItemsByUnHandle() {
		List<ProcessAudioItem> list = pAudioDao.queryAudioItemsByUnHandle();
		return list;
	}

	@Override
	public void updateDownloadedStatus(List<ProcessAudioItem> data) {
		int isUpdateSuccess = pAudioDao.updateDownloadedStatus(data);
		throwException(isUpdateSuccess, "更新下载状态到下载完成失败!");
	}
	
	@Override
	public void updateDownloadingStatus(List<ProcessAudioItem> data) {
		int isUpdateSuccess = pAudioDao.updateDownloadingStatus(data);
		throwException(isUpdateSuccess, "更新下载状态到下载中失败!");
	}

	
	
	
}

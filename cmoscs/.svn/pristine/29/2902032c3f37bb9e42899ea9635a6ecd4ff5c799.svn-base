package cn.productivetech.cmos.zhongbao.core.impl;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.productivetech.cmos.zhongbao.config.Const;
import cn.productivetech.cmos.zhongbao.core.IQueue;
import cn.productivetech.cmos.zhongbao.model.ProcessAudioItem;
import cn.productivetech.cmos.zhongbao.service.IProcessAudioService;

/**
 * 消息队列实现方式
 * 1、put由语音接续处理模块进行进队列
 * 2、pop由语音处理模块进行出队列
 * @author kenny.peng
 * @craeted 2019-04-19
 */
@Service
public class QueueImpl implements IQueue<ProcessAudioItem> {

	@Autowired
	private IProcessAudioService pAudioService;
	
	//阻塞队列，FIFO
//	private LinkedBlockingQueue<ProcessAudioItem> lBQueue = new LinkedBlockingQueue<ProcessAudioItem>(Const.QUEUE_SIZE);
	
	private synchronized LinkedBlockingQueue<ProcessAudioItem> getLBQueue(){
		return new LinkedBlockingQueue<ProcessAudioItem>(Const.QUEUE_SIZE);
	}
	private LinkedBlockingQueue<ProcessAudioItem> lBQueue = getLBQueue();
	
	int i = 0;
	
	@Override
	public void put(List<ProcessAudioItem> data) {
		
//		int count = lBQueue.size();
//		System.out.println(i++);
//		System.out.println(date.getId());
		
		//队列满了之后等待一段时间，让消费者先消费掉一些在添加
//		if (count >= Const.QUEUE_SIZE) {
//			try {
//				Thread.sleep(3000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
		//迭代data集合，将下载地址信息添加到队列中
		Iterator<ProcessAudioItem> iterator = data.iterator();
		while (iterator.hasNext()) {
			ProcessAudioItem processAudioItem = iterator.next();
			//向消息队列中添加数据
			lBQueue.add(processAudioItem);
		}
		
		
		
	}
	
	@Override
	public void pop() {
		ProcessAudioItem pAudioItem;
		int countSize = 0;															//记录每次循环出队列次数
		List<ProcessAudioItem> listDownloading = new ArrayList<ProcessAudioItem>();	//记录更新下载状态
		
		popWhile : while(Const.UPDATE_SIZE_EVERY_TIME > countSize){
			
			// 使用poll()方法 将产生非阻塞效果
			pAudioItem = lBQueue.poll();
			if (pAudioItem !=null) {
				countSize++;
				//1、更新下载状态,置为下载中
				pAudioItem.setDownloadStatus((byte) 1);
				listDownloading.add(pAudioItem);
			}else {
				
				//如果队列中消息为空，则结束循环
				break popWhile;
			}
		}
		if (listDownloading.size() > 0) {
			//完成一次pop量，批量更新下载状态为下载中,更新失败就抛出异常
			pAudioService.updateDownloadingStatus(listDownloading);

			//2、开始下载需要验证音频（listWaitHandle）,并批量更新下载状态到下载完成，更新失败抛出异常
			pAudioService.downloadAudio(listDownloading);
		}
		

		

	}


	@Override
	public int size() {
		return lBQueue.size();
	}

}

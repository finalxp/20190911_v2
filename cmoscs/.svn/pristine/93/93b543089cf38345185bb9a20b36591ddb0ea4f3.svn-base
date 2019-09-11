package cn.productivetech.cmos.zhongbao.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;

import cn.productivetech.cmos.zhongbao.model.EnrollAudioItem;
import cn.productivetech.cmos.zhongbao.service.IEnrollAudioService;

/**
 * 声纹模型建模控制器
 * 1、连续监听样本接续文件夹文件变化，
 * 2、有新增txt，解析txt，存储数据
 * 3、下载文件
 * 4、建模
 * 5、模型存储到(1)数据库(2)样本缓存
 * 
 * @author   kenny_peng
 * @created  2019年4月26日
 */
public class EnrollAudioController extends BaseController{
	
	private IEnrollAudioService eAudioService;
	
	@RequestMapping("/processEnrollTxt")
	public void processEnrollTxt(String path){
		List<EnrollAudioItem> listDownloadingAudio = new ArrayList<EnrollAudioItem>();
		
		//根据新推送txt文本路径获取ProcessAudioItem实体类集合
		List<EnrollAudioItem> listEnrollAudioItems = eAudioService.parseAudio(path);
		if (listEnrollAudioItems != null) {
			
			//ProcessAudioItem实体类集合信息存储到数据库中,存储失败抛出异常
			eAudioService.addAudioEntitys(listEnrollAudioItems);
			
			//1、开始下载语音文件
			for (EnrollAudioItem enrollAudioItem : listEnrollAudioItems) {
				
				//2、记录文件下载状态到下载中，存入list
				
				
				//3、建模前，先检查所有agentId是否已经存在，存在的agentId做强化，强化后的更新声纹模型表
				
				
				
				//4、建模,建模完成后记录建模状态、信息，分别存储模型list，模型日志list
				
				
				//5、模型存储到集合中
				
			}
			
			//更新下载状态到数据库中
			
			
			
			//模型存储到(1)数据库(2)样本缓存 模型信息、模型日志存储到数据库中
			
			
			
		}
	}
	
	

}

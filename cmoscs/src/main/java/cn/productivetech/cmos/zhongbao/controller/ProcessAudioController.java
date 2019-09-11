package cn.productivetech.cmos.zhongbao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.productivetech.cmos.zhongbao.config.Const;
import cn.productivetech.cmos.zhongbao.core.FileListenerCore;
import cn.productivetech.cmos.zhongbao.core.IQueue;
import cn.productivetech.cmos.zhongbao.model.ProcessAudioItem;
import cn.productivetech.cmos.zhongbao.service.IProcessAudioService;
/**
 * 接收语音验证请求url控制器
 * 启动时，将所有的未处理音频信息加入到消息队列中
 * @author kenny_peng
 * @created 2019-04-16
 */
@Controller
public class ProcessAudioController extends BaseController{

	@Autowired
	private IProcessAudioService ipAudioService;
	@Autowired
	private IQueue<ProcessAudioItem> queue;
	@Autowired
	private FileListenerCore fileListenerCore;
	
	/**
	 * 语音接续处理模块儿中监听到新TXT开始执行此方法
	 * 1、监听语音接续文件夹有新推送txt，语音接续处理模块接收到新推送txt的path（路径+名字）
	 * 2、根据path解析txt文件，得到坐席id，音频存放路径等待处理音频信息，即ProcessAudioItem实体类信息
	 * 3、根据ProcessAudioItem实体类信息集合，批量保存信息到数据库，并将下载任务加入到队列中
	 * @param path 新推送txt的位置加文件名
	 */
	@RequestMapping("/processVerifyTxt")
	@ResponseBody
	public void processVerifyTxt(String path){
		
		//根据新推送txt文本路径获取ProcessAudioItem实体类集合
		List<ProcessAudioItem> listProcessAudioItems = ipAudioService.parseAudio(path);
		if (listProcessAudioItems != null) {
			
			//ProcessAudioItem实体类集合信息存储到数据库中
			ipAudioService.addAudioEntitys(listProcessAudioItems);
			
			//将ProcessAudioItem实体类集合中音频路径加入到队列中
			queue.put(listProcessAudioItems);
		}
	}
	
	/**
	 * 监听语音接续文件夹文件变化状态
	 * @return
	 */
	@RequestMapping("/listenerVerifyTxtFile")
	@ResponseBody
	public String listenerTxtFile(){
		fileListenerCore.fileListener(Const.TXT_FILE_PATH);
		return "listenerTxtFile";
	}
	
	/**
	 * 完成语音处理模块中从消息队列中获取下载任务，更新下载状态，音频文件解码、预处理、样本验证
	 * @param pAudioItem
	 */
	@RequestMapping("/getpop")
	@ResponseBody
	public void getQueue(){
		queue.pop();
	}
	
//	getmod   获取模型
//  getQueeu 获取消息队列	
	
}

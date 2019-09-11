package cn.productivetech.cmos.zhongbao.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.productivetech.cmos.zhongbao.core.IQueue;
import cn.productivetech.cmos.zhongbao.model.ProcessAudioItem;
import cn.productivetech.cmos.zhongbao.service.IProcessAudioService;

/**
 * 测试队列
 * @author   kenny_peng
 * @created  2019年4月26日
 */
@Controller
public class QueueController {

	@Autowired
	private IQueue<ProcessAudioItem> queue;
	@Autowired
	private IProcessAudioService pAudioService;
	
	private long count =1l;
	@SuppressWarnings("unchecked")
	@RequestMapping("/queue/put")
	@ResponseBody
	public String queuePut(){
		ProcessAudioItem data = new ProcessAudioItem();
		
		for (int i = 0; i < 1000; i++) {
			data.setId(count++);
			queue.put((List<ProcessAudioItem>) data);
		}
		return "queue put test";
	}
	
	@RequestMapping("/queue/pop")
	@ResponseBody
	public String queuePop(){

		queue.pop();
		return "queue pop test";
	}
	@RequestMapping("/queue/size")
	@ResponseBody
	public String queueSize(){

		int size = queue.size();
		return "queue pop test"+size;
	}
	
	@RequestMapping("test")
	@ResponseBody
	public String test(){
		pAudioService.queryAudioItemsByUnHandle();
		return "tets";
	}
	
}

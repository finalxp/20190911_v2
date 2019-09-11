package cn.xs.erp.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.xs.erp.dto.RspResultDto;
import cn.xs.erp.model.JobItem;
import cn.xs.erp.model.PageInfo;
import cn.xs.erp.service.IJobService;

@Controller
public class JobManagerController extends BaseController{

	@Autowired
	private IJobService jobService;
	@RequestMapping("/job_manager")
	public String home(){
		return "job_manager/index";
	}
	
	@RequestMapping("/job_manager/add")
	public String add(Model model){
		model.addAttribute("jobItem", new JobItem());
		return "job_manager/edit";
	}
	
	@RequestMapping("/job_manager/edit")
	public String edit(Model model,@RequestParam int id){
		model.addAttribute("jobItem", jobService.selectJobByID(id));
		return "job_manager/edit";
	}
	
	@RequestMapping(value="/job_manager/list",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public PageInfo<JobItem> list(@RequestParam(value = "page", required = false) Integer page, 
			@RequestParam(value = "rows", required = false) Integer rows,
			@RequestParam(value = "rankFrom", required = false) String rankFrom,
			@RequestParam(value = "rankTo", required = false) String rankTo,
			@RequestParam(value = "jobName", required = false) String jobName
			){
		return jobService.selectAllByPage(page,rows,rankFrom,rankTo,jobName);
	}
	
	@RequestMapping(value="/job_manager/insert",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public RspResultDto insert(JobItem jobItem){
		
		return jobService.insertJobItem(jobItem,getUserID());
	}
	@RequestMapping(value="/job_manager/update",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public RspResultDto update(JobItem jobItem){
		
		return jobService.updateJobItem(jobItem,getUserID());
	}
	@RequestMapping(value="/job_manager/delete",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public RspResultDto delete(@RequestParam int id){
		
		return jobService.deleteJobItemByID(id);
	}
	
	
	
}

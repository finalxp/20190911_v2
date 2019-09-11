package cn.xs.erp.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.xs.erp.dto.RspResultDto;
import cn.xs.erp.model.CheckinRecordItem;
import cn.xs.erp.model.PageInfo;
import cn.xs.erp.service.ICheckinRecordService;
import cn.xs.erp.service.IEmployeeService;

@Controller
public class CheckinRecordController extends BaseController {

	@Autowired
	private ICheckinRecordService crService;
	@Autowired
	private IEmployeeService empService;

	@RequestMapping("/checkin_record")
	public String checkinRecordHome(Model model) {
		return "/checkin_record/index";
	}

	@RequestMapping("/manager_checkin")
	public String managerCheckinRecord(Model model) {
		return "/checkin_record_manager/index";
	}

	@RequestMapping("/checkin_record/add")
	public String add(Model model) {
		return "/checkin_record/add";
	}

	@RequestMapping(value = "/checkin_record/list", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public PageInfo<CheckinRecordItem> toolbarList(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "rows", required = false) Integer rows,
			@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime,
			@RequestParam(value = "ifApply", required = false) String ifApply) 
	{
		return crService.selectToolbarList(page, rows, startTime, endTime,getUserID(),ifApply);
	}
	
	
	@RequestMapping(value = "/checkin_record_manager/list", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public PageInfo<CheckinRecordItem> managerListAll(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "rows", required = false) Integer rows,
			@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime) 
	{
		return crService.selectAllManagerCheckinByPage(page, rows, getUserID(), startTime, endTime);
	}

	// 同意补卡申请
	@RequestMapping(value = "/checkin_record_manager/approvalApplyCheckin", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public RspResultDto approvalApplyCheckin(@RequestParam("id") String id) {
		return crService.updateCheckinRecord(Integer.parseInt(id),getUserID());
	}

	// 添加考勤
	@RequestMapping(value = "/checkin_record/insert", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public RspResultDto addCheckinRecord(CheckinRecordItem checkinRecordItem) {
		return crService.addCheckinRecord(checkinRecordItem);
	}

	// form表单提交 Date类型数据绑定
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd");
		sFormat.setLenient(true);// 指定日期/时间解析是否宽松
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(sFormat, true));
	}
}

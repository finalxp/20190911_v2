package cn.xs.erp.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.xs.erp.dto.RspResultDto;
import cn.xs.erp.model.DoorItem;
import cn.xs.erp.model.PageInfo;
import cn.xs.erp.service.IDoorService;

@Controller
public class DoorController extends BaseController{
	@Autowired
	private IDoorService doorService;
	
	@RequestMapping("/door")
	public String home(Model model){
		return "/door/index";
	}
	@RequestMapping("/door/add")
	public String add(Model model){
		model.addAttribute("doorItem", new DoorItem());
		return "/door/edit";
	}
	@RequestMapping("/door/edit")
	public String edit(Model model,@RequestParam int id){
		DoorItem doorItem = doorService.getDoorItemById(id);
		model.addAttribute("doorItem", doorItem);
		return "/door/edit";
	}
	
	@RequestMapping(value="/door/insert",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public RspResultDto addDoor(DoorItem doorItem){
		return doorService.addDoor(doorItem,getUserID());
	}
	
	@RequestMapping(value="/door/update",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public RspResultDto editDoor(DoorItem doorItem){
		return doorService.editDoor(doorItem,getUserID());
		
	}
	
	@RequestMapping(value="/door/delete",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public RspResultDto deleteDoor(@RequestParam int id){
		return doorService.deleteDoor(id);
	}	

	// 根据条件筛选门信息
	@RequestMapping(value = "/door/list", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public PageInfo<DoorItem> list(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "rows", required = false) Integer rows,
			@RequestParam(value = "doorNo", required = false) String doorNo,
			@RequestParam(value = "doorName", required = false) String doorName) {
		return doorService.selectDoorByPage(page, rows, doorNo, doorName);
	}
	
	@RequestMapping(value = "/door/comboxList", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public List<DoorItem> comboxList(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "rows", required = false) Integer rows) {
		return doorService.selectAll();
	}
	
}

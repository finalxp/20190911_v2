package cn.xs.erp.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.xs.erp.dto.RspResultDto;
import cn.xs.erp.model.DepartmentItem;
import cn.xs.erp.model.PageInfo;
import cn.xs.erp.service.IDepartmentService;

@Controller
public class DepartmentController extends BaseController {

	@Autowired
	private IDepartmentService departmentService;

	@RequestMapping("/department")
	public String selectAllDepInfo(Model model) {
		return "department/index";
	}

	@RequestMapping("/department/add")
	public String add(Model model) {
		model.addAttribute("dItem", new DepartmentItem());
		return "department/edit";
	}

	@RequestMapping("/department/edit")
	public String edit(Model model, @RequestParam int id) {
		DepartmentItem dItem = departmentService.getDepartmentById(id);
		model.addAttribute("dItem", dItem);
		return "department/edit";
	}

	// 员工管理页面，加载部门下拉框
	@RequestMapping(value = "/department/comboboxList", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public List<DepartmentItem> selectDepartementcomboboxList() {
		return departmentService.selectAllDepInfo();
	}

	@RequestMapping(value = "/department/list", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public PageInfo<DepartmentItem> selectDepartementList(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "rows", required = false) Integer rows,
			@RequestParam(value = "depInfo", required = false) String depInfo,
			@RequestParam(value = "empInfo", required = false) String empInfo,
			@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime
			) {
		return departmentService.selectDepartmentByPage(page, rows,depInfo);
	}

	@RequestMapping(value="/department/insert",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public RspResultDto insert(DepartmentItem departmentItem) {
		return departmentService.addDepartment(departmentItem, getUserID());
	}




	@RequestMapping(value="/department/update",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public RspResultDto update(DepartmentItem departmentItem) throws ParseException {
		return departmentService.updateDepartment(departmentItem,getUserID());

	}


	@RequestMapping(value = "/department/delete", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public RspResultDto deleteDepByID(@RequestParam int id) {
		return departmentService.deleteDepartment(id);

	}
}

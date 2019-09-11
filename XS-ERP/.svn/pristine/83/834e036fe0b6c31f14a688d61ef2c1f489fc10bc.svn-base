package cn.xs.erp.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.multipart.MultipartFile;

import cn.xs.erp.dto.RspResultDto;
import cn.xs.erp.model.EmployeeItem;
import cn.xs.erp.model.PageInfo;
import cn.xs.erp.service.IDepartmentService;
import cn.xs.erp.service.IEmployeeService;
import cn.xs.erp.util.ImageUtil;

@Controller
public class EmployeeController extends BaseController {

	@Autowired
	private IEmployeeService employeeService;
	@Autowired
	private IDepartmentService departmentService;

	@RequestMapping("/employee")
	public String selectAllEmpInfo(Model model) {
		return "employee/index";
	}

	@RequestMapping("/employee/add")
	public String addEmployee(Model model) {
		EmployeeItem eItem = new EmployeeItem();
		eItem.setAvatarUrl("");
		model.addAttribute("eItem", eItem);
		model.addAttribute("depItem", null);
		return "employee/edit";
	}

	@RequestMapping("/employee/edit")
	public String editEmployee(Model model,
			@RequestParam(value = "id", required = false) Integer id) {
		if (id == null || id == 0) {
			id = getUserID();
		}
		EmployeeItem eItem = employeeService.getEmployeeById(id);
		model.addAttribute("eItem", eItem);
		model.addAttribute("listDepItem",
				toJson(departmentService.selectAllDepInfoByEmpID(id)));
		return "employee/edit";
	}

	@RequestMapping("/employee/editPwd")
	public String editPwd(
			@RequestParam(value = "id", required = false) Integer id,
			@RequestParam(value = "empNo", required = false) String empNo,
			@RequestParam(value = "empName", required = false) String empName,
			Model model) {
		EmployeeItem eItem = new EmployeeItem();
		if (id == null) {
			eItem = employeeService.getEmployeeById(getUserID());
			model.addAttribute("loginUserEdit", "true");
		} else {
			eItem = new EmployeeItem();
			eItem.setId(id);
			eItem.setEmpName(empName);
			eItem.setEmpNo(empNo);
			model.addAttribute("loginUserEdit", "false");
		}
		model.addAttribute("editPwd", eItem);
		return "employee/editPassword";
	}

	@RequestMapping(value = "/employee/insert", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public RspResultDto addEmployee(EmployeeItem employeeItem, int[] depID) {
		return employeeService.addEmployeeDepartment(depID, employeeItem,
				getUserID());
	}

	@RequestMapping(value = "/employee/update", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public RspResultDto editEmployee(EmployeeItem employeeItem, int[] depID)
			throws ParseException {
		return employeeService.updateEmployeeDepartment(employeeItem,
				getUserID(), depID);
	}

	@RequestMapping(value = "/employee/delete", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public RspResultDto deleteEmpByID(int id) {
		return employeeService.deleteByPrimaryKey(id);
	}

	@RequestMapping(value = "/employee/updatePwd", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public RspResultDto updatePwd(
			EmployeeItem employeeItem,
			@RequestParam(value = "empOldPassword", required = false) String empOldPassword) {
		return employeeService.updatePwd(employeeItem, getUserID(),
				empOldPassword);
	}

	@RequestMapping(value = "employee/uploadImg", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public RspResultDto uploadPicture(
			@RequestParam(value = "file", required = false) MultipartFile file,
			HttpServletRequest request, HttpServletResponse response) {
		String rootPath = request.getServletContext().getRealPath("/");
		String path = ImageUtil.saveImage(rootPath, "avatar", file);
		if (path != null) {
			return new RspResultDto("0", path);
		}
		return new RspResultDto("1", "上传图片失败！");
	}

	@RequestMapping(value = "/employee/list", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public PageInfo<EmployeeItem> getQueryList(
			@RequestParam(value = "depID", required = false) String depID,
			@RequestParam(value = "empNo", required = false) String empNo,
			@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime,
			@RequestParam(value = "page", required = false) int page,
			@RequestParam(value = "rows", required = false) int rows) {
		return employeeService.selectEmployeeByPage(page, rows, empNo, depID,
				startTime, endTime);
	}

	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd");
		// 指定日期/时间解析是否宽松
		sFormat.setLenient(true);
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
				sFormat, true));
	}
}

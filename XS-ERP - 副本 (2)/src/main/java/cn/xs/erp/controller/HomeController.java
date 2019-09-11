package cn.xs.erp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.xs.erp.service.IEmployeeService;

@Controller
@ComponentScan
public class HomeController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private IEmployeeService employeeService;

	@RequestMapping("/left/{menuid}/")
	public String left(@PathVariable int menuid, Model model) {
		logger.info("menuid = " + menuid);
		return "left";
	}

	@RequestMapping("/top")
	public String top(Model model) {

		return "top";
	}

	@RequestMapping("/content")
	public String content(Model model) {

		return "main";
	}
	
}

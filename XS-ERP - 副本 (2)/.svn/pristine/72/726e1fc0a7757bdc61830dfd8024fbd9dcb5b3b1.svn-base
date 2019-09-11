package cn.xs.erp.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseApiController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@ExceptionHandler
	public String exp(HttpServletRequest request, Exception ex) {

		logger.error("ERROR", ex);

		return "{\"retCode\":500,\"retMsg\":\"服务器异常\"}";
	}
}
package cn.xs.erp.config.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import cn.xs.erp.config.Const;
import cn.xs.erp.dto.RspResultDto;
import cn.xs.erp.service.IPermissionService;
import cn.xs.erp.util.JsonUtil;
import cn.xs.erp.util.PageUtil;
import cn.xs.erp.util.StringUtils;

@WebFilter(filterName = "PermissionFilter", urlPatterns = "*")
public class SecurityFilter implements Filter {

	@Autowired
	private IPermissionService permissionService;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;  
		
		String url = httpRequest.getRequestURI();
		if (inWhiteUrlList(url)) {
			chain.doFilter(request, response);
			return;
		}
		String employeeId = (String)httpRequest.getSession().getAttribute(Const.LOGIN_SESSION_KEY);
		
		if (StringUtils.isEmpty(employeeId)) {
			if(PageUtil.isAjaxRequest(httpRequest)){
				httpResponse.setHeader("noAuthentication", "true");  
				httpResponse.setContentType("application/json;charset=UTF-8");

	            PrintWriter writer = httpResponse.getWriter();  
	            writer.write(JsonUtil.toJson(new RspResultDto("1002","未登录")));  
	            writer.close();  
	            httpResponse.flushBuffer();  
				return;
			}else{
				response.getWriter().write("<script type=\"text/javascript\">window.location.href=\"/login\"</script>");
			}
		}else{
			
			boolean result = permissionService.hasPermission(url);
			if(!result){
				response.getWriter().write("<script type=\"text/javascript\">alert('没有权限,请联系系统管理员!');</script>");
			}else{
				chain.doFilter(request, response);
				return;
			}
		}
	}

	private boolean inWhiteUrlList(String url) {
		if(url.startsWith("/login") || url.startsWith("/index"))
			return true;
		
		if (url.endsWith(".js") || url.endsWith(".css") || url.endsWith(".jpg") || url.endsWith(".gif") || url.endsWith(".png") || url.endsWith(".html") || url.endsWith(".eot")
				|| url.endsWith(".svg") || url.endsWith(".ttf") || url.endsWith(".woff") || url.endsWith(".ico") || url.endsWith(".woff2")) {
			return true;
		}
			return false;
		
	}
}

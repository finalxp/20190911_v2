package cn.productivetech.shtelcom.enrol.api.config;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.productivetech.shtelcom.enrol.api.response.RspApi;
import cn.productivetech.shtelcom.enrol.model.UserBean;
import cn.productivetech.shtelcom.enrol.service.IAppService;
import cn.productivetech.shtelcom.enrol.utils.AesEnc;
import cn.productivetech.shtelcom.enrol.utils.JSON;
import cn.productivetech.shtelcom.enrol.utils.Md5Enc;
import cn.productivetech.shtelcom.enrol.utils.Utils;

public class AuthenticationInterceptor implements HandlerInterceptor {

	@Autowired
	private IAppService appService;

	@Override
	public boolean preHandle(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Object object) throws IOException {

		// 判断对象是否是映射到一个方法，如果不是则直接通过
		if (!(object instanceof HandlerMethod)) {
			return true;
		}

		String token = httpServletRequest.getHeader("Token");

		boolean verify = false;
		UserBean ub = null;
		if (!Utils.isNullOrEmpty(token)) {
			try {
				String text = AesEnc.getIns().decrypt(token);
				String[] items = text.split(":");

				ub = appService.findByName(items[0]);
				if (ub != null) {
					String pwd = Md5Enc.enc(ub.getUserLoginName());
					long expireTime = Long.parseLong(items[2]);
					verify = System.currentTimeMillis() < expireTime
							&& ub.getAdministratorPrivileges() != 0
							&& ub.getUserLoginName().equals(items[0]) && pwd.equals(items[1]);
				}
			} catch (Exception e) {

				verify = false;
			}
		}

		if (!verify) {

			httpServletResponse.setContentType("application/json;charset=UTF-8");
			httpServletResponse.getWriter().append(JSON.toJSON(RspApi.unAuthen()));
			return false;
		} else {

			httpServletRequest.setAttribute("loginUser", ub);
			return true;
		}
	}

	@Override
	public void postHandle(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Object object, ModelAndView modelAndView)
			throws Exception {

	}

	public void afterCompletion(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
	}

}
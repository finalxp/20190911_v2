package cn.productivetech.shtelcom.enrol.api;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.productivetech.shtelcom.enrol.api.request.ReqDep;
import cn.productivetech.shtelcom.enrol.api.request.ReqEnrol;
import cn.productivetech.shtelcom.enrol.api.request.ReqLogin;
import cn.productivetech.shtelcom.enrol.api.request.ReqPrivilege;
import cn.productivetech.shtelcom.enrol.api.request.ReqSearch;
import cn.productivetech.shtelcom.enrol.api.response.RspApi;
import cn.productivetech.shtelcom.enrol.api.response.TextDto;
import cn.productivetech.shtelcom.enrol.api.response.UserDto;
import cn.productivetech.shtelcom.enrol.model.DepAndUserBean;
import cn.productivetech.shtelcom.enrol.model.TextBean;
import cn.productivetech.shtelcom.enrol.model.UserBean;
import cn.productivetech.shtelcom.enrol.service.IAppService;
import cn.productivetech.shtelcom.enrol.service.IUAService;
import cn.productivetech.shtelcom.enrol.utils.TokenUtils;
import cn.productivetech.shtelcom.enrol.utils.Utils;

@RestController
public class ApiController extends BaseApiController {

	@Autowired
	private IAppService appService;

	@Autowired
	private IUAService uaService;

	@RequestMapping(value = "/api/login", method = RequestMethod.POST)
	@ResponseBody
	public RspApi logon(@RequestBody ReqLogin body) throws Exception {

		if (Utils.isNullOrEmpty(body.getLoginName())) {
			return RspApi.error("报文不合法");
		}

		UserBean ub = appService.findByName(body.getLoginName());

		if (ub == null) {
			return RspApi.error("员工不存在");
		}

		if (ub.getAdministratorPrivileges() == 0) {
			return RspApi.error("没有权限，请联系管理员");

		}

		String token = TokenUtils.createToken(ub);

		List<DepAndUserBean> deps = null;

		if (ub.isSystemAdmin()) {
			deps = appService.selectRootDeps();
			DepAndUserBean unitBean = new DepAndUserBean();
			unitBean.setId("3");
			unitBean.setName("管理员列表");
			unitBean.setEnrolFlag("");
			unitBean.setPrivilege(-1);
			unitBean.setType(3);
			deps.add(unitBean);

		} else {
			deps = appService.selectUnitDeps(ub.getUserDepId());
		}

		return RspApi.success(new UserDto(deps, token, ub.getAdministratorPrivileges()));
	}

	@RequestMapping(value = "/api/enrolSpeaker", method = RequestMethod.POST)
	@ResponseBody
	public RspApi enrolSpeaker(HttpServletRequest httpServletRequest, @RequestBody ReqEnrol body)
			throws Exception {

		if (Utils.isNullOrEmpty(body.getBase64()) || Utils.isNullOrEmpty(body.getEmpId())
				|| Utils.isNullOrEmpty(body.getPhraseId())) {
			return RspApi.error("报文不合法");
		}

		UserBean ub = appService.findByName(body.getEmpId());

		if (ub == null) {
			return RspApi.error("员工不存在");
		}

		byte[] srcData = Utils.base64ToByteArray(body.getBase64());

		uaService.enrolSpeaker(body.getIdentifier(), srcData, body.getTimes());

		if (body.isLast()) {
			appService.updateTextNumber(ub.getUserId());
		}

		return RspApi.success();
	}

	@RequestMapping(value = "/api/textList", method = RequestMethod.POST)
	@ResponseBody
	public RspApi textList() {

		List<TextBean> textList = appService.getTextBeanList();
		List<TextDto> dtoList = new ArrayList<TextDto>(textList.size());
		for (TextBean bean : textList) {
			dtoList.add(new TextDto(bean));
		}

		return RspApi.success(dtoList);
	}

	@RequestMapping(value = "/api/unitUsers", method = RequestMethod.POST)
	@ResponseBody
	public RspApi unitUsers(HttpServletRequest httpServletRequest) {

		UserBean user = (UserBean) httpServletRequest.getAttribute("loginUser");

		if (user == null) {
			return RspApi.unAuthen();
		}
		if (!user.isSystemAdmin()) {
			return RspApi.error("没有权限");

		}

		List<DepAndUserBean> textList = appService.selectUnitAdminUsers();

		return RspApi.success(textList);
	}

	@RequestMapping(value = "/api/depAndUserList", method = RequestMethod.POST)
	@ResponseBody
	public RspApi depAndUserList(@RequestBody ReqDep body) {

		if (Utils.isNullOrEmpty(body.getDepId())) {
			return RspApi.error("报文不合法");
		}

		List<DepAndUserBean> dataList = appService.getDepAndUserList(body.getDepId());

		return RspApi.success(dataList);
	}

	@RequestMapping(value = "/api/userSearch", method = RequestMethod.POST)
	@ResponseBody
	public RspApi userSearch(HttpServletRequest httpServletRequest, @RequestBody ReqSearch body) {

		if (Utils.isNullOrEmpty(body.getKeyWord())) {
			return RspApi.error("报文不合法");
		}

		UserBean user = (UserBean) httpServletRequest.getAttribute("loginUser");

		if (user == null) {
			return RspApi.unAuthen();
		}

		if (user.isSystemAdmin() || user.isUnitAdmin()) {
			List<DepAndUserBean> dataList = appService.searchUser(body.getKeyWord(),
					user.getAdministratorPrivileges(), user.getUnitDep());

			return RspApi.success(dataList);
		} else {
			return RspApi.error("没有权限");
		}
	}

	@RequestMapping(value = "/api/updatePrivilege", method = RequestMethod.POST)
	@ResponseBody
	public RspApi updatePrivilege(HttpServletRequest httpServletRequest,
			@RequestBody ReqPrivilege body) {

		if (Utils.isNullOrEmpty(body.getEmpId()) || body.getLevel() == 0) {
			return RspApi.error("报文不合法");
		}

		UserBean user = (UserBean) httpServletRequest.getAttribute("loginUser");

		if (user == null) {
			return RspApi.unAuthen();
		}

		if (user.getAdministratorPrivileges() == 0) {

			return RspApi.error("没有权限");
		}

		UserBean updateUser = appService.findByName(body.getEmpId());

		if (updateUser == null) {

			return RspApi.error("员工不存在");
		}

		int selfPrivilege = user.getAdministratorPrivileges();
		int updatePrivilege = updateUser.getAdministratorPrivileges();

		if (updatePrivilege > selfPrivilege) {
			return RspApi.error("不能跨级修改权限");
		}

		if (body.getLevel() > selfPrivilege) {
			return RspApi.error("不能跨级修改权限");
		}

		appService.updatePrivilege(updateUser.getUserId(), body.getLevel());
		return RspApi.success();
	}
}

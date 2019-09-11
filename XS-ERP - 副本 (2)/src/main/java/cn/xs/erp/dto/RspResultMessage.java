package cn.xs.erp.dto;

public enum RspResultMessage {

	SUCCESS("0","操作成功！"),
	FAILED("9999","操作失败"),
	
	TryToVistWithOutLogin("1002","未登录!"),
	
	EmpNoAlreadyExist("000101","该员工编号已经存在，请使用其他员工编号！"),
	
	AddEmployeeSuccess("0","新增员工成功！"),
	AddEmployeeFailed("000201","添加员工失败！"),
	AddDepForEmployeeFailed("000202","为员工添加部门失败!"),
	
	EditEmployeeSuccess("0","修改员工信息成功！"),
	EditEmployeeFailed("000301","修改员工信息失败！"),
	
	EditDepForEmployeeFailed("000302","修改员工部门时失败"),
	
	EditEmployeePwdSuccess("0","修改密码成功！！"),
	EditEmployeePwdFailed("000301","修改密码失败！"),
	
	
	ErrorUploadingEmployeeImage("000401","系统异常，图片上传失败！"),
	
	DeleteEmployeeSuccess("0","删除员工成功！"),
	DeleteEmployeeFailed("000501","删除员工失败！"),
	
	
	AddDoorSuccess("0","添加门成功！"),
	AddDoorFailed("0000601","添加门失败！"), 
	
	EditDoorSuccess("0","修改门信息成功！"), 
	EditDoorFailed("000602","修改门信息失败！"), 

	DeleteDoorSuccess("0","删除门成功！"),
	DeleteDoorFailed("000603","删除门失败！"),
	
	
	EditCheckinRecordSuccess("0","审核考情成功！"),
	EditCheckinRecordFailed("0","审核考情失败！"),
	
	AddCheckinRecordSuccess("0","添加考勤成功！"),
	AddCheckinRecordFailed("000701","添加考勤失败！"), 
	
	EditDoorForEmployeeSuccess("0","修改门禁权限成功！"),
	EditDoorForEmployeeFailed("000801","修改门禁权限失败！"), 
	
	
	AddJobItemSuccess("0","添加职位成功！"),
	AddJobItemFailed("000901","添加职位失败！"), 
	
	
	EditJobItemSuccess("0","修改职位信息成功!"),
	EditJobItemFailed("000902","修改职位信息失败！"), 
	
	DeleteJobItemSuccess("0","删除职位成功！"),
	DeleteJobItemFailed("000903","删除职位失败！"), 
	
	
	EditMenuForEmployeeSuccess("0","修改员工Menu权限成功！"),
	EditMenuForEmployeeFailed("001001","修改员工Menu权限失败！"), 
	
	
	AddDepartmentSuccess("0","添加部门成功！"),
	AddDepartmentFailed("001101","添加部门失败！"),
	;
	
	private RspResultMessage(String code,String msg){
		this.code=code;
		this.msg=msg;
	}
	
	private String code;
	private String msg;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}

package cn.xs.erp.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;

import cn.xs.erp.dao.IEmployeeDao;
import cn.xs.erp.dao.IEmployeeDepartmentDao;
import cn.xs.erp.dto.RspResultDto;
import cn.xs.erp.dto.RspResultMessage;
import cn.xs.erp.model.EmployeeDepartmentItem;
import cn.xs.erp.model.EmployeeItem;
import cn.xs.erp.model.PageInfo;
import cn.xs.erp.service.IEmployeeService;
import cn.xs.erp.util.MD5Util;
import cn.xs.erp.util.RandomUtil;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

	@Autowired
	private IEmployeeDao empDao;
	@Autowired
	private IEmployeeDepartmentDao employeeDepartmentDao;

	@Override
	public EmployeeItem getEmployeeById(int Id) {
		return empDao.selectByPrimaryKey(Id);
	}

	@Override
	public PageInfo<EmployeeItem> selectEmployeeByPage(int page, int pageSize) {
		PageHelper.startPage(page, pageSize);
		List<EmployeeItem> docs = empDao.selectAllEmpInfo();
		PageInfo<EmployeeItem> pageInfo = new PageInfo<>(docs);
		return pageInfo;
	}
	
	@Override
	public PageInfo<EmployeeItem> selectEmployeeByPage(int page, int pagesize,
			String empNo, String depID, String startTime, String endTime) {
		PageHelper.startPage(page,pagesize);
		Map<String, String> map = new HashMap<String, String>();
		map.put("empNo", empNo);
		map.put("depID", depID);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		List<EmployeeItem> list = empDao.selectToolbarList(map);
		PageInfo<EmployeeItem> pageInfo = new PageInfo<EmployeeItem>(list);
		return pageInfo;
	}
	
	@Override
	public int addEmployee(EmployeeItem employeeItem) {
		return empDao.insert(employeeItem);
	}

	@Override
	public EmployeeItem getEmployeeByNo(String empNo) {
		return empDao.getEmployeeByNo(empNo);
	}

	@Override
	public List<EmployeeItem> selectAllEmpInfo() {
		return empDao.selectAllEmpInfo();
	}

	@Override
	public int updateEmployeeById(Map<String, Object> map) {
		return empDao.updateEmployeeById(map);
	}

	@Override
	public RspResultDto deleteByPrimaryKey(int id) {
		if(empDao.deleteByPrimaryKey(id)>0){
			return new RspResultDto(RspResultMessage.DeleteEmployeeSuccess);
		}else {
			return new RspResultDto(RspResultMessage.DeleteEmployeeFailed);
		}
	}

	@Override
	public RspResultDto updatePrimaryInfo(EmployeeItem employeeItem,int userID,int[] depID) {
		employeeItem.setUpdateBy(userID);
		employeeItem.setUpdateTime(new Date());
		int editPrimaryInfo = empDao.updateByPrimaryKeySelective(employeeItem);
		if (editPrimaryInfo<1) {
			return new RspResultDto(RspResultMessage.EditEmployeeFailed);
		}
		return new RspResultDto(RspResultMessage.EditEmployeeSuccess);
	}
	@Override
	public RspResultDto updatePwd(EmployeeItem employeeItem,int userID,String empOldPassword) {
		if (empOldPassword!=null && empOldPassword!="") {
			EmployeeItem eItem = empDao.selectByPrimaryKey(employeeItem.getId());
			String validPwd = MD5Util.getMD5(empOldPassword + eItem.getSalt());
			if (!validPwd.equals(eItem.getEmpPassword())) {
				return new RspResultDto(RspResultMessage.ErrorInputEmployeePassword);
			}
		}
		String salt = RandomUtil.getRandomNumber(8);
		employeeItem.setSalt(salt);
		String pwdString = MD5Util.getMD5(employeeItem.getEmpPassword() + salt);
		employeeItem.setEmpPassword(pwdString);
		employeeItem.setUpdateBy(userID);
		employeeItem.setUpdateTime(new Date());
		if (empDao.updateByPrimaryKeySelective(employeeItem)<1) {
			return new RspResultDto(RspResultMessage.EditEmployeePwdFailed);
		}
		return new RspResultDto(RspResultMessage.EditEmployeePwdSuccess);
	}

	@Override
	public RspResultDto uploadPicture(MultipartFile file) {
		String fileName = file.getOriginalFilename();// 获取文件名加后缀
		if (fileName != null && fileName != "") {
			String path = System.getProperty("user.dir"); // 文件存储位置
			String fileF = fileName.substring(fileName.lastIndexOf("."), fileName.length());// 文件后缀
			fileName = new Date().getTime() + "_" + new Random().nextInt(1000) + fileF;// 新的文件名
			// 先判断文件是否存在
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String fileAdd = sdf.format(new Date());
			// 获取文件夹路径
			File file1 = new File(path + "/" + fileAdd);
			// 如果文件夹不存在则创建
			if (!file1.exists() && !file1.isDirectory()) {
				file1.mkdir();
			}
			// 将图片存入文件夹
			File targetFile = new File(file1, fileName);
			try {
				// 将上传的文件写到服务器上指定的文件。
				file.transferTo(targetFile);
				//保存图片存储地址
				String url = "/" + fileAdd + "/" + fileName;
				url = url.replaceAll("\\\\", "/");
				return new RspResultDto("0", url);
			} catch (Exception e) {
				e.printStackTrace();
				return new RspResultDto(RspResultMessage.ErrorUploadingEmployeeImage);
			}
		}else {
			return new RspResultDto(RspResultMessage.ErrorUploadingEmployeeImage);
		}
	}
	
	@Override
	public RspResultDto addEmployeeDepartment(int[] depID, EmployeeItem eItem, int userID) {
		if (empDao.getEmployeeByNo(eItem.getEmpNo()) != null) {
			return new RspResultDto(RspResultMessage.EmpNoAlreadyExist);
		} else {
			eItem.setCreateBy(userID);
			eItem.setIsEnable((short) 1);
			eItem.setCreateTime(new Date());
			String salt = RandomUtil.getRandomNumber(8);
			eItem.setSalt(salt);
			String pwdString = MD5Util.getMD5("123456" + salt);
			eItem.setEmpPassword(pwdString);
			if (empDao.insert(eItem) > 0) {
				if (depID !=null && depID.length > 0) {
					int addEmpDep = 0;
					for (int j = 0; j < depID.length; j++) {
						EmployeeDepartmentItem edItem = new EmployeeDepartmentItem();
						edItem.setDepId(depID[j]);
						edItem.setEmpId(eItem.getId());
						edItem.setCreateBy(userID);
						edItem.setCreateTime(new Date());
						try {
							addEmpDep = employeeDepartmentDao.insertSelective(edItem);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					if (addEmpDep > 0) {
						return new RspResultDto(
								RspResultMessage.AddEmployeeSuccess);
					} else {
						return new RspResultDto(
								RspResultMessage.AddDepForEmployeeFailed);
					}
				} else {
					return new RspResultDto(RspResultMessage.AddEmployeeSuccess);
				}
			} else {
				return new RspResultDto(RspResultMessage.AddEmployeeFailed);
			}
		}
	}
	
	@Override
	public RspResultDto updateEmployeeDepartment(EmployeeItem employeeItem, int userID, int[] depID) {
		if (depID==null) {
			depID=new int[0];
		}
		employeeItem.setUpdateBy(userID);
		employeeItem.setUpdateTime(new Date());
		int editPrimaryInfo = empDao.updateByPrimaryKeySelective(employeeItem);
		if (editPrimaryInfo>0) {
			List<EmployeeDepartmentItem> list = employeeDepartmentDao.selectByEmpID(employeeItem.getId());
			if (depID.length > 0) {
				int addEmpDep = 0;
				List<String> depList = new ArrayList<String>();
				for (int i = 0; i < depID.length; i++) {
					depList.add(depID[i]+"");
				}
				//depID中不包含以前就有的部门id，删除以前存储的员工和部门对应记录
				for (EmployeeDepartmentItem eDepartmentItem : list) {
					if (!depList.contains(eDepartmentItem.getDepId()+"")) {
						if (deleteEmpDep(eDepartmentItem)<1) {
							return new RspResultDto(RspResultMessage.EditDepForEmployeeFailed);
						}
					}
				}
				//depID中包含以前就有的部门ID，则不再添加
				for (int j = 0; j < depID.length; j++) {
					for (EmployeeDepartmentItem employeeDepartmentItem : list) {
						if (employeeDepartmentItem.getDepId()==depID[j]) {
							depList.remove(depID[j]+"");
						}
					}
				}
				for (String integer : depList) {
					EmployeeDepartmentItem edItem = new EmployeeDepartmentItem();
					edItem.setDepId(Integer.parseInt(integer));
					edItem.setEmpId(employeeItem.getId());
					edItem.setCreateBy(userID);
					edItem.setCreateTime(new Date());
					addEmpDep = employeeDepartmentDao.insertSelective(edItem);
					if (addEmpDep<1) {
						return new RspResultDto(RspResultMessage.EditDepForEmployeeFailed);
					}
				}
				return new RspResultDto(RspResultMessage.EditEmployeeSuccess);
			} else {
				if (list.size() > 0) {
					for (EmployeeDepartmentItem employeeDepartmentItem : list) {
						if (deleteEmpDep(employeeDepartmentItem)<1) {
							return new RspResultDto(RspResultMessage.EditDepForEmployeeFailed);
						}
					}
					return new RspResultDto(RspResultMessage.EditEmployeeSuccess);
				} else {
					return new RspResultDto(RspResultMessage.EditEmployeeSuccess);
				}
			}
		}else {
			return new RspResultDto(RspResultMessage.EditEmployeeFailed);
		}
	}
	
	private int deleteEmpDep(EmployeeDepartmentItem eDepartmentItem) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("empID", eDepartmentItem.getEmpId());
		map.put("depID", eDepartmentItem.getDepId());
		return employeeDepartmentDao.deleteByEmpIdAndDepId(map);
	}
	
	
	
}

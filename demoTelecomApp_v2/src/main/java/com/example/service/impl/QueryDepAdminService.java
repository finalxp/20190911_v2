package com.example.service.impl;



import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mapper.QueryDepAdminMapper;
import com.example.model.Department;
import com.example.model.EmpDepList;
import com.example.model.User;
import com.example.service.IQueryDepAdminService;

@Service
public class QueryDepAdminService implements IQueryDepAdminService{

	@Autowired
	QueryDepAdminMapper queryDepAdminMapper;
	
	@SuppressWarnings("null")
	@Override
	public List<EmpDepList> queryDepAdmin() {
		
		//返回EmpDepList实体的集合list
		ArrayList<EmpDepList> empDepList = new ArrayList<EmpDepList>();
		
		//返回的EmpDepList实体
		//EmpDepList empDep = new EmpDepList();
		
		//查询到的所有为"2"的管理员集合list
		List<User> admin2list;
		
		//do...while中循环用的当前部门id
		String depNoSub;
		
		//do...while中循环用的上级部门id
		String depNoSup;
		
		//1.查询所有为"2"的管理员的根部门，查询该用户的上级部门id
		admin2list = queryDepAdminMapper.queryDepAdminList();
		
		//测试：打印list集合是否正确
		for (User string : admin2list) {
			System.out.println(string.getUser_dep_id());
		}
		
		//2.循环当上级部门id不为"0"或"1"时，returnNull；当上级部门id为"0"或"1"时，添加empdeplist实体类到list
		
		//2.1 do...while
		System.out.println("-------循环开始-------");
		for (User string : admin2list) {
			String getUser_dep_id = string.getUser_dep_id();
			System.out.println("当前的部门ID："+getUser_dep_id);
			String name = string.getName();
			System.out.println("当前的管理员姓名："+name);
			
			depNoSub = getUser_dep_id;
			
			Department supDepMapper = queryDepAdminMapper.getSupDepMapper(depNoSub);			
			depNoSup = supDepMapper.getDepartment_sup_id();
			System.out.println("当前管理员部门的上级部门ID："+depNoSup);
			
			//添加最上级部门为外部组织时，返回错误信息
			
			
			while (!depNoSup.equals("0") && !depNoSup.equals("1")) {
				
				
				
				System.out.println("-------开始执行while循环-------");
				depNoSub = depNoSup;//永远为下级部门，用于返回实体类
				System.out.println("-------------------------查看是否转换上级部门为当前部门"+depNoSub);
				depNoSup = null;
				
				System.out.println("-------");
				System.out.println("当前部门ID:"+depNoSub);
				System.out.println("上级部门ID:"+depNoSup);
				System.out.println("-------");
				
				//depNoSup = queryDepAdminMapper.getSupDepMapper(depNoSup);		
				Department supDepMapper2 = queryDepAdminMapper.getSupDepMapper(depNoSub);
				depNoSup = supDepMapper2.getDepartment_sup_id();
				
				System.out.println("-------");
				System.out.println("当前部门ID:"+depNoSub);
				System.out.println("上级部门ID:"+depNoSup);
				System.out.println("-------");
				System.out.println("------本次while循环结束-------");
				
				//如果上层部门为1则，break
				if (depNoSup.equals("1")) {
					
					continue;
				}
				
			}
			
			if (depNoSup.equals("1")) {
				
				continue;
			}
			
			Department departmentNameMapper = queryDepAdminMapper.getDepartmentNameMapper(depNoSub);
			String department_name = departmentNameMapper.getDepartment_name();
			System.out.println("返回管理员所在上层单位的名称："+department_name);
			System.out.println("返回管理员的姓名为："+name);
			
			EmpDepList empDep = new EmpDepList();
			empDep.setEmployee(name);
			empDep.setDepartment(department_name);
			empDepList.add(empDep);
		}

		return empDepList;
	}

}

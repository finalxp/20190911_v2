package com.example.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.mapper.QueryDepartmentTreeMapper;
import com.example.model.Department;
import com.example.model.User;

public class GetRootDepId {
	@Autowired
	QueryDepartmentTreeMapper queryDepartmentTreeMapper;
	
	public Department queryDepartmentTree2(String userId) {

		//String fistDepId = null;// 查询用的Id
		String fistDepId = new String();// 查询用的Id

		//String updateInputDep = null;// 每次查出来的部门ID
		String updateInputDep = new String();// 每次查出来的部门ID
		
		//List<User> queryDepartmentIdByUserId = null;//根据名字得到的部门id
		List<User> queryDepartmentIdByUserId = new ArrayList<User>();//根据名字得到的部门id
				
		//List<Department> supDepMapper = null;//查询得到的部门id
		List<Department> supDepMapper = new ArrayList<Department>();//查询得到的部门id

		//Department rootDep = null;// 返回实体
		Department rootDep = new Department();// 返回实体

		System.out.println("请求开始--------------");
		System.out.println("用户ID："+userId);
		//得到部门ID
		queryDepartmentIdByUserId = queryDepartmentTreeMapper.queryDepartmentIdByUserId(userId);
		for (User user : queryDepartmentIdByUserId) {
			// 得到当前用户的当前部门id
			fistDepId = user.getUser_dep_id();
			System.out.println("当前用户的当前部门id--fistDepId:"+fistDepId);
		}
		//查询当前部门id的上级部门id
		supDepMapper = queryDepartmentTreeMapper.getSupDepMapper(fistDepId);
		for (Department department : supDepMapper) {
			// 得到当前部门的上级部门
			updateInputDep = department.getDepartment_sup_id();
			System.out.println("上级部门id是updateInputDep--：" + updateInputDep);
		}
		System.out.println("-------上级部门id是updateInputDep--：" + updateInputDep);
		
		//判断上级部门id是否为0或者1.
		if (updateInputDep.equals("0") || updateInputDep.equals("1")) {
			System.out.println("第一次查询上级部门是0的fistID："+fistDepId);
			List<Department> subDepMapper = queryDepartmentTreeMapper.getSubDepMapper(fistDepId);

			for (Department object : subDepMapper) {
				rootDep = object;
				System.out.println("查看是否会执行第一次查询为0或1后返回的实体"+rootDep);
			}
			//return rootDep;
			
		}else {
			do {
				// 更新查询部门id为上次查询的上级部门id
				fistDepId = updateInputDep;
				System.out.println("第二次查询用的部门id：" + fistDepId);

				//得到当前部门的上级部门
				List<Department> supDepMapper2 = queryDepartmentTreeMapper.getSupDepMapper(fistDepId);
				for (Department department : supDepMapper2) {
					// 得到当前部门的上级部门id
					updateInputDep = department.getDepartment_sup_id();
					System.out.println("第二次查询查到的上级部门id：" + updateInputDep);
				}

				System.out.println("updateInputDep---" + updateInputDep);
				// 查看当前部门的上级部门是否为0或1
			} while (updateInputDep == "0" || updateInputDep == "1");

			/*
			 * while (updateInputDep == "0" || updateInputDep =="1") {
			 * List<Department> supDepMapper =
			 * queryDepartmentTreeMapper.getSupDepMapper(updateInputDep);
			 * 
			 * for (Department department : supDepMapper) { updateInputDep =
			 * department.getDepartment_sup_id(); }
			 * 
			 * System.out.println("updateInputDep---"+updateInputDep);
			 * 
			 * }
			 */

			System.out.println("当后面几次查询到上级部门是1或0的fistID->" + fistDepId);
			System.out.println("查看执行最后步骤时的updateId是不是0或1:" + updateInputDep);

			List<Department> subDepMapper = queryDepartmentTreeMapper.getSubDepMapper(fistDepId);

			for (Department object : subDepMapper) {
				rootDep = object;
			}

			return rootDep;
		}
			
		
		return rootDep;

	}
}

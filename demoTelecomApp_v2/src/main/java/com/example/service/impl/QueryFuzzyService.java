package com.example.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mapper.QueryFuzzyMapper;
import com.example.model.Department;
import com.example.model.User;
import com.example.service.IQueryDepartmentTree;
import com.example.service.IQueryFuzzyService;
import com.example.utils.GetRootDepId;

/**
 * 模糊查询实现类
 * @author leo
 * 
 * @date 2019年8月26日 下午1:28:20
 */
@Service
public class QueryFuzzyService implements IQueryFuzzyService{

	@Autowired
	QueryFuzzyMapper queryFuzzyMapper;
	
	@Autowired
	private IQueryDepartmentTree queryDepartmentTree;
	
	/*List<Department> queryFuzzyByUnitAdmin_getSupDepId = new ArrayList<Department>();
	List<Department> queryFuzzyByUnitAdmin_getSupDepId2 = new ArrayList<Department>();
	List<Department> queryFuzzyByUnitAdmin_getSupDepId3 = new ArrayList<Department>();
	List<Department> queryFuzzyByUnitAdmin_getSupDepId4 = new ArrayList<Department>();
	List<Department> queryFuzzyByUnitAdmin_getSupDepId5 = new ArrayList<Department>();*/
/*	private List<Department> queryFuzzyByUnitAdmin_getSupDepId2;
	private List<Department> queryFuzzyByUnitAdmin_getSupDepId3;
	private List<Department> queryFuzzyByUnitAdmin_getSupDepId4;
	private List<Department> queryFuzzyByUnitAdmin_getSupDepId5;*/
	//List<String> allDepId = new ArrayList<String>(); 
	
	
	@Override
	public List<User> queryFuzzyService(String namePart) {
		String addName = "%"+namePart+"%";
		System.out.println(addName);
		List<User> queryFuzzy = queryFuzzyMapper.queryFuzzy(addName);
		
		return queryFuzzy;
	}

	@Override
	public List<User> queryFuzzyService(String namePart, String rootDep) {
		// 查询所有用户信息
		System.out.println("部门管理员接口调用开始：");
		String addName = "%" + namePart + "%";		
		List<User> queryFuzzy = queryFuzzyMapper.queryFuzzy(addName);
		List<User> returnUserList = new ArrayList<User>();
		for (User user : queryFuzzy) {
			String user_login_name = user.getUser_login_name();
			System.out.println("userId---"+user_login_name);
			
			Department queryDepartmentTree2 = queryDepartmentTree.queryDepartmentTree2(user_login_name);
			//新增判断当dep实体为null
			if (queryDepartmentTree2==null) {
				continue;
			}
			
			String department_id = queryDepartmentTree2.getDepartment_id();
			
			if (department_id.equals(rootDep)) {
				returnUserList.add(user);
			}
			
			
			/*GetRootDepId getRootDepId = new GetRootDepId();
			Department queryDepartmentTree2 = getRootDepId.queryDepartmentTree2(user_login_name);
			String department_id = queryDepartmentTree2.getDepartment_id();
			if (department_id == rootDep) {
				returnUserList.add(user);
			}*/
		}
		
		
		
		/*//把跟部门也加入总集合
		allDepId.add(rootDep);
		// 根据rootDep查询第一层子部门id
		queryFuzzyByUnitAdmin_getSupDepId = queryFuzzyMapper.queryFuzzyByUnitAdmin_getSupDepId(rootDep);
		// 如果集合为空，则
		if (queryFuzzyByUnitAdmin_getSupDepId.size() == 0) {
			return null;
		}
		
		for (Department user : queryFuzzyByUnitAdmin_getSupDepId) {
			String department_id = user.getDepartment_id();
			System.out.println("depId--------"+department_id);
		}
		
		// 添加总的depId集合
		for (Department user : queryFuzzyByUnitAdmin_getSupDepId) {
			String department_id = user.getDepartment_id();
			System.out.println("2222--------"+department_id);
			allDepId.add(department_id);
		}
		

		// 遍历第二层的子部门集合
		for (Department depIdSub : queryFuzzyByUnitAdmin_getSupDepId) {
			//得到部门id
			String department_id = depIdSub.getDepartment_id();
			System.out.println("33333333----------"+department_id);
			//查询该子部门下的子部门
			queryFuzzyByUnitAdmin_getSupDepId2 = queryFuzzyMapper.queryFuzzyByUnitAdmin_getSupDepId(department_id);
			//判断该部门是否包含子部门
			System.out.println("大小---------"+queryFuzzyByUnitAdmin_getSupDepId2.size());
			if (queryFuzzyByUnitAdmin_getSupDepId2.size() == 0) {
				break;
			}
			// 添加总的depId集合
			for (Department user : queryFuzzyByUnitAdmin_getSupDepId2) {
				//得到部门id
				String department_id1 = user.getDepartment_id();
				System.out.println("第二层-------"+department_id1);
				allDepId.add(department_id1);
			}
			// 遍历第三层的子部门集合
			for (Department depIdSub2 : queryFuzzyByUnitAdmin_getSupDepId2) {
				//得到部门id
				String department_id1 = depIdSub2.getDepartment_id();
				//查询该子部门下的子部门
				queryFuzzyByUnitAdmin_getSupDepId3 = queryFuzzyMapper.queryFuzzyByUnitAdmin_getSupDepId(department_id1);
				//判断该部门是否包含子部门
				if (queryFuzzyByUnitAdmin_getSupDepId3.size() == 0) {
					break;
				}
				// 添加总的depId集合
				for (Department user : queryFuzzyByUnitAdmin_getSupDepId3) {
					String department_id2 = user.getDepartment_id();
					allDepId.add(department_id2);
				}
				// 遍历第四层的子部门集合
				for (Department depIdSub3 : queryFuzzyByUnitAdmin_getSupDepId3) {
					//
					String department_id2 = depIdSub3.getDepartment_id();
					//查询该子部门下的子部门
					queryFuzzyByUnitAdmin_getSupDepId4 = queryFuzzyMapper.queryFuzzyByUnitAdmin_getSupDepId(department_id2);
					//判断该部门是否包含子部门
					if (queryFuzzyByUnitAdmin_getSupDepId4.size() == 0) {
						break;
					}
					// 添加总的depId集合
					for (Department user : queryFuzzyByUnitAdmin_getSupDepId4) {
						String department_id3 = user.getDepartment_id();
						allDepId.add(department_id3);
					}
					// 遍历第五层的子部门集合
					for (Department depIdSub4 : queryFuzzyByUnitAdmin_getSupDepId3) {
						String department_id3 = depIdSub4.getDepartment_id();
						//查询该子部门下的子部门
						queryFuzzyByUnitAdmin_getSupDepId5 = queryFuzzyMapper.queryFuzzyByUnitAdmin_getSupDepId(department_id3);
						//判断该部门是否包含子部门
						if (queryFuzzyByUnitAdmin_getSupDepId5.size() == 0) {
							break;
						}
						// 添加总的depId集合
						for (Department user : queryFuzzyByUnitAdmin_getSupDepId5) {
							String department_id4 = user.getDepartment_id();
							allDepId.add(department_id4);
						}
					}
				}
			}

		}
		// 将新查询的部门id替换为循环查询的集合

		// 当查询为空，返回null

		// 当查询不为空

		// 将id放入list集合

		// 循环list集合，把集合中的部门id依次查询再下级部门id

		// 循环查询至没有下级部门时，把所有部门id传入集合

		// 查询用户的部门id是否包含在部门id的集合中

		// 当包含时，放入新的user实体类集合

		// return新的user集合
		
		//循环判断user集合中的depId是否在总的depID集合，在就添加至新的user集合
		for (User user : queryFuzzy) {
			String user_dep_id = user.getUser_dep_id();
			System.out.println("模糊查询出来的部门id-------"+user_dep_id);
			//把总的部门id打印出来
			for (String user2 : allDepId) {
				System.out.println("总的部门id号------"+user2);
			}
			if (allDepId.contains(user_dep_id)) {
				returnUserList.add(user);
			}
		}*/
		
		return returnUserList;
	}
	
	
}

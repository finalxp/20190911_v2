	var ifClick=false;//判断是否点击取消
	//添加员工时，是否提交
	function checkAddReturn(){
//	    var message = [[${addEMployee}]];
//	    console.log(message);
/*		if([[${addEMployee}]]=="error"){
			//员工编号不能重复
			alert("123");
		}*/
		if(ifClick){
			return false;
		}else if(clickAddHandler()){
			return true;
		}
		return false;	
	}
	
	//修改员工时，是否提交
	function checkEditReturn(){
		if(ifClick){
			return false;
		}else if(clickEditHandler()){
			return true;
		}
		return false;	
	}
	//判断新增用户是否存在	
	function verifyUser(){
		$.ajax({
			url:"/ifExistEmpNo",
			type:"get",
			data:{
				empNo:$(".empNo").val()
			},
			success:function(data){
				if(data=="success"){
//					alert("EmpNo不存在，可以使用！");
					$("#ifExist").html("");
				}else{
//					alert("EmpNo存在，不可以使用！");
					$("#ifExist").append("EmpNo存在，不可以使用！");
				}
			},
			error:function(){
				alert("配置信息有误！！");
			}
		});
		
		
	}	
	
	//添加员工时，检测填写内容
	function clickAddHandler(){
		var empNo= $("#empNo").val();
		var empPassword =$("#empPassword").val();
		var empName=$("#empName").val();
		var sex =$("#sex option:selected").val();
		if(empNo==""){
			$.messager.alert("警告","员工号不能为空！");
			ifClick=false;
			return false;
		}else if(empPassword==""){
			$.messager.alert("警告","员工密码不能为空！");
			ifClick=false;
			return false;
		}else if(empName==""){
			$.messager.alert("警告","员工姓名不能为空！");
			ifClick=false;
			return false;
		}else if(sex==""){
			$.messager.alert("警告","员工性别不能为空！");
			ifClick=false;
			return false;
		}
		return true;
	}
	//修改员工时，检测填写内容
	function clickEditHandler(){
		var empNo= $("#empNo").val();
		var empName=$("#empName").val();
		var sex =$("#sex option:selected").val();
		if(empNo==""){
			$.messager.alert("警告","员工号不能为空！");
			ifClick=false;
			return false;
		}else if(empName==""){
			$.messager.alert("警告","员工名字不能为空！");
			ifClick=false;
			return false;
		}else if(sex==""){
			$.messager.alert("警告","员工性别不能为空！");
			ifClick=false;
			return false;
		}
		return true;
	}
	
	//弹出新增员工框
	function newUser(){
		
		$("#w_addEmployee").window({"title" : "添加员工"}).window("open");
		$(".id").val("");
		$(".empNo").val("");
		$(".empName").val("");
		$(".sex").val("");
		$(".phoneNo").val("");
		$(".birthday").val("");
		$(".nickname").val("");
	}
	
	//添加员工
	function addEmployee(){
		

	}
	//修改员工信息
	function editUser(){
		var row =$("#employee_table").datagrid("getSelected");
		if(row!=null){
			$("#w_editEmployee").window({"title" : "修改员工"}).window("open");
			//$(".empPassword").hide();
			//$(".empPassword_text").hide();
			$(".id").val(row.id);
			$(".empNo").val(row.empNo);
			$(".empName").val(row.empName);
			var str =row.sex;
			/*var obj =$(".sex");
			alert($(".sex")[0].value);
			for(i=0;i<obj.length;i++){
				if(obj[i].value==str){
					obj[i].selected=true;
				}
			}*/
			
			$(".sex").find("option:contains('"+str+"')").attr("selected",true);
			$(".phoneNo").val(row.phoneNo);
			$(".birthday").val(row.birthday);
			$(".nickname").val(row.nickname);
		}else{
			$.messager.alert("提示","请先选择要修改的行，再点击“修改”按钮！");
		}
		
		
	
	}
	
	//删除员工
	function destroyUser(){
		var row =$("#employee_table").datagrid("getSelected");
		if(row!=null){
			$.ajax({
	            url:'/employee/deleteEmployee',
	            dataType:'text',
	            data:{_method:"DELETE","id":row.id},
	            type:'POST',
	            success:function (data) {
	            	
	            	 $.messager.alert("操作提示", "删除成功！", "info", function () {
	                     var i = true;
	                     if(i){
//	 	                	 $("#body").load("/employee");
	                    	 table_refresh();
	 	                }
	                 });
	            },
		        error: function (XMLHttpRequest, textStatus, errorThrown) {
		        	$.messager.alert("提示","删除失败！！");
		        	alert(XMLHttpRequest.status);
                    alert(XMLHttpRequest.readyState);
                    alert(textStatus);
                }
	        });
//			$("#employee_table").datagrid("reload");
			
			
		}else{
			$.messager.alert("提示","请先选择要修改的行，再点击“删除”按钮！");
		}
		
	}
	
    //刷新表格
	function table_refresh(){
		$("#body").load("/employee");
	}	
	
	//关闭窗口
	function close_window(){
		$("#w_addEmployee").window("close");
		$("#w_editEmployee").window("close");
		//$("#center_div").load("/employee");
	}
	

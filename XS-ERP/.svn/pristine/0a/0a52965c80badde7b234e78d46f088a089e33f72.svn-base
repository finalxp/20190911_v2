/**
 * author by leo.liu(Vic)
 */
//part of add;
//whether to submit when adding department;
function checkAddReturn(){
	if(ifClick){
		return false;
	}else if(clickAddHandler()){
		return true;
	}
}

//verify that the department exists;
function verifyDepartment(){
	$.ajax({
		url:"/ifExistDepId",
		type:"get",
		data:{
			depNo:$(".depId").val()
		},
		success:function(data){
			if(data=="success"){
				$("#ifExist").html("");
			}else{
				$("#ifExist").append("depId存在，不可以使用！")
			}
		},
		error:function(){
			alert("配置信息有误！")
		}
	});
}

//part of add;
//when adding a department,check the content filled in;
function clickAddHandler(){
	var depNo=$("#depId").val();
	var depName=$("#depName").val();
	var parentDepId=$("#parentDepId").val();
	
	if(depId==""){
		$.messager.alert("警告","部门ID不能为空！");
		ifClick=false;
		return false;
	}else if(depName==""){
		$.messager.alert("警告","部门名称不能为空！")
	}else if(parentDepId==""){
		$.messager.alert("警告","上级部门不能为空！")
	}
	return true;
}

//part of edit;
//whether to summit when edit department;
function checkEditReturn(){
	if(ifClick){
		return false;
	}else if(clickEditHandler()){
		return true;
	}
	return false;
}

//part of edit
//when editing a department,check the content filled in;
function clickEditHandler(){
	var depNo=$("#depId").val();
	var depName=$("#depName").val();
	var parentDepId=$("#parentDepId").val();
	
	if(depId==""){
		$.messager.alert("警告","部门ID不能为空！");
		ifClick=false;
		return false;
	}else if(depName==""){
		$.messager.alert("警告","部门名称不能为空！")
	}else if(parentDepId==""){
		$.messager.alert("警告","上级部门不能为空！")
	}
	return true;
}

//part of add;
//pop-up add department box
function addDepartment(){
	$("#w_addDepartment").window({"title":"添加部门"}).window("open");
	$(".id").val("");
	$(".depName").val("");
	$(".parentDepId").val("");
}

//part of edit;
//summit edit message;
function editDepartment(){
	var row=$("#department_table").datagrid("getSelected");
	if(row!=null){
		$("#w_editDepartment").window({"title":"修改部门"}).window("open");
		$(".depId").val(row.id);
		$(".depName").val(row.depName);
		$(".parentDepId").val(row.parentDepId);
	}else{
		$.messager.alert("提示","请先选择要修改的行，再点击”修改按钮！")
	}
}

//delete department
function destroyDepartment(){
	var row=$("#department_table").datagrid("getSelected");
	if(row!=null){
		$.ajax({
			url:'/department/deleteDepartment',
			dataType:'text',
			data:{_method:"DELETE","id":row.id},
			type:'POST',
			success:function (data) {
				$.message.alert("操作提示","删除成功！","info",function(){
					var i = true;
					if(i){
						$("#body").load("/department");
						table_refresh();
					}
				})
			},
			error: function (XMLHttpRequest, textStatus, errorThrown){
				$.messager.alert("提示","删除失败！");
				alert(XMLHttpRequest.status);
				alert(XMLHttpRequest.readyState);
				alert(textStatus);
			}
		});
	}else{
		$.message.alert("提示","请先选择要修改的行，再点击“删除按钮！");
	}
}

//refresh table;
function table_refresh(){
	$("#body").load("/department");
}

//close window;
function close_window(){
	$("#w_addDepartment").window("close");
	$("#w_editDepartment").window("close");
}

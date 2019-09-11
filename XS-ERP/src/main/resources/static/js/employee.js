$(function(){
	//中间栏
	$("#employee_table").datagrid({
		url:"/employee/list",
		rownumbers:true,//显示行编号
		singleSelect:true,//单行选择
		toolbar : "#toolbar",
		pagination : true,//分页插件
		columns : [[
		            {field : "id",width : 100,hidden:"hidden"},
		            {field : "empNo",width : 80,title : "员工编号",align:"center"},
		            {field : "empName",width : 80,title : "员工姓名",align:"center"},
		            {field : "sex",width : 80,title : "性别",align:"center"},
		            {field : "phoneNo",width : 80,title : "电话",align:"center"},
		            {field : "birthday",width : 80,title : "生日",formatter: formatDatebox,align:"center"},
		            {field : "nickname",width : 80,title : "昵称",align:"center"},
		            {field : "avatarUrl",width : 80,title : "头像存放地址",align:"center"},
		            {field : "enrolled",width : 80,title : "声纹是否注册",align:"center"},
		            {field : "hiredate",width : 80,title : "入职时间",formatter: formatDatebox,align:"center"}
		]],
		onDblClickRow : function(index, row){//双击
			alert("12");
			myeditshow(row);
		}
	});
	$.ajax({
		async:true,
		cache:false,
		type:'POST',
		dataType:'json',
		url:'',
		error:function(){
			alert("网络有问题啦！");
		},
		success:function(data){
			alert("success");
		}
		
	});
	
	
	var data, json;
	json = '[{"id":"年计划","text":"年计划","tetet":"111"}]';
	data = $.parseJSON(json);
	$("#rwlb").combobox("loadData", data);
	
	
	$("#dempartmentSelect").append("<option value='2'>部门3</option>");
});

function dempartmentSelect(value){
	alert("123");
	
}
function findEmployee(){
	
	$("#dempartmentSelect").append("<option value='2'>部门3</option>");
}

//格式化日期
Date.prototype.format = function (format) {
    var o = {
        "M+": this.getMonth() + 1, // month
        "d+": this.getDate(), // day
        "h+": this.getHours(), // hour
        "m+": this.getMinutes(), // minute
        "s+": this.getSeconds(), // second
        "q+": Math.floor((this.getMonth() + 3) / 3), // quarter
        "S": this.getMilliseconds()
        // millisecond
    }
    if (/(y+)/.test(format))
        format = format.replace(RegExp.$1, (this.getFullYear() + "")
            .substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(format))
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
    return format;
}
function formatDatebox(value) {
    if (value == null || value == '') {
        return '';
    }
    var dt;
    if (value instanceof Date) {
        dt = value;
    } else {
        dt = new Date(value);
    }
 
    return dt.format("yyyy-MM-dd"); //扩展的Date的format方法(上述插件实现)
}


var ifClick = false;// 判断是否点击取消
// 添加员工时，是否提交
function checkAddReturn() {
	// var message = [[${addEMployee}]];
	// console.log(message);
	/*
	 * if([[${addEMployee}]]=="error"){ //员工编号不能重复 alert("123"); }
	 */
	if (ifClick) {
		return false;
	} else if (clickAddHandler()) {
		return true;
	}
	return false;
}

// 修改员工时，是否提交
function checkEditReturn() {
	if (ifClick) {
		return false;
	} else if (clickEditHandler()) {
		return true;
	}
	return false;
}
// 判断新增用户是否存在
function verifyUser() {
	$.ajax({
		url : "/ifExistEmpNo",
		type : "get",
		data : {
			empNo : $(".empNo").val()
		},
		success : function(data) {
			if (data == "success") {
				// alert("EmpNo不存在，可以使用！");
				$("#ifExist").html("");
			} else {
				// alert("EmpNo存在，不可以使用！");
				$("#ifExist").append("EmpNo存在，不可以使用！");
			}
		},
		error : function() {
			alert("配置信息有误！！");
		}
	});

}

// 添加员工时，检测填写内容
function clickAddHandler() {
	var empNo = $("#empNo").val();
	var empPassword = $("#empPassword").val();
	var empName = $("#empName").val();
	var sex = $("#sex option:selected").val();
	if (empNo == "") {
		$.messager.alert("警告", "员工号不能为空！");
		ifClick = false;
		return false;
	} else if (empPassword == "") {
		$.messager.alert("警告", "员工密码不能为空！");
		ifClick = false;
		return false;
	} else if (empName == "") {
		$.messager.alert("警告", "员工姓名不能为空！");
		ifClick = false;
		return false;
	} else if (sex == "") {
		$.messager.alert("警告", "员工性别不能为空！");
		ifClick = false;
		return false;
	}
	return true;
}
// 修改员工时，检测填写内容
function clickEditHandler() {
	var empNo = $("#empNo").val();
	var empName = $("#empName").val();
	var sex = $("#sex option:selected").val();
	if (empNo == "") {
		$.messager.alert("警告", "员工号不能为空！");
		ifClick = false;
		return false;
	} else if (empName == "") {
		$.messager.alert("警告", "员工名字不能为空！");
		ifClick = false;
		return false;
	} else if (sex == "") {
		$.messager.alert("警告", "员工性别不能为空！");
		ifClick = false;
		return false;
	}
	return true;
}

// 弹出新增员工框
function newUser() {

	$("#w_addEmployee").window({
		"title" : "添加员工"
	}).window("open");
	$(".id").val("");
	$(".empNo").val("");
	$(".empName").val("");
	$(".sex").val("");
	$(".phoneNo").val("");
	$(".birthday").val("");
	$(".nickname").val("");
}

// 添加员工
function addEmployee() {

}
// 修改员工信息
function editUser() {
	var row = $("#employee_table").datagrid("getSelected");
	if (row != null) {
		$("#w_editEmployee").window({
			"title" : "修改员工"
		}).window("open");
		// $(".empPassword").hide();
		// $(".empPassword_text").hide();
		$(".id").val(row.id);
		$(".empNo").val(row.empNo);
		$(".empName").val(row.empName);
		var str = row.sex;
		/*
		 * var obj =$(".sex"); alert($(".sex")[0].value); for(i=0;i<obj.length;i++){
		 * if(obj[i].value==str){ obj[i].selected=true; } }
		 */

		$(".sex").find("option:contains('" + str + "')").attr("selected", true);
		$(".phoneNo").val(row.phoneNo);
		$(".birthday").val(row.birthday);
		$(".nickname").val(row.nickname);
	} else {
		$.messager.alert("提示", "请先选择要修改的行，再点击“修改”按钮！");
	}

}

// 删除员工
function destroyUser() {
	var row = $("#employee_table").datagrid("getSelected");
	if (row != null) {
		$.ajax({
			url : '/employee/deleteEmployee',
			dataType : 'text',
			data : {
				_method : "DELETE",
				"id" : row.id
			},
			type : 'POST',
			success : function(data) {

				$.messager.alert("操作提示", "删除成功！", "info", function() {
					var i = true;
					if (i) {
						// $("#body").load("/employee");
						table_refresh();
					}
				});
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				$.messager.alert("提示", "删除失败！！");
				alert(XMLHttpRequest.status);
				alert(XMLHttpRequest.readyState);
				alert(textStatus);
			}
		});
		// $("#employee_table").datagrid("reload");

	} else {
		$.messager.alert("提示", "请先选择要修改的行，再点击“删除”按钮！");
	}

}

// 刷新表格
function table_refresh() {
	$("#body").load("/employee");
}

// 关闭窗口
function close_window() {
	$("#w_addEmployee").window("close");
	$("#w_editEmployee").window("close");
	// $("#center_div").load("/employee");
}
$(function() {
	$.ajax({
		async : true, //是否异步  
		cache : false, // 是否使用缓存  
		type : 'post', // 请求方式,post  
		dataType : 'json', // 数据传输格式  
		url : '/allMenus',// 请求链接  
		error : function() {
			alert('亲，网络有点不给力呀！');
		},
		success : function(data) {
			for(var i=0; i<data.menuItem.length;i++){
				var href=data.menuItem[i].menuPath;
				var text=data.menuItem[i].menuName;
				var image=data.menuItem[i].menuIcon;
				if(i==data.length-1){
					$("#headMenus").append("<li style='width:20%'><a onclick=\"addTabs('"+text+"','"+href+"');\" style='height: 60px;color:#fff;text-align:center;'><p><img alt='' src='"+image+"' style='margin-right:5px;'></p><p>"+text+"</p></a></li>");
				}else{
					$("#headMenus").append("<li style='width:20%'><a onclick=\"addTabs('"+text+"','"+href+"');\" style='height: 60px;color:#fff;text-align:center;'><img alt='' src='"+image+"' style='margin-right:5px;'></p><p>"+text+"</p></a></li>");
				}
			}
			var str ="<li style='line-height:42px;width:10%;'><a title='搜索' style='text-align:center;' href='#'><img alt='搜索' src='/images/search.png' width='25px' height='25px'></a></li>"+
               "<li style='line-height:42px;width:8%;'><a id='toggle-sidebar' title='设置' style='text-align:center;' onclick='topRight();'><img alt='头像' src='"+data.employeeItem.avatarUrl+"' width='35px' height='35px' style='border-radius:50%'></a></li>"
			$("#headMenus").append(str);
		}  
	});
	
	tabCloseEven();// menu菜单功能实现
	$('.cs-navi-tab').click(function() {
		var $this = $(this);
		var href = $this.attr('src');
		var title = $this.text();
		addTabs(title, href); // 增加tab
	});
	//加载左侧列
	$.ajax({
		url:'/treeMenus',
		async : true, //是否异步  
		cache : false, // 是否使用缓存  
		type : 'post', // 请求方式,post  
		dataType : 'json', // 数据传输格式 
		error:function(){
			alert('亲，网络有点不给力呀！');
		},
		success:function(data){
			for(var i =0;i<data.length;i++){
				var child="";
				for(var j=0;j<data.length;j++){
					if(data[j].menuDepth==3 && data[j].parentId==data[i].id){
						child+="<li class='file folderTwo'><a onclick=\"addTabs('"+data[j].menuName+"','"+data[j].menuPath+"');\"><img alt='' src='"+data[j].menuIcon+"' style='margin-right:5px;'>"+data[j].menuName+"</a></li>";
					}
				}
				if(data[i].menuDepth==2){
					if(child!=""){
						$('.tree').append("<li><label for='folder"+i+"' class='folderOne'><img alt='' src='"+data[i].menuIcon+"' style='margin-right:5px;'>"+data[i].menuName+"</label> <input type='checkbox' id='folder"+i+"'/><ol>"+child+"</ol></li>");
						$(".tree").trigger("create");
					}else{
						$('.tree').append("<li><label for='folder"+i+"' class='folderOne' onclick=\"addTabs('"+data[i].menuName+"','"+data[i].menuPath+"');\"><img alt='' src='"+data[i].menuIcon+"' style='margin-right:5px;'>"+data[i].menuName+"</label> <input type='checkbox' id='folder"+i+"'/></li>");
						$(".tree").trigger("create");
					}
					
				}
			}
		}
	});
	$(".tree").on("click","label",function(){
	    //获取当前菜单旁边input的check状态
	    var isChecked = $(this).next("input[type='checkbox']").is(':checked');
	    //展开和收齐的不同状态下更换右侧小图标
	    if(isChecked){
	        $(this).css(
	            "background-image","url(../images/cp-detail-arrow-b.png)"
	        );
	    }else{
	        $(this).css(
	            "background-image","url(../images/cp-detail-arrow-t.png)"
	        );
	    }
	});

	
	/*$('#west').tree({
		url: '/treeMenus',
		loadFilter: function(rows){
			return convert(rows);
		},
		onClick:function(node){
			if(!node.children){
				addTabs(node.text,node.path);
			}
			
		},onBeforeExpand:function(){
			
		}
	});*/
	$.parser.parse();

	
});


//center添加tabs
function addTabs(name,path) {
//	var ts =$("#"+id+"").text();
//	var hre =$("#"+id+"").attr('name');

    if ($('#tabs').tabs('exists', name)) {
        $('#tabs').tabs('select', name);
    } else {
        var iframe = '<iframe src="' + path + '" scrolling="no" frameborder="0" style="border:0;width:100%;height:98%;"></iframe>';
        $('#tabs').tabs('add', {
            title: name,
            content: iframe,
            closable: true,
            bodyCls: 'content-doc',
        });
    }
}

function topRight(){
	var str ="<div id='setting' style='display:none;width:80px;'>"+
			"<p style='transform: translate(5%,0%);'><a class='btn btn-default btn-block' style='width:180px;margin-top: 15px;' onclick=\"showW(55,78,'/employee/edit','修改个人信息')\"><strong>修改个人信息</strong></a>"+
			"<a class='btn btn-default btn-block' style='width:180px;margin-top: 15px;' onclick=\"showW(40,60,'/employee/editPwd','修改密码')\"><strong>修改密码</strong></a>"+
			"<a class='btn btn-default btn-block' style='width:180px;margin-top: 15px;' href='/logOut'><strong>注销</strong></a></div></p>";
	$.messager.show({
        msg:str,
        showType:'show',
        width:220,
        height:210,
        border:1,
        timeout: 5000,
        style:{
            left:'',
            right:0,
            top:60,
            bottom:'',
            border:0
        }
    });
	if($("#setting").css("display")=="none"){
		$("#setting").show();
	}else{
		$("#setting").hide();
		$(".messager-body").window('close');//关闭$.message.show();
	}
}

function convert(rows){
	function exists(rows, parentId){
		for(var i=0; i<rows.length; i++){
			if (rows[i].id == parentId) return true;
		}
		return false;
	}
	
	var nodes = [];
	// get the top level nodes
	for(var i=0; i<rows.length; i++){
		var row = rows[i];
		if (!exists(rows, row.parentId)){
			nodes.push({
				id:row.id,
				text:row.menuName,
				path:row.menuPath
			});
		}
	}
	
	var toDo = [];
	for(var i=0; i<nodes.length; i++){
		toDo.push(nodes[i]);
	}
	while(toDo.length){
		var node = toDo.shift();	// the parent node
		// get the children nodes
		for(var i=0; i<rows.length; i++){
			var row = rows[i];
			if (row.parentId == node.id){
				var child = {id:row.id,text:row.menuName,path:row.menuPath};
				if (node.children){
					node.children.push(child);
				} else {
					node.children = [child];
				}
				toDo.push(child);
			}
		}
	}
	return nodes;
}




function addTab(title, url) {
	if ($('#tabs').tabs('exists', title)) {
		$('#tabs').tabs('select', title);// 选中tab并显示
		var currTab = $('#tabs').tabs('getSelected');
		var url = $(currTab.panel('options').content).attr('src');
		if (url != undefined && currTab.panel('options').title != '首页') {
			$('#tabs').tabs('update', {
				// 刷新这个tab
				tab : currTab,
				options : {
					content : createFrame(url)
				}
			})
		}
	} else {
		var content = createFrame(url);
		$('#tabs').tabs('add', {
			title : title,
			content : content,
			closable : true
		});
	}
	tabClose(); // 绑定menu菜单
}
function createFrame(url) {
	var s = '<iframe scrolling="auto" frameborder="0"  src="' + url
			+ '" style="width:100%;height:100%;"></iframe>';
	return s;
}
function tabClose() {
	/* 双击关闭TAB选项卡 */
	$(".tabs-inner").dblclick(function() { // jQuery双击方法
		var subtitle = $(this).text();
		$('#tabs').tabs('close', subtitle);
	})
	/* 为选项卡绑定右键 */
	$(".tabs-inner").bind('contextmenu', function(e) { // e这个参数对象封装鼠标坐标值
		$('#mm').menu('show', {
			left : e.pageX,
			top : e.pageY
		});
		var subtitle = $(this).text();
		$('#mm').data("currtab", subtitle); // jQuery方法，向被选元素附加数据
		$('#tabs').tabs('select', subtitle);
		return false;
	});
}
function tabCloseEven() {
	// 刷新
	$('#mm-tabupdate').click(function() {
		var currTab = $('#tabs').tabs('getSelected');
		var url = $(currTab.panel('options').content).attr('src');
		if (url != undefined && currTab.panel('options').title != '首页') {
			$('#tabs').tabs('update', {
				tab : currTab,
				options : {
					content : createFrame(url)
				}
			})
		}
	})
	// 关闭当前
	$('#mm-tabclose').click(function() {
		var currtab_title = $('#mm').data("currtab");
		$('#tabs').tabs('close', currtab_title);
	})
	// 全部关闭
	$('#mm-tabcloseall').click(function() {
		$('.tabs-inner span').each(function(i, n) { // i是循环index位置，n相当于this
			var t = $(n).text();
			if (t != '首页') {
				$('#tabs').tabs('close', t);
			}
		});
	});
	// 关闭除当前之外的TAB
	$('#mm-tabcloseother').click(function() {
		var prevall = $('.tabs-selected').prevAll(); // jQuery遍历方法，prevAll()
														// 获得当前匹配元素集合中每个元素的前面的同胞元素
		var nextall = $('.tabs-selected').nextAll();
		if (prevall.length > 0) {
			prevall.each(function(i, n) { // i是当前循环次数
				var t = $(n).text();
				if (t != '首页') {
					$('#tabs').tabs('close', t);
				}
			});
		}
		if (nextall.length > 0) {
			nextall.each(function(i, n) {
				var t = $(n).text();
				if (t != '首页') {
					$('.tabs').tabs('close', t);
				}
			});
		}
		return false;
	});
	// 关闭当前右侧的TAB
	$('#mm-tabcloseright').click(function() {
		var nextall = $('.tabs-selected').nextAll();
		if (nextall.length == 0) {
			return false;
		}
		nextall.each(function(i, n) {
			var t = $(n).text();
			$('#tabs').tabs('close', t);
		});
		return false;
	});
	// 关闭当前左侧的TAB
	$('#mm-tabcloseleft').click(function() {
		var prevall = $('.tabs-selected').prevAll();
		if (prevall.length == 0) {
			return false;
		}
		prevall.each(function(i, n) {
			var t = $(n).text();
			$('#tabs').tabs('close', t);
		});
		return false;
	});
	// 退出
	$("#mm-exit").click(function() {
		$('#mm').menu('hide'); // 隐藏menu菜单
	})
}
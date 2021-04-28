/**
 * index js
 */
$(document).ready(function () {


});


/**
 * 表格checkbox列渲染
 * @param data
 * @param type
 * @param row
 * @returns
 */
function checkRenderer(data, type, row) {
	return '<input type="checkbox" name="ids" value="'+data+'" class="selector">';
}

/**
 * 
 * @param data
 * @param type
 * @param row
 * @returns
 */
function statusRenderer(data, type, row) {
	var id="customSwitch"+row.id;
	var checked="";
	if(data=='1'){
		checked=" checked";
	}
	html='<div class="custom-control custom-switch">'+
		 '   <input type="checkbox"'+checked+' onclick="disableRole(\''+row.id+'\',\''+row.status+'\')" class="custom-control-input" id="'+id+'">'+
		 '   <label class="custom-control-label" for="'+id+'"></label>'+
		 '</div>';
	return html;
}
/**
 * 表格操作列渲染
 * @param data
 * @param type
 * @param row
 * @returns
 */
function operateRenderer(data, type, row) {
	var buttons = [];
	buttons.push('<a class="btn btn-primary btn-xs" href="#" onclick="dtOptions.lookup(\'' + data + '\', \'pages/role/form.html\',\'查看页\',60,70)"><i class="nav-icon fas fa-folder"></i>查看</a>');
	buttons.push('<a class="btn btn-info btn-xs" href="#" onclick="openEntityWin(true, \'' + data + '\')"><i class="nav-icon fas fa-edit"></i>修改</a>');
	buttons.push('<a class="btn btn-warning btn-xs" href="#" onclick=openAuthWin(true,"'+row.id+'","'+row.roleName+'")><i class="nav-icon fas fa-lock"></i>配权</a>');
	buttons.push('<a class="btn btn-danger btn-xs" href="#" onclick=deleteSelf("/api/role/delete","'+data+'","dataGrid")><i class="nav-icon far fa-trash-alt"></i>删除</a>');
	
	return buttons.join(' ');
}



/**
 * 禁用角色
 * @param id
 * @returns
 */
function disableRole(id,obj){
	var status=obj;//$(obj).val();
	
	if(status=="0"){
		status="1";
	}else{
		status="0";
	}
	var user={"id":id,"status":status}
	
	$.ajax({
		url : host+"/api/role/disable",
		dataType : 'json',
		contentType: "application/json;charset=utf-8",
		type : 'post',
		data : JSON.stringify(user),
		success : function(data) {
			if (data.success) {
				CommonUtils.notify("success", "操作成功", 1500);
//				search();
				dtOptions.reload('dataGrid');
			} else {
				CommonUtils.notify("error", data.responseMessage, 4000);
			}
			layer.closeAll('loading');
			
		}
	});
}

/**
 * 表格行删除按钮事件
 * @param url
 * @param id
 * @param gridId
 * @returns
 */
function deleteSelf(url, id, gridId) {
	dtOptions.delete4Self(function(index) {
		layer.load(1, {
			shade : [ 0.2 ]
		});
//		var ids = [];
//		ids.push(id);
		$.ajax({
			url : host+url,
			dataType : 'json',
			type : 'post',
			data : {
				"id" : id,
			},
			success : function(data) {
				if (data.success) {
					CommonUtils.notify("success", "操作成功", 1500);
					dtOptions.reload('dataGrid');
				} else {
					CommonUtils.notify("error", data.responseMessage, 4000);
				}
				layer.closeAll('loading');
				layer.close(index);
			}
		});
	});
}

/**
 * 查询功能
 */
var search = function() {
	$("#dataGrid").DataTable().fnSearch();
}



/** 
 * 打开窗口（layer）
 * 是否是修改页
 * update:false,true
 * 
 */
var openEntityWin = function(update, entityId, updatebtn) {
	//将主窗口名称传给iframe，便于在iframe页面调用主窗口方法
	var mainWindow=window.name;
	var IsRefresh = false;

	var params = {};
	var paramStr = '';
	if (params) {
		paramStr = CommonUtils.urlEncode(params);
	}

	top.layer.open({
		type : 2,
		title : FORM_TITLE_PRE + '表单页',
		closeBtn : 1, // 不显示关闭按钮
		shadeClose : false,
		shade : false,
		scrollbar : false,
		maxmin : true, // 开启最大化最小化按钮
		area : [ '60%', '70%' ],
		shade : [ 0.3 ],
		content : 'pages/role/form.html?isUpdate=' + update + '&entityId=' + entityId + paramStr + '&v=' + version,
		btn : ['保存', '取消' ],
		yes : function(index, layero) {
			IsRefresh = true;
			var iframeWin = layero.find('iframe')[0]; // 得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
			iframeWin.contentWindow.FormUtils.submitHandler(mainWindow, "submitEntityForm", "entityForm");
		},
		btn2 : function(index, layero) {
			// 按钮【按钮二】的回调
			IsRefresh = false;
		},
		cancel : function() {
			// 右上角关闭回调
			IsRefresh = false;
		},
		success : function(layero, index) {
			IframeWin = window[layero.find('iframe')[0]['name']]; // 得到iframe页的窗口对象，执行iframe页的方法：IframeWin.method()
		},
		end : function(index) {
			top.layer.closeAll('loading');
			if (IsRefresh)
				$("#dataGrid").DataTable().draw(false);
		}
	});
}

/** 
 * 打开配权窗口（layer）
 * 
 * update:false,true
 * 
 */
var openAuthWin = function(update, entityId, updatebtn) {
	//将主窗口名称传给iframe，便于在iframe页面调用主窗口方法
	var mainWindow=window.name;
	var IsRefresh = false;

	var params = {};
	params.name=updatebtn;
	var paramStr = '';
	if (params) {
		paramStr = CommonUtils.urlEncode(params);
	}

	top.layer.open({
		type : 2,
		title : FORM_TITLE_PRE + '配权页',
		closeBtn : 1, // 不显示关闭按钮
		shadeClose : false,
		shade : false,
		scrollbar : false,
		maxmin : true, // 开启最大化最小化按钮
		area : [ '60%', '75%' ],
		shade : [ 0.3 ],
		content : 'pages/role/config.html?isUpdate=' + update + '&entityId=' + entityId + paramStr + '&v=' + version,
		btn : ['保存', '取消' ],
		yes : function(index, layero) {
			IsRefresh = true;
			var iframeWin = layero.find('iframe')[0]; // 得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
			iframeWin.contentWindow.FormUtils.submitHandler(mainWindow, "submitEntityForm", "searchForm");
		},
		btn2 : function(index, layero) {
			// 按钮【按钮二】的回调
			IsRefresh = false;
		},
		cancel : function() {
			// 右上角关闭回调
			IsRefresh = false;
		},
		success : function(layero, index) {
			IframeWin = window[layero.find('iframe')[0]['name']]; // 得到iframe页的窗口对象，执行iframe页的方法：IframeWin.method()
		},
		end : function(index) {
			top.layer.closeAll('loading');
			if (IsRefresh)
				$("#dataGrid").DataTable().draw(false);
		}
	});
}

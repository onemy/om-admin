/**
*	公共组件函数库
*/


function group_select (callback,ids) {
	//var requestFrame = top.window[window.name];//取得上层页面的window对象
	
	top.layer.open({
		type : 2,
		title : '组选择',
		closeBtn : 1, // 不显示关闭按钮
		shadeClose : false,
		shade : false,
		scrollbar: false,
		maxmin : false, // 开启最大化最小化按钮
		shade : [ 0.3 ],
		area : [ "60%", "70%" ],
		shadeClose : false,
		content : "pages/common/groupSelect.html?ids="+ids,
		btn : ['确定', '取消'],
		yes : function (index, layero) {
			var iframe = layero.find('iframe')[0].contentWindow;
			var obj = iframe.choose();//调用打开页面内函数
			if (typeof callback == "function")
				callback(obj);//上层页面回调
			top.layer.close(index);
		}
		
	});
}


function role_select (callback) {
	var mainWin=window.name;
	
	top.layer.open({
		type : 2,
		title : '角色选择',
		closeBtn : 1, // 不显示关闭按钮
		shadeClose : false,
		shade : false,
		scrollbar: false,
		maxmin : false, // 开启最大化最小化按钮
		shade : [ 0.3 ],
		area : [ "60%", "70%" ],
		shadeClose : false,
		content : "pages/common/roleSelect.html?winName="+mainWin,
		btn : ['确定', '取消'],
		yes : function (index, layero) {
			var iframe = layero.find('iframe')[0].contentWindow;
			var obj = iframe.choose();//调用打开页面内函数
			if (typeof callback == "function")
				callback(obj);//上层页面回调
			top.layer.close(index);
		}
		
	});
}


function menu_select (callback,ids) {
	//var requestFrame = top.window[window.name];//取得上层页面的window对象
	
	top.layer.open({
		type : 2,
		title : '菜单选择',
		closeBtn : 1, // 不显示关闭按钮
		shadeClose : false,
		shade : false,
		scrollbar: false,
		maxmin : false, // 开启最大化最小化按钮
		shade : [ 0.3 ],
		area : [ "60%", "70%" ],
		shadeClose : false,
		content : "pages/common/menuSelect.html?ids="+ids,
		btn : ['确定', '取消'],
		yes : function (index, layero) {
			var iframe = layero.find('iframe')[0].contentWindow;
			var obj = iframe.choose();//调用打开页面内函数
			if (typeof callback == "function")
				callback(obj);//上层页面回调
			top.layer.close(index);
		}
		
	});
}

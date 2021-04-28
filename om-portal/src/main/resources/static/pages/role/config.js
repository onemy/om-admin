/**
 * index js
 */
$(document).ready(function () {
	//zTree config
	var setting = {
			check : {
				enable : true,
				chkStyle : 'checkbox'
			},
			data : {
				simpleData : {
					enable : true,
					idKey : 'id',
					pIdKey : 'pid',
					rootPId : 0
				},
				keep : {
					leaf : false,
					parent : false
				}
			},
			expandLevel: 1,
			callback : {
				onClick : nodeClick,
				onCheck: onCheck
			}
		};
	
	var roleId = CommonUtils.getUrlParam("entityId");
	//请求组列表
    $.ajax({
        url : host+'/api/menu/roleMenus',
        type : 'post',
        //contentType: "application/json",
        data : {"id":roleId},
        success : function(result, status, xhr) {
          if(result.success){
        	  var zNodes=result.data.menus;
        	  
        	  var treeObj=$.fn.zTree.init($("#ztree"), setting, zNodes);
        	  
        	  var ids=CommonUtils.getUrlParam("ids");
        	  var node = treeObj.getNodeByParam("id", ids);
        	  treeObj.selectNode(node);
        	  
        	  //初始化permission
        	  permissionData=result.data.permissions;
        	  $("#permissionData").val(JSON.stringify(permissionData));
        	  
          }else{
        	  CommonUtils.notify("error",result.msg,"4000");
          }
        }
    });

    /**
     * 增加：注册行点击事件
     */
    $('table[data-view=dataTables] tbody').on('click', 'tr', function () {
        var checkbox = $(this).find(':checkbox[name="ids"]');
        checkPermission(checkbox.val(), checkbox.prop('checked'));
        
        //如果permission对应的菜单未被checked，则将它checked
        var treeObj = $.fn.zTree.getZTreeObj("ztree");
        var node = treeObj.getNodeByParam("id",$("#menuId").val(),null);
        if(!node.checked){
        	treeObj.checkNode(node, true, true);
        }

        $("#permissionData").val(JSON.stringify(permissionData));
    });	
    /**
     * 增加：全选功能
     * @returns
     */
    $(':checkbox[name="all"]').click(function () {
    	var menuId=$("#menuId").val();
    	checkAllPermission(menuId, $('#all').prop('checked'));
    	
        //如果permission对应的菜单未被checked，则将它checked
        var treeObj = $.fn.zTree.getZTreeObj("ztree");
        var node = treeObj.getNodeByParam("id",$("#menuId").val(),null);
        if(!node.checked){
        	treeObj.checkNode(node, true, true);
        }
    	
    	
    	$("#permissionData").val(JSON.stringify(permissionData));
    });    
    
    
});

//permision全局数据
var permissionData=[];


function filter(treeId, parentNode, childNodes) {
	if (!childNodes)
		return null;
	for ( var i = 0, l = childNodes.length; i < l; i++) {
		childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
	}
	return childNodes;
}

//节点点击事件
function nodeClick(event, treeId, treeNode, clickFlag) {
	queryGrid(treeNode.id);
}

//checkbox点击事件
function onCheck(e, treeId, treeNode){
	
	var treeObj = $.fn.zTree.getZTreeObj("ztree");
	//获取当前节点，及其子节点（如果有）
	var nodes=[];
	getChildrenNodes(nodes, treeNode);
	
	//获取选中节点集合
	var selectedNodes = treeObj.getSelectedNodes();
	//循环选中的节点及其子节点
	for(var k in nodes){
		
		//更新permissionData数据check状态与菜单的一致
		checkAllPermission(nodes[k].id, nodes[k].checked);
		//如果变化的菜单项包含了选中的菜单，就刷新选中菜单右侧对应的表格，保持界面显示状态与permissionData一致
		if(exsitNode(selectedNodes,nodes[k])){
			queryGrid(nodes[k].id);
		}
	}
	//测试用
	$("#permissionData").val(JSON.stringify(permissionData));
	
}

//递归获取所有子节点（包括自己）
function getChildrenNodes(nodes, treeNode) {
   nodes.push(treeNode);
   if (treeNode.isParent) {
        for(var obj in treeNode.children) {
            getChildrenNodes(nodes, treeNode.children[obj]);
        }
   }
   return nodes;
}

//permissonData中的此菜单的所有选中状态与菜单状态保持一致
function checkAllPermission(menuId,checked){
	for(var k in permissionData){
		if(permissionData[k].menuId==menuId){
			permissionData[k].checked=checked;
		}
	}	
}

//设置permissonData中的此permission的状态
function checkPermission(id,checked){
	for(var k in permissionData){
		if(permissionData[k].id==id){
			permissionData[k].checked=checked;
		}
	}	
}

//通用集合包含node判断
function exsitNode(list,node){
	for(var k in list){
		if(list[k].id==node.id){
			return true;
		}
	}
	return false;
}

//刷新表格
function queryGrid(menuCode) {
	$("#menuId").val(menuCode);
	$("#dataGrid").DataTable().fnSearch();
}

//未使用
function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
	var tree = $.fn.zTree.getZTreeObj(treeId);
	tree.expandAll(true);
	layer.closeAll('loading');
}


/**
 * 表格checkbox列渲染
 * @param data
 * @param type
 * @param row
 * @returns
 */
function checkRenderer(data, type, row) {
	var checked="";
	//var isCheck=$('#autoAll').is(':checked');
	if(isPermissionChecked(row.id)){
		checked=" checked";
	}
	
	return '<input type="checkbox"'+checked+' name="ids" value="'+data+'" class="selector">';
}

//判断表格行数据与permissionData中数据状诚一致
function isPermissionChecked(id){
	//var json=JSON.parse($("#permissionData").val());
	for(var k in permissionData){
		if(permissionData[k].id==id){
			return permissionData[k].checked;
		}
	}
	return false;
}

/**
 * 查询功能
 */
var search = function() {
	$("#dataGrid").DataTable().fnSearch();
}

//保存提交
var submitEntityForm = function(formWinIndex,confirmIndex) {
	
	var requestData={};

	
	var treeObj = $.fn.zTree.getZTreeObj("ztree");
	var menus = treeObj.getCheckedNodes(true);
	requestData.role={};
	requestData.role.id=CommonUtils.getUrlParam("entityId");
	requestData.menus=menus;
	requestData.permissions=[];
	
	for(var k in permissionData){
		if(permissionData[k].checked){
			requestData.permissions.push(permissionData[k]);
		}
	}
	
	
//	console.log(JSON.stringify(requestData));
//	top.layer.closeAll('loading');
//	return;
	
	$.ajax({
		url : host+'/api/acl/saveRole',
		dataType : 'json',
		contentType: "application/json;charset=utf-8",
		type : 'post',
		data : JSON.stringify(requestData),
		success : function(result) {
			if (result.success) {
				CommonUtils.notify("success","操作成功<br>","1500");
				top.layer.close(confirmIndex);
				top.layer.close(formWinIndex); // 再执行关闭
			} else {
				CommonUtils.notify("error",result.msg,"4000");
				top.layer.closeAll('loading');
				top.layer.close(confirmIndex);
			}
		}
	});
}




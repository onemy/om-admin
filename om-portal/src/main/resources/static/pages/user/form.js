/**
 * form js
 * 
 */

$(document).ready(function () {
	initEntityForm();
});

//表单验证
$('#entityForm').validate({
	onkeyup:false,
	rules: {
      username: {
    	  required: true,
    	  minlength: 2,
          remote: {
              type: "post",
              url: host+"/api/user/checkUsernameExsit",
              data: {
                username: function() {
                    return $("#username").val();
                }
              },
              dataType: "json",
              dataFilter: function(data, type) {
            	  var result=JSON.parse(data);
                  return result.success;
              }
          }
      },
      nickname: {required: true,minlength: 2},
      password: {required: true,minlength: 6},
      orderNo: {required: true,digits: true},
      email: 	{email:true},
      mobile:	{isMobile:true}
    },
    messages: {
      username: {required: "请输入帐户名称",minlength: "长度至少2位",remote:"此帐户己经存在"},
      nickname: {required: "请输入用户昵称",minlength: "长度至少2位"},
      password: {required: "请输入密码",minlength: "长度至少6位"},
      orderNo: 	{required: "请输入排序号",digits: "必须为整数"},
      email:	{email: "请输入正确的邮箱地址"}
    }
    
});
  
 

//提交表单
var submitEntityForm = function(formWinIndex,confirmIndex){

	var requestData = FormUtils.serializeJson("entityForm");

	
	if($("#groupId").val()!=""){
		//var groups=[];
		var group={};
		group.id=$("#groupId").val();
		//groups.push(group);
		requestData.group=group;
	}
	
	if($("#roleId").val()!=""){
		var roles=[];
		
		
		
		var ids=$("#roleId").val();
		var idsArray=ids.split(",");
		for(var i in idsArray){
			var role={};
			role.id=idsArray[i];
			roles.push(role);
		}
		requestData.roles=roles;
	}
	
//	alert(JSON.stringify(requestData));
//	top.layer.closeAll('loading');
//	return;
	
	$.ajax({
		url : host+'/api/user/save',
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


//初始化表单
var initEntityForm = function(){

	var isUpdate = CommonUtils.getUrlParam("isUpdate");
 
	if(isUpdate == "true"){
		$("#username").attr("disabled","disabled");
		$("#password").attr("disabled","disabled");
		var entityId = CommonUtils.getUrlParam("entityId");
		layer.load(1, {
			shade : [ 0.2 ]
		// 透明度调整
		});
		$.ajax({
			url : host+'/api/user/getUser',
			dataType : 'json',
			type : 'post',
			async: false,
			data : 'id=' + entityId,
			success : function(result) {
				if (result.success) {
					//填充表单
					FormUtils.fillFormByData("entityForm", result.data);
					
					//填充组
					var group=result.data.group;
//					var groupIds=[];
//					var groupNames=[];
//					
//					for(var key in groups){
//						groupIds.push(groups[key].id);
//						groupNames.push(groups[key].groupName);
//					}
					$("#groupId").val(group.id);
					$("#groupName").val(group.groupName);
					
					//填充角色
					var roles=result.data.roles;
					var roleIds=[];
					var roleNames=[];
					
					for(var key in roles){
						roleIds.push(roles[key].id);
						roleNames.push(roles[key].roleName);
					}
					$("#roleId").val(roleIds.join());
					$("#roleName").val(roleNames.join());
					
					
					//置空密码
					$("#password").val("");
				}
				layer.closeAll('loading');
			}
		});
	}else{
		FormUtils.fillFormByData("entityForm", {id:''});
	}
	
};

//打开组选择窗口
$("#selectGroup").click(function() {
	var ids=$("#groupId").val();
	group_select(getGroup,ids);
});
//组回调方法
var getGroup = function(obj) {
	$("#groupId").val(obj.id);
	$("#groupName").val(obj.name);
}

//清空角色
$("#resetRole").click(function() {
	$("#roleId").val("");
	$("#roleName").val("");
});

//打开角色选择窗口
$("#selectRole").click(function() {
	var ids=$("#roleId").val();
	role_select(getRole,ids);
});

//角色回调方法
var getRole = function(obj) {
	var ids=[];
	if($("#roleId").val()!=""){
		ids=($("#roleId").val()).split(",");
	}
	var names=[];
	if($("#roleName").val()!=""){
		names=($("#roleName").val()).split(",");
	}
	
	for(var i=0;i<obj.length;i++){
		var item=obj[i];
		if($.inArray(item.id,ids)<0){
			ids.push(item.id);
			names.push(item.roleName);
		}
	}
	
	$("#roleId").val(ids.join());
	$("#roleName").val(names.join());
}

//下一窗口使用
var getRoleIds = function (){
	return $("#roleId").val();
}


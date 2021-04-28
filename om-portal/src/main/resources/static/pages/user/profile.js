$(document).ready(function () {
	var entityId=1;
	$.ajax({
		url : host+'/api/user/getInfo',
		dataType : 'json',
		type : 'get',
		async: false,
//		data : 'id=' + entityId,
		success : function(result) {
			if (result.success) {
				var user=result.data.user;
				//填充表单
				FormUtils.fillFormByData("entityForm", user);
				
				$("#p_nickname").append(user.nickname);
				$("#p_username").append(user.username);
				$("#p_mobile").append(user.mobile);
				$("#p_email").append(user.email);
				$("#p_createDate").append(user.createDate);
				
				var groups=[];
				for(var k in user.groups){
					groups.push(user.groups[k].groupName);
				}
				$("#p_group").append(groups.join());
			}
			layer.closeAll('loading');
		}
	});	
	
});



//表单验证
$('#entityForm').validate({
	onkeyup:	false,
	rules: {
		nickname:	{required: true,minlength: 2},
		password:	{required: true,minlength: 6},
		orderNo: 	{required: true,digits: true},
		email:		{email:true},
		mobile:		{isMobile:true}
	},
	messages: {
		username:	{required: "请输入帐户名称",minlength: "长度至少2位",remote:"此帐户己经存在"},
		nickname:	{required: "请输入用户昵称",minlength: "长度至少2位"},
		password:	{required: "请输入密码",minlength: "长度至少6位"},
		orderNo: 	{required: "请输入排序号",digits: "必须为整数"},
		email:		{email: "请输入正确的邮箱地址"}
	}
    
});

//表单验证2
$('#pwdForm').validate({
	onkeyup:	false,
	rules: {
		oldPassword:	{required: true},
		newPassword: 	{required: true,minlength: 6},
		newPassword2:	{required: true,equalTo : "#newPassword"}
	},
    messages: {
    	oldPassword:	{required: "请输入密码"},
		newPassword: 	{required: "请输入密码",minlength: "长度至少6位"},
		newPassword2:	{required: "请输入密码",equalTo : "必须与新密码相同"}
	}
    
});


//提交表单
var submitEntityForm = function(){
	
	if($("#entityForm").validate().form()){
		var requestData = FormUtils.serializeJson("entityForm");
		
		$.ajax({
			url : host+'/api/profile/update',
			dataType : 'json',
			contentType: "application/json;charset=utf-8",
			type : 'post',
			data : JSON.stringify(requestData),
			success : function(result) {
				if (result.success) {
					CommonUtils.notify("success","操作成功<br>","1500");
					location.reload();
				} else {
					CommonUtils.notify("error",result.msg,"4000");
					
				}
			}
		});
	}
	
}

//提交修改密码表单
var submitPwdForm = function(){
	if($("#pwdForm").validate().form()){
		var requestData = FormUtils.serializeJson("pwdForm");
		
		$.ajax({
			url : host+'/api/profile/updatePwd',
			dataType : 'json',
//			contentType: "application/json;charset=utf-8",
			type : 'post',
			data : (requestData),
			success : function(result) {
				if (result.success) {
					CommonUtils.notify("success","操作成功<br>","1500");
					//location.reload();
				} else {
					CommonUtils.notify("error",result.msg,"4000");
					
				}
			}
		});
	}

}
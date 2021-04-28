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
      groupCode: {
    	  required: true,
    	  minlength: 2,
          remote: {
              type: "post",
              url: host+"/api/group/checkGroupCodeExsit",
              data: {
            	  groupCode: function() {
                    return $("#groupCode").val();
                }
              },
              dataType: "json",
              dataFilter: function(data, type) {
            	  var result=JSON.parse(data);
                  return result.success;
              }
          }
      },
      groupName: {required: true,minlength: 2},
      orderNo: {required: true,digits: true}
    },
    messages: {
      groupCode: {required: "请输入角色编码",minlength: "长度至少2位",remote:"此编码己经存在"},
      groupName: {required: "请输入角色名称",minlength: "长度至少2位"},
      orderNo: 	{required: "请输入排序号",digits: "必须为整数"}
    }
    
});
  
 

//提交表单
var submitEntityForm = function(formWinIndex,confirmIndex){

	var requestData = FormUtils.serializeJson("entityForm");

	
//	alert(JSON.stringify(requestData));
//	top.layer.closeAll('loading');
//	return;
	
	$.ajax({
		url : host+'/api/group/save',
		dataType : 'json',
		//contentType: "application/json;charset=utf-8",
		type : 'post',
		//data : JSON.stringify(requestData),
		data : requestData,
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
		$("#groupCode").attr("disabled","disabled");

		var entityId = CommonUtils.getUrlParam("entityId");
		layer.load(1, {
			shade : [ 0.2 ]
		// 透明度调整
		});
		$.ajax({
			url : host+'/api/group/getGroup',
			dataType : 'json',
			type : 'post',
			async: false,
			data : 'id=' + entityId,
			success : function(result) {
				if (result.success) {
					//填充表单
					FormUtils.fillFormByData("entityForm", result.data);
					//填充组
					var group=result.data.parent;
					if(group!=null){
						$("#groupId").val(group.id);
						$("#groupName").val(group.groupName);						
					}
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

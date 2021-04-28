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
      code: {
    	  required: true,
    	  minlength: 2,
          remote: {
              type: "post",
              url: host+"/api/menu/checkMenuCodeExsit",
              data: {
            	  menuCode: function() {
                    return $("#menuCode").val();
                }
              },
              dataType: "json",
              dataFilter: function(data, type) {
            	  var result=JSON.parse(data);
                  return result.success;
              }
          }
      },
      name: {required: true,minlength: 2},
      orderNo: {required: true,digits: true}
    },
    messages: {
      code: {required: "请输入编码",minlength: "长度至少2位",remote:"此编码己经存在"},
      name: {required: "请输入名称",minlength: "长度至少2位"},
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
		url : host+'/api/menu/save',
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
		$("#menuCode").attr("disabled","disabled");

		var entityId = CommonUtils.getUrlParam("entityId");
		layer.load(1, {
			shade : [ 0.2 ]
		// 透明度调整
		});
		$.ajax({
			url : host+'/api/menu/getMenu',
			dataType : 'json',
			type : 'post',
			async: false,
			data : 'id=' + entityId,
			success : function(result) {
				if (result.success) {
					//填充表单
					FormUtils.fillFormByData("entityForm", result.data);
					//填充组
					var menu=result.data.parent;
					if(menu!=null){
						$("#menuId").val(menu.id);
						$("#menuName").val(menu.name);						
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
$("#selectMenu").click(function() {
	var ids=$("#menuId").val();
	menu_select(getMenu,ids);
});
//组回调方法
var getMenu = function(obj) {
	$("#menuId").val(obj.id);
	$("#menuName").val(obj.name);
}




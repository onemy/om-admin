$(document).ready(function () {

  $.ajax({
    url : 'http://pb.onemysoft.com:82/HPImageArchive.aspx?format=js&idx=0&n=1',
    type : 'get',
    dataType : 'json',
    success : function(data, status, xhr) {
      var url=data.images[0].url;
      url='https://www.bing.com'+url;
      $("body").css("background-image","url("+url+")");
      
    },
    error: function (xhr, textStatus, errorThrown) {
      //alert(xhr.status+" : "+xhr.statusText);
      CommonUtils.notify("error",xhr.statusText,"4000");
    }
  });


    $.validator.setDefaults({
      submitHandler: function () {
        var username=$("#username").val();
        var password=$("#password").val();
        var json={"username" : username,"password":password};

        $.ajax({
            url : host+'/login',
            type : 'post',
            contentType: "application/json",
            data : JSON.stringify(json),
            success : function(result, status, xhr) {

              if(result.success){
                Cookies.set('token', result.data.token);
                Cookies.set('username', result.data.username);
                window.location.href="index.html";
              }else{
            	  CommonUtils.notify("error",result.msg,"4000");
              }
            }
        });
      }
    });
    $('#loginForm').validate({
      rules: {
        username: {
          required: true
        },
        password: {
          required: true,
          minlength: 5
        }
      },
      messages: {
        username: {
          required: "请输入手机号",
          remote: "手机号己经存在"
        },
        password: {
          required: "请输入密码",
          minlength: "密码长度至少5位"
        }
      }
    });



});

var CommonUtils = {
		/**
		 * 通知方法 
		 * @param type 提示类型 {success, info, warning, error}
		 * @param msg 提示信息
		 * @param parent 通知在本页面还是顶级页面
		 */
		notify : function (type, msg, timeout) {
			top.toastr.options = {
					  "closeButton": false,
					  "debug": false,
					  "progressBar": false,
					  "positionClass": "toast-top-center",
					  "onclick": null,
					  "showDuration": "400",
					  "hideDuration": "1000",
					  "timeOut": timeout,
					  "extendedTimeOut": "1000",
					  "showEasing": "swing",
					  "hideEasing": "linear",
					  "showMethod": "fadeIn",
					  "hideMethod": "fadeOut"
			}
			top.toastr[type](msg);
		}
	}
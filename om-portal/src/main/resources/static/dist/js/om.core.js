

//全局ajax设置
$(function(){  
  $.ajaxSetup({  
      //contentType: "application/x-www-form-urlencoded;charset=utf-8",
      cache: false,
      beforeSend: function (request) {
          request.setRequestHeader("Authorization", Cookies.get('token'));
      },
      complete: function(XHR, TS){

          var resText = XHR.responseText;
          if (jQuery.isFunction(XHR.getResponseHeader)) {
            var sessionstatus = XHR.getResponseHeader("token");
            
            if (403 == XHR.status) {
              top.location.href = "/login.html";
              return;
            } else if (401 == XHR.status) {
            	//alert("您没有权限进行此项操作！");
            	CommonUtils.notify("error", '您没有权限进行此项操作！', 4000);
            	top.layer.closeAll('loading');
            	layer.closeAll('loading');
            }
          }
      }
  });
});


var getWindowSize = function() {
	return [ "Height", "Width" ].map(function(a) {
		return window["inner" + a] || document.compatMode === "CSS1Compat"
				&& document.documentElement["client" + a]
				|| document.body["client" + a]
	})
};

// 扩展Date的format方法
Date.prototype.format = function(format) {
	var o = {
		"M+" : this.getMonth() + 1,
		"d+" : this.getDate(),
		"h+" : this.getHours(),
		"m+" : this.getMinutes(),
		"s+" : this.getSeconds(),
		"q+" : Math.floor((this.getMonth() + 3) / 3),
		"S" : this.getMilliseconds()
	}
	if (/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	}
	for ( var k in o) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
					: ("00" + o[k]).substr(("" + o[k]).length));
		}
	}
	return format;
}

/**
 * 转换日期对象为日期字符串
 * 
 * @param date
 *            日期对象
 * @param isFull
 *            是否为完整的日期数据, 为true时, 格式如"2000-03-05 01:05:04" 为false时, 格式如
 *            "2000-03-05"
 * @return 符合要求的日期字符串
 */
function getSmpFormatDate(date, isFull) {
	var pattern = "";
	if (isFull == true || isFull == undefined) {
		pattern = "yyyy-MM-dd hh:mm:ss";
	} else {
		pattern = "yyyy-MM-dd";
	}
	return getFormatDate(date, pattern);
}
/**
 * 转换当前日期对象为日期字符串
 * 
 * @param date
 *            日期对象
 * @param isFull
 *            是否为完整的日期数据, 为true时, 格式如"2000-03-05 01:05:04" 为false时, 格式如
 *            "2000-03-05"
 * @return 符合要求的日期字符串
 */
function getSmpFormatNowDate(isFull) {
	return getSmpFormatDate(new Date(), isFull);
}
/**
 * 转换long值为日期字符串
 * 
 * @param l
 *            long值
 * @param isFull
 *            是否为完整的日期数据, 为true时, 格式如"2000-03-05 01:05:04" 为false时, 格式如
 *            "2000-03-05"
 * @return 符合要求的日期字符串
 */
function getSmpFormatDateByLong(l, isFull) {
	return getSmpFormatDate(new Date(l), isFull);
}
/**
 * 转换long值为日期字符串
 * 
 * @param l
 *            long值
 * @param pattern
 *            格式字符串,例如：yyyy-MM-dd hh:mm:ss
 * @return 符合要求的日期字符串
 */
function getFormatDateByLong(l, pattern) {
	return getFormatDate(new Date(l), pattern);
}
/**
 * 转换日期对象为日期字符串
 * 
 * @param l
 *            long值
 * @param pattern
 *            格式字符串,例如：yyyy-MM-dd hh:mm:ss
 * @return 符合要求的日期字符串
 */
function getFormatDate(date, pattern) {
	if (date == undefined) {
		date = new Date();
	}
	if (pattern == undefined) {
		pattern = "yyyy-MM-dd hh:mm:ss";
	}
	return date.format(pattern);
}

var formSubmit = function(formId) {
	$("#" + formId).submit();
}

var CommonUtils = {
	getUrlParam : function(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); // 构造一个含有目标参数的正则表达式对象
		var r = window.location.search.substr(1).match(reg); // 匹配目标参数
		if (r != null)
			return decodeURIComponent(r[2]);
		return null; // 返回参数值
	},
	validateUrl : function(url) {
		if (!url) 
			return false;
		var check = "((http|ftp|https)://)(([a-zA-Z0-9\._-]+\.[a-zA-Z]{2,6})|([0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}))(:[0-9]{1,4})*(/[a-zA-Z0-9\&%_\./-~-]*)?";
		var re = new RegExp(check);
		return re.test(url);
	},
	/**
	 * 对象转url参数()
	 * @example {name:'tom','class':{className:'class1'},classMates:[{name:'lily'}]}
	 * @param param
	 * @param key
	 * @param encode 
	 * @returns {String} url参数字符串
	 */
	urlEncode : function(param, key, encode) {
		if (!param) return '';
		var paramStr = "";
		var t = typeof (param);
		if (t == "string" || t == "number" || t == "boolean") {
			paramStr += '&' + key + '=' + ((encode==null||encode) ? encodeURIComponent(param) : param);
		} else {
			for (var i in param) {
				var k = key == null ? i : key + (param instanceof Array ? '[' + i + ']' : '.' + i);
				paramStr += CommonUtils.urlEncode(param[i], k, encode);
			}
		}
		return paramStr;
	},
	/**
	 * 通知方法 
	 * @param type 提示类型 {success, info, warning, error}
	 * @param msg 提示信息
	 * @param parent 通知在本页面还是顶级页面（未实现）
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
	},
	/**
	 * 针对一个具体的URL，判断其是否能正常被响应
	 */
	openUrl	: function(url){
		 $.ajax({
			  url: url,
			  type: 'GET',
			  complete: function(response) {
			   if(response.status == 200) {
			   	   return true;
			   } else {
				   return false;
			   }
			  }
		});
	},
	/**
	 * 去空格
	 * @param str 需处理字符串
	 * @param is_global 是否去掉全部空格
	 */
	trim : function(str, is_global) {
		var result;
	    result = str.replace(/(^\s+)|(\s+$)/g,"");
	    if(is_global)
	    {
	        result = result.replace(/\s/g,"");
	     }
	    return result;
	},
	flowSize : function(size) {
		if (!isNaN(size)) {
			var t = Number(size);
			
			//如果字节数少于1024，则直接以B为单位，否则先除于1024，后3位因太少无意义  
			if (t < 1024) {
				return t + "B";
			} else {
				t = (t / 1024).toFixed(2);
			}
			//如果原字节数除于1024之后，少于1024，则可以直接以KB作为单位  
		    //因为还没有到达要使用另一个单位的时候  
		    //接下去以此类推  
			if (t < 1024) {
				return t + "KB";
			} else {
				t = (t / 1024).toFixed(2);
			}
			if (t < 1024) {  
		        //因为如果以MB为单位的话，要保留最后1位小数，  
		        //因此，把此数乘以100之后再取余  
		        t = t * 100;  
		        return (t / 100) + "."  
		                + (t % 100) + "MB";  
		    } else {  
		        //否则如果要以GB为单位的，先除于1024再作同样的处理  
		        t = t * 100 / 1024;  
		        return (t / 100) + "."  
		                + (t % 100) + "GB";  
		    }
		}
	}
}



var dtOptions = {
		deleteItemFromGPGrid : function(deleteUrl, gridId, updatebtn) {
			var ids = $("#" + gridId).DataTable().fnGetSelectedIds();
			if(ids == null || ids == ""){
				layer.tips('您未选中需要删除的记录，请选择！', updatebtn, {
				  tips: [2, '#18a689'],
				  time: 2000
				});
			}else{
				var confirmId = layer.confirm('<small>您确定要执行此操作吗?</small>', {
					title : '<small>系统提示</small>',
					closeBtn : 0,
					icon:0,
					offset : '180px',
					btn : [ '确定', '取消' ]
				}, function() {
					layer.load(1, {
						shade : [ 0.2 ]
					});
					
					$.ajax({
						url : deleteUrl,
						dataType : 'json',
						type : 'post',
						data : {
							"ids":ids,
						},
						success : function(data) {
							if (data.success) {
								CommonUtils.notify("success", "操作成功", 1500);
								$("#" + gridId).DataTable().fnSearch(false);
							} else {
								CommonUtils.notify("error", data.responseMessage, 4000);
							}
							layer.closeAll('loading');
							layer.close(confirmId);
						}
					});
				});
			}
		},
		deleteItemFromGPGrid4Single : function(deleteUrl, gridId, updatebtn, callback){
			var ids = $("#" + gridId).DataTable().fnGetSelectedIds();
			
			if(ids == null || ids == ""){
				layer.tips('您未选中需要删除的记录，请选择！', updatebtn, {
				  tips: [2, '#18a689'],
				  time: 2000
				});
			} else if (ids.length > 1) {
				layer.tips('删除时只能选择单条数据进行操作！', updatebtn, {
					  tips: [2, '#18a689'],
					  time: 2000
					});
			} else {
				var confirmId = layer.confirm('<small>您确定要执行此操作吗?</small>', {
					title : '<small>系统提示</small>',
					closeBtn : 0,
					icon:0,
					offset : '180px',
					btn : [ '确定', '取消' ]
				}, function() {
					layer.load(1, {
						shade : [ 0.2 ]
					});
					
					$.ajax({
						url : deleteUrl,
						dataType : 'json',
						type : 'post',
						data : {
							"ids":ids[0],
						},
						success : function(data) {
							if (data.success) {
								CommonUtils.notify("success", "操作成功", 1500);
								if (typeof callback == "function")
									new callback();
								$("#" + gridId).DataTable().fnSearch(false);
							} else {
								CommonUtils.notify("error", data.responseMessage, 4000);
							}
							layer.closeAll('loading');
							layer.close(confirmId);
						}
					});
				});
			}
		},
		reload : function (gridId) {
			$('#' + gridId).DataTable().draw(false);
		},
		delete4Self : function (success) {
			var confirmId = layer.confirm('<small>您确定要执行此操作吗?</small>', {
				title : '<small>系统提示</small>',
				closeBtn : 0,
				icon:0,
				offset : '180px',
				btn : [ '确定', '取消' ]
			}, function(index){
				success(index);
			});
		},
		lookup : function(entityId, itemDetailUrl,lookupName,w,h) {
			top.layer.open({
				type : 2,
				title : FORM_TITLE_PRE + lookupName,
				closeBtn : 1, // 不显示关闭按钮
				shadeClose : false,
				shade : false,
				scrollbar: false,
				maxmin : true, // 开启最大化最小化按钮
				area : [ ''+w+'%', ''+h+'%' ],
				shade : [ 0.3 ],
				content : itemDetailUrl + "?isUpdate=true&lookup=true&entityId=" + entityId + '&v=' + version,
				btn : [ '关闭' ],
				yes : function(index,layero) {
					top.layer.close(index);
				},
				cancel: function(){ 
				    //右上角关闭回调
					DxsAppIsRefresh = false;
				},
				success : function(layero, index) {
					DxsAppIframeWin = window[layero.find('iframe')[0]['name']]; // 得到iframe页的窗口对象，执行iframe页的方法：DxsAppIframeWin.method()
				},
				end : function(index) {
					top.layer.closeAll('loading');
				}
			});
		}
}


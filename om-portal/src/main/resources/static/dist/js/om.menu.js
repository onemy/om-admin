window.onload = function(){
	/**
	* name是菜单显示的文本内容
	* icon 是菜单图片
	* lable 是菜单右侧显示的提示文本，最大长度为3
	* active ,值为active；有该值，则默认选中该项，高亮
	* open，菜单是否展开
	*/
	var Menu = [{
					name: "用户管理",
					icon: "fas fa-user",
					lable:"新",
					url:"pages/user/user.html"
				},{
					name: "菜单管理",
					icon: "fas fa-tasks",
					url:"pages/menu/menu.html"
				},
				{
					name: "角色管理",
					icon: "fas fa-users",
					url:"#"
				},
				{
					name: "日志管理",
					icon: "fas fa-edit",
					lable:"8",
					url:"#"
				},
				{
					name: "层级 1",
					icon: "fas fa-circle",
					url: "#",
					children: [
						{
							name: "层级 2.1",
							icon: "far fa-circle",
							url:"#"
						},
						{
							name: "层级 2.2",
							icon: "far fa-circle",
							url:"#"
						},
						{
							name: "层级 2.3",
							icon: "far fa-circle",
							url: "#",
							children: [
								{
									name: "层级 3.1",
									icon: "far fa-dot-circle",
									url:"#"
								},
								{
									name: "层级 3.2",
									icon: "far fa-dot-circle",
									url:"#"
								}
							]
						}
					]
				}
			];
	
	$.ajax({
	    url : host+'/api/menu/userTree',
	    type : 'post',
	    beforeSend: function (request) {
	        request.setRequestHeader("Authorization", Cookies.get('token'));
	    },	    
	    success : function(json, status, xhr) {
	    	//构建菜单
	    	menuTree(json.data,document.getElementById('menutree'));
	    }
	});

}

/*
 * @构建树形菜单
 * @arrJson:json数据
 * @container:菜单容器
 */
function menuTree(jsonArr,container){
    for(var i = 0 ;i < jsonArr.length; i++){
        var oLi = document.createElement('li');
        var onClick = jsonArr[i].children ? "rootOnclick(this);" : "leafOnclick(this);" ;
		var rigtxt = jsonArr[i].lable || "";
		rigtxt= rigtxt.length>3? rigtxt.substr(0,3) : rigtxt;
		var url = jsonArr[i].url || "";
		var active = jsonArr[i].active || "";
		var inHt ='<a href="'+jsonArr[i].url+'" class="nav-link '+active+'"><i class="nav-icon '+jsonArr[i].icon+'"></i>'; 

        if(jsonArr[i].children && jsonArr[i].children.length>0){//遇到是某级别根节点
			var open = jsonArr[i].open || ""; 
			$(oLi).addClass("nav-item has-treeview "+open);
			if(rigtxt==""){ inHt +='<p>'+jsonArr[i].name+'<i class="right fas fa-angle-left"></i></p></a>';
			}else{ inHt +='<p>'+jsonArr[i].name+'<span class="right badge badge-danger" style="margin-right:30px">'+rigtxt+'</span><i class="right fas fa-angle-left"></i></p></a>'; }
            oLi.innerHTML =inHt; 
			var oUl = document.createElement('ul');
			$(oUl).addClass("nav nav-treeview ");
			oLi.appendChild(oUl);
            menuTree(jsonArr[i].children,oUl);//递归构建子菜单
        }else{
			$(oLi).addClass("nav-item");
			if(rigtxt==""){ inHt +='<p>'+jsonArr[i].name+'</p></a>';
			}else{ inHt +='<p>'+jsonArr[i].name+'<span class="right badge badge-danger" >'+rigtxt+'</span></p></a>'; }
            oLi.innerHTML = inHt;
        }
        container.appendChild(oLi);
    }
}
function rootOnclick(){
	
}
function leafOnclick(e){
	//1.取消其它所有a标签选中态
	$("#menutree").find('a.active').each(function(){
		$(this).removeClass("active");
	});
	//2.设置父节点选中状态
	$(e).parentsUntil("#menutree","li.has-treeview").each(function(){//选中该元素的所有父根treeview节点
		$(this).children().first().addClass("active");
	});
	//3.给自己添加选中态
	$(e).addClass("active");
	//4.折叠其它treeview	
	$("#menutree").children("li.has-treeview.menu-open").each(function(){//children只寻找一层，即只查找儿子元素
		if($(this).find("a.active").length==0){ //find会查找所有子元素，包括孙子曾孙等
			$(this).children().first().click();
		}
	});
	//5.加载网页
	var url = $(e).data("url");
	console.log(url); //要跳转的url
	$("#iframeMain").attr("src",url);
}


$(function(){
	var vue = new Vue({
		el: "#index",
		data :{
			loginStatus:true, //默认显示登录
			registerStatus:false,
			mainStatus : false,
			updateStatus:false,
			addStatus:false,
			
			u_id:'',
			u_email:'',
			u_username:'',
			u_password:'',
			u_password2:'',//重新输入密码
			
			s_username:'',
			
			currentuser:'',//当前用户
			users :null  //用户列表
		},
		router:new VueRouter({path:'/loginok'}),
		methods:{
			//注册按钮-显示注册表单
			showRegister: function(){
				document.getElementById("registerForm").reset();//重置表单
				this.registerStatus = true;
				this.loginStatus = false;
			},
			//登录按钮-显示登录表单
			showLogin:function(){
				document.getElementById("loginForm").reset();//重置表单
				this.loginStatus = true;
				this.registerStatus = false;
			},
			//登录
			login: function(){
				var username = $("#username").val();
				var password = $("#password").val();
				if(username =='' || username == null){
					alert("请输入用户名");
					return false;
				}
				if(password =='' || password == null){
					alert("请输入密码");
					return false;
				}
				
				var url = '/springmvc/login?username='+username+'&password='+password;
				this.$http.post(url).then(function(response){
					var flag = response.body.flag;
					if(flag){
						this.mainStatus = true;
						this.loginStatus = false;
						vue.currentuser = response.body.returnMsg.username;
						
						$.ajax({
							url :"/springmvc/user/getAll",
							type:"post",
							success : function(result){
								vue.users=result;
							}
						});
					}else{
						alert("登录失败");
					}
				});
			},
			//退出
			logout:function(){
				var url = '/springmvc/logout';
				$.ajax({
					url :url,
					type:"post",
					success : function(result){
						if(result){
							alert("已退出");
							vue.loginStatus = true;
							vue.mainStatus = false;
						}else{
							alert("退出失败");
						}
					}
				});
			},
			//注册
			register: function(){
				if(vue.u_username == ""){
					alert("用户名不能为空");
					return false;
				}
				if(vue.u_email == ""){
					alert("邮箱不能为空");
					return false;
				}else{
					var flag = regEmail(vue.u_email);
					if(!flag){
						alert("邮箱格式错误");
						return false;
					}
				}
				if(vue.u_password == ""){
					alert("密码不能为空");
					return false;
				}
				if(vue.u_password != vue.u_password2){
					alert("密码不一致");
					return false;
				}
				
				var url = '/springmvc/user/register?email='+vue.u_email+'&username='+vue.u_username+'&password='+vue.u_password;
				$.ajax({
					url :url,
					type:"post",
					success : function(result){
						if(result.flag){
							alert("注册成功");
							vue.loginStatus = true;
							vue.registerStatus = false;
						}else{
							alert(result.returnMsg);
						}
					}
				});
			},
			//查询
			searchUser : function(){
				var url = '/springmvc/user/getUserByUsername?username='+vue.s_username;
				$.ajax({
					url :url,
					type:"post",
					success : function(result){
						vue.users=result;
					}
				});
			},
			//新增
			addUser: function(){
				this.mainStatus = false;
				this.addStatus = true;
			},
			doAddUser:function(){
				if(vue.u_email == ""){
					alert("邮箱不能为空");
					return false;
				}else{
					var flag = regEmail(vue.u_email);
					if(!flag){
						alert("邮箱格式错误");
						return false;
					}
				}
				if(vue.u_username == ""){
					alert("用户名不能为空");
				}
				if(vue.password == ""){
					alert("密码不能为空");
				}
				
				var url = '/springmvc/user/register?email='+vue.u_email+'&username='+vue.u_username+'&password='+vue.u_password;
				$.ajax({
					url :url,
					type:"post",
					success : function(result){
						if(result.flag){
							alert("新增成功");
							
							vue.mainStatus = true;
							vue.addStatus = false;
							//进行更新数据???其他方法
							$.ajax({
								url :"/springmvc/user/getAll",
								type:"post",
								success : function(result){
									vue.users=result;
								}
							});
						}else{
							alert(result.returnMsg);
						}
					}
				});
			},
			cancleAdd:function(){
				this.mainStatus = true;
				this.addStatus = false;
			},
			//编辑用户
			editUser : function(id){
				this.mainStatus = false;
				this.updateStatus = true;
				//先获取数据并展示
				var url = '/springmvc/user/getUserById?id='+id;
				$.ajax({
					url :url,
					type:"post",
					success : function(result){
						vue.u_email = result.email;
						vue.u_username = result.username;
						
						vue.u_id = result.id;
//						vue.u_password = result.password;
					}
				});
			},
			//更新用户
			updateUser:function(){
				var id = vue.u_id ;
				var email = vue.u_email;
				var username = vue.u_username;
				var password = vue.u_password;
				
				if(vue.u_email == ""){
					alert("邮箱不能为空");
					return false;
				}else{
					var flag = regEmail(vue.u_email);
					if(!flag){
						alert("邮箱格式错误");
						return false;
					}
				}
				
				var url='/springmvc/user/update?id='+id+'&username='+username+'&password='+password+'&email='+email;
				$.ajax({
					url :url,
					type:"post",
					success : function(result){
						if(result){
							//修改登录账户的密码-需要重新登录
							if(vue.u_password != "" && vue.u_username == vue.currentuser){
								alert("请重新登录")
								vue.updateStatus = false;
								vue.loginStatus=true;
							}else{
								alert("更新成功")
								vue.mainStatus = true;
								vue.updateStatus = false;
								//进行更新数据???其他方法
								$.ajax({
									url :"/springmvc/user/getAll",
									type:"post",
									success : function(result){
										vue.users=result;
									}
								});
							}
							
						}else{
							alert("更新失败")
						}
					}
				});
				
			},
			cancleUpdate :function(){
				this.updateStatus = false;
				this.mainStatus = true;
			},
			deleteUser:function(id,username){
				
				if(username == vue.currentuser){
					alert("不可删除当前用户");
					return false;
				}
				
				var url = '/springmvc/user/delete?id='+id;
				$.ajax({
					url :url,
					type:"post",
					success : function(result){
						if(result.flag){
							alert("删除成功")
							//进行更新数据???其他方法
							$.ajax({
								url :"/springmvc/user/getAll",
								type:"post",
								success : function(result){
									vue.users=result;
								}
							});
						}else{
							alert("删除失败")
						}
					}
				});
			},
			//点击登录或注册框阻止事件冒泡
	        stopProp: function(e) {
	            e = e || event;
	            e.stopPropagation();
	        },
		}
	});
});
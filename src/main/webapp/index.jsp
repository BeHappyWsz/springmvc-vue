<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">	
	<%
	    String path = request.getContextPath();
	    String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
	%>
	<base href="<%=basePath%>" />
	<title>登录注册页面</title>
</head>
<body>
	<div id="index">
		<!-- 登录开始 -->
		<div class="loginbox" v-show="loginStatus" style="display: none;">
			<div class="box" @click="stopProp()">
				<h3>用户登录</h3>
				<form id="loginForm" name="login" @submit.prevent="login">
					<input id="username" type="text" placeholder="输入用户名" >
					<input id="password" type="password" placeholder="输入登录密码">
					<input type="submit" value="立即登录">
                    <input type="button" value="立即注册" @click="showRegister()">
				</form>
			</div>
		</div>
		
		<!-- 注册 -->
		<div class="registerbox" v-show="registerStatus" style="display: none;">
			<div class="box" @click="stopProp()">
				<h3>用户注册</h3>
				<form id="registerForm" name="register" @submit.prevent="register">
					<input  v-model="u_id" type="hidden">
					<input  v-model="u_username"  placeholder="输入用户名"><br>
					<input  v-model="u_email"     placeholder="输入邮箱" ><br>
					<input  v-model="u_password"  placeholder="输入登录密码" type="password"><br>
					<input  v-model="u_password2" placeholder="重新输入密码" type="password"><br>
                    <input type="submit" value="立即注册" >
					<input type="button" value="立即登录" @click="showLogin()">
				</form>
			</div>
		</div>
		
		<!-- 主页面 -->
		<div class="mainbox" v-show="mainStatus" >
			<div class="box" @click="stopProp()">
				<h3>用户列表</h3>
				<table class="searchbar">
					<tr>
						<td style="width: 200px;"><input v-model="s_username" placeholder="查询用户"/></td>
						<td><input class="searchBtn" type="button" value="查询" @click="searchUser()"></td>
						<td><input class="searchBtn" type="button" value="新增" @click="addUser()"></td>
						<td><input class="searchBtn" type="button" value="退出" @click="logout()"></td>
						<td style="">当前用户：{{currentuser}}</span></td>
					</tr>
				</table>
				<table class="showList">
					<thead>
						<tr>
							<th>标识</th>
							<th>用户</th>
							<th>密码</th>
							<th>邮箱</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<tr v-for="user in users">
							<td>{{user.id}}</td>
							<td>{{user.username}}</td>
							<td>{{user.password}}</td>
							<td>{{user.email}}</td>
							<td>
								<input class="editBtn" type="button" value="编辑" @click="editUser(user.id)">
								<input class="editBtn" type="button" value="删除" @click="deleteUser(user.id,user.username)">
							</td>
						</tr>
					</tbody>
				</table>
				<div class="pagination">
					<ul>
						<li class="disablepage"><input class="preBtn" type="button" value="上一页" @click="preBtn()"></li>
						<li>第{{currentPage}}页/共{{totalPage}}页</li>
						<li class="nextPage"><input class="nextBtn" type="button" value="下一页" @click="nextBtn()"></li>
					</ul>
				</div>
			</div>
		</div>
		
		<!-- 更新页面 -->
		<div class="updatebox" v-show="updateStatus">
			<div class="box" @click="stopProp()">
				<h3>用户更新</h3>
				<form id="updateForm" name="updateForm">
					<input v-model="u_id" type="hidden">
					<input v-model="u_username" type="hidden">
					<label>邮箱（为空不修改）:</label><input v-model="u_email"    placeholder="输入邮箱" ><br>
					<label>密码（为空不修改）:</label><input v-model="u_password" placeholder="输入登录密码"><br>
		            <input type="button" value="更新" @click="updateUser()">
					<input type="button" value="取消" @click="cancleUpdate()">
				</form>
			</div>
		</div>
		
		<!-- 新增用户 -->
		<div class="addbox" v-show="addStatus">
			<div class="box" @click="stopProp()">
				<h3>用户新增</h3>
				<form id="addForm" name="addForm">
					<input v-model="u_id" type="hidden">
					<label>用户:</label><input v-model="u_username"  placeholder="输入用户名"><br>
					<label>邮箱:</label><input v-model="u_email" placeholder="输入邮箱" ><br>
					<label>密码:</label><input v-model="u_password" placeholder="输入登录密码"><br>
		            <input type="button" value="新增" @click="doAddUser()">
					<input type="button" value="取消" @click="cancleAdd()">
				</form>
			</div>
		</div>
	</div>
	<!--引入vue jquery-->
	<script src="https://cdn.bootcss.com/vue/2.4.2/vue.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/vue-resource@1.5.0"></script>
	<script src="//cdn.bootcss.com/vue-router/2.6.0/vue-router.js"></script> 
	<script src="js/common/jquery-3.3.1.min.js"></script>
	
	<script type="text/javascript" src="js/utils.js"></script>
	<script type="text/javascript" src="js/index.js"></script>
	<link   type="text/css" href="css/index.css" rel="stylesheet"/>
</body>
</html>
package springmvc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import springmvc.common.Pagation;
import springmvc.common.Result;
import springmvc.entity.User;
import springmvc.service.UserService;

/**
 * 用户管理controller-未控制post/get
*@author  wsz
*@createdTime 2018年3月16日
*/
@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@ResponseBody
	@RequestMapping(value ="/register")
	public Result register(User user) {
		return userService.register(user);
	}
	
	@ResponseBody
	@RequestMapping(value="/delete")
	public Result deleteUser(String id) {
		boolean flag = userService.deleteUserById(id);
		if(flag) {
			return new Result(true, "删除成功");
		}else {
			return new Result(false, "删除失败");
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/update")
	public boolean updateUser(User user) {
		return userService.updateUser(user);
	}
	
	@ResponseBody
	@RequestMapping(value="/page")
	public Pagation<User> page(String username,int currentPage) {
		if(currentPage < 1) 
			currentPage = 1;
		List<User> list = null;
		if(StringUtils.isEmpty(username)) {
			list = userService.getAllUser();
		}else {
			list = userService.getUserByName(username);
		}
		return new Pagation<User>(currentPage, 5, list);
	}
	
	@ResponseBody
	@RequestMapping(value="/updatePwd")
	public boolean updatePwd(String id,String password) {
		return userService.updatePwd(id, password);
	}
	
	@ResponseBody
	@RequestMapping(value="/getUserById")
	public User getUserById(String id) {
		return userService.getUserById(id);
	}
	
}

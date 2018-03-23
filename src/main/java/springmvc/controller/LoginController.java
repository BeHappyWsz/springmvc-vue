package springmvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import springmvc.common.Result;
import springmvc.entity.User;
import springmvc.service.UserService;

/**
 * 用户登录controller
*@author  wsz
*@createdTime 2018年3月16日
*/
@Controller
@RequestMapping("")
public class LoginController {

	@Autowired
	private UserService userService;
	
	@ResponseBody
	@RequestMapping("/login")
	public Result login(User user,HttpServletRequest request,HttpServletResponse response) {
		User flag = userService.login(user);
		if(null != flag) {
			//true保证session如果为null则新建一个
			request.getSession(true).setAttribute("user", flag);
			request.setAttribute("userList", userService.getAllUser());
			return new Result(true, flag);
		}
		return new Result(false, "账号不正确");
	}
	
	@ResponseBody
	@RequestMapping(value="/loginok")
	public ModelAndView loginok() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("main");
		return mav;
	}
	
	@ResponseBody
	@RequestMapping(value="/logout")
	public boolean logout(HttpServletRequest request,HttpServletResponse response) {
		try {
			request.getSession().removeAttribute("user");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}

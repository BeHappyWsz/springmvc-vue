package springmvc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import springmvc.common.Result;
import springmvc.dao.UserDao;
import springmvc.entity.User;
import springmvc.service.UserService;

/**
*用户管理service层实现类
*@author  wsz
*@createdTime 2018年3月16日
*/
@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserDao userDao;
	
	public User login(User user) {
		User flag = userDao.login(user);
		return flag;
	}

	public Result register(User user) {
		List<User> flag = userDao.getUserByName(user.getUsername());
		if(flag.isEmpty()) {
			userDao.addUser(user);
			return new Result(true,"注册成功");
		}
		return new Result(false,"用户名已存在");
	}

	public User getUserById(String id) {
		return userDao.getUserById(id);
	}

	public boolean deleteUserById(String id) {
		User userById = userDao.getUserById(id);
		if(null != userById) {
			return userDao.deleteUser(id);
		}
		return false;
	}

	public List<User> getAllUser() {
		return userDao.getAllUser();
	}

	public boolean updateUser(User user) {
		return userDao.updateUser(user);
	}

	public boolean updatePwd(String id, String password) {
		User user = userDao.getUserById(id);
		user.setPassword(password);
		return userDao.updateUser(user);
	}

	public List<User> getUserByName(String username) {
		return userDao.getUserByName(username);
	}
	
}

package springmvc.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import springmvc.common.FileUtils;
import springmvc.dao.UserDao;
import springmvc.entity.User;

/**
 * 操作txt文件实现类
*@author  wsz
*@createdTime 2018年3月16日
*/
@Repository
public class UserDaoImpl implements UserDao{

	@Autowired
	private FileUtils utils;
	
	public User login(User user) {
		return utils.login(user);
	}

	public boolean addUser(User user) {
		List<User> list = new ArrayList<User>();
		list.add(user);
		int maxId = utils.getMaxId();
		user.setId(maxId+1);
		boolean flag = utils.insertUser(list, "");
		return flag;
	}

	public boolean updateUser(User user) {
		return utils.updateUser(user);
	}

	public boolean deleteUser(String uid) {
		return utils.deleteUser(uid);
	}

	public List<User> getAllUser() {
		return utils.getUsers();
	}

	public User getUserById(String id) {
		return utils.getById(id);
	}

	public List<User> getUserByName(String name) {
		return utils.getByName(name);
	}

	/**
	 * 批量删除用户，暂未实现
	 */
	public boolean deleteUsers(List<String> ids) {
		return false;
	}

}

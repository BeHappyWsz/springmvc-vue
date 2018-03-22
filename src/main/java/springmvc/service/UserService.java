package springmvc.service;

import java.util.List;

import springmvc.common.Result;
import springmvc.entity.User;

/**
*@author  wsz
*@createdTime 2018年3月16日
*/
public interface UserService {

	/**
	 * 用户登录
	 * @param user
	 * @return
	 */
	User login(User user);
	
	/**
	 * 用户注册
	 * @param user
	 * @return
	 */
	Result register(User user);
	
	/**
	 * 根据id获取用户
	 * @param id
	 * @return
	 */
	User getUserById(String id);
	
	/**
	 * 获取所有注册的用户
	 * @return
	 */
	List<User> getAllUser();
	
	/**
	 * 更新用户
	 * @param user
	 * @return
	 */
	boolean updateUser(User user);
	
	/**
	 * 根据id删除用户
	 * @param id
	 * @return
	 */
	boolean deleteUserById(String id);
	
	/**
	 * 根据username获取用户
	 * @param username
	 * @return
	 */
	List<User> getUserByName(String username);
	
	/**
	 * 更新用户密码
	 * @param id
	 * @param password
	 * @return
	 */
	boolean updatePwd(String id,String password);
}

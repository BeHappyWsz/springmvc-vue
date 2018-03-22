package springmvc.dao;

import java.util.List;

import springmvc.entity.User;

/**
* DAO层接口
*@author  wsz
*@createdTime 2018年3月16日
*/
public interface UserDao {

	/**
	 * 用户登录
	 * @param user
	 * @return
	 */
	User login(User user);
	
	/**
	 * 根据id获取用户
	 * @param id
	 * @return
	 */
	User getUserById(String id);
	
	/**
	 * 新增/注册用户
	 * @param user
	 * @return
	 */
	boolean addUser(User user);
	
	/**
	 * 更新用户
	 * @param user
	 * @return
	 */
	boolean updateUser(User user);
	
	/**
	 * 根据id删除用户
	 * @param uid
	 * @return
	 */
	boolean deleteUser(String uid);
	
	/**
	 * 根据id批量删除用户
	 * @param ids
	 * @return
	 */
	boolean deleteUsers(List<String> ids);
	
	/**
	 * 获取所有用户
	 * @return
	 */
	List<User> getAllUser();
	
	/**
	 * 根据username获取用户
	 * @param name
	 * @return
	 */
	List<User> getUserByName(String name);
	
}

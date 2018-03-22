package springmvc.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import springmvc.entity.User;
/**
 * 操作txt文件方法类
 * @author wsz
 * @date 2018年3月16日
 */
@Component
public class FileUtils {
	
	private static final String URL = "d:\\user.txt";
	
	private static final String TEMP = "d:\\temp.txt";
	/**
	 * 
	 */
	public int getMaxId() {
		List<User> users = getUsers();
		int id = 0;
		for(User u : users) {
			if(u.getId() > 0)
				id= u.getId();
		}
		return id;
	}
	/**
	 * 获取所用注册的用户
	 * @return
	 */
	public List<User> getUsers(){
		List<User> list = new ArrayList<User>();
		File file = null;
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			file = new File(URL);
			if(!file.exists()) {
				file.createNewFile();
			}
			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis,"UTF-8");
			br  = new BufferedReader(isr);
			String info = null;
			while((info = br.readLine()) != null) {
				User temp = new User();
				String[] split = info.split(" ");//默认已空格隔开
				temp.setId(Integer.valueOf(split[0]));
				temp.setEmail(split[1]);
				temp.setUsername(split[2]);
				temp.setPassword(split[3]);
				list.add(temp);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if(null != br)
					br.close();
				if(null != isr)
					isr.close();
				if(null != fis)
					fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	/**
	 * 插入用户数据
	 * @param user
	 * @param fileName 默认为空，使用中间表时不为空
	 * @return
	 */
	public boolean insertUser(List<User> user,String fileName) {
		File file = null;
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			if(!"".equals(fileName)) {//判断是否建立中间表
				file = new File(fileName);
			}else {
				file = new File(URL);
			}
			if(!file.exists()) {
				file.createNewFile();
			}
			fw = new FileWriter(file,true);//默认追加每行数据
			bw = new BufferedWriter(fw);
			for(User u : user) {//遍历数据并写入txt文件
				bw.write(u.getId()+" "+u.getEmail()+" "+u.getUsername()+" "+u.getPassword());
				bw.newLine();
			}
			bw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if(null != bw)
					bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
	/**
	 * 删除某一行数据：采用中间文件形式
	 * 1.获取不用删除的数据列表list
	 * 2.把list重新写入新的temp.txt文件中
	 * 3.删除原user.txt文件
	 * 4.temp.txt文件重新命名为user.txt
	 * @param list
	 * @return
	 */
	public boolean deleteUser(String uid) {
		File file = null;
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			file = new File(URL);
			if(!file.exists()) {
				file.createNewFile();
			}
			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis,"UTF-8");
			br = new BufferedReader(isr);
			
			List<User> list = new ArrayList<User>();
			String info = null;
			while((info = br.readLine())!= null) {
				String[] split = info.split(" ");
				String id = split[0];
				if(!uid.equals(id)) {
					User temp = new User();
					temp.setId(Integer.valueOf(split[0]));
					temp.setEmail(split[1]);
					temp.setUsername(split[2]);
					temp.setPassword(split[3]);
					list.add(temp);
				}
			}
			br.close();//提前关闭
			
			insertUser(list, TEMP);//获取不需删除的数据并插入新的temp.txt文件中
			if(!file.delete()) {
				System.out.println("删除原user.txt文件失败");
				return false;
			}
			
			File tempFile = new File(TEMP);//重新获取temp.txt文件并命名为user.txt
			if(!tempFile.renameTo(new File(URL))) {
				System.out.println("无法重新命名中间文件");
				return false;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if(null != br)
					br.close();
				if(null != isr)
					isr.close();
				if(null != fis)
					fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
	/**
	 * 更新某一行用户信息
	 * @param user
	 * @return
	 */
	public boolean updateUser(User user) {
		File file = null;
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			file = new File(URL);
			if(!file.exists()) {
				file.createNewFile();
			}
			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis,"UTF-8");
			br = new BufferedReader(isr);
			
			List<User> list = new ArrayList<User>();
			
			String info = null;
			while((info = br.readLine())!= null) {
				String[] split = info.split(" ");
				String id = split[0];
				User temp = new User();
				temp.setId(Integer.valueOf(split[0]));
				temp.setEmail(split[1]);
				temp.setUsername(split[2]);
				temp.setPassword(split[3]);
				if(id.equals(String.valueOf(user.getId()))) {//查找并更新数据
					if(!StringUtils.isEmpty(user.getEmail()))
						temp.setEmail(user.getEmail());
					if(!StringUtils.isEmpty(user.getUsername()))
						temp.setUsername(user.getUsername());
					if(!StringUtils.isEmpty(user.getPassword()))
						temp.setPassword(user.getPassword());
				}
				list.add(temp);
			}
			//提前关闭
			br.close();
			insertUser(list, TEMP);//重新写入中间文件temp.txt
			if(!file.delete()) {
				System.out.println("无法删除user.txt文件");
				return false;
			}
			File tempFile = new File(TEMP);
			if(!tempFile.renameTo(new File(URL))) {
				System.out.println("temp.txt文件无法重新命名");
				return false;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if(null != br)
					br.close();
				if(null != isr)
					isr.close();
				if(null != fis)
					fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
	/**
	 * 根据id获取用户数据
	 * 1.先获取所有用户信息
	 * 2.遍历并查找
	 * @param id
	 * @return
	 */
	public User getById(String id){
		List<User> users = this.getUsers();
		for (User user : users) {
			if(id.equals(String.valueOf(user.getId())))
				return user;
		}
		return null;
	}
	
	/**
	 * 根据username查找用户数据
	 * 1.先获取所有用户信息
	 * 2.遍历并查找
	 * @param name
	 * @return
	 */
	public List<User> getByName(String name){
		List<User> list = new ArrayList<User>();
		List<User> users = this.getUsers();
		for (User user : users) {
			if(user.getUsername().contains(name))
				list.add(user);
		}
		return list;
	}
	
	/**
	 * 登录
	 * @param user
	 * @return
	 */
	public User login(User user) {
		List<User> users = this.getUsers();
		for (User u : users) {
			if(u.getUsername().equals(user.getUsername()) && u.getPassword().equals(user.getPassword()))
				return u;
		}
		return null;
	}
	
	
	public void get() {
		List<User> users = this.getUsers();
		System.out.println(users);
	}
	
	public void add() {
		FileUtils utils = new FileUtils();
		List<User> list = new ArrayList<User>();
		for(int i=0;i<100;i++) {
			list.add(new User(i,i+"",i+"",i+""));
		}
		utils.insertUser(list,"");
	}
	
	public void delete() {
		FileUtils utils = new FileUtils();
		boolean flag = utils.deleteUser("0");
		System.out.println(flag);
	}
	
	public void update() {
		FileUtils utils = new FileUtils();
		boolean flag = utils.updateUser(new User(1,"1111","1111","11111"));
		System.out.println(flag);
	}
	
	public void getById() {
		FileUtils utils = new FileUtils();
		User flag = utils.getById("50");
		System.out.println(flag);
	}
	
	public void getByName() {
		FileUtils utils = new FileUtils();
		List<User> byName = utils.getByName("80");
		System.out.println(byName);
	}
	
	public void testLogin() {
		FileUtils utils = new FileUtils();
		User login = utils.login(new User("88","88"));
		System.out.println(login);
	}
}

package springmvc.common;

import java.util.ArrayList;
import java.util.List;

/**
*分页工具类 
*@author  wsz
*@param <T>
*@createdTime 2018年3月20日
*/
public class Pagation<T> {
	/**
	 * 总数量
	 * 当前页
	 * 每页数量
	 * 总页数
	 */
	private int total = 0;
	
	private int currentPage = 1;
	
	private int pageSize = 5;
	
	private int pageNum = 0;

	private List<T> data;
	
	/**
	 * 改写获取方法
	 * @return
	 */
	public List<T> getData() {
		int start = (currentPage-1)*pageSize;
		int end = start + pageSize-1;
		List<T> list = new ArrayList<T>();
		for(int i =0; i < data.size(); i++) {
			if(start <= i && i <= end) {
				list.add(data.get(i));
			}
		}
		return list;
	}
	
	/**
	 * 改写获取方法
	 * @return
	 */
	
	public int getTotal() {
		return getData().size();
	}
	
	public int getCurrentPage() {
		return currentPage;
	}

	public int getTotalPage() {
		int totalpage = 0;
		if(pageSize >= data.size()) {
			totalpage =1;
		}else {
			totalpage =(int)(data.size()/pageSize) + 1;
		}
		return totalpage;
	}

	public void setData(List<T> data) {
		this.data = data;
	}
	
	public Pagation(int currentPage, int pageSize, List<T> data) {
		if(currentPage < 1) {
			this.currentPage = 1;
		}else {
			this.currentPage = currentPage;
		}
		
		this.data = data;
	}
	public Pagation() {}

	@Override
	public String toString() {
		return "Pagation [total=" + total + ", currentPage=" + currentPage + ", pageSize=" + pageSize + ", pageNum="
				+ pageNum + "]";
	}
	
	
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		for(int i =0;i < 50; i++) {
			list.add(String.valueOf(i));
		}
		Pagation<String> page = new Pagation<String>(1, 5, list);
		
		System.out.println(page.getTotal());
		System.out.println(page.getData());
		System.out.println(page.getTotalPage());
	}
}

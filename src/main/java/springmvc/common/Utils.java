package springmvc.common;
/**
 *
 *@author  wsz
 *@createdTime 2018年3月23日
*/
public class Utils {

	public static String reloadString(String str) {
		return str.trim().replace(" ", "");
	}
	
	public static void main(String[] args) {
		System.out.println(Utils.reloadString(" asd dff "));
	}
}

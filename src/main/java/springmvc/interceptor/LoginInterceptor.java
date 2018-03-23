package springmvc.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
*登录拦截器
*@author  wsz
*@createdTime 2018年3月16日
*/
public class LoginInterceptor implements HandlerInterceptor{

	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)throws Exception {
		
	}

	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)throws Exception {
		
	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		Object value = request.getSession().getAttribute("user");
		if(null == value) {
			String url = request.getRequestURI();
			String action = url.substring(url.lastIndexOf("/")+1);
			if("login".equals(action) || "register".equals(action)) {
				return true;
			}else {
				request.getRequestDispatcher("/error.jsp").forward(request, response);
				return false;
			}
		}else {
			return true;
		}
	}

}

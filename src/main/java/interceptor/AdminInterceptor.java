package interceptor;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AdminInterceptor extends HandlerInterceptorAdapter{
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		String login = (String) session.getAttribute("memid");
		
		if (login == null || !login.equals("admin")) {
			String msg = "not_authorized";
			response.sendRedirect(request.getContextPath() + "/member/login?msg=" + msg);
			return false;
		}
		
		return true;
	}
}

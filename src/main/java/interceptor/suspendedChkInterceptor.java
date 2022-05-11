package interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import model.Suspended_List;
import service.Suspended_ListDao;

public class suspendedChkInterceptor extends HandlerInterceptorAdapter{
	
	@Autowired
	Suspended_ListDao dao;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("memid");
		Suspended_List memberOne = dao.selectOne(id);
		
		
		if (memberOne != null && memberOne.getStatus().equals("Y")) {
			String msg = "no_permission";
			response.sendRedirect(request.getContextPath() + "/classes/main?msg=" + msg);
			
			return false;
		}
		
		return true;
	}
}

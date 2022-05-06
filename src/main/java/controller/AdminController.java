package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/")
public class AdminController {
	
	HttpServletRequest request;
	Model model;
	HttpSession session;
	
	@ModelAttribute
	void init(HttpServletRequest request, Model model) {
		this.request = request;
		this.model = model;
		this.session = request.getSession();
	}
	
	@RequestMapping("memberList")
	public String memberList() {
		return "view/admin/memberList";
	}
	
	@RequestMapping("blackList")
	public String blackList() {
		return "view/admin/blackList";
	}
	
	@RequestMapping("suspendedList")
	public String suspendedList() {
		return "view/admin/suspendedList";
	}
	
	@RequestMapping("report")
	public String report() {
		return "view/admin/report";
	}
}

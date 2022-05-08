package controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import model.Knoc_Member;
import service.Knoc_MemberDao;

@Controller
@RequestMapping("/admin/")
public class AdminController {
	
	HttpServletRequest request;
	Model model;
	HttpSession session;
	
	@Autowired
	Knoc_MemberDao md;
	
	@ModelAttribute
	void init(HttpServletRequest request, Model model) {
		this.request = request;
		this.model = model;
		this.session = request.getSession();
	}
	
	@RequestMapping("memberList")
	public String memberList() {
		// 페이징 작업 추가 필요
		// 회원 목록 아이디 검색창 검색버튼
		List<Knoc_Member> memberList = md.selectMemberAll();
		
		model.addAttribute("memberList", memberList);
		return "view/admin/memberList";
	}
	
	@RequestMapping("blackList")
	public String blackList() {
		List<Knoc_Member> blackList = md.selectBlackList();
		
		model.addAttribute("blackList", blackList);
		return "view/admin/blackList";
	}
	
	@RequestMapping("blackListPro")
	public String blackListPro(String member_id) {
		Knoc_Member member = md.selectOne(member_id);
		// 블랙리스트 N이면 y로 바꾸고, y면 n으로 바꾸기
		// blacklist 화면으로 redirect
		// 페이징 작업 필요
		String msg = "";
		String url = "";
		
		if (member.getBlacklist().equals("N")) {
			//회원 목록에서 블랙리스트로 추가한 경우
			md.addBlackList(member_id);
			msg = "블랙리스트로 추가되었습니다.";
			url = request.getContextPath() + "/admin/memberList";
		} else {
			// 블랙리스트에서 블랙리스트 해제한 경우
			md.deleteBlackList(member_id);
			msg = "블랙리스트에서 해제되었습니다.";
			url = request.getContextPath() + "/admin/blackList";
		}
		
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		
		return "/view/alert";
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

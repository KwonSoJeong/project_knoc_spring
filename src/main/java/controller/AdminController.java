package controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import model.Knoc_Member;
import model.Report;
import service.Knoc_MemberDao;
import service.ReportDao;

@Controller
@RequestMapping("/admin/")
public class AdminController {
	
	HttpServletRequest request;
	Model model;
	HttpSession session;
	
	@Autowired
	Knoc_MemberDao md;
	@Autowired
	ReportDao rd;
	
	@ModelAttribute
	void init(HttpServletRequest request, Model model) {
		this.request = request;
		this.model = model;
		this.session = request.getSession();
	}
	
	@RequestMapping("memberList")
	public String memberList(@RequestParam(value="pageNum", defaultValue = "1") int pageInt,
							 @RequestParam(required = false) String keyword) {
		// 페이징 작업 추가 필요
		// 회원 목록 아이디 검색창 검색버튼
		int limit = 10;
		
		List<Knoc_Member> memberList = md.memberList(pageInt, limit);
		int memberCount = md.memberCount();
		
		if (keyword != null) {
			memberList = md.memberListWithKeyword(pageInt, limit, keyword);
			memberCount = md.memberCountWithKeyword(keyword);
		}
		
		int bottomLine = 5;
		
		
		int memberNum = memberCount - (pageInt - 1) * limit;
		int startPage = (pageInt - 1) / bottomLine * bottomLine + 1;
		int endPage = startPage + bottomLine - 1;
		int maxPage = (memberCount / limit) + (memberCount % limit == 0 ? 0 : 1);
		if (endPage > maxPage) {
			endPage = maxPage;
		}
		
		model.addAttribute("pageInt", pageInt);
		model.addAttribute("memberNum", memberNum);
		model.addAttribute("startPage", startPage);
		model.addAttribute("bottomLine", bottomLine);
		model.addAttribute("endPage", endPage);
		model.addAttribute("maxPage", maxPage);
		model.addAttribute("memberList", memberList);
		
		return "view/admin/memberList";
	}
	
	@RequestMapping("deleteMember")
	public String deleteMember(String member_id) {
		Knoc_Member member = md.selectOne(member_id);
		int num = md.deleteMember(member);
		
		String msg = "회원정보가 삭제되었습니다.";
		String url = request.getContextPath() + "/admin/memberList";
		
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		
		return "/view/alert";
	}
	
	@RequestMapping("blackList")
	public String blackList(@RequestParam(value="pageNum", defaultValue = "1") int pageInt) {
		int limit = 10;
		
		List<Knoc_Member> blackList = md.selectBlackList(pageInt, limit);
		
		int bottomLine = 5;
		
		int memberCount = md.blackListCount();
		int memberNum = memberCount - (pageInt - 1) * limit;
		int startPage = (pageInt - 1) / bottomLine * bottomLine + 1;
		int endPage = startPage + bottomLine - 1;
		int maxPage = (memberCount / limit) + (memberCount % limit == 0 ? 0 : 1);
		if (endPage > maxPage) {
			endPage = maxPage;
		}
		
		model.addAttribute("pageInt", pageInt);
		model.addAttribute("memberNum", memberNum);
		model.addAttribute("startPage", startPage);
		model.addAttribute("bottomLine", bottomLine);
		model.addAttribute("endPage", endPage);
		model.addAttribute("maxPage", maxPage);
		model.addAttribute("blackList", blackList);
		
		return "view/admin/blackList";
	}
	
	@RequestMapping("addBlackList")
	public String addBlackList(String member_id) {
		Knoc_Member member = md.selectOne(member_id);
		md.addBlackList(member_id);
		
		String msg = "블랙리스트로 추가되었습니다.";
		String url = request.getContextPath() + "/admin/memberList";
		
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		
		return "/view/alert";
	}
	
	@RequestMapping("deleteBlackList")
	public String blackListPro(String member_id) {
		Knoc_Member member = md.selectOne(member_id);
		
		md.deleteBlackList(member_id);
		String msg = "블랙리스트에서 해제되었습니다.";
		String url = request.getContextPath() + "/admin/blackList";
		
		
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		
		return "/view/alert";
	}
	
	@RequestMapping("suspendedList")
	public String suspendedList() {
		return "view/admin/suspendedList";
	}
	
	@RequestMapping("report")
	public String report(String subject) {
		/*
		List<Map<String, Object>> reportList = null;
		
		if (subject.equals("study") ) {
			reportList = rd.reportList("study");
		} else if (subject.equals("mentoring")) {
			reportList = rd.reportList("mentoring");
		}
		System.out.println(reportList);
		*/
		return "view/admin/report";
	}
}

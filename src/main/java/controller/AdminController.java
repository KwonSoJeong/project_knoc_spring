package controller;

import java.util.Date;
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
import model.Mentoring;
import model.Report;
import model.Study;
import model.Suspended_List;
import service.Knoc_MemberDao;
import service.MentoringDao;
import service.ReportDao;
import service.StudyDao;
import service.Suspended_ListDao;

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
	@Autowired
	MentoringDao mtd;
	@Autowired
	StudyDao sd;
	@Autowired
	Suspended_ListDao sld;
	
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
	
	@RequestMapping("report")
	public String report(String subject, 
						 @RequestParam(value="pageNum", defaultValue = "1") int pageInt) {
		int limit = 10;
		List<Map<String, Object>> reportList = null;
		
		String title = null;
		if (subject.equals("study") ) {
			reportList = rd.reportList("study", pageInt, limit);
			title = "스터디";
		} else if (subject.equals("mentoring")) {
			reportList = rd.reportList("mentoring", pageInt, limit);
			title = "멘토링";
		}
		
		int bottomLine = 5;
		int reportCount = rd.reportCount(subject);
		int reportNum = reportCount - (pageInt - 1) * limit;
		int startPage = (pageInt - 1) / bottomLine * bottomLine + 1;
		int endPage = startPage + bottomLine - 1;
		int maxPage = (reportCount / limit) + (reportCount % limit == 0 ? 0 : 1);
		if (endPage > maxPage) {
			endPage = maxPage;
		}
		
		model.addAttribute("pageInt", pageInt);
		model.addAttribute("reportNum", reportNum);
		model.addAttribute("startPage", startPage);
		model.addAttribute("bottomLine", bottomLine);
		model.addAttribute("endPage", endPage);
		model.addAttribute("maxPage", maxPage);
		model.addAttribute("subject", title);
		model.addAttribute("reportList", reportList);
		
		return "view/admin/report";
	}
	
	@RequestMapping("addSuspendedMember")
	public String addSuspendedMember(String report_id) {
		String reportedId = "";
		
		String msg = "해당 지식공유자에게 3일의 활동정지 기간이 부여됩니다.";
		String url = request.getContextPath() + "/admin/suspendedList";
		
		if (report_id.contains("mentoring")) {
			Mentoring reportedMentoring = mtd.selectOne(report_id);
			reportedId = reportedMentoring.getMentor_Id(); 
			
		} else if (report_id.contains("study")) {
			Study reportedStudy = sd.selectOne(report_id);
			reportedId = reportedStudy.getLeader_Id();
		}
		
		Suspended_List reportedMember = sld.selectOne(reportedId);
		
		if (reportedMember == null) {
			sld.addSuspendedMember(reportedId);
			
		} else if (reportedMember.getAccCnt() < 2){
			sld.updateAccCnt(reportedId);
			
		} else if (reportedMember.getAccCnt() == 2) {
			sld.updateAccCnt(reportedId);
			md.addBlackList(reportedId);
			msg = "해당 지식공유자는 활동 제재 3회 누적으로 블랙리스트로 등록됩니다.";
			
		} else {
			msg = "활동 제재 3회 누적으로 블랙리스트로 등록된 사용자입니다.";
			
		}
		
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		
		return "/view/alert";
		
	}
	
	@RequestMapping("suspendedList")
	public String suspendedList(@RequestParam(value="pageNum", defaultValue = "1") int pageInt) {
		int limit = 10;
		
		List<Suspended_List> suspendedList = sld.selectList(pageInt, limit);
		Date now = new Date();
		
		int bottomLine = 5;
		int listCount = sld.suspendedListCount();
		int listNum = listCount - (pageInt - 1) * limit;
		int startPage = (pageInt - 1) / bottomLine * bottomLine + 1;
		int endPage = startPage + bottomLine - 1;
		int maxPage = (listCount / limit) + (listCount % limit == 0 ? 0 : 1);
		if (endPage > maxPage) {
			endPage = maxPage;
		}
		
		model.addAttribute("pageInt", pageInt);
		model.addAttribute("listNum", listNum);
		model.addAttribute("startPage", startPage);
		model.addAttribute("bottomLine", bottomLine);
		model.addAttribute("endPage", endPage);
		model.addAttribute("maxPage", maxPage);
		model.addAttribute("suspendedList", suspendedList);
		model.addAttribute("now", now);
		return "view/admin/suspendedList";
	}
	
	@RequestMapping("updateStatus")
	public String updateStatus(String reportedID) {
		sld.updateStatus(reportedID);
		
		String msg = "활동 제재가 해제되었습니다.";
		String url = request.getContextPath() + "/admin/suspendedList";
		
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		
		return "/view/alert";
	}
}

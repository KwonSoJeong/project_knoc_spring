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
import model.Member_Study_Info;
import model.Mentoring;
import model.Notification;
import model.Report;
import service.Knoc_MemberDao;
import service.Member_Study_InfoDao;
import service.MentoringDao;
import service.NotificationDao;
import service.ReportDao;
import service.StudyDao;


@Controller
@RequestMapping("/mentor/")
public class MentorController {
	static String msg = "";
	static String url = "";
	
	HttpServletRequest request;
	Model m;
	HttpSession session;
	@Autowired
	MentoringDao mtd;
	@Autowired
	Member_Study_InfoDao msid = new Member_Study_InfoDao();
	@Autowired
	StudyDao sd = new StudyDao();
	@Autowired
	Knoc_MemberDao memdao = new Knoc_MemberDao();
	@Autowired
	NotificationDao notid;
	@Autowired
	ReportDao rd;
	
	@ModelAttribute
	void init(HttpServletRequest request, Model m) {
		this.request=request;
		this.m=m;
		this.session=request.getSession();
	}

	
	// 멘토링 리스트 뷰
	@RequestMapping("mentorList")
	public String mentorList(@RequestParam(value = "keyword", required = false) String keyword) {

		List<Mentoring> mt;
		List profile;
		List ratingavg;
		if (keyword != null) { // 검색키워드가 있으면
			mt = mtd.selectListKeyword(keyword); // 멘토링 게시글 리스트
			profile = mtd.profileListKeyword(keyword); // 프로필사진 리스트
			ratingavg = mtd.selectListRatingKeyword(keyword);
		} else { // 전체 리스트
			mt = mtd.selectList(); // 멘토링 게시글 리스트
			profile = mtd.profileList(); // 프로필사진 리스트
			ratingavg = mtd.selectListRating();
		}
		// System.out.println("mt="+mt);
		// System.out.println("profile="+profile);
		
		m.addAttribute("ratingavg",ratingavg);
		m.addAttribute("profile", profile);
		m.addAttribute("mt", mt);
		return "/view/mentor/mentorList";
	}

	// 멘토링 등록 view
	@RequestMapping("mentorRegister")
	public String mentorRegister() {
		return "/view/mentor/mentorRegister";
	}
	// 멘토링 등록 process
	@RequestMapping("mentorRegisterPro")
	public String mentorRegisterPro(Mentoring mt) {
		
		Knoc_Member mem = new Knoc_Member();

		String id = (String) session.getAttribute("memid");
		mem = memdao.selectOne(id);

		String mentoringId = "mentoring" + mtd.nextNum();
		
		mt.setMentoring_Id(mentoringId);
		mt.setMentor_Id(mem.getId());

	//	System.out.println(mt);
		int num = mtd.insert(mt);

		if (num > 0) {
			Member_Study_Info msi = new Member_Study_Info();
			
			//member_study_info에 저장
			msi.setId(mt.getMentor_Id());
			msi.setMember_study_id(mt.getMentoring_Id());
			msi.setType(1);
			msi.setNo(msid.nextSeq());
			
			msid.insertInfo(msi);
			msg = "멘토링이 등록이 완료되었습니다.";
			url = request.getContextPath() + "/mentor/mentorList";
		} else {
			msg = "등록오류";
			url = request.getContextPath() + "/mentor/mentorRegister";
		}

		m.addAttribute("msg", msg);
		m.addAttribute("url", url);
		return "/view/alert";
	}
	
	// 멘토링 상세정보 view
	@RequestMapping("mentorInfo")
	public String mentorInfo(String mentoring_Id) {
		
		//session에 info위치 저장
		if(mentoring_Id == null) {
			mentoring_Id = (String) session.getAttribute("mentoring_Id");
		}
		session.setAttribute("mentoring_Id", mentoring_Id);
		
		System.out.println("session.mid="+session.getAttribute("mentoring_Id"));
		Mentoring mt = new Mentoring();
		
		mt = mtd.selectOne(mentoring_Id);
	//	System.out.println("mt"+mt);
		String profile = sd.callProfile(mt.getMentor_Id());
		double rating = mtd.selectOneRating(mentoring_Id);
		
		m.addAttribute("rating",rating);
		m.addAttribute("profile", profile);
		m.addAttribute("mt", mt);
		return "/view/mentor/mentorInfo";
		
	}
	
	// 멘토링 참가신청 pro
	@RequestMapping("mentoringEntry")
	public String mentoringEntry(String mentoring_Id) {
//		System.out.println("mentoring_Id="+mentoring_Id);
		//중복신청체크
		String id = (String) session.getAttribute("memid");
		if(notid.EntryCheck(id,mentoring_Id)!=0) {
			msg = "이미 참가신청한 멘토링 입니다.";
			url = request.getContextPath()+"/mentor/mentorInfo";
			m.addAttribute("msg", msg);
			m.addAttribute("url", url);
			return "/view/alert";
		}
	/*	
		Member_Study_Info msi = new Member_Study_Info();;
		msi.setId(id);
		msi.setMember_study_id(mentoring_Id);
		msi.setType(2);
		msi.setNo(msid.nextSeq());
		
		msid.insertInfo(msi);
	*/	
		//알람보내기 
		Notification noti = new Notification();
		Mentoring mt = new Mentoring();
		mt = mtd.selectOne(mentoring_Id);
		String noti_Content = id+"님이 ["+ mt.getTitle() +"] 멘토링 참가를 희망합니다.";
		
		noti.setNo(notid.nextNum());
		noti.setNoti_Code(mentoring_Id);
		noti.setNoti_Content(noti_Content);
		noti.setFrom_Id(id);
		noti.setTo_Id(mt.getMentor_Id());
		noti.setType(1);
		int num = notid.insertNoti(noti);
		
		url = request.getContextPath()+"/mentor/mentorInfo";
		if(num==0) {
			msg = "참가신청 오류 발생";
		}else {
			msg = "참가 신청을 보냈습니다. 상대방이 확인할 때까지 잠시만 기다려주세요!"; 
		}
		
		m.addAttribute("msg", msg);
		m.addAttribute("url", url);
		return "/view/alert";
	}
	
	//멘토링 게시글 수정
	@RequestMapping("mentorUpdate")
	public String mentorUpdate(String mentoring_Id) {
						
		Mentoring mt = new Mentoring();
		String id = (String)session.getAttribute("memid");
		mt = mtd.selectOne(mentoring_Id);
						
		if(id==null || !id.equals(mt.getMentor_Id())) { //작성자인지 체크
			msg = "게시글 수정은 작성자만 할 수 있습니다.";
			url = request.getContextPath()+"/mentor/mentorInfo";
			m.addAttribute("msg", msg);
			m.addAttribute("url", url);
			return "/view/alert";
		}

		m.addAttribute("mt", mt);
		return "/view/mentor/mentorUpdate";
	}
					
	//qna 게시글 수정 pro
	@RequestMapping("mentorUpdatePro")
	public String mentorUpdatePro(Mentoring mt) {			
		
		int num = mtd.update(mt.getTitle(),mt.getContent(),mt.getMentoring_Id(),mt.getIntro());
						
		if(num>0) {		//성공적으로 수정이 되었을 경우
			msg = "수정이 되었습니다";
			url = request.getContextPath()+"/mentor/mentorInfo";
		}else {			//수정에 오류 발생
			msg = "수정에 실패하였습니다";
			url = request.getContextPath()+"/mentor/mentorInfo?mentoring_Id="+mt.getMentoring_Id();
		}
		
		session.setAttribute("mentoring_Id", mt.getMentoring_Id());
		m.addAttribute("msg", msg);
		m.addAttribute("url", url);
		return "/view/alert";
	}
					
	//qna 게시글 삭제
	@RequestMapping("mentorDeletePro")
	public String mentorDeletePro(String mentoring_Id) {
						
		Mentoring mt = new Mentoring();
		mt = mtd.selectOne(mentoring_Id);			
		String id = (String) session.getAttribute("memid");

		if(id==null || !id.equals(mt.getMentor_Id())) { //작성자인지 체크
			msg = "게시글 삭제는 작성자만 할 수 있습니다.";
			url = request.getContextPath()+"/mentor/mentorInfo";
			m.addAttribute("msg", msg);
			m.addAttribute("url", url);
			return "/view/alert";
		}
						
		int num = mtd.delete(mentoring_Id);
		msid.deleteInfo(mentoring_Id);
		if(num>0) {		//성공적으로 삭제가 되었을 경우
			msg = "삭제가 되었습니다";
			url = request.getContextPath()+"/mentor/mentorList";
		}else {			//삭제에 오류 발생
			msg = "삭제에 실패하였습니다";
			url = request.getContextPath()+"/mentor/mentorInfo";
		}
//		m.addAttribute(attributeValue)
		m.addAttribute("msg", msg);
		m.addAttribute("url", url);
		return "/view/alert";
	}
	
	@RequestMapping("report")
	public String report(Report report) {
		report.setNo(rd.nextNum());
		
		int num = rd.insertReport(report);
		
		if(num > 0) { 
			msg = "신고접수가 완료되었습니다.";
		}else {
			msg = "신고 접수 오류";
		}
		url = request.getContextPath()+"/mentor/mentorInfo";
		m.addAttribute("msg",msg);
		m.addAttribute("url",url);
		return "/view/alert";
	}
	
	
	@RequestMapping("rating")
	public String rating(double rating,String mentoring_Id) {
		String id = (String) session.getAttribute("memid");
		
		int num = mtd.insertRating(mentoring_Id,rating);
		msid.deleteUser(id,mentoring_Id);
		
		if(num > 0) {
			msg = "별점이 등록 되었습니다";
		}else {
			msg = "별점 등록 오류";
		}
		url = request.getContextPath()+"/mentor/mentorManage";  //@@@@@@@@@@@@@@@페이지에 맞게 수정해야함
		m.addAttribute("msg",msg);
		m.addAttribute("url",url);
		return "/view/alert";
	}
	
	@RequestMapping("mentorManage")
	public String mentorManage() {
		String id = (String) session.getAttribute("memid");
		List<Map<String, Object>> mentoringList = msid.infoMentoringList(id);
		
		System.out.println("mentoringList===="+mentoringList);
		m.addAttribute("mentoringList",mentoringList);
		return "/view/mentor/mentorManage";
	}


}

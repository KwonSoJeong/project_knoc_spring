package controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import service.Knoc_MemberDao;
import service.Member_Study_InfoDao;
import service.MentoringDao;
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
	
	@ModelAttribute
	void init(HttpServletRequest request, Model m) {
		this.request=request;
		this.m=m;
		this.session=request.getSession();
	}

	
	// 멘토링 리스트 뷰
		@RequestMapping("mentorList")
		public String mentorList(@RequestParam(value = "keyword",required = false)String keyword) {
			
			List<Mentoring> mt;
			List profile;
			if(keyword!=null) { //검색키워드가 있으면
				mt = mtd.selectListKeyword(keyword);	//멘토링 게시글 리스트
				profile = mtd.profileListKeyword(keyword);		//프로필사진 리스트
			}else {					//전체 리스트
				mt = mtd.selectList();	//멘토링 게시글 리스트
				profile = mtd.profileList();		//프로필사진 리스트
			}
		//	System.out.println("mt="+mt);
		//	System.out.println("profile="+profile);
			
			m.addAttribute("profile", profile);
			m.addAttribute("mt", mt);
			return "/view/mentor/mentorList";
		}

	// 멘토링 등록 view
	@RequestMapping("mentorRegister")
	public String mentorRegister() {

		String memid = (String) request.getSession().getAttribute("memid");
		if (memid == null) {
			msg = "로그인이 필요한 서비스 입니다.";
			url = request.getContextPath()+"/member/login";
			m.addAttribute("msg", msg);
			m.addAttribute("url", url);
			return "/view/alert";
		}

		return "/view/mentor/mentorRegister";
	}
	// 멘토링 등록 process
	@RequestMapping("mentorRegisterPro")
	public String mentorRegisterPro(Mentoring mt) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Knoc_Member mem = new Knoc_Member();

		String id = (String) request.getSession().getAttribute("memid");
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
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//session에 info위치 저장
		if(mentoring_Id == null) {
			mentoring_Id = (String) request.getSession().getAttribute("mentoring_Id");
		}
		request.getSession().setAttribute("mentoring_Id", mentoring_Id);
		
	//	System.out.println(mentoring_Id);
		Mentoring mt = new Mentoring();
		
		mt = mtd.selectOne(mentoring_Id);
	//	System.out.println("mt"+mt);
		String profile = sd.callProfile(mt.getMentor_Id());
		m.addAttribute("profile", profile);
		m.addAttribute("mt", mt);
		return "/view/mentor/mentorInfo";
		
	}
	
	// 멘토링 참가신청 pro
	@RequestMapping("mentoringEntry")
	public String mentoringEntry(String mentoring_Id) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(request.getSession().getAttribute("memid")==null) { //로그인체크
			msg = "로그인이 필요한 서비스 입니다.";
			url = request.getContextPath()+"/member/login";
			m.addAttribute("msg", msg);
			m.addAttribute("url", url);
			return "/view/alert";
		}
		
		//중복신청체크
		String id = (String) request.getSession().getAttribute("memid");
		if(sd.infoChk(id,mentoring_Id)!=0) {
			msg = "이미 참가신청한 스터디 입니다.";
			url = request.getContextPath()+"/mentor/mentorInfo";
			m.addAttribute("msg", msg);
			m.addAttribute("url", url);
			return "/view/alert";
		}
		
		Member_Study_Info msi = new Member_Study_Info();
		Mentoring mt = new Mentoring();
		
		mt = mtd.selectOne(mentoring_Id);
		
		msi.setId(id);
		msi.setMember_study_id(mt.getMentoring_Id());
		msi.setType(2);
		msi.setNo(msid.nextSeq());
		
		msid.insertInfo(msi);
		
		msg = "신청이 완료되었습니다.";
		url = request.getContextPath()+"/mentor/mentorInfo";
		
		m.addAttribute("msg", msg);
		m.addAttribute("url", url);
		return "/view/alert";
	}
	
	//멘토링 게시글 수정
	@RequestMapping("mentorUpdate")
	public String mentorUpdate(String mentoring_Id) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
						
		Mentoring mt = new Mentoring();
		String id = (String)request.getSession().getAttribute("memid");
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
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
		
		int num = mtd.update(mt.getTitle(),mt.getContent(),mt.getMentoring_Id(),mt.getIntro());
						
		if(num>0) {		//성공적으로 수정이 되었을 경우
			msg = "수정이 되었습니다";
			url = request.getContextPath()+"/mentor/mentorInfo";
		}else {			//수정에 오류 발생
			msg = "수정에 실패하였습니다";
			url = request.getContextPath()+"/mentor/mentorInfo?mentoring_Id="+mt.getMentoring_Id();
		}

		m.addAttribute("msg", msg);
		m.addAttribute("url", url);
		return "/view/alert";
	}
					
	//qna 게시글 삭제
	@RequestMapping("mentorDeletePro")
	public String mentorDeletePro(String mentoring_Id) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
						
		Mentoring mt = new Mentoring();
		mt = mtd.selectOne(mentoring_Id);			
		String id = (String)request.getSession().getAttribute("memid");

		if(id==null || !id.equals(mt.getMentor_Id())) { //작성자인지 체크
			msg = "게시글 삭제는 작성자만 할 수 있습니다.";
			url = request.getContextPath()+"/mentor/mentorInfo";
			m.addAttribute("msg", msg);
			m.addAttribute("url", url);
			return "/view/alert";
		}
						
		int num = mtd.delete(mentoring_Id);
						
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
	

}

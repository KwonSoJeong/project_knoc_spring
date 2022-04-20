package controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Knoc_Member;
import model.Member_Study_Info;
import model.Mentoring;
import model.Qna;
import service.Knoc_MemberDao;
import service.Member_Study_InfoDao;
import service.MentoringDao;
import service.QnaDao;
import service.StudyDao;

//@WebServlet("/mentor/*")
public class MentorController extends MskimRequestMapping {
	static String msg = "";
	static String url = "";

	
	// 멘토링 리스트 뷰
		@RequestMapping("mentorList")
		public String mentorList(HttpServletRequest request, HttpServletResponse response) {
			MentoringDao mtd = new MentoringDao();
			
			String keyword="";
			List<Mentoring> mt;
			List profile;
			if(request.getParameter("keyword")!=null) { //검색키워드가 있으면
				keyword = request.getParameter("keyword");
				System.out.println("ketword="+keyword);
				mt = mtd.selectListKeyword(keyword);	//멘토링 게시글 리스트
				profile = mtd.profileListKeyword(keyword);		//프로필사진 리스트
			}else {					//전체 리스트
				mt = mtd.selectList();	//멘토링 게시글 리스트
				profile = mtd.profileList();		//프로필사진 리스트
			}
			System.out.println("mt="+mt);
			System.out.println("profile="+profile);
			
			
			request.setAttribute("profile", profile);
			request.setAttribute("mt", mt);
			return "/view/mentor/mentorList.jsp";
		}

	// 멘토링 등록 view
	@RequestMapping("mentorRegister")
	public String mentorRegister(HttpServletRequest request, HttpServletResponse response) {

		String memid = (String) request.getSession().getAttribute("memid");
		if (memid == null) {
			msg = "로그인이 필요한 서비스 입니다.";
			url = request.getContextPath()+"/member/login";
			request.setAttribute("msg", msg);
			request.setAttribute("url", url);
			return "/view/alert.jsp";
		}

		return "/view/mentor/mentorRegister.jsp";
	}
	// 멘토링 등록 process
	@RequestMapping("mentorRegisterPro")
	public String mentorRegisterPro(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Mentoring m = new Mentoring();
		MentoringDao md = new MentoringDao();
		Knoc_MemberDao memdao = new Knoc_MemberDao();
		Knoc_Member mem = new Knoc_Member();

		String id = (String) request.getSession().getAttribute("memid");
		mem = memdao.selectOne(id);

		String mentoringId = "mentoring" + md.nextNum();
		
		m.setTitle(request.getParameter("input"));
		m.setMentoring_Id(mentoringId);
		m.setMentor_Id(mem.getId());
		m.setContent(request.getParameter("content"));
		m.setIntro(request.getParameter("intro"));

		System.out.println(m);
		int num = md.insert(m);

		if (num > 0) {
			Member_Study_Info msi = new Member_Study_Info();
			Member_Study_InfoDao msid = new Member_Study_InfoDao();
			
			//member_study_info에 저장
			msi.setId(m.getMentor_Id());
			msi.setMember_study_id(m.getMentoring_Id());
			msi.setType(1);
			msi.setNo(msid.nextSeq());
			
			msid.insertInfo(msi);
			msg = "멘토링이 등록이 완료되었습니다.";
			url = request.getContextPath() + "/mentor/mentorList";
		} else {
			msg = "등록오류";
			url = request.getContextPath() + "/mentor/mentorRegister";
		}

		request.setAttribute("msg", msg);
		request.setAttribute("url", url);
		return "/view/alert.jsp";
	}
	
	// 멘토링 상세정보 view
	@RequestMapping("mentorInfo")
	public String mentorInfo(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//session에 info위치 저장
		String mentoringId = request.getParameter("mentoring_Id");
		if(mentoringId == null) {
			mentoringId = (String) request.getSession().getAttribute("mentoring_Id");
		}
		request.getSession().setAttribute("mentoring_Id", mentoringId);
		
		System.out.println(mentoringId);
		MentoringDao md = new MentoringDao();
		Mentoring m = new Mentoring();
		StudyDao sd = new StudyDao(); //작성자 프로필이미지를 불러오기 위함
		
		
		m = md.selectOne(mentoringId);
		System.out.println("m"+m);
		String profile = sd.callProfile(m.getMentor_Id());
		request.setAttribute("profile", profile);
		request.setAttribute("m", m);
		return "/view/mentor/mentorInfo.jsp";
		
	}
	
	// 멘토링 참가신청 pro
	@RequestMapping("mentoringEntry")
	public String mentoringEntry(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(request.getSession().getAttribute("memid")==null) { //로그인체크
			msg = "로그인이 필요한 서비스 입니다.";
			url = request.getContextPath()+"/member/login";
			request.setAttribute("msg", msg);
			request.setAttribute("url", url);
			return "/view/alert.jsp";
		}
		
		//중복신청체크
		StudyDao sd = new StudyDao();
		MentoringDao md = new MentoringDao();
		String id = (String) request.getSession().getAttribute("memid");
		String mentoringId = request.getParameter("mentoringId");
		if(sd.infoChk(id,mentoringId)!=0) {
			msg = "이미 참가신청한 스터디 입니다.";
			url = request.getContextPath()+"/mentor/mentorInfo";
			request.setAttribute("msg", msg);
			request.setAttribute("url", url);
			return "/view/alert.jsp";
		}
		
		
		Member_Study_Info msi = new Member_Study_Info();
		Member_Study_InfoDao msid = new Member_Study_InfoDao();
		Mentoring m = new Mentoring();
		
		m = md.selectOne(mentoringId);
		
		msi.setId(id);
		msi.setMember_study_id(m.getMentoring_Id());
		msi.setType(2);
		msi.setNo(msid.nextSeq());
		
		msid.insertInfo(msi);
		
		msg = "신청이 완료되었습니다.";
		url = request.getContextPath()+"/mentor/mentorInfo";
		
		request.setAttribute("msg", msg);
		request.setAttribute("url", url);
		return "/view/alert.jsp";
	}
	
	//멘토링 게시글 수정
	@RequestMapping("mentorUpdate")
	public String mentorUpdate(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
						
		MentoringDao md = new MentoringDao();
		Mentoring m = new Mentoring();
		String mentoring_Id = request.getParameter("mentoring_Id");
		String id = (String)request.getSession().getAttribute("memid");
		m = md.selectOne(mentoring_Id);
						
		if(id==null || !id.equals(m.getMentor_Id())) { //작성자인지 체크
			msg = "게시글 수정은 작성자만 할 수 있습니다.";
			url = request.getContextPath()+"/mentor/mentorInfo";
			request.setAttribute("msg", msg);
			request.setAttribute("url", url);
			return "/view/alert.jsp";
		}

		request.setAttribute("m", m);
		return "/view/mentor/mentorUpdate.jsp";
	}
					
	//qna 게시글 수정 pro
	@RequestMapping("mentorUpdatePro")
	public String mentorUpdatePro(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
								
		MentoringDao md = new MentoringDao();
		String mentoring_Id = request.getParameter("mentoring_Id");
		String title = request.getParameter("input");
		String content = request.getParameter("content");
		String intro = request.getParameter("intro");
						
		int num = md.update(title,content,mentoring_Id,intro);
						
		if(num>0) {		//성공적으로 수정이 되었을 경우
			msg = "수정이 되었습니다";
			url = request.getContextPath()+"/mentor/mentorInfo";
		}else {			//수정에 오류 발생
			msg = "수정에 실패하였습니다";
			url = request.getContextPath()+"/mentor/mentorInfo?mentoring_Id="+mentoring_Id;
		}

		request.setAttribute("msg", msg);
		request.setAttribute("url", url);
		return "/view/alert.jsp";
	}
					
	//qna 게시글 삭제
	@RequestMapping("mentorDeletePro")
	public String mentorDeletePro(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
						
		MentoringDao md = new MentoringDao();
		Mentoring m = new Mentoring();
		String mentoring_Id = request.getParameter("mentoring_Id");
		m = md.selectOne(mentoring_Id);			
		String id = (String)request.getSession().getAttribute("memid");

		if(id==null || !id.equals(m.getMentor_Id())) { //작성자인지 체크
			msg = "게시글 삭제는 작성자만 할 수 있습니다.";
			url = request.getContextPath()+"/mentor/mentorInfo";
			request.setAttribute("msg", msg);
			request.setAttribute("url", url);
			return "/view/alert.jsp";
		}
						
		int num = md.delete(mentoring_Id);
						
		if(num>0) {		//성공적으로 삭제가 되었을 경우
			msg = "삭제가 되었습니다";
			url = request.getContextPath()+"/mentor/mentorList";
		}else {			//삭제에 오류 발생
			msg = "삭제에 실패하였습니다";
			url = request.getContextPath()+"/mentor/mentorInfo";
		}

		request.setAttribute("msg", msg);
		request.setAttribute("url", url);
		return "/view/alert.jsp";
	}
	

}

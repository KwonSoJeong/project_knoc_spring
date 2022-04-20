package controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Member_Study_Info;
import model.Study;
import model.Study_Comment;
import service.Member_Study_InfoDao;
import service.StudyDao;
import service.Study_CommentDao;

//@WebServlet("/study/*")
public class StudyController extends MskimRequestMapping {
	static String msg ="";
	static String url ="";
	
	//스터디 게시글 작성 view
	@RequestMapping("studyWrite")
	public String studyWrite(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(request.getSession().getAttribute("memid")==null) {
			msg = "로그인이 필요한 서비스 입니다.";
			url = request.getContextPath()+"/member/login";
			request.setAttribute("msg", msg);
			request.setAttribute("url", url);
			return "/view/alert.jsp";
		}
		String id = (String)request.getSession().getAttribute("memid");
		StudyDao sd = new StudyDao();
		String profile = sd.callProfile(id);
		
		request.setAttribute("profile", profile);
		request.setAttribute("id", id);
		return "/view/study/studyWrite.jsp";
	}
	
	//스터디 게시글 작성 process
	@RequestMapping("studyWritePro")
	public String studyWritePro(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Study s = new Study();
		StudyDao sd = new StudyDao();
		String writerId = (String) request.getSession().getAttribute("memid");
		String id = "study"+sd.nextNum();
		s.setStudy_Id(id);
		s.setLeader_Id(writerId);
		s.setTitle(request.getParameter("title"));
		s.setContent(request.getParameter("text"));
		int num = sd.insertStudy(s);
		if(num>0) { //게시글 작성 성공
			Member_Study_Info msi = new Member_Study_Info();
			Member_Study_InfoDao msid = new Member_Study_InfoDao();
			
			//member_study_info에 저장
			msi.setId(s.getLeader_Id());
			msi.setMember_study_id(s.getStudy_Id());
			msi.setType(1);
			msi.setNo(msid.nextSeq());
			
			msid.insertInfo(msi);
			
			msg = "게시글이 등록되었습니다.";
			url = request.getContextPath()+"/study/studyList";
		}else{	//게시글 등록 실패
			msg = "게시글 등록 실패";
			url = request.getContextPath()+"/study/studyWirte";
		}
		request.setAttribute("msg", msg);
		request.setAttribute("url", url);
		return "/view/alert.jsp";
	}
	
	//스터디 게시판 view 
	@RequestMapping("studyList")
	public String studyList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int process = 3;  //process = 1 :모집중, process = 2 : 모집완료, process = 3 : 전체
		
		if(request.getParameter("process")!=null) {
			process = Integer.parseInt(request.getParameter("process"));
		}
		
		int limit = 10; //한 페이지에 보이는 게시글 수
		
		String pageNum="";
		if(request.getParameter("pageNum")!=null) {
			pageNum = request.getParameter("pageNum");
		}else {
			pageNum = "1";
		}
		int pageInt = Integer.parseInt(pageNum);
		
		String keyword = null;
		if(request.getParameter("keyword")!=null) {
			keyword = request.getParameter("keyword");
		}
		
		StudyDao sd = new StudyDao();
		List<Study> list;
		List<String> profileList;
		int studyCount;
		if(process==3) {	//전체 선택되면 모든 게시글 출력
			studyCount = sd.studyAllCount();
			list = sd.studyAllList(pageInt,limit,keyword);
			profileList = sd.callProfileList(pageInt,limit,keyword);
		}else {				//선택된 게시글만 출력
			studyCount = sd.studyCount(process);
			list = sd.studyList(pageInt,limit,process,keyword); 
			profileList = sd.callProfileList(pageInt,limit,process,keyword);
		}
		
		
		int bottomLine = 5; //최대 페이징 수
		
		
		int startPage = (pageInt - 1)/bottomLine * bottomLine + 1;
		int endPage = startPage + bottomLine -1;
		int maxPage = (studyCount/limit)+(studyCount%limit == 0?0:1);
		if(endPage>maxPage) endPage = maxPage;
		
		/*
		 * for (String q : profileList) { System.out.println(q); }
		 */
		
		request.setAttribute("profileList", profileList);
		request.setAttribute("pageInt", pageInt);
		request.setAttribute("studyCount", studyCount);
		request.setAttribute("startPage", startPage);
		request.setAttribute("bottomLine", bottomLine);
		request.setAttribute("endPage", endPage);
		request.setAttribute("maxPage", maxPage);
		request.setAttribute("process", process);
		request.setAttribute("list", list);
		return "/view/study/studyList.jsp";
	}
	
	//스터디 게시글 정보 view
	@RequestMapping("studyInfo")
	public String studyInfo(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//session에 info위치 저장
		String studyId = request.getParameter("study_Id");
		if(studyId == null) {
			studyId = (String) request.getSession().getAttribute("study_Id");
		}
		request.getSession().setAttribute("study_Id", studyId);
		
		StudyDao sd = new StudyDao();
		Study_CommentDao scd = new Study_CommentDao();
		Study s = new Study();
		
		//게시글과 답글 불러오기
		s = sd.selectOne(studyId);
		String refId = studyId;
		List<Study_Comment> commentlist = scd.selectComment(refId);
		
		//댓글 갯수
		int count = scd.count(refId);
		
		//작성자 프로필 사진
		String leaderProfile = sd.callProfile(s.getLeader_Id());
		
		//댓글 작성자 프로필 사진
		List<String> commentProfileList = scd.callProfile(refId);
		
		request.setAttribute("commentProfileList", commentProfileList);
		request.setAttribute("leaderProfile", leaderProfile);
		request.setAttribute("count", count);
		request.setAttribute("commentList", commentlist);
		request.setAttribute("s", s);
		return "/view/study/studyInfo.jsp";
	}
	
	@RequestMapping("writeStudyCommentPro")
	public String writeStudyComment(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Study_CommentDao scd = new Study_CommentDao();
		Study_Comment sc = new Study_Comment();
		
		String id = (String) request.getSession().getAttribute("memid");
		String study_Id = request.getParameter("study_Id");
		String refId = study_Id;
		
		String commentId = "studycomment"+scd.nextNum();
		
		sc.setComment_Id(commentId);
		sc.setWriter(id);
		sc.setContent(request.getParameter("text"));
		sc.setRefId(refId);
		
		int num = scd.insert(sc);
		if(num>0) {
			msg = "답글이 등록되었습니다.";
			url = request.getContextPath()+"/study/studyInfo";
		}else {
			msg = "답글 등록 실패";
			url = request.getContextPath()+"/study/studyInfo";
		}

		request.setAttribute("msg", msg);
		request.setAttribute("url", url);

		return "/view/alert.jsp";
	}
	
	//``
		@RequestMapping("studyEntry")
		public String studyEntry(HttpServletRequest request, HttpServletResponse response) {
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
			
			StudyDao sd = new StudyDao();
			Member_Study_Info msi = new Member_Study_Info();
			Member_Study_InfoDao msid = new Member_Study_InfoDao();
			Study s = new Study();
			
			String id = (String) request.getSession().getAttribute("memid");
			String studyId = request.getParameter("studyId");
			s = sd.selectOne(studyId);	//스터디 정보 불러오기
			
			//중복신청체크

			if(sd.infoChk(id,studyId)!=0) {
				msg = "이미 참가신청한 스터디 입니다.";
				url = request.getContextPath()+"/study/studyInfo";
				request.setAttribute("msg", msg);
				request.setAttribute("url", url);
				return "/view/alert.jsp";
			}
			
			msi.setId(id);
			msi.setMember_study_id(s.getStudy_Id());
			msi.setType(2);
			msi.setNo(msid.nextSeq());
			
			msid.insertInfo(msi);
			
			msg = "신청이 완료되었습니다.";
			url = request.getContextPath()+"/study/studyInfo";
			
			request.setAttribute("msg", msg);
			request.setAttribute("url", url);
			return "/view/alert.jsp";
		}
		
		// 스터디 info에서 모집전환 버튼 클릭시 모집전환 프로세스
		@RequestMapping("studyChangeProcess")
		public String studyChangeProcess(HttpServletRequest request, HttpServletResponse response) {
			try {
				request.setCharacterEncoding("UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			StudyDao sd = new StudyDao();
			Study s = new Study();
			String studyId = request.getParameter("studyId");
			s = sd.selectOne(studyId);
			String id = (String)request.getSession().getAttribute("memid");
			
			if(id==null || !id.equals(s.getLeader_Id())) { //작성자인지 체크
				msg = "모집전환은 작성자 본인만 전환할 수 있습니다.";
				url = request.getContextPath()+"/study/studyInfo";
				request.setAttribute("msg", msg);
				request.setAttribute("url", url);
				return "/view/alert.jsp";
			}

			System.out.println("process"+s.getProcess());
			// 모집중 전환
			if (s.getProcess() == 1) {
				sd.changeProcessToTwo(studyId);
				msg = "모집완료로 전환하였습니다.";
				url = request.getContextPath() + "/study/studyInfo";
			} else {
				sd.changeProcessToOne(studyId);
				msg = "모집중으로 전환하였습니다.";
				url = request.getContextPath() + "/study/studyInfo";
			}

			request.setAttribute("msg", msg);
			request.setAttribute("url", url);

			return "/view/alert.jsp";
		}
		
		//스터디 게시글 수정
		@RequestMapping("studyUpdate")
		public String studyUpdate(HttpServletRequest request, HttpServletResponse response) {
			try {
				request.setCharacterEncoding("UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			StudyDao sd = new StudyDao();
			Study s = new Study();
			String study_Id = request.getParameter("study_Id");
			String id = (String)request.getSession().getAttribute("memid");
			s = sd.selectOne(study_Id);
			
			if(id==null || !id.equals(s.getLeader_Id())) { //작성자인지 체크
				msg = "게시글 수정은 작성자만 할 수 있습니다.";
				url = request.getContextPath()+"/study/studyInfo";
				request.setAttribute("msg", msg);
				request.setAttribute("url", url);
				return "/view/alert.jsp";
			}
			
			String leaderProfile = sd.callProfile(s.getLeader_Id());	//프로필 call
			
			request.setAttribute("leaderProfile", leaderProfile);
			request.setAttribute("s", s);
			return "/view/study/studyUpdate.jsp";
		}
		
		//스터디 게시글 수정 pro
		@RequestMapping("studyUpdatePro")
		public String studyUpdatePro(HttpServletRequest request, HttpServletResponse response) {
			try {
				request.setCharacterEncoding("UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
					
			StudyDao sd = new StudyDao();
			String study_Id = request.getParameter("study_Id");
			String title = request.getParameter("title");
			String content = request.getParameter("text");
			
			int num = sd.update(title,content,study_Id);
			
			if(num>0) {		//성공적으로 수정이 되었을 경우
				msg = "수정이 되었습니다";
				url = request.getContextPath()+"/study/studyInfo";
			}else {			//수정에 오류 발생
				msg = "수정에 실패하였습니다";
				url = request.getContextPath()+"/study/studyInfo?study_Id="+study_Id;
			}

			request.setAttribute("msg", msg);
			request.setAttribute("url", url);
			return "/view/alert.jsp";
		}
		
		//스터디 게시글 삭제
		@RequestMapping("stydyDeletePro")
		public String studyDeletePro(HttpServletRequest request, HttpServletResponse response) {
			try {
				request.setCharacterEncoding("UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			StudyDao sd = new StudyDao();
			Study s = new Study();
			String study_Id = request.getParameter("study_Id");
			s = sd.selectOne(study_Id);
			String id = (String)request.getSession().getAttribute("memid");

			if(id==null || !id.equals(s.getLeader_Id())) { //작성자인지 체크
				msg = "게시글 삭제는 작성자만 할 수 있습니다.";
				url = request.getContextPath()+"/study/studyInfo";
				request.setAttribute("msg", msg);
				request.setAttribute("url", url);
				return "/view/alert.jsp";
			}
			
			int num = sd.delete(study_Id);
			
			if(num>0) {		//성공적으로 삭제가 되었을 경우
				msg = "삭제가 되었습니다";
				url = request.getContextPath()+"/study/studyList";
			}else {			//삭제에 오류 발생
				msg = "삭제에 실패하였습니다";
				url = request.getContextPath()+"/study/studyInfo";
			}

			request.setAttribute("msg", msg);
			request.setAttribute("url", url);
			return "/view/alert.jsp";
		}
}

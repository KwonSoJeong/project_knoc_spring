package controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import model.Member_Study_Info;
import model.Notification;
import model.Report;
import model.Study;
import model.Study_Comment;
import service.Member_Study_InfoDao;
import service.NotificationDao;
import service.ReportDao;
import service.StudyDao;
import service.Study_CommentDao;

@Controller
@RequestMapping("/study/")
public class StudyController {
	static String msg = "";
	static String url = "";

	HttpServletRequest request;
	Model m;
	HttpSession session;

	@Autowired
	StudyDao sd;
	@Autowired
	Study_CommentDao scd;
	@Autowired
	Member_Study_InfoDao msid = new Member_Study_InfoDao();
	@Autowired
	NotificationDao notid;
	@Autowired
	ReportDao rd;

	@ModelAttribute
	void init(HttpServletRequest request, Model m) {
		this.request = request;
		this.m = m;
		this.session = request.getSession();
	}

	// 스터디 게시글 작성 view
	@RequestMapping("studyWrite")
	public String studyWrite() {
		String id = (String) session.getAttribute("memid");
		String profile = sd.callProfile(id);

		m.addAttribute("profile", profile);
		m.addAttribute("id", id);
		return "/view/study/studyWrite";
	}

	// 스터디 게시글 작성 process
	@RequestMapping("studyWritePro")
	public String studyWritePro(Study s) {

		String writerId = (String) session.getAttribute("memid");
		String id = "study" + sd.nextNum();

		s.setStudy_Id(id);
		s.setLeader_Id(writerId);

		int num = sd.insertStudy(s);
		if (num > 0) { // 게시글 작성 성공
			Member_Study_Info msi = new Member_Study_Info();

			// member_study_info에 저장
			msi.setId(s.getLeader_Id());
			msi.setMember_study_id(s.getStudy_Id());
			msi.setType(1);
			msi.setNo(msid.nextSeq());

			msid.insertInfo(msi);

			msg = "게시글이 등록되었습니다.";
			url = request.getContextPath() + "/study/studyList";
		} else { // 게시글 등록 실패
			msg = "게시글 등록 실패";
			url = request.getContextPath() + "/study/studyWirte";
		}
		m.addAttribute("msg", msg);
		m.addAttribute("url", url);
		return "/view/alert";
	}

	// 스터디 게시판 view
	@RequestMapping("studyList")
	public String studyList(@RequestParam(value = "process", defaultValue = "3") int process,
			@RequestParam(value = "pageNum", defaultValue = "1") int pageInt,
			@RequestParam(value = "keyword", required = false) String keyword) {

		// process = 1 :모집중, process = 2 : 모집완료, process = 3 : 전체
		session.setAttribute("process", process);
		// System.out.println("porcess="+process);
		int limit = 10; // 한 페이지에 보이는 게시글 수

		List<Study> list;
		List<String> profileList;
		int studyCount;
		
		/*
		// 페이지 저장
		int pageInt = 1;
		if (request.getParameter("pageNum") != null) {
			session.setAttribute("pageNum", request.getParameter("pageNum"));
		}
		String pageNum = (String) session.getAttribute("pageNum");
		if (pageNum == null) {
			pageNum = "1";
		}
		pageInt = Integer.parseInt(pageNum);
		*/
		
		// System.out.println("keyword="+keyword);
		if (process == 3) { // 전체 선택되면 모든 게시글 출력
			studyCount = sd.studyAllCount(keyword);
			list = sd.studyAllList(pageInt, limit, keyword);
			profileList = sd.callProfileList(pageInt, limit, keyword);
		} else { // 선택된 게시글만 출력
			studyCount = sd.studyCount(process, keyword);
			list = sd.studyList(pageInt, limit, process, keyword);
			profileList = sd.callProfileList(pageInt, limit, process, keyword);
		}

		int bottomLine = 5; // 최대 페이징 수

		int startPage = (pageInt - 1) / bottomLine * bottomLine + 1;
		int endPage = startPage + bottomLine - 1;
		int maxPage = (studyCount / limit) + (studyCount % limit == 0 ? 0 : 1);
		if (endPage > maxPage)
			endPage = maxPage;

		/*
		 * for (String q : profileList) { System.out.println(q); }
		 */

		m.addAttribute("profileList", profileList);
		m.addAttribute("pageInt", pageInt);
		m.addAttribute("studyCount", studyCount);
		m.addAttribute("startPage", startPage);
		m.addAttribute("bottomLine", bottomLine);
		m.addAttribute("endPage", endPage);
		m.addAttribute("maxPage", maxPage);
		m.addAttribute("process", process);
		m.addAttribute("list", list);
		return "/view/study/studyList";
	}

	// 스터디 게시글 정보 view
	@RequestMapping("studyInfo")
	public String studyInfo(String study_Id) {

		// session에 info위치 저장
		if (study_Id == null) {
			study_Id = (String) session.getAttribute("study_Id");
		}
		session.setAttribute("study_Id", study_Id);

		Study s = new Study();

		// 게시글과 답글 불러오기
		s = sd.selectOne(study_Id);
		String refId = study_Id;
		List<Study_Comment> commentlist = scd.selectComment(refId);

		// 댓글 갯수
		int count = scd.count(refId);

		// 작성자 프로필 사진
		String leaderProfile = sd.callProfile(s.getLeader_Id());

		// 댓글 작성자 프로필 사진
		List<String> commentProfileList = scd.callProfile(refId);

		m.addAttribute("commentProfileList", commentProfileList);
		m.addAttribute("leaderProfile", leaderProfile);
		m.addAttribute("count", count);
		m.addAttribute("commentList", commentlist);
		m.addAttribute("s", s);
		return "/view/study/studyInfo";
	}

	@RequestMapping("writeStudyCommentPro")
	public String writeStudyComment(Study_Comment sc) {

		String id = (String) session.getAttribute("memid");
		String commentId = "studycomment" + scd.nextNum();

		sc.setComment_Id(commentId);
		sc.setWriter(id);

		int num = scd.insert(sc);
		if (num > 0) {
			msg = "답글이 등록되었습니다.";
			url = request.getContextPath() + "/study/studyInfo";
		} else {
			msg = "답글 등록 실패";
			url = request.getContextPath() + "/study/studyInfo";
		}

		m.addAttribute("msg", msg);
		m.addAttribute("url", url);

		return "/view/alert";
	}

	// ``
	@RequestMapping("studyEntry")
	public String studyEntry(String study_Id) {

		String id = (String) session.getAttribute("memid");
		// 중복신청체크
		if (notid.EntryCheck(id, study_Id)!=0) {
			msg = "이미 참가신청한 스터디 입니다.";
			url = request.getContextPath() + "/study/studyInfo";
			m.addAttribute("msg", msg);
			m.addAttribute("url", url);
			return "/view/alert";
		}
	/*
	   	Member_Study_Info msi = new Member_Study_Info();
		msi.setId(id);
		msi.setMember_study_id(study_Id);
		msi.setType(2);
		msi.setNo(msid.nextSeq());

		msid.insertInfo(msi);
	*/
		//알람 보내기
		Notification noti = new Notification();
		Study s = new Study();
		s = sd.selectOne(study_Id);
		String noti_Content = id+"님이 ["+s.getTitle()+"] 스터디에 참가를 희망합니다.";
		
		noti.setNo(notid.nextNum());
		noti.setNoti_Code(study_Id);
		noti.setNoti_Content(noti_Content);
		noti.setFrom_Id(id);
		noti.setTo_Id(s.getLeader_Id());
		noti.setType(1);
		int num = notid.insertNoti(noti);
		
		url = request.getContextPath() + "/study/studyInfo";
		if(num==0) {
			msg = "참가신청 오류 발생";
		}else {
			msg = "참가 신청을 보냈습니다. 상대방이 확인할 때까지 잠시만 기다려주세요!"; 
		}
		
		m.addAttribute("msg", msg);
		m.addAttribute("url", url);
		return "/view/alert";
	}

	// 스터디 info에서 모집전환 버튼 클릭시 모집전환 프로세스
	@RequestMapping("studyChangeProcess")
	public String studyChangeProcess(String study_Id) {

		Study s = new Study();
		s = sd.selectOne(study_Id);
		String id = (String) session.getAttribute("memid");

		if (id == null || !id.equals(s.getLeader_Id())) { // 작성자인지 체크
			msg = "모집전환은 작성자 본인만 전환할 수 있습니다.";
			url = request.getContextPath() + "/study/studyInfo";
			m.addAttribute("msg", msg);
			m.addAttribute("url", url);
			return "/view/alert";
		}

		System.out.println("process" + s.getProcess());
		// 모집중 전환
		if (s.getProcess() == 1) {
			sd.changeProcessToTwo(study_Id);
			msg = "모집완료로 전환하였습니다.";
			url = request.getContextPath() + "/study/studyInfo";
		} else {
			sd.changeProcessToOne(study_Id);
			msg = "모집중으로 전환하였습니다.";
			url = request.getContextPath() + "/study/studyInfo";
		}

		m.addAttribute("msg", msg);
		m.addAttribute("url", url);

		return "/view/alert";
	}

	// 스터디 게시글 수정
	@RequestMapping("studyUpdate")
	public String studyUpdate(String study_Id) {

		Study s = new Study();
		String id = (String) session.getAttribute("memid");
		s = sd.selectOne(study_Id);

		if (id == null || !id.equals(s.getLeader_Id())) { // 작성자인지 체크
			msg = "게시글 수정은 작성자만 할 수 있습니다.";
			url = request.getContextPath() + "/study/studyInfo";
			m.addAttribute("msg", msg);
			m.addAttribute("url", url);
			return "/view/alert";
		}

		String leaderProfile = sd.callProfile(s.getLeader_Id()); // 프로필 call

		m.addAttribute("leaderProfile", leaderProfile);
		m.addAttribute("s", s);
		return "/view/study/studyUpdate";
	}

	// 스터디 게시글 수정 pro
	@RequestMapping("studyUpdatePro")
	public String studyUpdatePro(Study s) {

		int num = sd.update(s.getTitle(), s.getContent(), s.getStudy_Id());

		if (num > 0) { // 성공적으로 수정이 되었을 경우
			msg = "수정이 되었습니다";
			url = request.getContextPath() + "/study/studyInfo";
		} else { // 수정에 오류 발생
			msg = "수정에 실패하였습니다";
			url = request.getContextPath() + "/study/studyInfo?study_Id=" + s.getStudy_Id();
		}

		m.addAttribute("msg", msg);
		m.addAttribute("url", url);
		return "/view/alert";
	}

	// 스터디 게시글 삭제
	@RequestMapping("stydyDeletePro")
	public String studyDeletePro(String study_Id) {

		Study s = new Study();
		s = sd.selectOne(study_Id);
		String id = (String) session.getAttribute("memid");

		if (id == null || !id.equals(s.getLeader_Id())) { // 작성자인지 체크
			msg = "게시글 삭제는 작성자만 할 수 있습니다.";
			url = request.getContextPath() + "/study/studyInfo";
			m.addAttribute("msg", msg);
			m.addAttribute("url", url);
			return "/view/alert";
		}

		int num = sd.delete(study_Id);
		msid.deleteInfo(study_Id);

		if (num > 0) { // 성공적으로 삭제가 되었을 경우
			msg = "삭제가 되었습니다";
			url = request.getContextPath() + "/study/studyList";
		} else { // 삭제에 오류 발생
			msg = "삭제에 실패하였습니다";
			url = request.getContextPath() + "/study/studyInfo";
		}

		m.addAttribute("msg", msg);
		m.addAttribute("url", url);
		return "/view/alert";
	}
	
	@RequestMapping("report")
	public String report(Report report) {
		//프론트 화면에서 
		report.setNo(rd.nextNum());
		
		int num = rd.insertReport(report);
		
		if(num > 0) { 
			msg = "신고접수가 완료되었습니다.";
		}else {
			msg = "신고 접수 오류";
		}
		url = request.getContextPath()+"/study/studyInfo";
		m.addAttribute("msg",msg);
		m.addAttribute("url",url);
		return "/view/alert";
	}
	
}

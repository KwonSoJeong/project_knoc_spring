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

import model.Qna;
import model.Qna_Comment;
import service.QnaDao;
import service.Qna_CommentDao;

@Controller
@RequestMapping("/help/")
public class HelpController {

	static String msg = "";
	static String url = "";
	
	HttpServletRequest request;
	Model m;
	HttpSession session;
	@Autowired
	QnaDao qd = new QnaDao();
	@Autowired
	Qna_CommentDao qcd = new Qna_CommentDao();
	
	@ModelAttribute
	void init(HttpServletRequest request, Model m) {
		this.request=request;
		this.m=m;
		this.session=request.getSession();
	}

	// qnalsit view
	@RequestMapping("qnaList")
	public String qnaList(@RequestParam(value = "pageNum", defaultValue = "1")int pageInt) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int limit = 3; // 한 페이지에 보이는 게시글 수

		List<Qna> list = qd.qnaList(pageInt, limit);

		int bottomLine = 5; // 최대 페이징 수

		int qnaCount = qd.qnaCount();
		int startPage = (pageInt - 1) / bottomLine * bottomLine + 1;
		int endPage = startPage + bottomLine - 1;
		int maxPage = (qnaCount / limit) + (qnaCount % limit == 0 ? 0 : 1);
		if (endPage > maxPage)
			endPage = maxPage;

		// 게시글마다 달린 댓글 수 list
		List countList = qcd.countList(pageInt, limit);
		
		m.addAttribute("pageInt", pageInt);
		m.addAttribute("countList", countList);
		m.addAttribute("startPage", startPage);
		m.addAttribute("bottomLine", bottomLine);
		m.addAttribute("endPage", endPage);
		m.addAttribute("maxPage", maxPage);
		m.addAttribute("list", list);
		return "/view/help/qnaList";
	}

	// qnaboard view
	@RequestMapping("qnaInfo")
	public String qnaInfo(@RequestParam(value = "qna_Id", required = false)String qnaId) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Qna q = new Qna();
		
		//session에 info위치 저장
		if(qnaId == null) {
			qnaId = (String) request.getSession().getAttribute("qna_Id");
		}
		request.getSession().setAttribute("qna_Id", qnaId);
		
		//qna 상세정보 불러오기
		q = qd.selectOne(qnaId);
		
		String memid = (String) request.getSession().getAttribute("memid");
		if(memid==null) {
			memid = "";
		}
		
		//비밀글일시 admin, 작성자 체크
		// !equals가 적용이 안됨 // 왜 안되는지 모르겠음 ㅠ
		if(q.getSecret()==2) {	//게시글이 비밀글일때
			if(memid.equals("admin") || memid.equals(q.getWriter())){ //admin, 작성자인 경우 접속
				
				//comment count
				String refId = qnaId;
				int commentCount = qcd.countOne(refId);
				
				//comment select
				Qna_Comment comment = qcd.selectOneComment(refId);
		//		System.out.println(comment);
				m.addAttribute("comment", comment);
				m.addAttribute("commentCount", commentCount);
				m.addAttribute("q", q);
				return "/view/help/qnaInfo";
			}else { //admin, 작성자가 아닌 경우 접근불가
				msg = "작성자와 관리자만 볼 수 있는 게시글 입니다.";
				url = request.getContextPath()+"/help/qnaList";
				m.addAttribute("msg", msg);
				m.addAttribute("url", url);
				return "/view/alert";
			}
		}	//일반게시글
			
		
		//comment count
		String refId = qnaId;
		int commentCount = qcd.countOne(refId);
		
		//comment select
		Qna_Comment comment = qcd.selectOneComment(refId);
//		System.out.println(comment);
		m.addAttribute("comment", comment);
		m.addAttribute("commentCount", commentCount);
		m.addAttribute("q", q);
		return "/view/help/qnaInfo";

	}

	/*
	 * // qnaboardwrite view
	 * 
	 * @RequestMapping("qnaWrite") public String qnaWrite(HttpServletRequest
	 * request, HttpServletResponse response) {
	 * 
	 * return "/view/help/qnaWrite"; }
	 */

	// qnaboardwrite process
	@RequestMapping("qnaWritePro")
	public String qnaWritePro(Qna q) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//로그인 체크
		String memid = (String) request.getSession().getAttribute("memid");
		if (memid == null) {
			msg = "로그인이 필요한 서비스 입니다.";
			url = request.getContextPath()+"/member/login";
			m.addAttribute("msg", msg);
			m.addAttribute("url", url);
			return "/view/alert";
		}

		String writerId = (String) request.getSession().getAttribute("memid");
		String qnaId = "qna" + qd.nextNum();

		q.setQna_Id(qnaId);
		q.setWriter(writerId);

		int num = qd.insert(q);

		if (num > 0) { // 게시글 작성 완료
			msg = "QnA 질문이 등록 되었습니다.";
			url = request.getContextPath() + "/help/qnaList";
		} else {
			msg = "게시글 등록 실패";
			url = request.getContextPath() + "/help/qnaWrite";
		}

		m.addAttribute("msg", msg);
		m.addAttribute("url", url);

		return "/view/alert";
	}
	
	// qnaboarcomment process
		@RequestMapping("commentWritePro")
		public String commentPro(String qna_Id, Qna_Comment qc) {
			try {
				request.setCharacterEncoding("UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//어드민 계정 체크
			String memid = (String) request.getSession().getAttribute("memid");
			if (!memid.equals("admin")) {
				msg = "어드민 계정만 답글을 등록하실 수 있습니다.";
				url = request.getContextPath()+"/help/qnaInfo";
				m.addAttribute("msg", msg);
				m.addAttribute("url", url);
				return "/view/alert";
			}
			
			String id = "qnacomment"+qcd.nextNum();
			
			qc.setWriter(memid);
			qc.setComment_Id(id);
			qc.setRefId(qna_Id);
			qc.setTitle("");
			
		//	System.out.println(qc);
			
			int num = qcd.insert(qc);
			
			if(num>0) {
				msg = "답글이 등록되었습니다.";
				url = request.getContextPath()+"/help/qnaInfo";
			}else {
				msg = "답글 등록 실패";
				url = request.getContextPath()+"/help/qnaInfo";
			}
			
			m.addAttribute("msg", msg);
			m.addAttribute("url", url);

			return "/view/alert";
		}
		
		//qna 게시글 수정
		@RequestMapping("qnaUpdate")
		public String qnaUpdate(String qna_Id) {
			try {
				request.setCharacterEncoding("UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
					
			Qna q = new Qna();
			String id = (String)request.getSession().getAttribute("memid");
			q = qd.selectOne(qna_Id);
					
			if(id==null || !id.equals(q.getWriter())) { //작성자인지 체크
				msg = "게시글 수정은 작성자만 할 수 있습니다.";
				url = request.getContextPath()+"/help/qnaInfo";
				m.addAttribute("msg", msg);
				m.addAttribute("url", url);
				return "/view/alert";
			}

			m.addAttribute("q", q);
			return "/view/help/qnaUpdate";
		}
				
		//qna 게시글 수정 pro
		@RequestMapping("qnaUpdatePro")
		public String qnaUpdatePro(Qna q) {
			try {
				request.setCharacterEncoding("UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
					
			int num = qd.update(q.getTitle(),q.getContent(),q.getQna_Id(),q.getSecret());
					
			if(num>0) {		//성공적으로 수정이 되었을 경우
				msg = "수정이 되었습니다";
				url = request.getContextPath()+"/help/qnaInfo";
			}else {			//수정에 오류 발생
				msg = "수정에 실패하였습니다";
				url = request.getContextPath()+"/help/qnaInfo?qna_Id="+q.getQna_Id();
			}

			m.addAttribute("msg", msg);
			m.addAttribute("url", url);
			return "/view/alert";
		}
				
		//qna 게시글 삭제
		@RequestMapping("qnaDeletePro")
		public String qnaDeletePro(String qna_Id) {
			try {
				request.setCharacterEncoding("UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
					
			Qna q = new Qna();
			q = qd.selectOne(qna_Id);
			String id = (String)request.getSession().getAttribute("memid");

			if(id==null || !id.equals(q.getWriter())) { //작성자인지 체크
				msg = "게시글 삭제는 작성자만 할 수 있습니다.";
				url = request.getContextPath()+"/help/qnaInfo";
				m.addAttribute("msg", msg);
				m.addAttribute("url", url);
				return "/view/alert";
			}
					
			int num = qd.delete(qna_Id);
					
			if(num>0) {		//성공적으로 삭제가 되었을 경우
				msg = "삭제가 되었습니다";
				url = request.getContextPath()+"/help/qnaList";
			}else {			//삭제에 오류 발생
				msg = "삭제에 실패하였습니다";
				url = request.getContextPath()+"/help/qnaInfo";
			}

			m.addAttribute("msg", msg);
			m.addAttribute("url", url);
			return "/view/alert";
		}

}

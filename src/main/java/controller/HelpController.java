package controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Qna;
import model.Qna_Comment;
import service.QnaDao;
import service.Qna_CommentDao;
import service.StudyDao;

//@WebServlet("/help/*")
public class HelpController extends MskimRequestMapping {

	static String msg = "";
	static String url = "";

	// qnalsit view
	@RequestMapping("qnaList")
	public String qnaList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int limit = 3; // 한 페이지에 보이는 게시글 수

		// 페이지 받아오기
		String pageNum = "";
		if (request.getParameter("pageNum") != null) {
			pageNum = request.getParameter("pageNum");
		} else {
			pageNum = "1";
		}
		int pageInt = Integer.parseInt(pageNum);

		QnaDao qd = new QnaDao();
		List<Qna> list = qd.qnaList(pageInt, limit);

		int bottomLine = 5; // 최대 페이징 수

		int qnaCount = qd.qnaCount();
		int startPage = (pageInt - 1) / bottomLine * bottomLine + 1;
		int endPage = startPage + bottomLine - 1;
		int maxPage = (qnaCount / limit) + (qnaCount % limit == 0 ? 0 : 1);
		if (endPage > maxPage)
			endPage = maxPage;

		// 게시글마다 달린 댓글 수 list
		Qna_CommentDao qcd = new Qna_CommentDao();
		List countList = qcd.countList(pageInt, limit);
		
		
		request.setAttribute("pageInt", pageInt);
		request.setAttribute("countList", countList);
		request.setAttribute("startPage", startPage);
		request.setAttribute("bottomLine", bottomLine);
		request.setAttribute("endPage", endPage);
		request.setAttribute("maxPage", maxPage);
		request.setAttribute("list", list);
		return "/view/help/qnaList.jsp";
	}

	// qnaboard view
	@RequestMapping("qnaInfo")
	public String mentorInfo(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		QnaDao qd = new QnaDao();
		Qna q = new Qna();
		
		//session에 info위치 저장
		String qnaId = request.getParameter("qna_Id");
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
				Qna_CommentDao qcd = new Qna_CommentDao();
				
				//comment count
				String refId = qnaId;
				int commentCount = qcd.countOne(refId);
				
				//comment select
				Qna_Comment comment = qcd.selectOneComment(refId);
				System.out.println(comment);
				request.setAttribute("comment", comment);
				request.setAttribute("commentCount", commentCount);
				request.setAttribute("q", q);
				return "/view/help/qnaInfo.jsp";
			}else { //admin, 작성자가 아닌 경우 접근불가
				msg = "작성자와 관리자만 볼 수 있는 게시글 입니다.";
				url = request.getContextPath()+"/help/qnaList";
				request.setAttribute("msg", msg);
				request.setAttribute("url", url);
				return "/view/alert.jsp";
			}
		}	//일반게시글
			
		Qna_CommentDao qcd = new Qna_CommentDao();
		
		//comment count
		String refId = qnaId;
		int commentCount = qcd.countOne(refId);
		
		//comment select
		Qna_Comment comment = qcd.selectOneComment(refId);
		System.out.println(comment);
		request.setAttribute("comment", comment);
		request.setAttribute("commentCount", commentCount);
		request.setAttribute("q", q);
		return "/view/help/qnaInfo.jsp";

	}

	/*
	 * // qnaboardwrite view
	 * 
	 * @RequestMapping("qnaWrite") public String qnaWrite(HttpServletRequest
	 * request, HttpServletResponse response) {
	 * 
	 * return "/view/help/qnaWrite.jsp"; }
	 */

	// qnaboardwrite process
	@RequestMapping("qnaWritePro")
	public String qnaWritePro(HttpServletRequest request, HttpServletResponse response) {
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
			request.setAttribute("msg", msg);
			request.setAttribute("url", url);
			return "/view/alert.jsp";
		}

		Qna q = new Qna();
		QnaDao qd = new QnaDao();

		String writerId = (String) request.getSession().getAttribute("memid");
		String qnaId = "qna" + qd.nextNum();

		q.setQna_Id(qnaId);
		q.setContent(request.getParameter("content")); // 임시
		q.setSecret(Integer.parseInt(request.getParameter("secret"))); // 임시
		q.setTitle(request.getParameter("title")); // 임시
		q.setWriter(writerId);

		int num = qd.insert(q);

		if (num > 0) { // 게시글 작성 완료
			msg = "QnA 질문이 등록 되었습니다.";
			url = request.getContextPath() + "/help/qnaList";
		} else {
			msg = "게시글 등록 실패";
			url = request.getContextPath() + "/help/qnaWrite";
		}

		request.setAttribute("msg", msg);
		request.setAttribute("url", url);

		return "/view/alert.jsp";
	}
	
	// qnaboarcomment process
		@RequestMapping("commentWritePro")
		public String commentPro(HttpServletRequest request, HttpServletResponse response) {
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
				request.setAttribute("msg", msg);
				request.setAttribute("url", url);
				return "/view/alert.jsp";
			}
			
			Qna_CommentDao qcd = new Qna_CommentDao();
			Qna_Comment qc = new Qna_Comment();
			
			String qnaId = request.getParameter("qna_Id");
			String refId = qnaId;
			String id = "qnacomment"+qcd.nextNum();
			String title=" ";
			if(request.getParameter("title")!=null) {
				title = request.getParameter("title");
			}
			
			qc.setWriter(memid);
			qc.setComment_Id(id);
			qc.setContent(request.getParameter("content"));
			qc.setRefId(refId);
			qc.setTitle(title);
			
			System.out.println(qc);
			
			int num = qcd.insert(qc);
			
			if(num>0) {
				msg = "답글이 등록되었습니다.";
				url = request.getContextPath()+"/help/qnaInfo";
			}else {
				msg = "답글 등록 실패";
				url = request.getContextPath()+"/help/qnaInfo";
			}
			
			request.setAttribute("msg", msg);
			request.setAttribute("url", url);

			return "/view/alert.jsp";
		}
		
		//qna 게시글 수정
		@RequestMapping("qnaUpdate")
		public String qnaUpdate(HttpServletRequest request, HttpServletResponse response) {
			try {
				request.setCharacterEncoding("UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
					
			QnaDao qd = new QnaDao();
			Qna q = new Qna();
			String qna_Id = request.getParameter("qna_Id");
			String id = (String)request.getSession().getAttribute("memid");
			q = qd.selectOne(qna_Id);
					
			if(id==null || !id.equals(q.getWriter())) { //작성자인지 체크
				msg = "게시글 수정은 작성자만 할 수 있습니다.";
				url = request.getContextPath()+"/help/qnaInfo";
				request.setAttribute("msg", msg);
				request.setAttribute("url", url);
				return "/view/alert.jsp";
			}

			request.setAttribute("q", q);
			return "/view/help/qnaUpdate.jsp";
		}
				
		//qna 게시글 수정 pro
		@RequestMapping("qnaUpdatePro")
		public String qnaUpdatePro(HttpServletRequest request, HttpServletResponse response) {
			try {
				request.setCharacterEncoding("UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
							
			QnaDao qd = new QnaDao();
			String qna_Id = request.getParameter("qna_Id");
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			int secret = Integer.parseInt(request.getParameter("secret"));
					
			int num = qd.update(title,content,qna_Id,secret);
					
			if(num>0) {		//성공적으로 수정이 되었을 경우
				msg = "수정이 되었습니다";
				url = request.getContextPath()+"/help/qnaInfo";
			}else {			//수정에 오류 발생
				msg = "수정에 실패하였습니다";
				url = request.getContextPath()+"/help/qnaInfo?qna_Id="+qna_Id;
			}

			request.setAttribute("msg", msg);
			request.setAttribute("url", url);
			return "/view/alert.jsp";
		}
				
		//qna 게시글 삭제
		@RequestMapping("qnaDeletePro")
		public String qnaDeletePro(HttpServletRequest request, HttpServletResponse response) {
			try {
				request.setCharacterEncoding("UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
					
			QnaDao qd = new QnaDao();
			Qna q = new Qna();
			String qna_Id = request.getParameter("qna_Id");
			q = qd.selectOne(qna_Id);
			String id = (String)request.getSession().getAttribute("memid");

			if(id==null || !id.equals(q.getWriter())) { //작성자인지 체크
				msg = "게시글 삭제는 작성자만 할 수 있습니다.";
				url = request.getContextPath()+"/help/qnaInfo";
				request.setAttribute("msg", msg);
				request.setAttribute("url", url);
				return "/view/alert.jsp";
			}
					
			int num = qd.delete(qna_Id);
					
			if(num>0) {		//성공적으로 삭제가 되었을 경우
				msg = "삭제가 되었습니다";
				url = request.getContextPath()+"/help/qnaList";
			}else {			//삭제에 오류 발생
				msg = "삭제에 실패하였습니다";
				url = request.getContextPath()+"/help/qnaInfo";
			}

			request.setAttribute("msg", msg);
			request.setAttribute("url", url);
			return "/view/alert.jsp";
		}

}

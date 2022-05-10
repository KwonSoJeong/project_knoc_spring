package controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

//import com.oreilly.servlet.MultipartRequest;
//import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import model.Class_Content;
import model.Classes;
import model.Knoc_Member;
import model.Member_Study_Info;
import model.WebChat;
import model.WishList;
import service.CategoryDao;
import service.Class_ContentDao;
import service.ClassesDao;
import service.Knoc_MemberDao;
import service.Member_Study_InfoDao;
import service.WebChatDao;
import service.WishListDao;

@Controller
@RequestMapping("/classes/")
public class ClassesController {
	
	HttpServletRequest request;
	Model model;
	HttpSession session;
	
	@Autowired
	CategoryDao cgd;
	@Autowired
	Class_ContentDao ccd;
	@Autowired
	ClassesDao cd;
	@Autowired
	Member_Study_InfoDao msd;
	@Autowired
	WebChatDao wcd;
	@Autowired
	WishListDao wld;
	@Autowired
	Knoc_MemberDao md;
	
	@ModelAttribute
	void init(HttpServletRequest request, Model model) {
		this.request = request;
		this.model = model;
		this.session = request.getSession();
	}
	
	// main화면 view
	@RequestMapping("main")
	public String main() {
		String userId = (String) session.getAttribute("memid");
		String groupId = userId;
		
		if (userId == null) {
			groupId = "";
		}
		
		// 일반 사용자의 경우, 문의톡 아이콘을 눌렀을 때 바로 대화 내용 출력
		List<WebChat> chatList = wcd.chatList(groupId);
		// 관리자(admin)일 경우, 문의톡 아이콘을 눌렀을 때 우선 문의한 유저 리스트가 먼저 출력
		List<String> groupList = wcd.groupList();
		
		//WishListDao wld = new WishListDao();
		if (userId != null) {
			List<Map<String, Object>> wishList = wld.wishListOne(userId);
			model.addAttribute("wishList", wishList);
		}
		List<Classes> newClassList = cd.sortedClassList("regdate");
		List<Classes> favoriteClassList = cd.sortedClassList("favorite");
		
		model.addAttribute("userId", userId);
		model.addAttribute("groupId", groupId);
		model.addAttribute("chatList", chatList);
		model.addAttribute("groupList", groupList);
		model.addAttribute("newClassList", newClassList);
		model.addAttribute("favoriteClassList", favoriteClassList);
		
		return "/view/main";
	}
	
	// main화면 문의톡에서 img 업로드 할 때 사용할 페이지 
	@RequestMapping("imgUpload")
	public String imgUpload(MultipartFile file) {
		String path = request.getServletContext().getRealPath("/") + "chatimg/";
		
		// 파일 이름 중복 방지를 위해 원본 파일 이름 앞에 저장일시, 임의의 난수 붙임
		String oriFileName = file.getOriginalFilename();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmssSSS");
		int randomNum = (int) (Math.random() * 1000);
		
		String newFileName = sdf.format(System.currentTimeMillis()) + "_" + randomNum + "_" + oriFileName;
		
		try {
			file.transferTo(new File(path, newFileName));
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		model.addAttribute("filename", newFileName);
		
		return "/single/imgUpload";
	}
	
	// main화면 문의톡에서 admin 계정에서 사용할 문의한 고객 리스트 전달
	@RequestMapping("adminChat")
	public String adminChat(String groupId) {
		//HttpSession session = request.getSession();
		
		//String groupId = request.getParameter("groupId");
		String userId = (String) session.getAttribute("memid");
		
		//WebChatDao wcd = new WebChatDao();
		List<WebChat> chatList = wcd.chatList(groupId);
		
		model.addAttribute("groupId", groupId);
		model.addAttribute("userId", userId);
		model.addAttribute("chatList", chatList);
		
		return "/single/adminChat";
	}
	
	// 클래스 리스트 view
	@RequestMapping("classList")
	public String classList(@RequestParam(value = "pageInt", required = false) String pageNum, 
							@RequestParam(value = "category_id", required = false) String category,
							@RequestParam(value = "search_keyword", required = false) String title) {
		String userId = (String) session.getAttribute("memid");
		
		// page 번호를 지정하지 않았을 시 1페이지부터 시작
		int pageInt = 1;//얘를 2로 바꿔서 다오에서 리미트가 2인채로 받아오고싶은건데
		// 한 페이지 당 최대 12개 요소까지 출력
		int limit = 12;

		if (pageNum != null) {
			pageInt = Integer.parseInt(pageNum);
		}
		
		// 카테고리를 전달하지 않고 view를 출력하면 전체 리스트 반환
		List<Classes> classList = cd.classList(pageInt, limit);
		System.out.println(classList.size());
		// 카테고리를 전달하고 view를 출력하면 해당 카테고리에 맞는 리스트 반환
		if (category != null) {
			classList = cd.classifiedList(category, pageInt, limit); 
		}
		
		// 검색어를 입력하고 view를 출력하면 해당 단어가 제목에 포함된 리스트 반환
		if (title != null) {
			classList = cd.searchedList(title, pageInt, limit);
		}
		
		//WishListDao wld = new WishListDao();
		if (userId != null) {
			List<Map<String, Object>> wishList = wld.wishListOne(userId);
			model.addAttribute("wishList", wishList);
		}
		
	//	model.addAttribute("pageInt", pageInt);
	//	model.addAttribute("limit", limit);
	//	model.addAttribute("size", classList.size());
		model.addAttribute("userId", userId);
		model.addAttribute("classList", classList);
		if (pageInt==1)  {
			return "/view/classes/classList";
		} else {
		return "/single/singleClass";}
		
	}
	
	// 신규 클래스 등록 view
	@RequestMapping("classUpload")
	public String classUpload() {
		String id = (String) session.getAttribute("memid");
		
		if (id == null) {
			String msg = "로그인 정보가 없습니다.";
			String url = request.getContextPath() + "/member/login";
			
			model.addAttribute("msg", msg);
			model.addAttribute("url", url);
			
			return "/view/alert";
		}
		
		return "/view/classes/classUpload";
	}
	
	// 신규 클래스 등록 process
	@RequestMapping("classUploadPro")
	public String classUploadPro(Classes newClass, String[] contentTitle, MultipartFile[] contentFile) {
		String id = (String) session.getAttribute("memid");
		
		// 사용자에게 입력받지 않는 pk값, 공유자 아이디 지정
		newClass.setClass_id("class" + cd.newClassNum());
		newClass.setLec_id(id);
		
		String msg = "클래스 등록에 실패하였습니다.";
		String url = request.getContextPath() + "/classes/classUpload";
		
		// 클래스 업로드
		int classResult = cd.classUpload(newClass);
		
		// 차시별 컨텐츠 파일 업로드 경로
		String path = request.getServletContext().getRealPath("/") + "/contentfile/";
		
		// 컨텐츠 업로드 정상적으로 되었는지 확인 위한 변수
		int contentResult = 0;

		String[] fileNameArr = new String[contentFile.length];
		
		// 입력한 컨텐츠 파일 서버에 저장하고, 파일 이름을 따로 배열에 저장
		for (int i = 0; i < contentFile.length; i++) {
			// 파일 이름 중복 방지를 위해 원본 파일 이름 앞에 저장일시, 임의의 난수 붙임
			String oriFileName = contentFile[i].getOriginalFilename();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmssSSS");
			int randomNum = (int) (Math.random() * 1000);

			String newFileName = sdf.format(System.currentTimeMillis()) + "_" + randomNum + "_" + oriFileName;
			try {
				contentFile[i].transferTo(new File(path, newFileName));
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			fileNameArr[i] = newFileName;
		}
		
		for (int i = 0; i < contentTitle.length; i++) {
			
			Class_Content newContent = new Class_Content();
			
			newContent.setClass_Id(newClass.getClass_id());
			newContent.setContent_Id("content" + ccd.newContentNum());
			newContent.setTitle(contentTitle[i]);
			newContent.setFile1(fileNameArr[i]);
			
			if (ccd.contentUpload(newContent) > 0) {
				contentResult++;
			}
		}
		
		// 클래스와 각 차시별 컨텐츠가 정상적으로 DB에 저장되면
		if (classResult > 0 && contentResult == contentTitle.length) {
			msg = "클래스가 정상적으로 등록되었습니다.";
			url = request.getContextPath() + "/classes/classInfo?class_id=" + newClass.getClass_id();
			
			Member_Study_Info msi = new Member_Study_Info(newClass.getLec_id(), newClass.getClass_id(), 1, msd.nextSeq());
			
			msd.insertInfo(msi);
		}
		
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);

		return "/view/alert";
		
	}
	
	// 클래스 썸네일 등록 view
	@RequestMapping("thumbnailForm")
	public String thumbnailForm() {

		return "/single/thumbnailForm";
	}

	// 클래스 썸네일 등록 process
	@RequestMapping("thumbnailPro")
	public String thumbnailPro(MultipartFile thumbnail) {
		String path = request.getServletContext().getRealPath("/") + "thumbnail/";
		
		// 파일 이름 중복 방지를 위해 원본 파일 이름 앞에 저장일시, 임의의 난수 붙임
		String oriFileName = thumbnail.getOriginalFilename();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmssSSS");
		int randomNum = (int) (Math.random() * 1000);
		
		String newFileName = sdf.format(System.currentTimeMillis()) + "_" + randomNum + "_" + oriFileName;
		
		try {
			thumbnail.transferTo(new File(path, newFileName));
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		model.addAttribute("filename", newFileName);
		
		return "/single/thumbnailPro";
	}

	// 클래스 상세 view 
	@RequestMapping("classInfo")
	public String classInfo(String class_id) {
		Classes classone = cd.classOne(class_id);
		
		String categoryName = cgd.selectCategoryName(classone.getCategory_id());
		
		List<Class_Content> contentList = ccd.contentList(class_id);
		int contentNo = 1;
		// parameter로 전달된 classId는 session에 저장, content view 출력 시 활용
		HttpSession session = request.getSession();
		session.setAttribute("classId", class_id);
		
		model.addAttribute("contentNo", contentNo);
		model.addAttribute("classone", classone);
		model.addAttribute("category", categoryName);
		model.addAttribute("contentList", contentList);
		return "/view/classes/classInfo";
	}
	
	// 클래스 수강신청 클릭 시 process
	@RequestMapping("classRegister")
	public String classRegister() {
		String id = (String) session.getAttribute("memid");
		String class_id = (String) session.getAttribute("classId");
		// 수강신청을 눌렀을 때 로그인이 안되어있으면 로그인 화면으로 이동
		String msg = "로그인 후 이용 가능합니다.";
		String url = request.getContextPath() + "/member/login";
		
		Classes classOne = cd.classOne(class_id);
		
		if (id != null) {
			// 수강신청을 눌렀을 때 수강신청이 되어있는 상태라면 바로 컨텐츠 화면으로 이동
			Member_Study_Info msi = msd.infoOne(id, class_id);
			if (msi != null) {
				msg = "수강신청이 완료된 강의입니다. 수강 화면으로 이동합니다.";
				url = request.getContextPath() + "/classes/classContent";
			} else if (classOne.getPrice() == 0) {
				// 수강신청을 눌렀을 때 수강신청이 안 되어 있으면 db 추가하고 수강신청 완료 메세지 출력 후 컨텐츠 화면으로 이동
				msg = "수강신청이 완료되었습니다.";
				url = request.getContextPath() + "/classes/classContent";
				
				Member_Study_Info newInfo = new Member_Study_Info(id, class_id, 2, msd.nextSeq());
				msd.insertInfo(newInfo);
				
			} else if (classOne.getPrice() > 0) {
				msg = "결제가 필요한 클래스입니다.";
				url = request.getContextPath() + "/classes/classPay";
			}

		}
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);

		return "/view/alert";
		
	}
	
	@RequestMapping("classPay")
	public String classPay() {
		String classId = (String) session.getAttribute("classId");
		Classes classOne = cd.classOne(classId);
		
		String id = (String) session.getAttribute("memid");
		Knoc_Member member = md.selectOne(id);
		
		model.addAttribute("classOne", classOne);
		model.addAttribute("member", member);
		return "/view/classes/classPay";
	}
	
	@RequestMapping("classPayPro")
	public String classPayPro(String class_id) {
		String id = (String) session.getAttribute("memid");
		
		Member_Study_Info newInfo = new Member_Study_Info(id, class_id, 2, msd.nextSeq());
		msd.insertInfo(newInfo);
		
		String msg = "결제 및 수강신청이 완료되었습니다. 수강 화면으로 이동합니다.";
		String url = request.getContextPath() + "/classes/classContent?class_id=" + class_id;
		
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		
		return "/view/alert";
	}
	
	// 클래스 수강 view
	@RequestMapping("classContent")
	public String classContent(@RequestParam(value = "class_id", required = false) String classId,
							   @RequestParam(value = "content_id", required = false) String contentId) {
		HttpSession session = request.getSession();
		
		// mypage를 통해서 content 페이지로 왔을 때는 세션에 url에서 classid 받아오고, session에 저장
		if (classId != null) {
			session.setAttribute("classId", classId);
		} 
		
		// classInfo 화면을 거쳐서 content 페이지로 왔을 때는 세션에 classId가 저장되어 있음
		classId = (String) session.getAttribute("classId");
		String id = (String) session.getAttribute("memid");
		
		List<Class_Content> contentList = ccd.contentList(classId);
		
		if (contentId == null) {
			contentId = contentList.get(0).getContent_Id();
		}
		Class_Content contentOne = ccd.contentOne(classId, contentId);
		
		if (id == null) {
			String msg = "로그인 정보가 없습니다.";
			String url = request.getContextPath() + "/member/login";
			
			model.addAttribute("msg", msg);
			model.addAttribute("url", url);
			
			return "/view/alert";
		}
		
		int contentNo = 1;
		
		model.addAttribute("contentList", contentList);
		model.addAttribute("content", contentOne);
		model.addAttribute("contentNo", contentNo);
		return "/view/classes/classContent";
	}
	
	// 클래스 관심등록 process, classInfo에서 javaScript + ajax로 구현
	@RequestMapping("classFavorite")
	public String classFavorite(@RequestParam(value = "class_id", required = false) String classId) {
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("memid");
		String status = "login-null";
		
		if (classId == null) {
			classId = (String) session.getAttribute("classId");
		}
		
		if (id != null) {
			WishList wishOne =  wld.wishOne(id, classId);

			if (wishOne == null) {
				WishList newWish = new WishList(wld.nextSeq(), id, classId);
				int num = wld.insertWish(newWish);
				int cntUp = cd.favoriteCntUp(classId);
				status = "favorite-Cnt-Up";
			} else {
				int num = wld.deleteWish(id, classId);
				int cntDown = cd.favoriteCntDown(classId);
				status = "favorite-Cnt-Down";
			}
			
		}
		
		Classes classone = cd.classOne(classId);
		int favoriteCnt = classone.getFavorite();
		 
		model.addAttribute("favoriteCnt", favoriteCnt);
		model.addAttribute("status", status);
		return "/single/classFavorite";
	}
	
	// 클래스 수정 view
	@RequestMapping("classUpdate")
	public String classUpdate(@RequestParam("class_id") String classId) {
		String id = (String) session.getAttribute("memid");
		
		if (id == null) {
			String msg = "로그인 정보가 없습니다.";
			String url = request.getContextPath() + "/member/login";
			
			model.addAttribute("msg", msg);
			model.addAttribute("url", url);
			
			return "/view/alert";
		} 
		Classes classOne = cd.classOne(classId);
		
		List<Class_Content> contentList = ccd.contentList(classId);
		
		model.addAttribute("classOne", classOne);
		model.addAttribute("contentList", contentList);
		return "/view/classes/classUpdate";
	}
	
	// 클래스 수정 process
	@RequestMapping("classUpdatePro")
	public String classUpdatePro(Classes updatedClass, String[] content_id,
								String[] contentTitle, String[] originalFile,
								 MultipartFile[] newFile) {
		
		// 수정가능 요소 : 클래스 소개글, 가격, 썸네일, 각 차시별 제목, 각 차시별 컨텐츠
		// 수정 불가능 : 클래스 아이디, 강사 아이디, 클래스 제목, 카테고리, 타입, 관심수, 등록일
		
		int classUpdateCnt = cd.classUpdate(updatedClass);
		String classId = updatedClass.getClass_id();
		
		String path = request.getServletContext().getRealPath("/") + "contentfile";
		int contentUpdateCnt = 0;

		for (int i = 0; i < content_id.length; i++) {
			Class_Content updatedContent = new Class_Content();
			updatedContent.setClass_Id(classId);
			updatedContent.setContent_Id(content_id[i]);
			updatedContent.setTitle(contentTitle[i]);
			
			// 새로운 컨텐츠 파일이 업로드 된 경우 서버에 새로 업로드한 후 업데이트
			if (newFile[i] != null) {
				// 파일 이름 중복 방지를 위해 원본 파일 이름 앞에 저장일시, 임의의 난수 붙임
				String oriFileName = newFile[i].getOriginalFilename();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmssSSS");
				int randomNum = (int) (Math.random() * 1000);

				String newFileName = sdf.format(System.currentTimeMillis()) + "_" + randomNum + "_" + oriFileName;

				try {
					newFile[i].transferTo(new File(path, newFileName));
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				updatedContent.setFile1(newFileName);
			} else {
				updatedContent.setFile1(originalFile[i]);
			}
			
			contentUpdateCnt += ccd.contentUpdate(updatedContent);

		}
		
		String msg = "";
		String url = "";
		
		if (classUpdateCnt > 0 && contentUpdateCnt == content_id.length) {
			msg = "클래스 수정이 정상적으로 처리되었습니다.";
			url = request.getContextPath() + "/classes/classInfo?class_id=" + classId;
		} else {
			msg = "클래스 수정에 실패하였습니다.";
			url = request.getContextPath() + "/classes/classUpdate?class_id=" + classId;
		}
		
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		
		return "/view/alert";
	}
	
	// 클래스 삭제 process
	@RequestMapping("classDeletePro")
	public String classDelete(@RequestParam("class_id") String classId) {
		int classDeleteCnt = cd.classDelete(classId);
		int listSize = (ccd.contentList(classId)).size(); 
		int contentDeleteCnt = ccd.contentDelete(classId);
				
		String msg = "";
		String url = request.getContextPath() + "/member/myPage";
		
		msd.deleteInfo(classId);
		
		// 클래스와 차시별 객체 전체가 정상적으로 삭제되면
		if (classDeleteCnt > 0 && contentDeleteCnt == listSize) {
			msg = "클래스 삭제가 정상적으로 처리되었습니다.";
		} else {
			msg = "클래스 삭제에 실패하였습니다.";
		}
		
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		return "/view/alert";
	}
}

package controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import model.Knoc_Member;
import model.Member_Study_Info;
import service.Knoc_MemberDao;
import service.Member_Study_InfoDao;
import service.WishListDao;

@Controller
@RequestMapping("/member/")
public class MemberController {
	
	HttpServletRequest request;
	Model model;
	HttpSession session;
	
	@Autowired
	Knoc_MemberDao md;
	@Autowired
	Member_Study_InfoDao msd;
	@Autowired
	WishListDao wd;
	
	@ModelAttribute
	void init(HttpServletRequest request, Model model) {
		this.request = request;
		this.model = model;
		this.session = request.getSession();
	}
	
	// 회원가입 view
	@RequestMapping("memberInput")
	public String memberInput() {
		
		return "/view/member/memberInput";
	}
	
	// 회원가입 process
	@RequestMapping("memberInputPro")
	public String memberInputPro(Knoc_Member newMember) {
		// Knoc_Member m = new Knoc_Member();
		Knoc_Member chk = new Knoc_Member();
		
		String msg = "이미 존재하는 ID 입니다";
		String url = request.getContextPath() + "/member/memberInput";
		
		newMember.setBlacklist("N");
		
		// 동일한 아이디 존재하는지 확인
		chk = md.selectOne(newMember.getId());
		
		if (chk == null) {
			int num = md.insertMember(newMember);
			if (num > 0) { // 회원가입 성공
				msg = "회원 가입이 완료되었습니다.";
				url = request.getContextPath() + "/member/login";
			} else { // 가입 실패
				msg = "회원 가입이 실패 하였습니다.";
				url = request.getContextPath() + "/member/login";
			}
		}
		
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		
		return "/view/alert";
	}
	
	// 회원가입, 수정 시 프로필 사진 등록 view
	@RequestMapping("pictureForm")
	public String pictureForm() {
		
		return "/single/pictureForm";
	}
	
	// 프로필 사진 등록 process
	@RequestMapping("picturePro")
	public String picturePro(MultipartFile profile) {
		String path = request.getServletContext().getRealPath("/")+"profile/";
		
		// 파일 이름 중복 방지를 위해 원본 파일 이름 앞에 저장일시, 임의의 난수 붙임
		String oriFileName = profile.getOriginalFilename();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmssSSS");
		int randomNum = (int) (Math.random() * 1000);
		
		String newFileName = sdf.format(System.currentTimeMillis()) + "_" + randomNum + "_" + oriFileName;
		
		try {
			profile.transferTo(new File(path, newFileName));
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		model.addAttribute("filename", newFileName);
		
		return "/single/picturePro";
	}
	
	// 회원가입 시 아이디 유효성 확인
	@RequestMapping("idChk")
	public String idChk(String id) {
		String chk = "";
		Knoc_Member member = md.selectOne(id);
		
		if (member == null) {
			chk = "ok";
		} else {
			chk = "alreadyExistId";
		}
		
		model.addAttribute("chk", chk);
		
		return "/single/userInputChk";
	}
	
	// 회원가입 시 비밀번호 유효성 체크
	@RequestMapping("pwdChk")
	public String pwdChk(String pwd) {
		String chk = "";
		
		Pattern pwdPattern = Pattern.compile("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W)");
		Matcher pwdMatcher = pwdPattern.matcher(pwd);
		
		if (!pwdMatcher.find()) {
			chk = "false";
		} else {
			chk = "true";
		}
		
		model.addAttribute("chk", chk);
		
		return "/single/userInputChk";
	}
	
	@RequestMapping("login")
	public String login() {

		return "/view/member/login";
	}

	@RequestMapping("loginPro")
	public String loginPro(String id, String pwd) {
		Knoc_Member m = md.selectOne(id);
		
		String msg = "아이디를 확인해 주세요";
		String url = request.getContextPath() + "/member/login";
		
		if (m != null) { // 계정이 존재함
			if (m.getPwd().equals(pwd)) { // 패스워드가 일치함
				//request.getSession().setAttribute("memid", id);
				session.setAttribute("memid", id);
				msg = "로그인 되었습니다.";
				url = request.getContextPath() + "/classes/main";
			} else { // 패스워드 불일치
				msg = "비밀번호를 확인해 주세요";
				url = request.getContextPath() + "/member/login";
			}
		}
			
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		
		return "/view/alert";
	}

	@RequestMapping("logout")
	public String logout() {
		
		//request.getSession().invalidate();
		session.invalidate();
		
		String msg = "로그아웃 되었습니다.";
		String url = request.getContextPath() + "/classes/main";
		
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		
		return "/view/alert";
	}
	
	// 회원정보 수정 view
	@RequestMapping("memberUpdate")
	public String memberUpdate() {
		
		// HttpSession session = request.getSession();
		String memid = (String) session.getAttribute("memid");
		
		String msg = "로그인 정보가 없습니다.";
		String url = request.getContextPath()+"/member/login";
		
		if (memid != null) {
			
			Knoc_Member member = md.selectOne(memid);
			model.addAttribute("member", member);
			
			return "/view/member/memberUpdate";
		}
		
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		
		return "/view/alert";
	}

	// 회원정보 수정 process
	@RequestMapping("memberUpdatePro")
	public String memberUpdatePro(Knoc_Member member) {
		String msg = "비밀번호가 일치하지 않습니다.";
		String url = request.getContextPath() + "/member/memberUpdate";
		
		// 비밀번호 일치 여부 확인하기 위해 아이디에 해당하는 회원 객체 생성
		Knoc_Member mem = md.selectOne(member.getId());
		
		// 비밀번호 일치 여부 확인
		if (mem.getPwd().equals(member.getPwd())) {
			int num = md.updateMember(member);

			if (num > 0) {
				msg = "회원정보가 수정되었습니다.";
			} else {
				msg = "회원정보 수정에 실패하였습니다.";
			}
		}
		
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		
		return "/view/alert";
	}
	
	// 비밀번호 수정 view
	@RequestMapping("password")
	public String password() {
		//HttpSession session = request.getSession();
		
		String id = (String) session.getAttribute("memid");
		
		if (id == null) {
			String msg = "로그인 정보가 없습니다.";
			String url = request.getContextPath() + "/member/login";
			
			model.addAttribute("msg", msg);
			model.addAttribute("url", url);
			
			return "/view/alert";
			
		}
		
		return "/view/member/password";
	}
	
	// 비밀번호 수정 process
	@RequestMapping("passwordPro")
	public String passwordPro(String pwd, String newpwd) {
		//HttpSession session = request.getSession();

		String id = (String) session.getAttribute("memid");
		Knoc_Member mem = md.selectOne(id);
		
		String msg = "비밀번호가 일치하지 않습니다.";
		String url = request.getContextPath() + "/member/password";
		
		if (mem.getPwd().equals(pwd)) {
			mem.setPwd(newpwd);
			int num = md.updatePwd(mem);
			
			if (num > 0) {
			msg = "비밀번호가 수정되었습니다.";
			url = request.getContextPath() + "/member/memberUpdate";
			
			} else {
				msg= "비밀번호 수정에 실패하였습니다.";
			}
		}
		
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);

		return "/view/alert";
	}

	// 회원탈퇴 view
	@RequestMapping("memberDelete")
	public String memberDelete() {
		//HttpSession session = request.getSession();

		String id = (String) session.getAttribute("memid");

		if (id == null) {
			String msg = "로그인 정보가 없습니다.";
			String url = request.getContextPath() + "/member/login";

			model.addAttribute("msg", msg);
			model.addAttribute("url", url);

			return "/view/alert";

		}
		Knoc_Member member = md.selectOne(id);

		model.addAttribute("member", member);

		return "/view/member/memberDelete";
	}
	
	// 회원탈퇴 process
	@RequestMapping("memberDeletePro")
	public String memberDeletePro(String id, String pwd) {
		Knoc_Member mem = md.selectOne(id);
		
		String msg = "비밀번호가 일치하지 않습니다.";
		String url = request.getContextPath() + "/member/memberDelete";

		if (mem.getPwd().equals(pwd)) {
			int num = md.deleteMember(mem);

			if (num > 0) {
				session.invalidate();

				msg = "탈퇴가 완료되었습니다.";
				url = request.getContextPath() + "/classes/main";
			} else {
				msg = "탈퇴에 실패하였습니다.";
			}
		}

		model.addAttribute("msg", msg);
		model.addAttribute("url", url);

		return "/view/alert";
	}
	
	// mypage view, 참여하는 클래스, 스터디, 관심 클래스, 멘토링, 회원 정보 전달
	@RequestMapping("myPage")
	public String myPage() {
		//HttpSession session = request.getSession();

		String id = (String) session.getAttribute("memid");

		if (id == null) {
			String msg = "로그인 정보가 없습니다.";
			String url = request.getContextPath() + "/member/login";

			model.addAttribute("msg", msg);
			model.addAttribute("url", url);

			return "/view/alert";

		}
		
		// 1) 회원 정보 가져오기
		Knoc_Member member = md.selectOne(id);
		
		// 2) 내가 개설한/수강중인 클래스 리스트 가져오기
		List<Map<String, Object>> classList = msd.infoClassList(id);
		// 3) 참여중인 스터디 리스트 가져오기
		List<Map<String, Object>> studyList = msd.infoStudyList(id);
		// 4) 참여중인 멘토링 리스트 가져오기
		List<Map<String, Object>> mentoringList = msd.infoMentoringList(id);
		
		// 5) 관심등록한 클래스 리스트 가져오기
		List<Map<String, Object>> wishList = wd.wishListOne(id);
		
		model.addAttribute("member", member);
		model.addAttribute("classList", classList);
		model.addAttribute("studyList", studyList);
		model.addAttribute("mentoringList", mentoringList);
		model.addAttribute("wishList", wishList);
		
		return "/view/member/myPage";
	}
	
}

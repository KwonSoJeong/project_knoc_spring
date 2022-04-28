package controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import model.Member_Study_Info;
import model.Notification;
import model.Study;
import service.Member_Study_InfoDao;
import service.MentoringDao;
import service.NotificationDao;
import service.StudyDao;

@Controller
@RequestMapping("/noti/")
public class NorificationController {
	static String msg = "";
	static String url = "";
	
	HttpServletRequest request;
	Model m;
	HttpSession session;
	@Autowired
	NotificationDao notid;
	@Autowired
	Member_Study_InfoDao msid;
	@Autowired
	StudyDao sd;
	@Autowired
	MentoringDao mtd;
	
	@ModelAttribute
	void init(HttpServletRequest request, Model m) {
		this.request = request;
		this.m = m;
		this.session = request.getSession();
	}
	
	@RequestMapping("notiList")
	public String notiList() {
		String id = (String) session.getAttribute("memid");
		List<Notification> notiList = notid.selectList(id);
		int unreadNoti = notid.unreadCnt(id);
		
		System.out.println("unreadNoti="+unreadNoti);
		System.out.println("notiList="+notiList);
		
		m.addAttribute("unreadNoti",unreadNoti);	//알람 리스트
		m.addAttribute("notiList",notiList);		//읽지 않은 알람 갯수
		return "/view/noti/notiList";
	}
	
	@RequestMapping("allReadChk")
	public String allReadChk() {
		String id = (String) session.getAttribute("memid");
		int num = notid.allReadChk(id);
		
		System.out.println("num="+num);
		
		return "redirect:/noti/notiList";
		
	}
	
	@RequestMapping("accept")
	public String accept(int no) {
		Notification noti = new Notification();
		noti = notid.selectOne(no);
		
		//참가 중복 체크
		if (msid.infoOne(noti.getFrom_Id(), noti.getNoti_Code()) != null) {
			msg = "참가 되어있는 유저입니다.";
			url = request.getContextPath() + "/noti/notiList";
			m.addAttribute("msg", msg);
			m.addAttribute("url", url);
			return "/view/alert";
		}
		
		//msi insert
		Member_Study_Info msi = new Member_Study_Info();
		
		msi.setId(noti.getFrom_Id());
		msi.setMember_study_id(noti.getNoti_Code());
		msi.setType(2);
		msi.setNo(msid.nextSeq());
		
		msid.insertInfo(msi);
		
		//type 전환 
		notid.typeChange(no);
		
		//읽음체크
		notid.readChk(no);
		
		//신청자에게 알람 보내기
		String id = (String) session.getAttribute("memid");
		String title="";
		String category = noti.getNoti_Code().charAt(0)=='s'?"스터디":"멘토링";
		if(category.equals("스터디")) {
			title = sd.selectOne(noti.getNoti_Code()).getTitle();
		}else {
			title = mtd.selectOne(noti.getNoti_Code()).getTitle();
		}
		String noti_Content = "["+title+"] "+category+" 참가 신청이 수락되었습니다.";
		
		Notification noti2 = new Notification();
		noti2.setNo(notid.nextNum());
		noti2.setNoti_Code(noti.getNoti_Code());
		noti2.setNoti_Content(noti_Content);
		noti2.setFrom_Id(id);
		noti2.setTo_Id(noti.getFrom_Id());
		noti2.setType(0);
		int num = notid.insertNoti(noti2);
		
		url = request.getContextPath()+"/noti/notiList";
		if(num!=0) {
			msg = noti.getFrom_Id()+"님의 신청이 수락되었습니다.";
		}else {
			msg = "오류 발생";
		}
				
		m.addAttribute("msg",msg);
		m.addAttribute("url",url);
		return "/view/alert";
	}
	
	@RequestMapping("cancel")
	public String cancel(int no) {
		Notification noti = new Notification();
		noti = notid.selectOne(no);

		//type 전환 
		notid.typeChange(no);
		 
		//읽음체크
		notid.readChk(no);
						
		//신청자에게 알람 보내기
		Notification noti2 = new Notification();
				
		String id = (String) session.getAttribute("memid");
		String title="";
		String category = noti.getNoti_Code().charAt(0)=='s'?"스터디":"멘토링";
		if(category.equals("스터디")) {
			title = sd.selectOne(noti.getNoti_Code()).getTitle();
		}else {
			title = mtd.selectOne(noti.getNoti_Code()).getTitle();
		}
		String noti_Content = "["+title+"] "+category+" 참가 신청이 거절되었습니다.";
						
		noti2.setNo(notid.nextNum());
		noti2.setNoti_Code(noti.getNoti_Code());
		noti2.setNoti_Content(noti_Content);
		noti2.setFrom_Id(id);
		noti2.setTo_Id(noti.getFrom_Id());
		noti2.setType(0);
		int num = notid.insertNoti(noti2);
				
		url = request.getContextPath()+"/noti/notiList";
		if(num!=0) {
			msg = noti.getFrom_Id()+"님의 신청이 거절되었습니다.";
		}else {
			msg = "오류 발생";
		}
				
		m.addAttribute("msg",msg);
		m.addAttribute("url",url);
		return "/view/alert";
	}
}

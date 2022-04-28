package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import model.Notification;
import util.MySqlSessionFactory;

@Component
public class NotificationDao {
	private final static String ns = "notification.";
	private Map<String, Object> map = new HashMap<String, Object>();
	
	@Autowired
	MySqlSessionFactory sqlSessionFactory;
	SqlSession sqlsession;
	@PostConstruct
	public void setSqlSession() {
		this.sqlsession = sqlSessionFactory.sqlmap.openSession();
	}
	
	public int nextNum() {
		return sqlsession.selectOne(ns+"nextNum");
	}
	
	public int insertNoti(Notification noti) {
		try {
			return sqlsession.insert(ns+"insertNoti",noti);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			sqlsession.commit();
		}
		return 0;
		
	}
	
	public List<Notification> selectList(String to_Id){
		return sqlsession.selectList(ns+"selectList",to_Id);
	}
	
	public int unreadCnt(String to_Id){
		return sqlsession.selectOne(ns+"unreadCnt",to_Id);
	}
	
	public int allReadChk(String to_Id) {
		try {
			return sqlsession.update(ns+"allReadChk",to_Id);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			sqlsession.commit();
		}
		return 0;
	}
	
	public int EntryCheck(String from_Id, String noti_Code ) {
		map.clear();
		map.put("from_Id", from_Id);
		map.put("noti_Code", noti_Code);
		return sqlsession.selectOne(ns+"EntryCheck",map);
	}
	
	public int typeChange(int no) {
		try {
			return sqlsession.update(ns+"typeChange",no);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			sqlsession.commit();
		}
		return 0;
	}
	
	public Notification selectOne(int no) {
		return sqlsession.selectOne(ns+"selectOne",no);
	}
	
	public int readChk(int no) {
		try {
			return sqlsession.update(ns+"readChk",no);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			sqlsession.commit();
		}
		return 0;
	}
	
}

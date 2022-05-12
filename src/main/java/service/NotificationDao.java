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
		try {
			setSqlSession();
		return sqlsession.selectOne(ns+"nextNum");
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			sqlsession.close();
		}
		return 0;
	}
	
	public int insertNoti(Notification noti) {
		try {
			setSqlSession();
			return sqlsession.insert(ns+"insertNoti",noti);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			sqlsession.commit();
		}
		return 0;
		
	}
	
	public List<Notification> selectList(String to_Id){
		try {
			setSqlSession();
		return sqlsession.selectList(ns+"selectList",to_Id);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			sqlsession.close();
		}
		return null;
	}
	
	public int unreadCnt(String to_Id){
		try {
			setSqlSession();
		return sqlsession.selectOne(ns+"unreadCnt",to_Id);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			sqlsession.close();
		}
		return 0;
	}
	
	public int allReadChk(String to_Id) {
		try {
			setSqlSession();
			return sqlsession.update(ns+"allReadChk",to_Id);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			sqlsession.commit();
		}
		return 0;
	}
	
	public int EntryCheck(String from_Id, String noti_Code ) {
		try {
			setSqlSession();
		map.clear();
		map.put("from_Id", from_Id);
		map.put("noti_Code", noti_Code);
		return sqlsession.selectOne(ns+"EntryCheck",map);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			sqlsession.close();
		}
		return 0;
	}
	
	public int typeChange(int no) {
		try {
			setSqlSession();
			return sqlsession.update(ns+"typeChange",no);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			sqlsession.commit();
		}
		return 0;
	}
	
	public Notification selectOne(int no) {
		try {
			setSqlSession();
		return sqlsession.selectOne(ns+"selectOne",no);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			sqlsession.close();
		}
		return null;
	}
	
	public int readChk(int no) {
		try {
			setSqlSession();
			return sqlsession.update(ns+"readChk",no);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			sqlsession.commit();
		}
		return 0;
	}
	
}

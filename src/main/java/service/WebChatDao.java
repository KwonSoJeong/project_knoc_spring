package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import model.WebChat;
import util.MySqlSessionFactory;

@Component
public class WebChatDao {
	private final static String ns = "webchat.";
	private Map<String, Object> map = new HashMap<>();
	
	@Autowired
	MySqlSessionFactory sqlSessionFactory;
	SqlSession sqlSession;
	
	@PostConstruct
	public void setSqlSession() {
		this.sqlSession = sqlSessionFactory.sqlmap.openSession();
	}
	
	// 채팅 메세지에 새 번호 붙이기 위해 시퀀스 번호 불러옴
	public int nextSeq() {
		try {
		setSqlSession();
		return sqlSession.selectOne(ns + "nextSeq");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return 0;
		
	}
	
	// 채팅 메세지 db에 저장
	public int insertChat(WebChat webChat) {
		try {
			setSqlSession();
			return sqlSession.insert(ns + "insertChat", webChat);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.commit();
		}
		return 0;
	}
	
	// 그룹 id에 따라 해당 채팅방에서 주고받은 메세지 리스트로 반환
	public List<WebChat> chatList(String groupId) {
		try {
			setSqlSession();
		return sqlSession.selectList(ns + "chatList", groupId);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return null;
	}
	
	// 각 유저의 id가 그룹의 id가 되므로, 관리자가 답변할 유저를 선택할 수 있게 그룹 id를 리스트로 반환
	public List<String> groupList() {
		try {
			setSqlSession();
		return sqlSession.selectList(ns + "groupList");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return null;
	}
	
	public int userChatCount(String userId) {
		try {
			setSqlSession();
		return sqlSession.selectOne(ns + "userChatCount", userId);
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		sqlSession.close();
	}
	return 0;
	}
	
	public int adminChatCount() {
		try {
			setSqlSession();
		return sqlSession.selectOne(ns + "adminChatCount");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return 0;
	}
	
	public void userReadChkUpdate(String userId) {
		try {
			setSqlSession();
			sqlSession.update(ns + "userReadChkUpdate", userId);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.commit();
		}
	}
	
	public void adminReadChkUpdate() {
		try {
			setSqlSession();
			sqlSession.update(ns + "adminReadChkUpdate");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.commit();
		}
	}
}

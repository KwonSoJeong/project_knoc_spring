package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import model.WebChat;
import util.MyBatisConnection;

public class WebChatDao {
	private final static String ns = "webchat.";
	private Map<String, Object> map = new HashMap<>();
	
	// 채팅 메세지에 새 번호 붙이기 위해 시퀀스 번호 불러옴
	public int nextSeq() {
		SqlSession sqlSession = MyBatisConnection.getConnection();
		
		try {
			return sqlSession.selectOne(ns + "nextSeq");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(sqlSession);
		}
		
		return 0;
	}
	
	// 채팅 메세지 db에 저장
	public int insertChat(WebChat webChat) {
		SqlSession sqlSession = MyBatisConnection.getConnection();

		try {
			return sqlSession.insert(ns + "insertChat", webChat);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(sqlSession);
		}
		return 0;
	}
	
	// 그룹 id에 따라 해당 채팅방에서 주고받은 메세지 리스트로 반환
	public List<WebChat> chatList(String groupId) {
		SqlSession sqlSession = MyBatisConnection.getConnection();

		try {
			return sqlSession.selectList(ns + "chatList", groupId);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(sqlSession);
		}

		return null;
	}
	
	// 각 유저의 id가 그룹의 id가 되므로, 관리자가 답변할 유저를 선택할 수 있게 그룹 id를 리스트로 반환
	public List<String> groupList() {
		SqlSession sqlSession = MyBatisConnection.getConnection();
		
		try {
			return sqlSession.selectList(ns + "groupList");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(sqlSession);
		}
		
		return null;
	}
}

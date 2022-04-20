package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import model.Class_Content;
import util.MyBatisConnection;

public class Class_ContentDao {
	private final static String ns = "class_content.";
	private static Map<String, Object> map = new HashMap<>();

	// 신규 컨텐츠 아이디 생성을 위해 시퀀스 다음 번호 반환
	public int newContentNum() {
		SqlSession sqlSession = MyBatisConnection.getConnection();

		try {
			return sqlSession.selectOne(ns + "newContentNum");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(sqlSession);
		}

		return 0;
	}
	
	// 신규 컨텐츠 등록
	public int contentUpload(Class_Content newContent) {
		SqlSession sqlSession = MyBatisConnection.getConnection();
		
		try {
			return sqlSession.insert(ns + "contentUpload", newContent);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(sqlSession);
		}

		return 0;
	}
	
	public Class_Content contentOne(String classId, String contentId) {
		SqlSession sqlSession = MyBatisConnection.getConnection();
		
		map.clear();
		map.put("classId", classId);
		map.put("contentId", contentId);
		
		try {
			return sqlSession.selectOne(ns + "contentOne", map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(sqlSession);
		}

		return null;
	}
	
	// 클래스 아이디를 매개변수로 해당 클래스의 컨텐츠를 리스트로 반환
	public List<Class_Content> contentList(String classId) {
		SqlSession sqlSession = MyBatisConnection.getConnection();
		
		try {
			return sqlSession.selectList(ns + "contentList", classId);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(sqlSession);
		}

		return null;
	}
	
	// 컨텐츠 수정
	public int contentUpdate(Class_Content updatedContent) {
		SqlSession sqlSession = MyBatisConnection.getConnection();
		
		try {
			return sqlSession.update(ns + "contentUpdate", updatedContent);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(sqlSession);
		}

		return 0;
	}
	
	// 컨텐츠 삭제
	public int contentDelete(String classId) {
		SqlSession sqlSession = MyBatisConnection.getConnection();
		
		try {
			return sqlSession.delete(ns + "contentDelete", classId);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(sqlSession);
		}

		return 0;
	}
	
	/* DAO 테스트 코드
	public static void main(String[] args) {
		SqlSession sqlSession = MyBatisConnection.getConnection();
		map.clear();
		map.put("classId", "class8");
		map.put("contentNo", "3");
		Class_Content con = sqlSession.selectOne(ns + "contentOne", map);
		
		System.out.println(con);
	}
	*/
}

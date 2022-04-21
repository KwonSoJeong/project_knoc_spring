package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import model.Class_Content;
import util.MyBatisConnection;
import util.MySqlSessionFactory;

@Component
public class Class_ContentDao {
	private final static String ns = "class_content.";
	private static Map<String, Object> map = new HashMap<>();
	
	@Autowired
	MySqlSessionFactory sqlSessionFactory;
	SqlSession sqlSession;
	
	@PostConstruct
	public void setSqlSession() {
		this.sqlSession = sqlSessionFactory.sqlmap.openSession();
	}
	
	// 신규 컨텐츠 아이디 생성을 위해 시퀀스 다음 번호 반환
	public int newContentNum() {
		
		return sqlSession.selectOne(ns + "newContentNum");
		
	}
	
	// 신규 컨텐츠 등록
	public int contentUpload(Class_Content newContent) {
		
		try {
			return sqlSession.insert(ns + "contentUpload", newContent);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.commit();
		}

		return 0;
	}
	
	public Class_Content contentOne(String classId, String contentId) {
		map.clear();
		map.put("classId", classId);
		map.put("contentId", contentId);
		
		return sqlSession.selectOne(ns + "contentOne", map);
		
	}
	
	// 클래스 아이디를 매개변수로 해당 클래스의 컨텐츠를 리스트로 반환
	public List<Class_Content> contentList(String classId) {
		
		return sqlSession.selectList(ns + "contentList", classId);
		
	}
	
	// 컨텐츠 수정
	public int contentUpdate(Class_Content updatedContent) {
		
		try {
			return sqlSession.update(ns + "contentUpdate", updatedContent);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.commit();
		}

		return 0;
	}
	
	// 컨텐츠 삭제
	public int contentDelete(String classId) {
		
		try {
			return sqlSession.delete(ns + "contentDelete", classId);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.commit();
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

package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import model.Classes;
import util.MyBatisConnection;
import util.MySqlSessionFactory;

@Component
public class ClassesDao {
	private final static String ns = "classes.";
	private static Map<String, Object> map = new HashMap<>();
	
	@Autowired
	MySqlSessionFactory sqlSessionFactory;
	SqlSession sqlSession;
	
	@PostConstruct
	public void setSqlSession() {
		this.sqlSession = sqlSessionFactory.sqlmap.openSession();
	}
	
	// 전체 classes 리스트 생성하여 한 페이지당 classes 객체를 12개씩 반환
	public List<Classes> classList(int pageInt, int limit) {
		map.clear();
		map.put("start", (pageInt - 1) * limit + 1);
		map.put("end", pageInt * limit);
		
		return sqlSession.selectList(ns + "classList", map);
		
	}
	
	// 신규 클래스 아이디 생성을 위해 시퀀스 다음 번호 반환
	public int newClassNum() {
		
		return sqlSession.selectOne(ns + "newClassNum");
		
	}
	
	// 신규 클래스 등록
	public int classUpload(Classes newClass) {
		try {
			return sqlSession.insert(ns + "classUpload", newClass);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.commit();
		}

		return 0;
	}

	// 클래스 아이디를 매개변수로 특정 클래스 조회
	public Classes classOne(String classId) {
		
		return sqlSession.selectOne(ns + "classOne", classId);
		
	}
	
	// 특정 컬럼 기준 내림차순으로 테이블을 정렬하여, 상위 4개 객체만 리스트로 반환
	public List<Classes> sortedClassList(String columnName) {
		
		return sqlSession.selectList(ns + "sortedClassList", columnName);
		
	}
	
	// 특정 카테고리에 맞는 클래스만 한 페이지당 classes 객체를 12개씩 반환
	public List<Classes> classifiedList(String value, int pageInt, int limit) {
		map.clear();
		map.put("value", value);
		map.put("start", (pageInt - 1) * limit + 1);
		map.put("end", pageInt * limit);
		
		return sqlSession.selectList(ns + "classifiedList", map);
		
	}
	
	// 검색어를 입력하여 해당 검색어를 제목에 포함하는 클래스만 한 페이지당 classes 객체를 12개씩 반환
	public List<Classes> searchedList(String value, int pageInt, int limit) {
		String keyword = value.trim();
		
		map.put("value", keyword);
		map.put("start", (pageInt - 1) * limit + 1);
		map.put("end", pageInt * limit);
		
		return sqlSession.selectList(ns + "searchedList", map);
		
	}

	// 클래스 관심 등록 수 1 증가
	public int favoriteCntUp(String classId) {
		try {
			return sqlSession.update(ns + "favoriteCntUp", classId);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.commit();
		}

		return 0;
	}
	
	// 클래스 관심 등록 수 1 감소
	public int favoriteCntDown(String classId) {
		try {
			return sqlSession.update(ns + "favoriteCntDown", classId);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.commit();
		}

		return 0;
	}
	
	// 클래스 수정
	public int classUpdate(Classes updatedClass) {
		try {
			return sqlSession.update(ns + "classUpdate", updatedClass);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.commit();
		}

		return 0;
	}
	
	// 클래스 삭제
	public int classDelete(String classId) {
		try {
			return sqlSession.delete(ns + "classDelete", classId);
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
		String value = "  하하   ";
		String keyword = value.trim();
		int num = sqlSession.update(ns + "favoriteCntUp", "class24");
		MyBatisConnection.close(sqlSession);
		System.out.println(num);
	}
	*/
}

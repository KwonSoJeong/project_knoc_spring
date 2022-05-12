package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import model.Mentoring;
import util.MySqlSessionFactory;

@Component
public class MentoringDao {
	private final static String ns = "mentoring.";
	private Map<String, Object> map = new HashMap<>();

	@Autowired
	MySqlSessionFactory sqlSessionFactory;
	SqlSession sqlSession;
	@PostConstruct
	public void setSqlSession() {
		this.sqlSession=sqlSessionFactory.sqlmap.openSession();
	} 
	
	public int nextNum() {
		try {
			setSqlSession();
			return sqlSession.selectOne(ns + "nextNum");
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return 0;
	}
	
	public int insert(Mentoring m) {
		try {
			setSqlSession();
			return sqlSession.insert(ns + "insert",m);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.commit();
		}
		return 0;
	}
	
	public List<Mentoring> selectList() {
		try {
			setSqlSession();
			return sqlSession.selectList(ns + "selectList");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return null;
	}
	
	public List<Mentoring> selectListKeyword(String keyword) {
		try {
			setSqlSession();
			map.clear();
			map.put("keyword", keyword);
			return sqlSession.selectList(ns + "selectListKeyword",map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return null;
	}
	
	public Mentoring selectOne(String mentoring_Id) {
		try {
		setSqlSession();
			return sqlSession.selectOne(ns + "selectOne",mentoring_Id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return null;
	}
	
	public List profileList() {
		try {
		setSqlSession();
			return sqlSession.selectList(ns + "profileList");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return null;
	}
	
	public List profileListKeyword(String keyword) {
		try {
		setSqlSession();
			map.clear();
			map.put("keyword", keyword);
			return sqlSession.selectList(ns + "profileListKeyword",map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return null;
	}
	
	public int delete(String mentoring_Id) {
		try {
			setSqlSession();
			return sqlSession.delete(ns + "delete",mentoring_Id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.commit();
		}
		return 0;
	}
	
	public int update(String title, String content, String mentoring_Id, String intro) {
		try {
			setSqlSession();
			map.clear();
			map.put("mentoring_Id", mentoring_Id);
			map.put("title", title);
			map.put("content", content);
			map.put("intro", intro);
			return sqlSession.update(ns + "update",map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.commit();
		}
		return 0;
	}
	
	//별점등록
	public int insertRating(String mentoring_Id, double rating) {
		try {
			setSqlSession();
			map.clear();
			map.put("mentoring_Id", mentoring_Id);
			map.put("rating", rating);
			return sqlSession.insert(ns + "insertRating",map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.commit();
		}
		return 0;
	}
	
	//별점 불러오기
	public double selectOneRating(String mentoring_Id) {
		try {
			setSqlSession();
		return sqlSession.selectOne(ns + "selectOneRating",mentoring_Id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return 0;
	}
	
	public List selectListRating() {
		try {
			setSqlSession();
		return sqlSession.selectList(ns + "selectListRating");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return null;
	}

	public List selectListRatingKeyword(String keyword) {
		try {
			setSqlSession();
		map.clear();
		map.put("keyword", keyword);
		return sqlSession.selectList(ns + "selectListRatingKeyword",map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return null;
	}
	
}

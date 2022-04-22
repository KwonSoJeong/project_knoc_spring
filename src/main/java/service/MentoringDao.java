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
			return sqlSession.selectOne(ns + "nextNum");
	}
	
	public int insert(Mentoring m) {
		try {
			return sqlSession.insert(ns + "insert",m);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.commit();
		}
		return 0;
	}
	
	public List<Mentoring> selectList() {
			return sqlSession.selectList(ns + "selectList");
	}
	
	public List<Mentoring> selectListKeyword(String keyword) {
			map.clear();
			map.put("keyword", keyword);
			return sqlSession.selectList(ns + "selectListKeyword",map);
	}
	
	public Mentoring selectOne(String mentoring_Id) {
			return sqlSession.selectOne(ns + "selectOne",mentoring_Id);
	}
	
	public List profileList() {
			return sqlSession.selectList(ns + "profileList");
	}
	
	public List profileListKeyword(String keyword) {
			map.clear();
			map.put("keyword", keyword);
			return sqlSession.selectList(ns + "profileListKeyword",map);
	}
	
	public int delete(String mentoring_Id) {
		try {
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
	
}

package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import model.Mentoring;
import util.MyBatisConnection;

public class MentoringDao {
	private final static String ns = "mentoring.";
	private Map<String, Object> map = new HashMap<>();
	
	public int nextNum() {
		SqlSession sqlSession = MyBatisConnection.getConnection();
		try {
			return sqlSession.selectOne(ns + "nextNum");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(sqlSession);
		}
		return 0;
	}
	
	public int insert(Mentoring m) {
		SqlSession sqlSession = MyBatisConnection.getConnection();
		try {
			return sqlSession.insert(ns + "insert",m);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(sqlSession);
		}
		return 0;
	}
	
	public List<Mentoring> selectList() {
		SqlSession sqlSession = MyBatisConnection.getConnection();
		try {
			return sqlSession.selectList(ns + "selectList");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(sqlSession);
		}
		return null;
	}
	
	public List<Mentoring> selectListKeyword(String keyword) {
		SqlSession sqlSession = MyBatisConnection.getConnection();
		try {
			map.clear();
			map.put("keyword", keyword);
			return sqlSession.selectList(ns + "selectListKeyword",map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(sqlSession);
		}
		return null;
	}
	
	public Mentoring selectOne(String mentoring_Id) {
		SqlSession sqlSession = MyBatisConnection.getConnection();
		try {
			return sqlSession.selectOne(ns + "selectOne",mentoring_Id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(sqlSession);
		}
		return null;
	}
	
	public List profileList() {
		SqlSession sqlSession = MyBatisConnection.getConnection();
		try {
			return sqlSession.selectList(ns + "profileList");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(sqlSession);
		}
		return null;
	}
	
	public List profileListKeyword(String keyword) {
		SqlSession sqlSession = MyBatisConnection.getConnection();
		try {
			map.clear();
			map.put("keyword", keyword);
			return sqlSession.selectList(ns + "profileListKeyword",map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(sqlSession);
		}
		return null;
	}
	
	public int delete(String mentoring_Id) {
		SqlSession sqlSession = MyBatisConnection.getConnection();
		try {
			return sqlSession.delete(ns + "delete",mentoring_Id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(sqlSession);
		}
		return 0;
	}
	
	public int update(String title, String content, String mentoring_Id, String intro) {
		SqlSession sqlSession = MyBatisConnection.getConnection();
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
			MyBatisConnection.close(sqlSession);
		}
		return 0;
	}
	
}

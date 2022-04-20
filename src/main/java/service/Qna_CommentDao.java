package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import model.Qna_Comment;
import util.MyBatisConnection;

public class Qna_CommentDao {
	private final static String ns = "qna_comment.";
	private Map<String, Object> map = new HashMap<>();
	
	/*
	public List<Qna_Comment> selectComment(int refNum) {
		SqlSession sqlSession = MyBatisConnection.getConnection();
		try {
			return sqlSession.selectList(ns + "selectComment",refNum);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(sqlSession);
		}
		return null;
	}
	*/
	
	public Qna_Comment selectOneComment(String refId) {
		SqlSession sqlSession = MyBatisConnection.getConnection();
		try {
			return sqlSession.selectOne(ns + "selectOneComment",refId);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(sqlSession);
		}
		return null;
	}
	
	public List countList(int pageInt, int limit) {
		SqlSession sqlSession = MyBatisConnection.getConnection();
		try {
			map.clear();
			map.put("start", (pageInt-1)*limit+1);
			map.put("end", pageInt * limit);
			return sqlSession.selectList(ns + "countList",map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(sqlSession);
		}
		return null;
	}
	
	//다음 번호 불러오기
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
		
		public int insert(Qna_Comment qc) {
			SqlSession sqlSession = MyBatisConnection.getConnection();
			try {
				return sqlSession.insert(ns + "insert",qc);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				MyBatisConnection.close(sqlSession);
			}
			return 0;
		}
		
		public int countOne(String refId) {
			SqlSession sqlSession = MyBatisConnection.getConnection();
			try {
				return sqlSession.selectOne(ns + "countOne",refId);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				MyBatisConnection.close(sqlSession);
			}
			return 0;
		}
}

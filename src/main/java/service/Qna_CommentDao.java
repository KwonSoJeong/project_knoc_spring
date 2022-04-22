package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import model.Qna_Comment;
import util.MySqlSessionFactory;

@Component
public class Qna_CommentDao {
	private final static String ns = "qna_comment.";
	private Map<String, Object> map = new HashMap<>();

	@Autowired
	MySqlSessionFactory sqlSessionFactory;
	SqlSession sqlSession;
	@PostConstruct
	public void setSqlSession() {
		this.sqlSession=sqlSessionFactory.sqlmap.openSession();
	} 
	
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
			return sqlSession.selectOne(ns + "selectOneComment",refId);
	}
	
	public List countList(int pageInt, int limit) {
			map.clear();
			map.put("start", (pageInt-1)*limit+1);
			map.put("end", pageInt * limit);
			return sqlSession.selectList(ns + "countList",map);
	}
	
	//다음 번호 불러오기
		public int nextNum() {
				return sqlSession.selectOne(ns + "nextNum");
		}
		
		public int insert(Qna_Comment qc) {
			try {
				return sqlSession.insert(ns + "insert",qc);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				sqlSession.commit();
			}
			return 0;
		}
		
		public int countOne(String refId) {
				return sqlSession.selectOne(ns + "countOne",refId);
		}
}

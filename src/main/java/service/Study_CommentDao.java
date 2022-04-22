package service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import model.Study_Comment;
import util.MySqlSessionFactory;


@Component
public class Study_CommentDao {
	private final static String ns = "study_comment.";
	
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
	
	public int insert(Study_Comment sc) {
		try {
			return sqlSession.insert(ns + "insert",sc);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.commit();
		}
		return 0;
	}
	
	public List<Study_Comment> selectComment(String refId) {
			return sqlSession.selectList(ns + "selectComment",refId);
	}
	
	public int count(String refId) {
			return sqlSession.selectOne(ns + "count",refId);
	}
	
	public List<String> callProfile(String refId) {
			return sqlSession.selectList(ns + "callProfile",refId);
	}
	
	
	
	
}

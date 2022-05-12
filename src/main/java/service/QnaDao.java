package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import model.Qna;
import util.MySqlSessionFactory;

@Component
public class QnaDao {
	private final static String ns = "qna.";
	private Map<String, Object> map = new HashMap<>();
	
	@Autowired
	MySqlSessionFactory sqlSessionFactory;
	SqlSession sqlSession;
	@PostConstruct
	public void setSqlSession() {
		this.sqlSession=sqlSessionFactory.sqlmap.openSession();
	} 
	
	//qna 다음 id 불러오기
	public int nextNum() {
		try {
			setSqlSession();
			return sqlSession.selectOne(ns + "nextNum");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return 0;
	}
	
	//특정 qna 상세정보 불러오기
	public Qna selectOne(String id) {
		try {
			setSqlSession();
			return sqlSession.selectOne(ns + "selectOne",id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return null;
	}
	
	//새 qna 게시글 작성
	public int insert(Qna q) {
		try {
			setSqlSession();
			return sqlSession.insert(ns + "insert", q);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.commit();
		}
		return 0;
	}
	
	//qna 리스트
	public List<Qna> qnaList(int pageInt, int limit) {
		try {
			setSqlSession();
			map.clear();
			map.put("start", (pageInt-1)*limit+1);
			map.put("end", pageInt * limit);
			return sqlSession.selectList(ns + "qnaList", map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return null;
	}
	
	//qna 개시글 수
	public int qnaCount() {
		try {
			setSqlSession();
			return sqlSession.selectOne(ns + "qnaCount");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return 0;
	}
	
	public int update(String title, String content, String qna_Id, int secret) {
		try {
			setSqlSession();
			map.clear();
			map.put("qna_Id", qna_Id);
			map.put("title", title);
			map.put("content", content);
			map.put("secret", secret);
			return sqlSession.update(ns + "update",map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.commit();
		}
		return 0;
	}
	
	public int delete(String qna_Id) {
		try {
			setSqlSession();
			return sqlSession.delete(ns + "delete",qna_Id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.commit();
		}
		return 0;
	}
	
}

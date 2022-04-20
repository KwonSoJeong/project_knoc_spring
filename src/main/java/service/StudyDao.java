package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import model.Knoc_Member;
import model.Study;
import util.MyBatisConnection;

public class StudyDao {
	private final static String ns = "study.";
	private Map<String, Object> map = new HashMap<>();
	
	//게시판 다음 번호 불러오기
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
	
	//게시판 생성
	public int insertStudy(Study s) {
		SqlSession sqlSession = MyBatisConnection.getConnection();
		try {
			return sqlSession.insert(ns + "insertStudy", s);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(sqlSession);
		}
		return 0;
	}
	
	public int studyCount(int process) {
		SqlSession sqlSession = MyBatisConnection.getConnection();
		try {
			return sqlSession.selectOne(ns + "studyCount", process);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(sqlSession);
		}
		return 0;
	}
	
	public int studyAllCount() {
		SqlSession sqlSession = MyBatisConnection.getConnection();
		try {
			return sqlSession.selectOne(ns + "studyAllCount");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(sqlSession);
		}
		return 0;
	}
	
	//분류 나눈 게시판 리스트
	public List<Study> studyList(int pageInt,int limit,int process) {
		SqlSession sqlSession = MyBatisConnection.getConnection();
		try {
			map.clear();
			map.put("process", process);
			map.put("start", (pageInt-1)*limit+1);
			map.put("end", pageInt * limit); 
			return sqlSession.selectList(ns + "studyList", map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(sqlSession);
		}
		return null;
	}
	
	//분류 나눈 게시판에서 검색
	public List<Study> studyList(int pageInt,int limit,int process, String keyword) {
		SqlSession sqlSession = MyBatisConnection.getConnection();
		try {
			map.clear();
			map.put("keyword", keyword);
			map.put("process", process);
			map.put("start", (pageInt-1)*limit+1);
			map.put("end", pageInt * limit); 
			return sqlSession.selectList(ns + "studyListKeyword", map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(sqlSession);
		}
		return null;
	}
	
	//전체 게시판 리스트
	public List<Study> studyAllList(int pageInt,int limit) {
		SqlSession sqlSession = MyBatisConnection.getConnection();
		try {
			map.clear();
			map.put("start", (pageInt-1)*limit+1);
			map.put("end", pageInt * limit);
			return sqlSession.selectList(ns + "studyAllList", map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(sqlSession);
		}
		return null;
	}
	
	//전체 게시판 검색
	public List<Study> studyAllList(int pageInt,int limit, String keyword) {
		SqlSession sqlSession = MyBatisConnection.getConnection();
		try {
			map.clear();
			map.put("keyword", keyword);
			map.put("start", (pageInt-1)*limit+1);
			map.put("end", pageInt * limit);
			return sqlSession.selectList(ns + "studyAllListKeyword", map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(sqlSession);
		}
		return null;
	}
	
	public Study selectOne(String studyId) {
		SqlSession sqlSession = MyBatisConnection.getConnection();
		try {
			return sqlSession.selectOne(ns + "selectOne", studyId);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(sqlSession);
		}
		return null;
	}
	
	public String callProfile(String id) {
		SqlSession sqlSession = MyBatisConnection.getConnection();
		try {
			return sqlSession.selectOne(ns + "callProfile", id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(sqlSession);
		}
		return null;
	}
	
	public List<String> callProfileList(int pageInt, int limit, String keyword) {
		SqlSession sqlSession = MyBatisConnection.getConnection();
		try {
			map.put("keyword", keyword);
			map.put("start", (pageInt-1)*limit+1);
			map.put("end", pageInt * limit);
			return sqlSession.selectList(ns + "callProfileAllListKeyword", map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(sqlSession);
		}
		return null;
	}
	
	public List<String> callProfileList(int pageInt, int limit) {
		SqlSession sqlSession = MyBatisConnection.getConnection();
		try {
			return sqlSession.selectList(ns + "callProfileAllList", map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(sqlSession);
		}
		return null;
	}
	
	public List<String> callProfileList(int pageInt, int limit, int process) {
		SqlSession sqlSession = MyBatisConnection.getConnection();
		try {
			return sqlSession.selectList(ns + "callProfileList", map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(sqlSession);
		}
		return null;
	}
	
	public List<String> callProfileList(int pageInt, int limit, int process, String keyword) {
		SqlSession sqlSession = MyBatisConnection.getConnection();
		try {
			return sqlSession.selectList(ns + "callProfileListKeyword", map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(sqlSession);
		}
		return null;
	}
	
	public int infoChk(String id, String member_study_id) {
		SqlSession sqlSession = MyBatisConnection.getConnection();
		try {
			map.clear();
			map.put("id", id);
			map.put("member_study_id", member_study_id);
			return sqlSession.selectOne(ns + "infoChk",map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(sqlSession);
		}
		return 0;
	}
	
	public void changeProcessToTwo(String study_Id) {
		SqlSession sqlSession = MyBatisConnection.getConnection();
		try {
			sqlSession.update(ns + "changeProcessToTwo",study_Id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(sqlSession);
		}
		
	}
	
	public void changeProcessToOne(String study_Id) {
		SqlSession sqlSession = MyBatisConnection.getConnection();
		try {
			sqlSession.update(ns + "changeProcessToOne",study_Id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(sqlSession);
		}
		
	}
	
	public int delete(String study_Id) {
		SqlSession sqlSession = MyBatisConnection.getConnection();
		try {
			return sqlSession.delete(ns + "delete",study_Id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(sqlSession);
		}
		return 0;
	}
	
	public int update(String title, String content, String study_Id) {
		SqlSession sqlSession = MyBatisConnection.getConnection();
		try {
			map.clear();
			map.put("study_Id", study_Id);
			map.put("title", title);
			map.put("content", content);
			return sqlSession.update(ns + "update",map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(sqlSession);
		}
		return 0;
	}
	
	
	
	
}

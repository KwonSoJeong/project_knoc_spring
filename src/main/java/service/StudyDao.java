package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import model.Study;
import util.MySqlSessionFactory;

@Component
public class StudyDao {
	private final static String ns = "study.";
	private Map<String, Object> map = new HashMap<>();

	@Autowired
	MySqlSessionFactory sqlSessionFactory;
	SqlSession sqlSession;

	@PostConstruct
	public void setSqlSession() {
		this.sqlSession = sqlSessionFactory.sqlmap.openSession();
	}

	// 게시판 다음 번호 불러오기
	public int nextNum() {
		return sqlSession.selectOne(ns + "nextNum");
	}

	// 게시판 생성
	public int insertStudy(Study s) {
		try {
			return sqlSession.insert(ns + "insertStudy", s);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.commit();
		}
		return 0;
	}

	public int studyCount(int process) {
		return sqlSession.selectOne(ns + "studyCount", process);
	}
	
	public int studyCount(int process, String keyword) {
		map.clear();
		map.put("process", process);
		map.put("keyword", keyword);
		return sqlSession.selectOne(ns + "studyCountKeyword", map);
	}

	public int studyAllCount() {
		return sqlSession.selectOne(ns + "studyAllCount");
	}
	
	public int studyAllCount(String keyword) {
		map.clear();
		map.put("keyword", keyword);
		return sqlSession.selectOne(ns + "studyAllCountKeyword", map);
	}

	// 분류 나눈 게시판 리스트
	public List<Study> studyList(int pageInt, int limit, int process) {
		map.clear();
		map.put("process", process);
		map.put("start", (pageInt - 1) * limit + 1);
		map.put("end", pageInt * limit);
		return sqlSession.selectList(ns + "studyList", map);
	}

	// 분류 나눈 게시판에서 검색
	public List<Study> studyList(int pageInt, int limit, int process, String keyword) {
		map.clear();
		map.put("keyword", keyword);
		map.put("process", process);
		map.put("start", (pageInt - 1) * limit + 1);
		map.put("end", pageInt * limit);
		return sqlSession.selectList(ns + "studyListKeyword", map);
	}

	// 전체 게시판 리스트
	public List<Study> studyAllList(int pageInt, int limit) {
		map.clear();
		map.put("start", (pageInt - 1) * limit + 1);
		map.put("end", pageInt * limit);
		return sqlSession.selectList(ns + "studyAllList", map);
	}

	// 전체 게시판 검색
	public List<Study> studyAllList(int pageInt, int limit, String keyword) {
		map.clear();
		map.put("keyword", keyword);
		map.put("start", (pageInt - 1) * limit + 1);
		map.put("end", pageInt * limit);
		return sqlSession.selectList(ns + "studyAllListKeyword", map);
	}

	public Study selectOne(String studyId) {
		return sqlSession.selectOne(ns + "selectOne", studyId);
	}

	public String callProfile(String id) {
		return sqlSession.selectOne(ns + "callProfile", id);
	}

	public List<String> callProfileList(int pageInt, int limit, String keyword) {
		map.put("keyword", keyword);
		map.put("start", (pageInt - 1) * limit + 1);
		map.put("end", pageInt * limit);
		return sqlSession.selectList(ns + "callProfileAllListKeyword", map);
	}

	public List<String> callProfileList(int pageInt, int limit) {
		return sqlSession.selectList(ns + "callProfileAllList", map);
	}

	public List<String> callProfileList(int pageInt, int limit, int process) {
		return sqlSession.selectList(ns + "callProfileList", map);
	}

	public List<String> callProfileList(int pageInt, int limit, int process, String keyword) {
		return sqlSession.selectList(ns + "callProfileListKeyword", map);
	}

	public int infoChk(String id, String member_study_id) {
		map.clear();
		map.put("id", id);
		map.put("member_study_id", member_study_id);
		return sqlSession.selectOne(ns + "infoChk", map);
	}

	public void changeProcessToTwo(String study_Id) {
		try {
			sqlSession.update(ns + "changeProcessToTwo", study_Id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.commit();
		}

	}

	public void changeProcessToOne(String study_Id) {
		try {
			sqlSession.update(ns + "changeProcessToOne", study_Id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.commit();
		}

	}

	public int delete(String study_Id) {
		try {
			return sqlSession.delete(ns + "delete", study_Id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.commit();
		}
		return 0;
	}

	public int update(String title, String content, String study_Id) {
		try {
			map.clear();
			map.put("study_Id", study_Id);
			map.put("title", title);
			map.put("content", content);
			return sqlSession.update(ns + "update", map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.commit();
		}
		return 0;
	}
	

}

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

	// 게시판 생성
	public int insertStudy(Study s) {
		try {
			setSqlSession();
			return sqlSession.insert(ns + "insertStudy", s);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.commit();
		}
		return 0;
	}

	public int studyCount(int process) {
		try {
			setSqlSession();
		return sqlSession.selectOne(ns + "studyCount", process);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return 0;
	}
	
	public int studyCount(int process, String keyword) {
		try {
			setSqlSession();
		map.clear();
		map.put("process", process);
		map.put("keyword", keyword);
		return sqlSession.selectOne(ns + "studyCountKeyword", map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return 0;
	}

	public int studyAllCount() {
		try {
			setSqlSession();
		return sqlSession.selectOne(ns + "studyAllCount");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return 0;
	}
	
	public int studyAllCount(String keyword) {
		try {
			setSqlSession();
		map.clear();
		map.put("keyword", keyword);
		return sqlSession.selectOne(ns + "studyAllCountKeyword", map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return 0;
	}

	// 분류 나눈 게시판 리스트
	public List<Study> studyList(int pageInt, int limit, int process) {
		try {
			setSqlSession();
		map.clear();
		map.put("process", process);
		map.put("start", (pageInt - 1) * limit + 1);
		map.put("end", pageInt * limit);
		return sqlSession.selectList(ns + "studyList", map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return null;
	}

	// 분류 나눈 게시판에서 검색
	public List<Study> studyList(int pageInt, int limit, int process, String keyword) {
		try {
			setSqlSession();
		map.clear();
		map.put("keyword", keyword);
		map.put("process", process);
		map.put("start", (pageInt - 1) * limit + 1);
		map.put("end", pageInt * limit);
		return sqlSession.selectList(ns + "studyListKeyword", map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return null;
	}

	// 전체 게시판 리스트
	public List<Study> studyAllList(int pageInt, int limit) {
		try {
			setSqlSession();
		map.clear();
		map.put("start", (pageInt - 1) * limit + 1);
		map.put("end", pageInt * limit);
		return sqlSession.selectList(ns + "studyAllList", map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return null;
	}

	// 전체 게시판 검색
	public List<Study> studyAllList(int pageInt, int limit, String keyword) {
		try {
			setSqlSession();
		map.clear();
		map.put("keyword", keyword);
		map.put("start", (pageInt - 1) * limit + 1);
		map.put("end", pageInt * limit);
		return sqlSession.selectList(ns + "studyAllListKeyword", map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return null;
	}

	public Study selectOne(String studyId) {
		try {
			setSqlSession();
		return sqlSession.selectOne(ns + "selectOne", studyId);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return null;
	}

	public String callProfile(String id) {
		try {
			setSqlSession();
		return sqlSession.selectOne(ns + "callProfile", id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return null;
	}

	public List<String> callProfileList(int pageInt, int limit, String keyword) {
		try {
			setSqlSession();
		map.put("keyword", keyword);
		map.put("start", (pageInt - 1) * limit + 1);
		map.put("end", pageInt * limit);
		return sqlSession.selectList(ns + "callProfileAllListKeyword", map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return null;
	}

	public List<String> callProfileList(int pageInt, int limit) {
		try {
			setSqlSession();
		return sqlSession.selectList(ns + "callProfileAllList", map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return null;
	}

	public List<String> callProfileList(int pageInt, int limit, int process) {
		try {
			setSqlSession();
		return sqlSession.selectList(ns + "callProfileList", map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return null;
	}

	public List<String> callProfileList(int pageInt, int limit, int process, String keyword) {
		try {
			setSqlSession();
		return sqlSession.selectList(ns + "callProfileListKeyword", map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return null;
	}

	public int infoChk(String id, String member_study_id) {
		try {
			setSqlSession();
		map.clear();
		map.put("id", id);
		map.put("member_study_id", member_study_id);
		return sqlSession.selectOne(ns + "infoChk", map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return 0;
	}

	public void changeProcessToTwo(String study_Id) {
		try {
			setSqlSession();
			sqlSession.update(ns + "changeProcessToTwo", study_Id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.commit();
		}

	}

	public void changeProcessToOne(String study_Id) {
		try {
			setSqlSession();
			sqlSession.update(ns + "changeProcessToOne", study_Id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.commit();
		}

	}

	public int delete(String study_Id) {
		try {
			setSqlSession();
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
			setSqlSession();
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

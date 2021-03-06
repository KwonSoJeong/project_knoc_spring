package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import model.Report;
import util.MySqlSessionFactory;

@Component
public class ReportDao {
	private final static String ns = "report.";
	private static Map<String, Object> map = new HashMap<>();

	@Autowired
	MySqlSessionFactory sqlSessionFactory;
	SqlSession sqlSession;

	@PostConstruct
	public void setSqlSession() {
		this.sqlSession = sqlSessionFactory.sqlmap.openSession();
	}

	// 다음 번호 불러오기
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
	
	public int insertReport(Report report) {
		try {
			setSqlSession();
			return sqlSession.insert(ns + "insertReport", report);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.commit();
		}
		return 0;
	}
	
	public List<Map<String, Object>> reportList(String subject, int pageInt, int limit) {
		try {
			setSqlSession();
		map.clear();
		map.put("start", (pageInt - 1) * limit + 1);
		map.put("end", pageInt * limit);
		map.put("value", subject);
		
		return sqlSession.selectList(ns + "reportList", map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return null;
	}
	
	public int reportCount(String subject) {
		try {
			setSqlSession();
		return sqlSession.selectOne(ns + "reportCount", subject);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return 0;
	}
}

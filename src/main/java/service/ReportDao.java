package service;

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

	@Autowired
	MySqlSessionFactory sqlSessionFactory;
	SqlSession sqlSession;

	@PostConstruct
	public void setSqlSession() {
		this.sqlSession = sqlSessionFactory.sqlmap.openSession();
	}

	// 다음 번호 불러오기
	public int nextNum() {
		return sqlSession.selectOne(ns + "nextNum");
	}
	
	public int insertReport(Report report) {
		try {
			return sqlSession.insert(ns + "insertReport", report);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.commit();
		}
		return 0;
	}
	
	public List<Map<String, Object>> reportList(String subject) {
		return sqlSession.selectList(ns + "reportList", subject);
	}
}

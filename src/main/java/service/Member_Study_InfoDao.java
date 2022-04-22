package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import model.Member_Study_Info;
import util.MySqlSessionFactory;

@Component
public class Member_Study_InfoDao {
	private final static String ns = "member_study_info.";
	private static Map<String, Object> map = new HashMap<>();
	
	@Autowired
	MySqlSessionFactory sqlSessionFactory;
	SqlSession sqlSession;
	
	@PostConstruct
	public void setSqlSession() {
		this.sqlSession = sqlSessionFactory.sqlmap.openSession();
	}
	
	// 클래스/멘토링/스터디 생성, 신청 시에 info 등록
	// controller에서 msi 객체에 유저 아이디, 해당 클래스/멘토링/스터디 아이디, 등록자일 시 1/ 참가자일 시 2로 set 하고 전달
	public void insertInfo(Member_Study_Info msi) {
		try {
			sqlSession.insert(ns + "insertInfo", msi);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.commit();
		}

	}
	
	public Member_Study_Info infoOne(String id, String value) {
		map.clear();
		map.put("id", id);
		map.put("value", value);
		
		return sqlSession.selectOne(ns + "infoOne", map);
		
	}
	
	public List<Member_Study_Info> infoList(String id, String value) {
		map.clear();
		map.put("id", id);
		map.put("value", value);
		
		return sqlSession.selectList(ns + "infoList", map);
		
	}
	
	public int nextSeq() {
		
		return sqlSession.selectOne(ns + "nextSeq");

	}
	
	public List<Map<String, Object>> infoClassList(String id) {
		map.clear();
		map.put("table", "classes");
		map.put("id", "class_id");
		map.put("value", id);
		
		return sqlSession.selectList(ns + "infoTitleList", map);
		
	}
	
	public List<Map<String, Object>> infoStudyList(String id) {
		map.clear();
		map.put("table", "study");
		map.put("id", "study_id");
		map.put("value", id);
		
		return sqlSession.selectList(ns + "infoTitleList", map);
		
	}
	
	public List<Map<String, Object>> infoMentoringList(String id) {
		map.clear();
		map.put("table", "mentoring");
		map.put("id", "mentoring_id");
		map.put("value", id);
		
		return sqlSession.selectList(ns + "infoTitleList", map);
		
	}
	
	/* DAO 테스트 코드 
	public static void main(String[] args) {
		SqlSession sqlSession = MyBatisConnection.getConnection();
		map.clear();
		map.put("table", "classes");
		map.put("id", "class_id");
		map.put("value", "qq");
		List<Map<String, Object>> list = sqlSession.selectList(ns + "infoTitleList", map);
		for (Map<String, Object> msi : list) {
			for(String s : msi.keySet()) {
				System.out.println(msi.get(s));
			}
		}
	}
	*/
	
}

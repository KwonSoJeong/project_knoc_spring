package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import model.Member_Study_Info;
import util.MyBatisConnection;

public class Member_Study_InfoDao {
	private final static String ns = "member_study_info.";
	private static Map<String, Object> map = new HashMap<>();
	
	// 클래스/멘토링/스터디 생성, 신청 시에 info 등록
	// controller에서 msi 객체에 유저 아이디, 해당 클래스/멘토링/스터디 아이디, 등록자일 시 1/ 참가자일 시 2로 set 하고 전달
	public void insertInfo(Member_Study_Info msi) {
		SqlSession sqlSession = MyBatisConnection.getConnection();
		
		try {
			sqlSession.insert(ns + "insertInfo", msi);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(sqlSession);
		}

	}
	
	public Member_Study_Info infoOne(String id, String value) {
		SqlSession sqlSession = MyBatisConnection.getConnection();
		map.clear();
		map.put("id", id);
		map.put("value", value);
		try {
			return sqlSession.selectOne(ns + "infoOne", map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(sqlSession);
		}
		
		return null;
	}
	
	public List<Member_Study_Info> infoList(String id, String value) {
		SqlSession sqlSession = MyBatisConnection.getConnection();
		map.clear();
		map.put("id", id);
		map.put("value", value);
		
		try {
			return sqlSession.selectList(ns + "infoList", map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(sqlSession);
		}
		
		return null;
	}
	
	public int nextSeq() {
		SqlSession sqlSession = MyBatisConnection.getConnection();
		
		try {
			return sqlSession.selectOne(ns + "nextSeq");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(sqlSession);
		}
		
		return 0;

	}
	
	public List<Map<String, Object>> infoClassList(String id) {
		SqlSession sqlSession = MyBatisConnection.getConnection();
		
		map.clear();
		map.put("table", "classes");
		map.put("id", "class_id");
		map.put("value", id);
		
		try {
			return sqlSession.selectList(ns + "infoTitleList", map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(sqlSession);
		}
		
		return null;
	}
	
	public List<Map<String, Object>> infoStudyList(String id) {
		SqlSession sqlSession = MyBatisConnection.getConnection();
		
		map.clear();
		map.put("table", "study");
		map.put("id", "study_id");
		map.put("value", id);
		
		try {
			return sqlSession.selectList(ns + "infoTitleList", map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(sqlSession);
		}
		
		return null;
	}
	
	public List<Map<String, Object>> infoMentoringList(String id) {
		SqlSession sqlSession = MyBatisConnection.getConnection();
		
		map.clear();
		map.put("table", "mentoring");
		map.put("id", "mentoring_id");
		map.put("value", id);
		
		try {
			return sqlSession.selectList(ns + "infoTitleList", map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(sqlSession);
		}
		
		return null;
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

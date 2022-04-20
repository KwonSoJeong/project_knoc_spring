package service;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import model.Knoc_Member;
import util.MyBatisConnection;

public class Knoc_MemberDao {
	private final static String ns = "knoc_member.";
	private static Map<String, Object> map = new HashMap<>();
	
	// 신규 회원 추가
	public int insertMember(Knoc_Member m) {
		SqlSession sqlSession = MyBatisConnection.getConnection();
		try {
			return sqlSession.insert(ns + "insertMember", m);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(sqlSession);
		}
		return 0;
	}
	
	// id로 해당하는 knoc_member 객체 반환
	public Knoc_Member selectOne(String id) {
		SqlSession sqlSession = MyBatisConnection.getConnection();
		try {
			return sqlSession.selectOne(ns + "selectOne", id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(sqlSession);
		}
		return null;
	}
	
	// 회원 정보 수정
	public int updateMember(Knoc_Member member) {
		SqlSession sqlSession = MyBatisConnection.getConnection();
		
		try {
			return sqlSession.update(ns + "updateMember", member);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(sqlSession);
		}
		
		return 0;
		
	}
	
	// 비밀번호 수정
	public int updatePwd(Knoc_Member member) {
		SqlSession sqlSession = MyBatisConnection.getConnection();
		
		try {
			return sqlSession.update(ns + "updatePwd", member);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(sqlSession);
		}
		
		return 0;
	}
	
	public int deleteMember(Knoc_Member member) {
		SqlSession sqlSession = MyBatisConnection.getConnection();

		try {
			return sqlSession.delete(ns + "deleteMember", member);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(sqlSession);
		}

		return 0;
	}

}

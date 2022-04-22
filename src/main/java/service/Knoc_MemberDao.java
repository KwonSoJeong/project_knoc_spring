package service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import model.Knoc_Member;
import util.MySqlSessionFactory;

@Component
public class Knoc_MemberDao {
	private final static String ns = "knoc_member.";
	private static Map<String, Object> map = new HashMap<>();
	
	@Autowired
	MySqlSessionFactory sqlSessionFactory;
	SqlSession sqlSession;
	
	@PostConstruct
	public void setSqlSession() {
		this.sqlSession = sqlSessionFactory.sqlmap.openSession();
	}
	
	// 신규 회원 추가
	public int insertMember(Knoc_Member m) {
		try {
			return sqlSession.insert(ns + "insertMember", m);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.commit();
		}
		return 0;
	}
	
	// id로 해당하는 knoc_member 객체 반환
	public Knoc_Member selectOne(String id) {
		
		return sqlSession.selectOne(ns + "selectOne", id);
		
	}
	
	// 회원 정보 수정
	public int updateMember(Knoc_Member member) {
		try {
			return sqlSession.update(ns + "updateMember", member);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.commit();
		}
		
		return 0;
		
	}
	
	// 비밀번호 수정
	public int updatePwd(Knoc_Member member) {
		try {
			return sqlSession.update(ns + "updatePwd", member);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.commit();
		}
		
		return 0;
	}
	
	public int deleteMember(Knoc_Member member) {
		try {
			return sqlSession.delete(ns + "deleteMember", member);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.commit();
		}

		return 0;
	}

}

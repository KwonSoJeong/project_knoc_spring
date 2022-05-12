package service;

import java.util.HashMap;
import java.util.List;
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
			setSqlSession();
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
		try {
			setSqlSession();
		return sqlSession.selectOne(ns + "selectOne", id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return null;
		
	}
	
	public List<Knoc_Member> memberList(int pageInt, int limit) {
		try {
			setSqlSession();
		map.clear();
		map.put("start", (pageInt - 1) * limit + 1);
		map.put("end", pageInt * limit);
		
		return sqlSession.selectList(ns + "memberList", map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return null;
	}
	
	public int memberCount() {
		try {
			setSqlSession();
		return sqlSession.selectOne(ns + "memberCount");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return 0;
	}
	
	public List<Knoc_Member> memberListWithKeyword(int pageInt, int limit, String keyword) {
		try {
			setSqlSession();
		map.clear();
		map.put("start", (pageInt - 1) * limit + 1);
		map.put("end", pageInt * limit);
		map.put("keyword", keyword);
		
		return sqlSession.selectList(ns + "memberListWithKeyword", map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return null;
	}
	
	public int memberCountWithKeyword(String keyword) {
		try {
			setSqlSession();
		return sqlSession.selectOne(ns + "memberCountWithKeyword", keyword);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return 0;
	}
	
	public List<Knoc_Member> selectBlackList(int pageInt, int limit) {
		try {
			setSqlSession();
		map.clear();
		map.put("start", (pageInt - 1) * limit + 1);
		map.put("end", pageInt * limit);
		
		return	sqlSession.selectList(ns + "selectBlackList", map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return null;
				
	}
	
	public int blackListCount() {
		try {
			setSqlSession();
		return sqlSession.selectOne(ns + "blackListCount");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return 0;
	}
	
	// 회원 정보 수정
	public int updateMember(Knoc_Member member) {
		try {
			setSqlSession();
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
			setSqlSession();
			return sqlSession.update(ns + "updatePwd", member);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.commit();
		}
		
		return 0;
	}
	
	public void addBlackList(String id) {
		try {
			setSqlSession();
			sqlSession.update(ns + "addBlackList", id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.commit();
		}
	}
	
	public void deleteBlackList(String id) {
		try {
			setSqlSession();
			sqlSession.update(ns + "deleteBlackList", id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.commit();
		}
	}
	
	public int deleteMember(Knoc_Member member) {
		try {
			setSqlSession();
			return sqlSession.delete(ns + "deleteMember", member);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.commit();
		}

		return 0;
	}

}

package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import model.Suspended_List;
import util.MySqlSessionFactory;

@Component
public class Suspended_ListDao {
	private final static String ns = "suspended_list.";
	private static Map<String, Object> map = new HashMap<>();
	
	@Autowired
	MySqlSessionFactory sqlSessionFactory;
	SqlSession sqlSession;
	
	@PostConstruct
	public void setSqlSession() {
		this.sqlSession = sqlSessionFactory.sqlmap.openSession();
	}
	
	public void addSuspendedMember(String id) {
		try {
			setSqlSession();
			sqlSession.insert(ns + "addSuspendedMember", id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.commit();
		}

	}
	
	public Suspended_List selectOne(String id) {
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
	
	public List<Suspended_List> selectList(int pageInt, int limit) {
		try {
			setSqlSession();
		map.clear();
		map.put("start", (pageInt - 1) * limit + 1);
		map.put("end", pageInt * limit);
		
		return sqlSession.selectList(ns + "selectList", map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return null;
	}
	
	public int suspendedListCount() {
		try {
			setSqlSession();
		return sqlSession.selectOne(ns + "suspendedListCount");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return 0;
	}
	
	public void updateAccCnt (String id) {
		try {
			setSqlSession();
			sqlSession.update(ns + "updateAccCnt", id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.commit();
		}
	}
	
	public void updateStatus(String id) {
		try {
			setSqlSession();
			sqlSession.update(ns + "updateStatus", id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.commit();
		}
	}

}

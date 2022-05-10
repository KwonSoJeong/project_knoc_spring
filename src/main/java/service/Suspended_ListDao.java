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
			sqlSession.insert(ns + "addSuspendedMember", id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.commit();
		}

	}
	
	public Suspended_List selectOne(String id) {
		return sqlSession.selectOne(ns + "selectOne", id);
	}
	
	public List<Suspended_List> selectList() {
		return sqlSession.selectList(ns + "selectList");
	}
	
	public void updateAccCnt (String id) {
		try {
			sqlSession.update(ns + "updateAccCnt", id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.commit();
		}
	}

}

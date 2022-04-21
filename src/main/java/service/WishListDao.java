package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import model.WishList;
import util.MyBatisConnection;
import util.MySqlSessionFactory;

@Component
public class WishListDao {
	private final static String ns = "wishlist.";
	private static Map<String, Object> map = new HashMap<>();
	
	@Autowired
	MySqlSessionFactory sqlSessionFactory;
	SqlSession sqlSession;
	
	@PostConstruct
	public void setSqlSession() {
		this.sqlSession = sqlSessionFactory.sqlmap.openSession();
	}
	
	public int nextSeq() {
		
		return sqlSession.selectOne(ns + "nextSeq");
		
	}
	
	public WishList wishOne(String id, String classId) {
		map.clear();
		map.put("id", id);
		map.put("classId", classId);
		
		return sqlSession.selectOne(ns + "wishOne", map);
		
	}
	
	public int insertWish(WishList newWish) {
		try {
			return sqlSession.insert(ns + "insertWish", newWish);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.commit();
		}
		
		return 0;
	}
	
	public int deleteWish(String id, String classId) {
		map.clear();
		map.put("id", id);
		map.put("classId", classId);
		try {
			return sqlSession.delete(ns + "deleteWish", map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.commit();
		}
		
		return 0;
	}
	
	public List<Map<String, Object>> wishListOne(String id) {
		
		return sqlSession.selectList(ns + "wishListOne", id);
		
	}
	
	/* DAO 테스트 코드 
	public static void main(String[] args) {
		SqlSession sqlSession = MyBatisConnection.getConnection();
		List<Map<String, Object>> list = sqlSession.selectList(ns + "wishListOne", "qq");
		for (Map<String, Object> msi : list) {
			System.out.println(msi.keySet());
		}
	}
	*/
}

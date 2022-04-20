package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import model.WishList;
import util.MyBatisConnection;

public class WishListDao {
	private final static String ns = "wishlist.";
	private static Map<String, Object> map = new HashMap<>();
	
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
	
	public WishList wishOne(String id, String classId) {
		SqlSession sqlSession = MyBatisConnection.getConnection();
		
		map.clear();
		map.put("id", id);
		map.put("classId", classId);
		try {
			return sqlSession.selectOne(ns + "wishOne", map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(sqlSession);
		}
		
		return null;
	}
	
	public int insertWish(WishList newWish) {
		SqlSession sqlSession = MyBatisConnection.getConnection();
		
		try {
			return sqlSession.insert(ns + "insertWish", newWish);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(sqlSession);
		}
		
		return 0;
	}
	
	public int deleteWish(String id, String classId) {
		SqlSession sqlSession = MyBatisConnection.getConnection();
		
		map.clear();
		map.put("id", id);
		map.put("classId", classId);
		try {
			return sqlSession.delete(ns + "deleteWish", map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(sqlSession);
		}
		
		return 0;
	}
	
	public List<Map<String, Object>> wishListOne(String id) {
		SqlSession sqlSession = MyBatisConnection.getConnection();
		
		try {
			return sqlSession.selectList(ns + "wishListOne", id);
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
		List<Map<String, Object>> list = sqlSession.selectList(ns + "wishListOne", "qq");
		for (Map<String, Object> msi : list) {
			System.out.println(msi.keySet());
		}
	}
	*/
}

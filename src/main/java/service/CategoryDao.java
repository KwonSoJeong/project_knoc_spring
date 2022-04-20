package service;

import org.apache.ibatis.session.SqlSession;

import util.MyBatisConnection;

public class CategoryDao {
	private final static String ns = "category.";
	
	public String selectCategoryName(String categoryId) {
		SqlSession sqlSession = MyBatisConnection.getConnection();
		
		try {
			return sqlSession.selectOne(ns + "selectCategoryName", categoryId);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(sqlSession);
		}

		return null;
	}
}

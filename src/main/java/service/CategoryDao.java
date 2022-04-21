package service;

import javax.annotation.PostConstruct;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import util.MyBatisConnection;
import util.MySqlSessionFactory;

@Component
public class CategoryDao {
	private final static String ns = "category.";
	
	@Autowired
	MySqlSessionFactory sqlSessionFactory;
	SqlSession sqlSession;
	
	@PostConstruct
	public void setSqlSession() {
		this.sqlSession = sqlSessionFactory.sqlmap.openSession();
	}
	
	public String selectCategoryName(String categoryId) {
		
		return sqlSession.selectOne(ns + "selectCategoryName", categoryId);
		
	}
}

package com.gms.web.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.gms.web.constant.Database;
import com.gms.web.constant.SQL;
import com.gms.web.constant.Vendor;
import com.gms.web.domain.ArticleBean;
import com.gms.web.factory.DatabaseFactory;



public class ArticleDaoImpl implements ArticleDao{
	
List<ArticleBean> articles;
ArticleBean article;

	public static ArticleDaoImpl getInstance() {
	return new ArticleDaoImpl();
}
	private ArticleDaoImpl(){
		
	}

	@Override
	public String insert(ArticleBean article) {
		String rs="";
		try {
			PreparedStatement pstmt=DatabaseFactory.createDatabase(Vendor.ORACLE, Database.USERNAME,Database.PASSWORD)
					.getConnection().prepareStatement(SQL.ARTICLE_INSERT);
			pstmt.setString(1, article.getId());
			pstmt.setString(2, article.getTitle());
			pstmt.setString(3, article.getContent());
			rs=String.valueOf(pstmt.executeUpdate());
			System.out.println(rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	@Override
	public List<ArticleBean> selectAll() {
		articles=new ArrayList<>();
		
		try {
			ArticleBean list=null;
			ResultSet rs=DatabaseFactory.createDatabase(Vendor.ORACLE, Database.USERNAME,Database.PASSWORD)
					.getConnection().prepareStatement(SQL.ARTICLE_LIST).executeQuery();
			
			while(rs.next()){
				list=new ArticleBean();
				list.setArticleSeq(rs.getInt(Database.ARTICLE_SEQ));
				list.setId(rs.getString(Database.ARTICLE_ID));
				list.setTitle(rs.getString(Database.TITLE));
				list.setContent(rs.getString(Database.ARTICLE_CONTENT));
				list.setHitcount(rs.getInt(Database.ARTICLE_HITCOUNT));
				list.setRegdate(rs.getString(Database.ARTICLE_REGDATE));
				articles.add(list);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return articles;
	}

	@Override
	public List<ArticleBean> selectById(String id) {
		articles=new ArrayList<>();
		
		try {
			PreparedStatement pstmt=DatabaseFactory.createDatabase(Vendor.ORACLE, Database.USERNAME, Database.PASSWORD).getConnection().prepareStatement(
					SQL.ARTICLE_FINDBYID);
			pstmt.setString(1, id);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()){
				article=new ArticleBean();
				article.setArticleSeq(rs.getInt(Database.ARTICLE_SEQ));
				article.setId(rs.getString(Database.ARTICLE_ID));
				article.setTitle(rs.getString(Database.TITLE));
				article.setContent(rs.getString(Database.ARTICLE_CONTENT));
				article.setHitcount(rs.getInt(Database.ARTICLE_HITCOUNT));
				article.setRegdate(rs.getString(Database.ARTICLE_REGDATE));
				articles.add(article);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return articles;
	}

	@Override
	public ArticleBean selectBySeq(String seq) {
		article=new ArticleBean();
		try {
			PreparedStatement pstmt=DatabaseFactory.createDatabase(Vendor.ORACLE, Database.USERNAME, Database.PASSWORD).getConnection().prepareStatement(SQL.ARTICLE_FINDBYSEQ);
			pstmt.setString(1, seq);

			ResultSet rs=pstmt.executeQuery();
			while(rs.next()){
				article.setArticleSeq(rs.getInt(Database.ARTICLE_SEQ));
				article.setId(rs.getString(Database.ARTICLE_ID));
				article.setTitle(rs.getString(Database.TITLE));
				article.setContent(rs.getString(Database.ARTICLE_CONTENT));
				article.setHitcount(rs.getInt(Database.ARTICLE_HITCOUNT));
				article.setRegdate(rs.getString(Database.ARTICLE_REGDATE));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return article;
	}

	@Override
	public String update(ArticleBean article) {
		ArticleBean temp=selectBySeq(String.valueOf(article.getArticleSeq()));
		String title=(article.getTitle().equals(""))?temp.getTitle():article.getTitle();
		String content=(article.getContent().equals(""))?temp.getContent():article.getContent();
		String result="";
		try {
			
			PreparedStatement pstmt=DatabaseFactory.createDatabase(Vendor.ORACLE, Database.USERNAME, Database.PASSWORD).getConnection().prepareStatement(SQL.ARTICLE_UPDATE);
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setString(3, String.valueOf(article.getArticleSeq()));
			result=String.valueOf(pstmt.executeUpdate());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String delete(String seq) {
		String result="";
		try {
			PreparedStatement pstmt=DatabaseFactory.createDatabase(Vendor.ORACLE, Database.USERNAME, Database.PASSWORD).getConnection().prepareStatement(SQL.ARTICLE_DELETE);
			pstmt.setString(1, seq);
			result=String.valueOf(pstmt.executeUpdate());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String count() {
		int result=0;
		try {
			ResultSet rs=DatabaseFactory.createDatabase(Vendor.ORACLE, Database.USERNAME,Database.PASSWORD)
					.getConnection().prepareStatement(SQL.ARTICLE_COUNT).executeQuery();
			
			
			while(rs.next()){
				result=rs.getInt("count");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return String.valueOf(result);
	}

}

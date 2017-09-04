package com.gms.web.service;

import java.util.List;

import com.gms.web.dao.ArticleDaoImpl;
import com.gms.web.domain.ArticleBean;



public class ArticleServiceImpl implements ArticleService{
	//ArticleDao dao=new ArticleDaoImpl();
	
	public static ArticleServiceImpl getInstance() {
		return new ArticleServiceImpl();
	}

	@Override
	public String write(ArticleBean article) {
		return (ArticleDaoImpl.getInstance().insert(article).equals("0"))?"등록실패":"등록성공";
	}

	@Override
	public List<ArticleBean> getArticles() {
		return ArticleDaoImpl.getInstance().selectAll();
	}

	@Override
	public List<ArticleBean> findById(String id) {
		return ArticleDaoImpl.getInstance().selectById(id);
	}

	@Override
	public ArticleBean findBySeq(String seq) {
		return ArticleDaoImpl.getInstance().selectBySeq(seq);
	}

	@Override
	public String modify(ArticleBean article) {
		if(article.getTitle().equals("")){
			article.setTitle(findBySeq(String.valueOf(article.getArticleSeq())).getTitle());
		}
		if(article.getContent().equals("")){
			article.setContent(findBySeq(String.valueOf(article.getArticleSeq())).getContent());
		}
		return (ArticleDaoImpl.getInstance().update(article).equals("0"))?"글수정실패":"글수정성공";
	}

	@Override
	public String remove(String seq) {
		return (ArticleDaoImpl.getInstance().delete(seq).equals("0"))?"삭제실패":"삭제성공";
	}

	@Override
	public String count() {
		return ArticleDaoImpl.getInstance().count();
	}

}

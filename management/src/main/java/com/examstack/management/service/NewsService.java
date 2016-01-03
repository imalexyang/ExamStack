package com.examstack.management.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.examstack.common.domain.news.News;
import com.examstack.common.util.Page;

@Service
public interface NewsService {

	public List<News> getNewsList(Page<News> page);
	
	public News getNewsById(int newsId);
	
	public void addNews(News news);
}

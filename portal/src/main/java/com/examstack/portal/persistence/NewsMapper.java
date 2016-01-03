package com.examstack.portal.persistence;

import java.util.List;

import com.examstack.common.domain.news.News;
import com.examstack.common.util.Page;

public interface NewsMapper {

	public List<News> getNewsList(Page<News> page);
	
	public News getNewsById(int newsId);
}

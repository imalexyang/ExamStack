package com.examstack.portal.service;

import java.util.List;

import com.examstack.common.domain.question.Comment;
import com.examstack.common.util.Page;


public interface CommentService {

	public List<Comment> getCommentByTypeAndReferId(int referType,int referId,int indexId,Page<Comment> page);
	
	public void addComment(Comment comment);
}

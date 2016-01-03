package com.examstack.portal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.examstack.common.domain.question.Comment;
import com.examstack.common.util.Page;
import com.examstack.portal.persistence.CommentMapper;

@Service("commentService")
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentMapper commentMapper;
	@Override
	public List<Comment> getCommentByTypeAndReferId(int commentType,int referId,int indexId,Page<Comment> page) {
		// TODO Auto-generated method stub
		return commentMapper.getCommentByTypeAndReferId(commentType, referId, indexId, page);
	}
	@Override
	@Transactional
	public void addComment(Comment comment) {
		// TODO Auto-generated method stub
		try{
			Object index = commentMapper.getMaxCommentIndexByTypeAndReferId(comment.getCommentType(), comment.getReferId());
			int i = 0;
			if(index == null)
				i = 0;
			else 
				i = (Integer) index;
			comment.setIndexId(i + 1);
			commentMapper.addComment(comment);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}

}

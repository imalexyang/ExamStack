package com.examstack.common.domain.question;

import java.io.Serializable;
import java.util.List;

public class Comments implements Serializable  {

	private static final long serialVersionUID = -515766630713170465L;
	private List<Comment> comments = null;
	private int size;

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

}
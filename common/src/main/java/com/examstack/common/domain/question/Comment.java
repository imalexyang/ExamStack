package com.examstack.common.domain.question;

import java.io.Serializable;
import java.util.Date;

public class Comment implements Serializable  {
	
	private static final long serialVersionUID = 304471288989898758L;
	private int commentId;
	private int referId;
	private int commentType;
	private int indexId;
	private int userId;
	private String username;
	private String contentMsg;
	private int quotoId;
	private int reId;
	private Date createTime;
	public int getCommentId() {
		return commentId;
	}
	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}
	
	public int getReferId() {
		return referId;
	}
	public void setReferId(int referId) {
		this.referId = referId;
	}
	public int getCommentType() {
		return commentType;
	}
	public void setCommentType(int commentType) {
		this.commentType = commentType;
	}
	public int getIndexId() {
		return indexId;
	}
	public void setIndexId(int indexId) {
		this.indexId = indexId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getContentMsg() {
		return contentMsg;
	}
	public void setContentMsg(String contentMsg) {
		this.contentMsg = contentMsg;
	}
	public int getQuotoId() {
		return quotoId;
	}
	public void setQuotoId(int quotoId) {
		this.quotoId = quotoId;
	}
	public int getReId() {
		return reId;
	}
	public void setReId(int reId) {
		this.reId = reId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}

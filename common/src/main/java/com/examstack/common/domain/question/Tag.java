package com.examstack.common.domain.question;

import java.io.Serializable;
import java.util.Date;

public class Tag implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 4266590307234837998L;
	private int tagId;
    private String tagName;
    private Date createTime;
    private int creator;
    private String creatorName;
    private boolean privatee;
    private String memo;

    public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public int getCreator() {
        return creator;
    }

    public void setCreator(int creator) {
        this.creator = creator;
    }

    public boolean isPrivatee() {
        return privatee;
    }

    public void setPrivatee(boolean privatee) {
        this.privatee = privatee;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}

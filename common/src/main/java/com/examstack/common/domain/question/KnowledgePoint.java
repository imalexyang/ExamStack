package com.examstack.common.domain.question;

/**
 * 知识点
 * @author mars
 *
 */
public class KnowledgePoint {

	private int pointId;
	private String pointName;
	private int fieldId;
	private String fieldName;
	private String memo;
	private boolean removeable;
	private int state;
	
	public boolean isRemoveable() {
		return removeable;
	}
	public void setRemoveable(boolean removeable) {
		this.removeable = removeable;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public int getPointId() {
		return pointId;
	}
	public void setPointId(int pointId) {
		this.pointId = pointId;
	}
	public String getPointName() {
		return pointName;
	}
	public void setPointName(String pointName) {
		this.pointName = pointName;
	}
	public int getFieldId() {
		return fieldId;
	}
	public void setFieldId(int fieldId) {
		this.fieldId = fieldId;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
}

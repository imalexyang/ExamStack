package com.examstack.common.domain.question;

public class QuestionStatistic {

	private int fieldId;
	private String fieldName;
	private int pointId;
	private String pointName;
	private int questionTypeId;
	private String questionTypeName;
	private int amount;
	private int rightAmount;
	private int wrongAmount;
	public int getQuestionTypeId() {
		return questionTypeId;
	}
	public void setQuestionTypeId(int questionTypeId) {
		this.questionTypeId = questionTypeId;
	}
	public String getQuestionTypeName() {
		return questionTypeName;
	}
	public void setQuestionTypeName(String questionTypeName) {
		this.questionTypeName = questionTypeName;
	}
	public int getFieldId() {
		return fieldId;
	}
	public void setFieldId(int fieldId) {
		this.fieldId = fieldId;
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
	
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int getRightAmount() {
		return rightAmount;
	}
	public void setRightAmount(int rightAmount) {
		this.rightAmount = rightAmount;
	}
	public int getWrongAmount() {
		return wrongAmount;
	}
	public void setWrongAmount(int wrongAmount) {
		this.wrongAmount = wrongAmount;
	}
	
}

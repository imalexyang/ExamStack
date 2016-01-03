package com.examstack.common.domain.question;

public class QuestionStruts {

	private int questionId;
	private int questionTypeId;
	private double exposeTimes;
	private double rightTimes;
	private double wrongTimes;
	private float difficulty;
	private int pointId;
	private int referenceId;
	private float point;
	private String keyword;
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public float getPoint() {
		return point;
	}
	public void setPoint(float point) {
		this.point = point;
	}
	public int getPointId() {
		return pointId;
	}
	public void setPointId(int pointId) {
		this.pointId = pointId;
	}
	public int getReferenceId() {
		return referenceId;
	}
	public void setReferenceId(int referenceId) {
		this.referenceId = referenceId;
	}
	public int getQuestionId() {
		return questionId;
	}
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	public int getQuestionTypeId() {
		return questionTypeId;
	}
	public void setQuestionTypeId(int questionTypeId) {
		this.questionTypeId = questionTypeId;
	}
	public double getExposeTimes() {
		return exposeTimes;
	}
	public void setExposeTimes(double exposeTimes) {
		this.exposeTimes = exposeTimes;
	}
	public double getRightTimes() {
		return rightTimes;
	}
	public void setRightTimes(double rightTimes) {
		this.rightTimes = rightTimes;
	}
	public double getWrongTimes() {
		return wrongTimes;
	}
	public void setWrongTimes(double wrongTimes) {
		this.wrongTimes = wrongTimes;
	}
	public float getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(float difficulty) {
		this.difficulty = difficulty;
	}
	
}

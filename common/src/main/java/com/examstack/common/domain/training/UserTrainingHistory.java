package com.examstack.common.domain.training;

import java.util.Date;

public class UserTrainingHistory {

	private int histId;
	private int trainingId;
	private int sectionId;
	private int userId;
	private float process;
	private float duration;
	private Date startTime;
	private Date lastStateTime;
	private String userTrainingDetail;
	
	public int getSectionId() {
		return sectionId;
	}
	public void setSectionId(int sectionId) {
		this.sectionId = sectionId;
	}
	public int getHistId() {
		return histId;
	}
	public void setHistId(int histId) {
		this.histId = histId;
	}
	public int getTrainingId() {
		return trainingId;
	}
	public void setTrainingId(int trainingId) {
		this.trainingId = trainingId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public float getProcess() {
		return process;
	}
	public void setProcess(float process) {
		this.process = process;
	}
	public float getDuration() {
		return duration;
	}
	public void setDuration(float duration) {
		this.duration = duration;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getLastStateTime() {
		return lastStateTime;
	}
	public void setLastStateTime(Date lastStateTime) {
		this.lastStateTime = lastStateTime;
	}
	public String getUserTrainingDetail() {
		return userTrainingDetail;
	}
	public void setUserTrainingDetail(String userTrainingDetail) {
		this.userTrainingDetail = userTrainingDetail;
	}
	
}

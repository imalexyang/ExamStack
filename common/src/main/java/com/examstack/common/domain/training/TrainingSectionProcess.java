package com.examstack.common.domain.training;

public class TrainingSectionProcess extends TrainingSection {

	private String trainingName;
	private int userId;
	private String userName;
	private float process;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTrainingName() {
		return trainingName;
	}

	public void setTrainingName(String trainingName) {
		this.trainingName = trainingName;
	}

	public float getProcess() {
		return process;
	}

	public void setProcess(float process) {
		this.process = process;
	}
	
}

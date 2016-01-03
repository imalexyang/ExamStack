package com.examstack.management.service;

import java.util.List;
import java.util.Map;

import com.examstack.common.domain.training.Training;
import com.examstack.common.domain.training.TrainingSection;
import com.examstack.common.domain.training.TrainingSectionProcess;
import com.examstack.common.domain.training.UserTraining;
import com.examstack.common.util.Page;

public interface TrainingService {

	/**
	 * 获取培训列表
	 * @param userId 为-1时，获取所有培训，否则获取userId发布的培训
	 * @param page
	 * @return
	 */
	public List<Training> getTrainingList(int userId, Page<Training> page);
	
	/**
	 * 添加培训
	 * @param training
	 */
	public void addTraining(Training training);
	
	/**
	 * 添加章节
	 * @param section
	 */
	public void addTrainingSection(TrainingSection section);
	
	/**
	 * 获取培训章节
	 * @param trainingId
	 * @param userId 不等于-1则为该用户发布的培训
	 * @param page
	 * @return
	 */
	public List<TrainingSection> getTrainingSectionByTrainingId(int trainingId, int userId, Page<TrainingSection> page);
	
	/**
	 * 获取培训章节
	 * @param sectionId
	 * @param userId 不等于-1则为该用户发布的培训
	 * @param page
	 * @return
	 */
	public List<TrainingSection> getTrainingSectionById(int sectionId, int userId, Page<TrainingSection> page);
	
	/**
	 * 
	 * @param sectionId
	 * @param userId 不等于-1则为该用户发布的培训
	 */
	public void deleteTrainingSection(int sectionId, int userId);
	
	/**
	 * 获取培训id下每个用户的章节培训进度
	 * @param trainingId
	 * @param searchStr
	 * @param page
	 * @return
	 */
	public Map<Integer, List<TrainingSectionProcess>> getTrainingSectionProcessMapByTrainingId(int trainingId, List<Integer> idList,String searchStr);
	
	/**
	 * 获取培训id下用户培训清单
	 * @param trainingId
	 * @param userId
	 * @param searchStr
	 * @param page
	 * @return
	 */
	public List<UserTraining> getUserTrainingList(int trainingId, int userId,String searchStr, Page<UserTraining> page);
}

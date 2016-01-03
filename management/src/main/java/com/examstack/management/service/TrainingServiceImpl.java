package com.examstack.management.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examstack.common.domain.training.Training;
import com.examstack.common.domain.training.TrainingSection;
import com.examstack.common.domain.training.TrainingSectionProcess;
import com.examstack.common.domain.training.UserTraining;
import com.examstack.common.util.Page;
import com.examstack.management.persistence.TrainingMapper;

@Service("trainingService")
public class TrainingServiceImpl implements TrainingService {

	@Autowired
	private TrainingMapper trainingMapper;
	@Override
	public List<Training> getTrainingList(int userId, Page<Training> page) {
		// TODO Auto-generated method stub
		return trainingMapper.getTrainingList(userId, page);
	}
	@Override
	public void addTraining(Training training) {
		// TODO Auto-generated method stub
		trainingMapper.addTraining(training);
	}
	@Override
	public List<TrainingSection> getTrainingSectionByTrainingId(int trainingId, int userId, Page<TrainingSection> page) {
		// TODO Auto-generated method stub
		return trainingMapper.getTrainingSectionByTrainingId(trainingId, userId, page);
	}
	@Override
	public List<TrainingSection> getTrainingSectionById(int sectionId, int userId, Page<TrainingSection> page) {
		// TODO Auto-generated method stub
		return trainingMapper.getTrainingSectionById(sectionId, userId, page);
	}
	@Override
	public void deleteTrainingSection(int sectionId, int userId) {
		// TODO Auto-generated method stub
		trainingMapper.deleteTrainingSection(sectionId, userId);
	}
	@Override
	public void addTrainingSection(TrainingSection section) {
		// TODO Auto-generated method stub
		trainingMapper.addTrainingSection(section);
	}
	@Override
	public Map<Integer, List<TrainingSectionProcess>> getTrainingSectionProcessMapByTrainingId(int trainingId, List<Integer> idList,String searchStr) {
		// TODO Auto-generated method stub
		if(idList.size() == 0)
			idList = null;
		List<TrainingSectionProcess> processList = trainingMapper.getTrainingSectionProcessListByTrainingId(trainingId, idList,searchStr, null);
		HashMap<Integer,List<TrainingSectionProcess>> map = new HashMap<Integer,List<TrainingSectionProcess>>();
		for(TrainingSectionProcess process : processList){
			List<TrainingSectionProcess> tmpList = new ArrayList<TrainingSectionProcess>();
			if(map.containsKey(process.getUserId()))
				tmpList = map.get(process.getUserId());
			tmpList.add(process);
			map.put(process.getUserId(), tmpList);
		}
		return map;
	}
	@Override
	public List<UserTraining> getUserTrainingList(int trainingId, int userId,String searchStr, Page<UserTraining> page) {
		// TODO Auto-generated method stub
		return trainingMapper.getUserTrainingList(trainingId, userId,searchStr, page);
	}

}

package com.examstack.portal.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examstack.common.domain.training.Training;
import com.examstack.common.domain.training.TrainingSection;
import com.examstack.common.domain.training.TrainingSectionProcess;
import com.examstack.common.domain.training.UserTrainingHistory;
import com.examstack.common.util.Page;
import com.examstack.portal.persistence.TrainingMapper;

@Service("trainingService")
public class TrainingServiceImpl implements TrainingService {

	@Autowired
	private TrainingMapper trainingMapper;
	@Override
	public List<Training> getTrainingList(Page<Training> page) {
		// TODO Auto-generated method stub
		return trainingMapper.getTrainingList(page);
	}
	
	@Override
	public List<TrainingSection> getTrainingSectionByTrainingId(int trainingId, Page<TrainingSection> page) {
		// TODO Auto-generated method stub
		return trainingMapper.getTrainingSectionByTrainingId(trainingId, page);
	}
	@Override
	public List<TrainingSection> getTrainingSectionById(int sectionId, Page<TrainingSection> page) {
		// TODO Auto-generated method stub
		return trainingMapper.getTrainingSectionById(sectionId, page);
	}

	@Override
	public UserTrainingHistory getTrainingHistBySectionId(int sectionId, int userId) {
		// TODO Auto-generated method stub
		return trainingMapper.getTrainingHistBySectionId(sectionId, userId);
	}

	@Override
	public void setUserTrainingHistory(UserTrainingHistory hist) {
		// TODO Auto-generated method stub
		trainingMapper.setUserTrainingHistory(hist);
	}

	@Override
	public Map<Integer, List<TrainingSectionProcess>> getTrainingSectionProcessMapByUserId(int userId) {
		// TODO Auto-generated method stub
		List<TrainingSectionProcess> processList = trainingMapper.getTrainingSectionProcessListByUserId(userId, null);
		HashMap<Integer,List<TrainingSectionProcess>> map = new HashMap<Integer,List<TrainingSectionProcess>>();
		for(TrainingSectionProcess process : processList){
			List<TrainingSectionProcess> tmpList = new ArrayList<TrainingSectionProcess>();
			if(map.containsKey(process.getTrainingId()))
				tmpList = map.get(process.getTrainingId());
			tmpList.add(process);
			map.put(process.getTrainingId(), tmpList);
		}
		return map;
	}
}

package com.examstack.management.controller.page.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.examstack.common.domain.practice.KnowledgePointAnalysisResult;
import com.examstack.common.domain.practice.TypeAnalysis;
import com.examstack.common.domain.question.Field;
import com.examstack.common.domain.question.QuestionStatistic;
import com.examstack.common.domain.training.Training;
import com.examstack.common.domain.training.TrainingSection;
import com.examstack.common.domain.training.TrainingSectionProcess;
import com.examstack.common.domain.training.UserTraining;
import com.examstack.common.util.Page;
import com.examstack.common.util.PagingUtil;
import com.examstack.management.service.QuestionHistoryService;
import com.examstack.management.service.QuestionService;
import com.examstack.management.service.TrainingService;

@Controller
public class TrainingPageAdmin {
	
	@Autowired
	private TrainingService trainingService;
	@Autowired
	private QuestionService questionService;
	@Autowired
	private QuestionHistoryService questionHistoryService;
	/**
	 * 培训列表
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/admin/training/training-list", method = RequestMethod.GET)
	public String trainingListPage(Model model, HttpServletRequest request, @RequestParam(value="page",required=false,defaultValue="1") int page) {
	
		Page<Training> pageModel = new Page<Training>();
		pageModel.setPageNo(page);
		pageModel.setPageSize(10);
		List<Training> trainingList = trainingService.getTrainingList(-1, pageModel);
		String pageStr = PagingUtil.getPageBtnlink(page,
				pageModel.getTotalPage());
		model.addAttribute("pageStr", pageStr);
		model.addAttribute("trainingList", trainingList);
		return "training-list";
	}
	
	/**
	 * 添加培训
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/admin/training/training-add", method = RequestMethod.GET)
	public String trainingAddPage(Model model, HttpServletRequest request) {
		List<Field> fieldList = questionService.getAllField(null);
		model.addAttribute("fieldList", fieldList);
		return "training-add";
	}
	
	
	@RequestMapping(value = "/admin/training/student-practice-status/{userId}", method = RequestMethod.GET)
	public String practiceStatusDefaultPage(Model model, HttpServletRequest request, @PathVariable int userId) {
		List<Field>  fieldList = questionService.getAllField(null);
		return "redirect:/admin/training/student-practice-status/"+ fieldList.get(0).getFieldId() +"/" + userId;
	}
	/**
	 * 学习进度
	 * @param model
	 * @param request
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/admin/training/student-practice-status/{fieldId}/{userId}", method = RequestMethod.GET)
	public String practiceStatusPage(Model model, HttpServletRequest request, @PathVariable int fieldId, @PathVariable int userId) {
		
		//TO-DO:fieldId=0时取第一个fieldId，或者取有用户练习记录的fieldId中的第一条
		Map<Integer, Map<Integer, QuestionStatistic>> questionMap = questionService.getTypeQuestionStaticByFieldId(fieldId);
		Map<Integer, Map<Integer, QuestionStatistic>> historyMap = questionHistoryService.getTypeQuestionHistStaticByFieldId(fieldId, userId);
		
		List<KnowledgePointAnalysisResult> kparl = new ArrayList<KnowledgePointAnalysisResult>();
		for(Map.Entry<Integer, Map<Integer, QuestionStatistic>> entry : questionMap.entrySet()){
			KnowledgePointAnalysisResult kpar = new KnowledgePointAnalysisResult();
			kpar.setKnowledgePointId(entry.getKey());
			List<TypeAnalysis> tal = new ArrayList<TypeAnalysis>();
			int totalRightAmount = 0;
			int totalAmount = 0;
			for(Map.Entry<Integer, QuestionStatistic> entry1 : entry.getValue().entrySet()){
				TypeAnalysis ta = new TypeAnalysis();
				ta.setQuestionTypeId(entry1.getKey());
				ta.setQuestionTypeName(entry1.getValue().getQuestionTypeName());
				int rightAmount = 0;
				int wrongAmount = 0;
				try {
					rightAmount = historyMap.get(entry.getKey()).get(entry1.getKey()).getRightAmount();
				} catch (Exception e) {}
				try {
					wrongAmount = historyMap.get(entry.getKey()).get(entry1.getKey()).getWrongAmount();
				} catch (Exception e) {}
				ta.setRightAmount(rightAmount);
				ta.setWrongAmount(wrongAmount);
				ta.setRestAmount(entry1.getValue().getAmount() - rightAmount - wrongAmount);
				tal.add(ta);
				if(kpar.getKnowledgePointName() == null)
					kpar.setKnowledgePointName(entry1.getValue().getPointName());
				totalRightAmount += rightAmount;
				totalAmount += entry1.getValue().getAmount();
			}
			kpar.setTypeAnalysis(tal);
			if(totalAmount > 0)
				kpar.setFinishRate((float)totalRightAmount / (float)totalAmount);
			kparl.add(kpar);
		}
		
		model.addAttribute("kparl", kparl);
		model.addAttribute("fieldList", questionService.getAllField(null));
		model.addAttribute("fieldId", fieldId);
		return "practice-status";
	}
	
	/**
	 * 培训进度
	 * @param model
	 * @param request
	 * @param trainingHistoryId
	 * @return
	 */
	@RequestMapping(value = "/admin/training/student-training-history/{trainingId}", method = RequestMethod.GET)
	public String trainingHistoryPage(Model model, HttpServletRequest request, @PathVariable int trainingId,@RequestParam(value="searchStr",required=false,defaultValue="") String searchStr, @RequestParam(value="page",required=false,defaultValue="1") int page) {
		
		Page<UserTraining> pageModel = new Page<UserTraining>();
		pageModel.setPageNo(page);
		pageModel.setPageSize(10);
		List<UserTraining> userTrainingList = trainingService.getUserTrainingList(trainingId, -1,searchStr, pageModel);
		List<Integer> idList = new ArrayList<Integer>();
		for(UserTraining userTraining : userTrainingList){
			idList.add(userTraining.getUserId());
		}
		Map<Integer,List<TrainingSectionProcess>> map = trainingService.getTrainingSectionProcessMapByTrainingId(trainingId, idList,searchStr);
		String pageStr = PagingUtil.getPageBtnlink(page,
				pageModel.getTotalPage());
		model.addAttribute("trainingId", trainingId);
		model.addAttribute("userTrainingList", userTrainingList);
		model.addAttribute("processMap", map);
		model.addAttribute("pageStr", pageStr);
		model.addAttribute("searchStr", searchStr);
		return "training-history";
	}
	
	/**
	 * 培训章节
	 * @param model
	 * @param request
	 * @param trainingId
	 * @return
	 */
	@RequestMapping(value = "/admin/training/section-list/{trainingId}", method = RequestMethod.GET)
	public String sectionPage(Model model, HttpServletRequest request, @PathVariable int trainingId) {
		
		List<TrainingSection> sectionList = trainingService.getTrainingSectionByTrainingId(trainingId, -1, null);
		model.addAttribute("sectionList", sectionList);
		return "section";
	}
}

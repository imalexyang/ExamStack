package com.examstack.portal.controller.page;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.examstack.common.domain.exam.ExamHistory;
import com.examstack.common.domain.practice.KnowledgePointAnalysisResult;
import com.examstack.common.domain.practice.TypeAnalysis;
import com.examstack.common.domain.question.Field;
import com.examstack.common.domain.question.QuestionStatistic;
import com.examstack.common.util.Page;
import com.examstack.common.util.PagingUtil;
import com.examstack.portal.security.UserInfo;
import com.examstack.portal.service.ExamService;
import com.examstack.portal.service.QuestionHistoryService;
import com.examstack.portal.service.QuestionService;
import com.google.gson.Gson;

/**
 * @author Ocelot
 * @date 2014年7月29日 下午4:31:03
 */
@Controller
public class UserCenterPage {

	@Autowired
	private QuestionService questionService;
	@Autowired
	private QuestionHistoryService questionHistoryService;
	@Autowired
	private ExamService examService;
	
	
	
	@RequestMapping(value = "student/usercenter", method = RequestMethod.GET)
	public String userCenterDefaultPage(Model model, HttpServletRequest request) {
		
		List<Field>  fieldList = questionService.getAllField(null);
		return "redirect:/student/usercenter/"+ fieldList.get(0).getFieldId();
		
	}

	/**
	 * 用户中心主页
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "student/usercenter/{fieldId}", method = RequestMethod.GET)
	public String userCenterPage(Model model, HttpServletRequest request, @PathVariable int fieldId) {

		UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		//TO-DO:手动设置fieldId需要删除，这里仅测试
		
		model.addAttribute("username", userInfo.getUsername());
		model.addAttribute("username", userInfo.getTrueName());
		model.addAttribute("email", userInfo.getEmail());
		model.addAttribute("field", userInfo.getFieldName());
		
		Map<Integer,QuestionStatistic> questionStatisticMap = questionService.getQuestionStaticByFieldId(fieldId);
		Map<Integer,QuestionStatistic> questionHistStatisticMap = questionHistoryService.getQuestionHistStaticByFieldId(fieldId, userInfo.getUserid());
		model.addAttribute("lastLoginTime", userInfo.getLastLoginTime());
		List<StatisticsResult> resultList = new ArrayList<StatisticsResult>();
		
		List<Label> titleList = new ArrayList<Label>();
		List<Float> finishRateList = new ArrayList<Float>();
		List<Float> rightRateList = new ArrayList<Float>();
		for(Map.Entry<Integer,QuestionStatistic> entry : questionStatisticMap.entrySet()){
			StatisticsResult sr = new StatisticsResult();
			sr.setPointId(entry.getValue().getPointId());
			sr.setPointName(entry.getValue().getPointName());
			sr.setAmount(entry.getValue().getAmount());
			if(questionHistStatisticMap.containsKey(entry.getKey())){
				int rightAmount = questionHistStatisticMap.get(entry.getKey()).getRightAmount();
				int wrongAmount = questionHistStatisticMap.get(entry.getKey()).getWrongAmount();
				int amount = questionHistStatisticMap.get(entry.getKey()).getAmount();
				sr.setRightTimes(rightAmount);
				sr.setWrongTimes(wrongAmount);
				sr.setFinishRate((float)amount / (float)entry.getValue().getAmount());
				sr.setRightRate(amount == 0 ? 0 : ((float)rightAmount / (float)amount));
				finishRateList.add(sr.getFinishRate());
				rightRateList.add(sr.getRightRate());
			}else{
				finishRateList.add(0f);
				rightRateList.add(0f);
			}
			resultList.add(sr);
			Label label = new Label(sr.getPointName(),1);
			titleList.add(label);
		}
		
		Gson gson = new Gson();
		model.addAttribute("sr", resultList);
		model.addAttribute("labels", gson.toJson(titleList));
		model.addAttribute("finishrate", gson.toJson(finishRateList));
		model.addAttribute("correctrate", gson.toJson(rightRateList));
		model.addAttribute("fieldList", questionService.getAllField(null));
		model.addAttribute("fieldId", fieldId);
		return "usercenter";
	}
	
	
	@RequestMapping(value = "student/analysis", method = RequestMethod.GET)
	public String userAnalysisDefaultPage(Model model, HttpServletRequest request) {
		List<Field>  fieldList = questionService.getAllField(null);
		return "redirect:/student/analysis/"+ fieldList.get(0).getFieldId();
	}
	
	/**
	 * 分析页面
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "student/analysis/{fieldId}", method = RequestMethod.GET)
	public String userAnalysisPage(Model model, HttpServletRequest request,  @PathVariable int fieldId) {

		UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		model.addAttribute("lastLoginTime", userInfo.getLastLoginTime());
		Map<Integer, Map<Integer, QuestionStatistic>> questionMap = questionService.getTypeQuestionStaticByFieldId(fieldId);
		Map<Integer, Map<Integer, QuestionStatistic>> historyMap = questionHistoryService.getTypeQuestionHistStaticByFieldId(fieldId, userInfo.getUserid());
		
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
		return "analysis";
	}
	
	@RequestMapping(value = "student/exam-history", method = RequestMethod.GET)
	public String userExamHistPage(Model model, HttpServletRequest request){
		
		int index = 1;
		if(request.getParameter("page") != null)
			index = Integer.parseInt(request.getParameter("page"));
		UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		Page<ExamHistory> pageModel = new Page<ExamHistory>();
		//pageModel.setPageSize(1);
		pageModel.setPageNo(index);
		List<ExamHistory> hisList = examService.getUserExamHistByUserId(userInfo.getUserid(), pageModel, 1,2,3);
		model.addAttribute("hisList", hisList);
		String pageStr = PagingUtil.getPagelink(index, pageModel.getTotalPage(), "", "student/exam-history");
		model.addAttribute("pageStr", pageStr);
		return "exam-history";
	}
	
	@RequestMapping(value = "/user-detail/{userName}", method = RequestMethod.GET)
	public String userInfoPage(Model model, HttpServletRequest request, @PathVariable("userName") String userName) {

		return "redirect:/student/usercenter";
	}

	class Label{
		public String text;
		public int max;
		public Label(String text,int max){
			this.text = text;
			this.max = max;
		}
	}

	public class StatisticsResult {
		public int pointId;
		public String pointName;
		public int amount;
		public int rightTimes;
		public int wrongTimes;
		public float finishRate;
		public float rightRate;

		public float getFinishRate() {
			return finishRate;
		}

		public void setFinishRate(float finishRate) {
			this.finishRate = finishRate;
		}

		public float getRightRate() {
			return rightRate;
		}

		public void setRightRate(float rightRate) {
			this.rightRate = rightRate;
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

		public int getRightTimes() {
			return rightTimes;
		}

		public void setRightTimes(int rightTimes) {
			this.rightTimes = rightTimes;
		}

		public int getWrongTimes() {
			return wrongTimes;
		}

		public void setWrongTimes(int wrongTimes) {
			this.wrongTimes = wrongTimes;
		}

	}
}

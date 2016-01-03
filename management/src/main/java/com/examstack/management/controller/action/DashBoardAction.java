package com.examstack.management.controller.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examstack.common.domain.exam.ExamHistory;
import com.examstack.common.domain.exam.ExamPaper;
import com.examstack.common.domain.question.PointStatistic;
import com.examstack.common.domain.question.Question;
import com.examstack.common.domain.question.QuestionFilter;
import com.examstack.common.domain.user.User;
import com.examstack.common.util.Page;
import com.examstack.management.security.UserInfo;
import com.examstack.management.service.ExamPaperService;
import com.examstack.management.service.ExamService;
import com.examstack.management.service.QuestionService;
import com.examstack.management.service.UserService;

@Controller
public class DashBoardAction {
	
	@Autowired
	private QuestionService questionService;
	@Autowired
	private UserService userService;
	@Autowired
	private ExamPaperService examPaperService;
	@Autowired
	private ExamService examService;
	@RequestMapping(value = "/secure/dashboard/baseinfo", method = RequestMethod.GET)
	public @ResponseBody List<Integer> baseInfo(Model model) {
		
		UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext()
			    .getAuthentication()
			    .getPrincipal();
		Page<User> pageUser = new Page<User>();
		pageUser.setPageNo(1);
		pageUser.setPageSize(1);
		userService.getUserListByRoleId(userInfo.getRoleMap().get("ROLE_STUDENT").getRoleId(), pageUser);
		
		Page<Question> pageQuestion = new Page<Question>();
		pageQuestion.setPageNo(1);
		pageQuestion.setPageSize(1);
		QuestionFilter qf = new QuestionFilter();
		qf.setFieldId(0);
		qf.setKnowledge(0);
		qf.setQuestionType(0);
		qf.setTag(0);
		qf.setSearchParam("-1");
		questionService.getQuestionList(pageQuestion, qf);
		
		Page<ExamPaper> pageExamPaper = new Page<ExamPaper>();
		pageExamPaper.setPageNo(1);
		pageExamPaper.setPageSize(1);
		examPaperService.getEnabledExamPaperList(userInfo.getUsername(), pageExamPaper);
		List<Integer> l = new ArrayList<Integer>();
		l.add(pageQuestion.getTotalRecord());
		l.add(pageExamPaper.getTotalRecord());
		l.add(pageUser.getTotalRecord());
		return l;
	}

	@RequestMapping(value = "/secure/dashboard/studentApprovedList", method = RequestMethod.GET)
	public @ResponseBody List<ExamHistory> studentApprovedList(Model model) {
		Page<ExamHistory> page = new Page<ExamHistory>();
		page.setPageNo(1);
		page.setPageSize(4);
		List<ExamHistory> histList = examService.getUserExamHistList(page, 0);
		
		return histList;
	}

	@RequestMapping(value = "/secure/dashboard/StudentMarkList", method = RequestMethod.GET)
	public @ResponseBody List<ExamHistory> studentMarkList(Model model) {
		Page<ExamHistory> page = new Page<ExamHistory>();
		page.setPageNo(1);
		page.setPageSize(4);
		List<ExamHistory> histList = examService.getUserExamHistList(page, 2);
		
		return histList;
	}

	@RequestMapping(value = "/secure/dashboard/chartinfo/{fieldId}", method = RequestMethod.GET)
	public @ResponseBody List<FieldNumber> chartInfo(Model model,@PathVariable("fieldId") int fieldId) {

		List<PointStatistic> pointStatisticList = questionService.getPointCount(fieldId, null);
		List<FieldNumber> l = new ArrayList<FieldNumber>();
		
		for(PointStatistic ps : pointStatisticList){
			FieldNumber fieldNumber = new FieldNumber();
			fieldNumber.name = ps.getPointName();
			fieldNumber.amount = ps.getAmount();
			l.add(fieldNumber);
		}
		
		return l;
	}
	
	
	class FieldNumber{
		private String name;
		private int amount;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getAmount() {
			return amount;
		}
		public void setAmount(int amount) {
			this.amount = amount;
		}
		
	}

}

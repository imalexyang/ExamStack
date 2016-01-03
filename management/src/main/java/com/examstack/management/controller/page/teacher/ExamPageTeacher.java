package com.examstack.management.controller.page.teacher;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.examstack.common.domain.exam.Exam;
import com.examstack.common.domain.exam.ExamHistory;
import com.examstack.common.domain.exam.ExamPaper;
import com.examstack.common.domain.question.QuestionQueryResult;
import com.examstack.common.domain.user.Group;
import com.examstack.common.util.Page;
import com.examstack.common.util.PagingUtil;
import com.examstack.common.util.QuestionAdapter;
import com.examstack.management.security.UserInfo;
import com.examstack.management.service.ExamPaperService;
import com.examstack.management.service.ExamService;
import com.examstack.management.service.UserService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Controller
public class ExamPageTeacher {

	@Autowired
	private UserService userService;
	@Autowired
	private ExamPaperService examPaperService;
	@Autowired
	private ExamService examService;
	/**
	 * 考试管理
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/teacher/exam/exam-list", method = RequestMethod.GET)
	private String examListPage(Model model, HttpServletRequest request, @RequestParam(value="page",required=false,defaultValue="1") int page) {
		
		Page<Exam> pageModel = new Page<Exam>();
		pageModel.setPageNo(page);
		pageModel.setPageSize(8);
		List<Exam> examList = examService.getExamList(pageModel,1,2);
		String pageStr = PagingUtil.getPagelink(page, pageModel.getTotalPage(), "", "teacher/exam/exam-list");

		model.addAttribute("examList", examList);
		model.addAttribute("pageStr", pageStr);
		return "exam-list";
	}
	
	/**
	 * 模拟考试列表
	 * @param model
	 * @param request
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/teacher/exam/model-test-list", method = RequestMethod.GET)
	private String modelTestListPage(Model model, HttpServletRequest request, @RequestParam(value="page",required=false,defaultValue="1") int page) {
		
		Page<Exam> pageModel = new Page<Exam>();
		pageModel.setPageNo(page);
		pageModel.setPageSize(10);
		List<Exam> examList = examService.getExamList(pageModel,3);
		String pageStr = PagingUtil.getPageBtnlink(page,
				pageModel.getTotalPage());
		model.addAttribute("examList", examList);
		model.addAttribute("pageStr", pageStr);
		return "model-test-list";
	}

	/**
	 * 发布考试
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/teacher/exam/exam-add", method = RequestMethod.GET)
	private String examAddPage(Model model, HttpServletRequest request) {
		
		UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext()
			    .getAuthentication()
			    .getPrincipal();
		List<Group> groupList = userService.getGroupListByUserId(userInfo.getUserid(), null);
		List<ExamPaper> examPaperList = examPaperService.getEnabledExamPaperList(userInfo.getUsername(), null);
		model.addAttribute("groupList", groupList);
		model.addAttribute("examPaperList", examPaperList);
		return "exam-add";
	}
	
	/**
	 * 发布考试
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/teacher/exam/model-test-add", method = RequestMethod.GET)
	private String modelTestAddPage(Model model, HttpServletRequest request) {
		
		UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext()
			    .getAuthentication()
			    .getPrincipal();
		List<ExamPaper> examPaperList = examPaperService.getEnabledExamPaperList(userInfo.getUsername(), null);
		
		model.addAttribute("examPaperList", examPaperList);
		return "model-test-add";
	}

	/**
	 * 学员名单
	 * 
	 * @param model
	 * @param request
	 * @param examId
	 * @return
	 */
	@RequestMapping(value = "/teacher/exam/exam-student-list/{examId}", method = RequestMethod.GET)
	private String examStudentListPage(Model model, HttpServletRequest request, @PathVariable int examId,@RequestParam(value="searchStr",required=false,defaultValue="") String searchStr,@RequestParam(value="order",required=false,defaultValue="") String order,@RequestParam(value="limit",required=false,defaultValue="0") int limit, @RequestParam(value="page",required=false,defaultValue="1") int page) {
		UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext()
			    .getAuthentication()
			    .getPrincipal();
		Page<ExamHistory> pageModel = new Page<ExamHistory>();
		pageModel.setPageNo(page);
		pageModel.setPageSize(10);
		if("".equals(searchStr))
			searchStr = null;
		if("".equals(order) || (!"desc".equals(order) && !"asc".equals(order)))
			order = null;
		List<ExamHistory> histList = examService.getUserExamHistListByExamId(examId, searchStr, order, limit, pageModel);
		String pageStr = PagingUtil.getPageBtnlink(page,
				pageModel.getTotalPage());
		List<Group> groupList = userService.getGroupListByUserId(userInfo.getUserid(), null);
		model.addAttribute("groupList", groupList);
		model.addAttribute("histList", histList);
		model.addAttribute("pageStr", pageStr);
		model.addAttribute("examId", examId);
		model.addAttribute("limit", limit);
		model.addAttribute("order", order);
		model.addAttribute("searchStr", searchStr);
		return "user-exam-list";
	}
	
	/**
	 * 学员试卷
	 * @param model
	 * @param request
	 * @param examhistoryId
	 * @return
	 */
	@RequestMapping(value = "/teacher/exam/student-answer-sheet/{histId}", method = RequestMethod.GET)
	private String studentAnswerSheetPage(Model model, HttpServletRequest request, @PathVariable int histId) {
		
		ExamHistory history = examService.getUserExamHistListByHistId(histId);
		int examPaperId = history.getExamPaperId();
		
		String strUrl = "http://" + request.getServerName() // 服务器地址
				+ ":" + request.getServerPort() + "/";
		
		ExamPaper examPaper = examPaperService.getExamPaperById(examPaperId);
		StringBuilder sb = new StringBuilder();
		if(examPaper.getContent() != null && !examPaper.getContent().equals("")){
			Gson gson = new Gson();
			String content = examPaper.getContent();
			List<QuestionQueryResult> questionList = gson.fromJson(content, new TypeToken<List<QuestionQueryResult>>(){}.getType());
			
			for(QuestionQueryResult question : questionList){
				QuestionAdapter adapter = new QuestionAdapter(question,strUrl);
				sb.append(adapter.getStringFromXML());
			}
		}
		
		model.addAttribute("htmlStr", sb);
		model.addAttribute("exampaperid", examPaperId);
		model.addAttribute("examHistoryId", history.getHistId());
		model.addAttribute("exampapername", examPaper.getName());
		model.addAttribute("examId", history.getExamId());
		return "student-answer-sheet";
	}
	
	/**
	 * 人工阅卷
	 * @param model
	 * @param request
	 * @param examhistoryId
	 * @return
	 */
	@RequestMapping(value = "/teacher/exam/mark-exampaper/{examhistoryId}", method = RequestMethod.GET)
	private String markExampaperPage(Model model, HttpServletRequest request, @PathVariable int examhistoryId) {
		
		ExamHistory history = examService.getUserExamHistListByHistId(examhistoryId);
		int examPaperId = history.getExamPaperId();
		
		String strUrl = "http://" + request.getServerName() // 服务器地址
				+ ":" + request.getServerPort() + "/";
		
		ExamPaper examPaper = examPaperService.getExamPaperById(examPaperId);
		StringBuilder sb = new StringBuilder();
		if(examPaper.getContent() != null && !examPaper.getContent().equals("")){
			Gson gson = new Gson();
			String content = examPaper.getContent();
			List<QuestionQueryResult> questionList = gson.fromJson(content, new TypeToken<List<QuestionQueryResult>>(){}.getType());
			
			for(QuestionQueryResult question : questionList){
				QuestionAdapter adapter = new QuestionAdapter(question,strUrl);
				sb.append(adapter.getStringFromXML());
			}
		}
		
		model.addAttribute("htmlStr", sb);
		model.addAttribute("exampaperid", examPaperId);
		model.addAttribute("examHistoryId", history.getHistId());
		model.addAttribute("exampapername", examPaper.getName());
		model.addAttribute("examId", history.getExamId());
		return "exampaper-mark";
	}
	
}

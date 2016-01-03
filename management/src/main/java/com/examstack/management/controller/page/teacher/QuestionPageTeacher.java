package com.examstack.management.controller.page.teacher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.examstack.common.domain.question.Field;
import com.examstack.common.domain.question.Question;
import com.examstack.common.domain.question.QuestionFilter;
import com.examstack.common.domain.question.QuestionQueryResult;
import com.examstack.common.domain.question.Tag;
import com.examstack.common.util.Page;
import com.examstack.common.util.PagingUtil;
import com.examstack.common.util.QuestionAdapter;
import com.examstack.management.security.UserInfo;
import com.examstack.management.service.QuestionService;

@Controller
public class QuestionPageTeacher {
	
	@Autowired
	private QuestionService questionService;
	
	@RequestMapping(value = "/teacher/question/question-list", method = RequestMethod.GET)
	public String questionListPage(Model model) {

		return "redirect:question-list/filter-0-0-0-0-0-1.html";
	}
	
	@RequestMapping(value = "/teacher/question/question-list/filter-{fieldId}-{knowledge}-{questionType}-{tag}-{searchParam}-{page}.html", method = RequestMethod.GET)
	public String questionListFilterPage(Model model,
			@PathVariable("fieldId") int fieldId,
			@PathVariable("knowledge") int knowledge,
			@PathVariable("questionType") int questionType,
			@PathVariable("tag") int tag,
			@PathVariable("searchParam") String searchParam,
			@PathVariable("page") int page) {
		
		
		QuestionFilter qf = new QuestionFilter();
		qf.setFieldId(fieldId);
		qf.setKnowledge(knowledge);
		qf.setQuestionType(questionType);
		qf.setTag(tag);
		if (searchParam.equals("0"))
			searchParam = "-1";
		qf.setSearchParam(searchParam);

		Page<Question> pageModel = new Page<Question>();
		pageModel.setPageNo(page);
		pageModel.setPageSize(20);

		//TODO 查找questionlist的时候需要tag
		List<Question> questionList = questionService.getQuestionList(
				pageModel, qf);

		String pageStr = PagingUtil.getPageBtnlink(page,
				pageModel.getTotalPage());

		List<Field> fieldList = questionService.getAllField(null);
		model.addAttribute("fieldList", fieldList);

		/*if(fieldList.size() > 0)
			fieldId = fieldList.get(0).getFieldId();*/
		model.addAttribute("knowledgeList",
				questionService.getKnowledgePointByFieldId(fieldId,null));

		model.addAttribute("questionTypeList",
				questionService.getQuestionTypeList());

		model.addAttribute("questionFilter", qf);
		model.addAttribute("questionList", questionList);
		
		model.addAttribute("pageStr", pageStr);
		model.addAttribute("tagList", questionService.getTags(null));
		//保存筛选信息，删除后跳转页面时使用
		model.addAttribute("fieldId", fieldId);
		model.addAttribute("knowledge", knowledge);
		model.addAttribute("questionType", questionType);
		model.addAttribute("searchParam", "-1".equals(searchParam)?"":searchParam);

		return "question-list";
	}
	
	/**
	 * 题库页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/teacher/question/question-list/filterdialog-{fieldId}-{knowledge}-{questionType}-{searchParam}-{page}.html", method = RequestMethod.GET)
	public String questionListFilterDialogPage(Model model,
			@PathVariable("fieldId") int fieldId,
			@PathVariable("knowledge") int knowledge,
			@PathVariable("questionType") int questionType,
			@PathVariable("searchParam") String searchParam,
			@PathVariable("page") int page) {
		
		
		QuestionFilter qf = new QuestionFilter();
		qf.setFieldId(fieldId);
		qf.setKnowledge(knowledge);
		qf.setQuestionType(questionType);
		if (searchParam.equals("0"))
			searchParam = "-1";
		qf.setSearchParam(searchParam);

		Page<Question> pageModel = new Page<Question>();
		pageModel.setPageNo(page);
		pageModel.setPageSize(20);

		List<Question> questionList = questionService.getQuestionList(
				pageModel, qf);

		String pageStr = PagingUtil.getPageBtnlink(page,
				pageModel.getTotalPage());

		model.addAttribute("fieldList", questionService.getAllField(null));

		model.addAttribute("knowledgeList",
				questionService.getKnowledgePointByFieldId(fieldId,null));

		model.addAttribute("questionTypeList",
				questionService.getQuestionTypeList());

		model.addAttribute("questionFilter", qf);
		model.addAttribute("questionList", questionList);
		model.addAttribute("pageStr", pageStr);
		
		//保存筛选信息，删除后跳转页面时使用
		model.addAttribute("fieldId", fieldId);
		model.addAttribute("knowledge", knowledge);
		model.addAttribute("questionType", questionType);
		model.addAttribute("searchParam", searchParam);
		model.addAttribute("tagList", questionService.getTags(null));
		return "question-list-dialog";
	}
	
	/**
	 * 添加试题页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/teacher/question/question-add", method = RequestMethod.GET)
	public String questionAddPage(Model model) {
		List<Field> fieldList = questionService.getAllField(null);
		model.addAttribute("fieldList", fieldList);
		return "question-add";
	}
	
	/**
	 * 试题导入页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/teacher/question/question-import", method = RequestMethod.GET)
	public String questionImportPage(Model model) {
		
		List<Field> fieldList = questionService.getAllField(null);
		model.addAttribute("fieldList", fieldList);
		return "question-import";
	}
	
	@RequestMapping(value = "/teacher/question/question-preview/{questionId}", method = RequestMethod.GET)
	public String questionPreviewPage(Model model,
			@PathVariable("questionId") int questionId, HttpServletRequest request){
		String strUrl = "http://" + request.getServerName() //服务器地址  
                + ":"   
                + request.getServerPort() + "/";
		Question question = questionService.getQuestionByQuestionId(questionId);
		List<Integer> idList = new ArrayList<Integer>();
		idList.add(questionId);
		List<QuestionQueryResult> questionQueryList = questionService.getQuestionDescribeListByIdList(idList);
		HashMap<Integer, QuestionQueryResult> questionMap = new HashMap<Integer, QuestionQueryResult>();
		for (QuestionQueryResult qqr : questionQueryList) {
			if (questionMap.containsKey(qqr.getQuestionId())) {
				QuestionQueryResult a = questionMap.get(qqr.getQuestionId());
				questionMap.put(qqr.getQuestionId(), a);
			} else {
				questionMap.put(qqr.getQuestionId(), qqr);
			}
		}
		QuestionAdapter adapter = new QuestionAdapter(question,null,questionMap.get(questionId),strUrl);
		String strHtml = adapter.getStringFromXML(true, false, true);
		model.addAttribute("strHtml", strHtml);
		model.addAttribute("question", question);
		return "question-preview";
	}
}

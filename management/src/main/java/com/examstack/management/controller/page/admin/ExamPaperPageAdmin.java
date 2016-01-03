package com.examstack.management.controller.page.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.examstack.common.domain.exam.ExamPaper;
import com.examstack.common.domain.question.Field;
import com.examstack.common.domain.question.QuestionQueryResult;
import com.examstack.common.util.Page;
import com.examstack.common.util.PagingUtil;
import com.examstack.common.util.QuestionAdapter;
import com.examstack.management.service.ExamPaperService;
import com.examstack.management.service.QuestionService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


@Controller
public class ExamPaperPageAdmin {
	
	@Autowired
	private ExamPaperService examPaperService;
	@Autowired
	private QuestionService questionService;
	/**
	 * 试卷管理
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/admin/exampaper/exampaper-list/{paperType}", method = RequestMethod.GET)
	private String examPaperListPage(Model model, HttpServletRequest request, @PathVariable("paperType") String paperType, @RequestParam(value="searchStr",required=false,defaultValue="") String searchStr,  @RequestParam(value="page",required=false,defaultValue="1") int page){
		Page<ExamPaper> pageModel = new Page<ExamPaper>();
		pageModel.setPageNo(page);
		pageModel.setPageSize(8);
		List<ExamPaper> paper = examPaperService.getExamPaperList(searchStr, paperType, pageModel);
		List<Field> fieldList = questionService.getAllField(null);
		
		String pageStr = PagingUtil.getPagelink(page, pageModel.getTotalPage(), "", "admin/exampaper/exampaper-list/" + paperType);
		model.addAttribute("fieldList", fieldList);
		model.addAttribute("paper", paper);
		model.addAttribute("pageStr", pageStr);
		model.addAttribute("searchStr", searchStr);
		return "exampaper-list";
	}
	
	/**
	 * 创建试卷
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/admin/exampaper/exampaper-add", method = RequestMethod.GET)
	private String examPaperAddPage(Model model, HttpServletRequest request){
		
		List<Field> fieldList = questionService.getAllField(null);
		model.addAttribute("fieldList", fieldList);
		return "exampaper-add";
	}
	
	/**
	 * 修改试卷
	 * @param model
	 * @param request
	 * @param exampaperId
	 * @return
	 */
	@RequestMapping(value = "/admin/exampaper/exampaper-edit/{exampaperId}", method = RequestMethod.GET)
	private String examPaperEditPage(Model model, HttpServletRequest request, @PathVariable int exampaperId){
		String strUrl = "http://" + request.getServerName() // 服务器地址
				+ ":" + request.getServerPort() + "/";
		
		ExamPaper examPaper = examPaperService.getExamPaperById(exampaperId);
		StringBuilder sb = new StringBuilder();
		if(examPaper.getContent() != null && !examPaper.getContent().equals("")){
			Gson gson = new Gson();
			List<QuestionQueryResult> questionList = gson.fromJson(examPaper.getContent(), new TypeToken<List<QuestionQueryResult>>(){}.getType());
			for(QuestionQueryResult question : questionList){
				/*AnswerSheetItem as = new AnswerSheetItem();
				as.setAnswer(question.getAnswer());
				as.setQuestion_type_id(question.getQuestionTypeId());
				as.setPoint(question.getQuestionPoint());*/
				QuestionAdapter adapter = new QuestionAdapter(question,strUrl);
				sb.append(adapter.getStringFromXML());
			}
		}
		
		model.addAttribute("htmlStr", sb);
		model.addAttribute("exampaperid", exampaperId);
		model.addAttribute("exampapername", examPaper.getName());
		return "exampaper-edit";
	}
	
	/**
	 * 预览试卷
	 * @param model
	 * @param request
	 * @param exampaperId
	 * @return
	 */
	@RequestMapping(value = "/admin/exampaper/exampaper-preview/{examPaperId}", method = RequestMethod.GET)
	private String examPaperPreviewPage(Model model, HttpServletRequest request, @PathVariable int examPaperId){
		String strUrl = "http://" + request.getServerName() // 服务器地址
				+ ":" + request.getServerPort() + "/";
		
		ExamPaper examPaper = examPaperService.getExamPaperById(examPaperId);
		StringBuilder sb = new StringBuilder();
		if(examPaper.getContent() != null && !examPaper.getContent().equals("")){
			Gson gson = new Gson();
			List<QuestionQueryResult> questionList = gson.fromJson(examPaper.getContent(), new TypeToken<List<QuestionQueryResult>>(){}.getType());
			
			for(QuestionQueryResult question : questionList){
				QuestionAdapter adapter = new QuestionAdapter(question,strUrl);
				sb.append(adapter.getStringFromXML());
			}
		}
		
		model.addAttribute("htmlStr", sb);
		model.addAttribute("exampaperid", examPaperId);
		model.addAttribute("exampapername", examPaper.getName());
		return "exampaper-preview";
	}
}

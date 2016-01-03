package com.examstack.management.controller.action.teacher;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examstack.common.domain.exam.AnswerSheet;
import com.examstack.common.domain.exam.AnswerSheetItem;
import com.examstack.common.domain.exam.ExamPaper;
import com.examstack.common.domain.exam.Message;
import com.examstack.common.domain.exam.PaperCreatorParam;
import com.examstack.common.domain.question.QuestionQueryResult;
import com.examstack.common.domain.question.QuestionStruts;
import com.examstack.common.util.QuestionAdapter;
import com.examstack.common.util.StandardPasswordEncoderForSha1;
import com.examstack.management.security.UserInfo;
import com.examstack.management.service.ExamPaperService;
import com.examstack.management.service.QuestionService;
import com.google.gson.Gson;

@Controller
public class ExamPaperActionTeacher {

	@Autowired
	private ExamPaperService examPaperService;
	@Autowired
	private QuestionService questionService;

	/**
	 * 自动或者手动组卷(插入一张空试卷)
	 * 
	 * @param examPaperParam
	 * @return
	 */
	@RequestMapping(value = "teacher/exampaper-add", method = RequestMethod.POST)
	public @ResponseBody
	Message createExamPaper(@RequestBody PaperCreatorParam param) {

		UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		Message message = new Message();
		ExamPaper examPaper = new ExamPaper();
		examPaper.setName(param.getPaperName());
		examPaper.setDuration(param.getTime());
		examPaper.setPass_point(param.getPassPoint());
		examPaper.setField_id(param.getPaperType());
		examPaper.setCreator(userInfo.getUsername());
		examPaper.setTotal_point(param.getPaperPoint());
		examPaper.setIs_subjective(true);
		
		//手工组卷
		if(param.getQuestionKnowledgePointRate().size() == 0){
			try{
				
				examPaperService.insertExamPaper(examPaper);
			}catch(Exception ex){
				message.setResult(ex.getMessage());
			}
			message.setGeneratedId(examPaper.getId());
			return message;
		}
		List<Integer> idList = new ArrayList<Integer>();

		HashMap<Integer, Float> knowledgeMap = param
				.getQuestionKnowledgePointRate();
		Iterator<Integer> it = knowledgeMap.keySet().iterator();
		while(it.hasNext()){
			idList.add(it.next());
		}

		HashMap<Integer, HashMap<Integer, List<QuestionStruts>>> questionMap = questionService
				.getQuestionStrutsMap(idList);
		
		try{
			examPaperService.createExamPaper(questionMap, param.getQuestionTypeNum(),
					param.getQuestionTypePoint(),
					param.getQuestionKnowledgePointRate(), examPaper);
			message.setGeneratedId(examPaper.getId());
		}catch(Exception e){
			e.printStackTrace();
			message.setResult(e.getMessage());
		}
		
		
		return message;
	}
	
	/**
	 * 批量添加试题
	 * @param model
	 * @param idList
	 * @return
	 */
	@RequestMapping(value = "/teacher/exampaper/get-question-detail4add", method = RequestMethod.POST)
	public @ResponseBody List<QuestionQueryResult> getQuestion5add(Model model, HttpServletRequest request, @RequestBody List<Integer> idList) {
		String strUrl = "http://" + request.getServerName() // 服务器地址
				+ ":" + request.getServerPort() + "/";
		
		Set<Integer> set = new TreeSet<Integer>();
		for(int id : idList){
			set.add(id);
		}
		idList.clear();
		Iterator<Integer> it = set.iterator();
		while(it.hasNext()){
			idList.add(it.next());
		}
		List<QuestionQueryResult> returnList = questionService.getQuestionDescribeListByIdList(idList);
		
		for(QuestionQueryResult question : returnList){
			QuestionAdapter adapter = new QuestionAdapter(question, strUrl);
			question.setContent(adapter.getStringFromXML());
		}
		return returnList;
	}
	
	@RequestMapping(value = "/teacher/exampaper/update-exampaper/{examPaperId}", method = RequestMethod.POST)
	public @ResponseBody
	Message exampaperOnUpdate(Model model,
			@PathVariable("examPaperId") int examPaperId,
			@RequestBody HashMap<Integer, Float> questionPointMap) {
		
		
		Message message = new Message();
		try{
			ExamPaper examPaper = new ExamPaper();
			List<Integer> idList = new ArrayList<Integer>();
			Iterator<Integer> it = questionPointMap.keySet().iterator();
			float sum = 0;
			while(it.hasNext()){
				int key = it.next();
				idList.add(key);
			}
			List<QuestionQueryResult> questionList = questionService.getQuestionDescribeListByIdList(idList);
			AnswerSheet as = new AnswerSheet();
			as.setExamPaperId(examPaperId);
			List<AnswerSheetItem> asList = new ArrayList<AnswerSheetItem>();
			for(QuestionQueryResult q : questionList){
				AnswerSheetItem item = new AnswerSheetItem();
				item.setAnswer(q.getAnswer());
				item.setQuestionId(q.getQuestionId());
				item.setPoint(questionPointMap.get(q.getQuestionId()));
				item.setQuestionTypeId(q.getQuestionTypeId());
				q.setQuestionPoint(questionPointMap.get(q.getQuestionId()));
				sum += questionPointMap.get(q.getQuestionId());
				asList.add(item);
			}
			as.setPointMax(sum);
			as.setAnswerSheetItems(asList);
			Gson gson = new Gson();
			String content = gson.toJson(questionList);
			String answerSheet = gson.toJson(as);
			examPaper.setAnswer_sheet(answerSheet);
			examPaper.setContent(content);
			examPaper.setTotal_point(sum);
			examPaper.setId(examPaperId);
			
			//这两个属性区别试卷是否可用
			//examPaper.setIs_subjective(true);
			//examPaper.setStatus(1);
			examPaperService.updateExamPaper(examPaper);
		}catch(Exception e){
			message.setResult(e.getLocalizedMessage());
		}
		return message;
	}
	
	@RequestMapping(value = "teacher/exampaper/paper-delete", method = RequestMethod.POST)
	public @ResponseBody Message deleteExamPaper(@RequestBody Integer examPaperId){
		Message message = new Message();
		try{
			ExamPaper examPaper = examPaperService.getExamPaperById(examPaperId);
			if(examPaper.getStatus() == 1){
				message.setResult("已发布的试卷不允许删除");
				return message;
			}
			examPaperService.deleteExamPaper(examPaperId);
		}catch(Exception e){
			message.setResult(e.getClass().getName());
		}
		return message;
	}
	
	/**
	 * 生成试卷doc
	 * @param examPaperId
	 * @return
	 */
	@RequestMapping(value = "teacher/exampaper/create-doc-{examPaperId}", method = RequestMethod.GET)
	public @ResponseBody Message createDocPaper(@PathVariable("examPaperId") int examPaperId){
		Message msg = new Message();
		UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		String dateStr = new SimpleDateFormat("yyyyMMddhh24mmss").format(new Date());
		String filePath = System.getProperty("catalina.base")
				+ ",webapps,files,tmp," + userInfo.getUsername() + "," + dateStr;
		String relativePath = "files,tmp," + userInfo.getUsername() + "," + dateStr;
		ExamPaper examPaper = examPaperService.getExamPaperById(examPaperId);
		try {
			examPaperService.generateDoc(examPaper, filePath.replace(",", File.separator));
			msg.setMessageInfo((relativePath + "," + examPaper.getName() + ".docx").replace(",", File.separator));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msg;
	}
}

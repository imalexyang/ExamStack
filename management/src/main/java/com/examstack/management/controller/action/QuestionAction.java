package com.examstack.management.controller.action;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examstack.common.domain.exam.Message;
import com.examstack.common.domain.question.KnowledgePoint;
import com.examstack.common.domain.question.Question;
import com.examstack.common.domain.question.QuestionTag;
import com.examstack.common.util.file.FileUploadUtil;
import com.examstack.management.security.UserInfo;
import com.examstack.management.service.QuestionService;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

@Controller
public class QuestionAction {

	@Autowired
	private QuestionService questionService;

	/**
	 * 添加试题
	 * 
	 * @param question
	 * @return
	 */
	@RequestMapping(value = "/secure/question/question-add", method = RequestMethod.POST)
	public @ResponseBody Message addQuestion(@RequestBody Question question) {

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Message message = new Message();
		Gson gson = new Gson();
		question.setContent(gson.toJson(question.getQuestionContent()));
		question.setCreate_time(new Date());
		question.setCreator(userDetails.getUsername());
		try {
			questionService.addQuestion(question);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			message.setResult("error");
			message.setMessageInfo(e.getClass().getName());
			e.printStackTrace();
		}

		return message;
	}

	/**
	 * 获取试题的标签列表
	 * 
	 * @param questionId
	 * @return
	 */
	@RequestMapping(value = "/secure/question/question-tag/{questionId}", method = RequestMethod.GET)
	public @ResponseBody Message getQuestionTag(@PathVariable("questionId") int questionId) {
		Message message = new Message();
		UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<QuestionTag> tagList = questionService.getQuestionTagByQuestionIdAndUserId(questionId,
				userInfo.getUserid(), null);
		message.setObject(tagList);
		return message;
	}

	/**
	 * 为试题添加标签
	 * 
	 * @param questionId
	 * @param questionTagList
	 * @return
	 */
	@RequestMapping(value = "/secure/question/add-question-tag", method = RequestMethod.POST)
	public @ResponseBody Message addQuestionTag(@RequestBody int questionId,
			@RequestBody List<QuestionTag> questionTagList) {
		Message message = new Message();
		UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try {
			questionService.addQuestionTag(questionId, userInfo.getUserid(), questionTagList);
		} catch (Exception e) {
			e.printStackTrace();
			message.setResult(e.getClass().getName());
		}

		return message;
	}

	/**
	 * 获取试题详细信息
	 * @param questionId
	 * @return
	 */
	@RequestMapping(value = "/secure/question/question-detail/{questionId}", method = RequestMethod.GET)
	public @ResponseBody Message getQuestionDetail(@PathVariable("questionId") int questionId) {
		Message message = new Message();
		//UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try {
			Question question = questionService.getQuestionDetail(questionId, 0);
			message.setObject(question);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			message.setResult(e.getCause().getMessage());
		}
		return message;
	}
	/**
	 * 修改试题知识点
	 * 
	 * @param questionId
	 * @param pointId
	 * @param questionTagList
	 * @return
	 */
	@RequestMapping(value = "/secure/question/question-update/{questionId}/{pointId}", method = RequestMethod.POST)
	public @ResponseBody Message updateQuestionKnowledge(@PathVariable int questionId, @PathVariable int pointId,
			@RequestBody List<QuestionTag> questionTagList) {

		Message message = new Message();
		UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Question question = new Question();
		question.setId(questionId);
		List<Integer> pointIdList = new ArrayList<Integer>();
		pointIdList.add(pointId);
		question.setPointList(pointIdList);
		try {
			questionService.updateQuestionPoint(question, userInfo.getUserid(), questionTagList);
		} catch (Exception e) {
			message.setResult(e.getClass().getName());
		}

		return message;
	}
	
	@RequestMapping(value = "/secure/question/question-update", method = RequestMethod.POST)
	public @ResponseBody Message updateQuestion(@RequestBody String jsonStr){
		Message msg = new Message();
		Gson gson = new Gson();
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(jsonStr);
		List<QuestionTag> questionTagList = gson.fromJson(element.getAsJsonObject().get("tags"), new TypeToken<ArrayList<QuestionTag>>(){}.getType());
		Question question = gson.fromJson(element.getAsJsonObject().get("question"), Question.class);
		try {
			questionService.updateQuestion(question, questionTagList);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			msg.setResult(e.getCause().getMessage());
		}
		//TO-DO:需要提交到数据库，保证在事务中提交
		return msg;
	}

	@RequestMapping(value = "/secure/question/get-knowledge-point/{fieldId}", method = RequestMethod.GET)
	public @ResponseBody Message getQuestionPointByFieldId(@PathVariable int fieldId) {
		Message message = new Message();
		HashMap<Integer, String> pointMap = new HashMap<Integer, String>();
		List<KnowledgePoint> pointList = questionService.getKnowledgePointByFieldId(fieldId, null);
		for (KnowledgePoint point : pointList) {
			pointMap.put(point.getPointId(), point.getPointName());
		}
		message.setObject(pointMap);
		return message;
	}

	@RequestMapping(value = "/secure/question/delete-question/{questionId}", method = RequestMethod.GET)
	public @ResponseBody Message deleteQuestion(Model model, @PathVariable("questionId") int questionId) {

		// UserDetails userDetails = (UserDetails)
		// SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Message message = new Message();
		try {
			questionService.deleteQuestionByQuestionId(questionId);
		} catch (Exception ex) {
			message.setResult(ex.getClass().getName());
		}

		return message;
	}

	@RequestMapping(value = "/secure/upload-uploadify-img", method = RequestMethod.POST)
	public @ResponseBody String uploadImg(HttpServletRequest request, HttpServletResponse response) {
		UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<String> filePathList = new ArrayList<String>();
		try {
			filePathList = FileUploadUtil.uploadImg(request, response, userInfo.getUsername());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		if (filePathList.size() == 0) {
			return "系统错误";
		}

		return filePathList.get(0);
	}

	@RequestMapping(value = "/secure/upload-uploadify", method = RequestMethod.POST)
	public @ResponseBody String uploadFile(HttpServletRequest request, HttpServletResponse response) {
		UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<String> filePathList = new ArrayList<String>();
		try {
			filePathList = FileUploadUtil.uploadFile(request, response, userInfo.getUsername());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		if (filePathList.size() == 0) {
			return "系统错误";
		}

		return filePathList.get(0);
	}
	
	@RequestMapping(value = "/secure/question-import/{id}", method = RequestMethod.POST)
	public @ResponseBody Message courseImport(@RequestBody String filePath, @PathVariable("id") int id) {
		Message message = new Message();
		UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		if(id == 0){
			message.setResult("error");
			message.setMessageInfo("请选择题库");
			return message;
		}
		try{
			questionService.uploadQuestions(filePath, userInfo.getUsername(),id);
		}catch(RuntimeException e){
			message.setResult(e.getClass().getName() + ":" + e.getMessage());
			message.setMessageInfo(e.getMessage());
		}
		
		return message;
	}
}

package com.examstack.management.controller.action.admin;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examstack.common.Constants;
import com.examstack.common.domain.exam.AnswerSheet;
import com.examstack.common.domain.exam.AnswerSheetItem;
import com.examstack.common.domain.exam.Exam;
import com.examstack.common.domain.exam.ExamHistory;
import com.examstack.common.domain.exam.Message;
import com.examstack.management.security.UserInfo;
import com.examstack.management.service.ExamService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

@Controller
public class ExamActionAdmin {
	@Autowired
	private ExamService examService;
	
	@Autowired
	private org.springframework.amqp.core.AmqpTemplate qmqpTemplate;

	/**
	 * 添加考试
	 * @param exam
	 * @return
	 */
	@RequestMapping(value = "admin/exam/add-exam", method = RequestMethod.POST)
	public @ResponseBody Message addExam(@RequestBody Exam exam) {

		UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Message msg = new Message();
		try {
			exam.setCreator(userInfo.getUserid());
			exam.setCreatorId(userInfo.getUsername());
			exam.setApproved(1);
			examService.addExam(exam);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			msg.setResult(e.getClass().getName());
		}
		return msg;
	}
	
	/**
	 * 将用户添加到考试中
	 * @param userNameStr
	 * @param examId
	 * @return
	 */
	@RequestMapping(value = "admin/exam/add-exam-user/{examId}", method = RequestMethod.POST)
	public @ResponseBody Message addExamUser(@RequestBody String userNameStr,@PathVariable("examId") int examId) {

		UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		userNameStr = userNameStr.replace("\"", "");
		Message msg = new Message();
		try {
			
			examService.addExamUser(examId, userNameStr, userInfo.getRoleMap());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			msg.setResult(e.getClass().getName());
		}
		return msg;
	}
	
	/**
	 * 将用户组中的用户添加到考试中
	 * @param groupIdList
	 * @param examId
	 * @return
	 */
	@RequestMapping(value = "admin/exam/add-exam-group/{examId}", method = RequestMethod.POST)
	public @ResponseBody Message addExamGroup(@RequestBody List<Integer> groupIdList,@PathVariable("examId") int examId) {

		Message msg = new Message();
		try {
			examService.addGroupUser2Exam(groupIdList, examId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			msg.setResult(e.getClass().getName());
		}
		return msg;
	}
	
	/**
	 * 添加模拟考试
	 * @param exam
	 * @return
	 */
	@RequestMapping(value = "admin/exam/add-model-test", method = RequestMethod.POST)
	public @ResponseBody Message addModelTest(@RequestBody Exam exam) {

		UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Message msg = new Message();
		try {
			Calendar c = Calendar.getInstance();
			c.add(Calendar.YEAR, 10);
			exam.setCreator(userInfo.getUserid());
			exam.setApproved(1);
			exam.setEffTime(new Date());
			exam.setExpTime(c.getTime());
			examService.addExam(exam);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			msg.setResult(e.getClass().getName());
		}
		return msg;
	}

	@RequestMapping(value = "addAnswerSheet4Test", method = RequestMethod.GET)
	public @ResponseBody Message addAnswerSheet4Test(Model model) throws JsonProcessingException, IOException {
		Message msg = new Message();
		AnswerSheet as = new AnswerSheet();
		as.setExamPaperId(2);
		ObjectMapper om = new ObjectMapper();
		qmqpTemplate.convertAndSend(Constants.ANSWERSHEET_DATA_QUEUE, om.writeValueAsBytes(as));
		return msg;
	}
	
	/**
	 * 删除考试
	 * @param examId
	 * @return
	 */
	@RequestMapping(value = "admin/exam/delete-exam/{examId}", method = RequestMethod.GET)
	public @ResponseBody Message deleteExam(@PathVariable("examId") int examId){
		
		Message msg = new Message();
		try {
			examService.deleteExamById(examId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			msg.setResult(e.getMessage());
		}
		return msg;
	}
	
	/**
	 * 改变考试的审核状态
	 * @param examId
	 * @param mark
	 * @return
	 */
	@RequestMapping(value = "admin/exam/mark-exam/{examId}/{mark}", method = RequestMethod.GET)
	public @ResponseBody Message markExam(@PathVariable("examId") int examId,@PathVariable("mark") int mark){
		
		Message msg = new Message();
		try {
			examService.changeExamStatus(examId, mark);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			msg.setResult(e.getMessage());
		}
		return msg;
	}
	
	/**
	 * 改变用户考试申请的审核状态
	 * @param histId
	 * @param mark
	 * @return
	 */
	@RequestMapping(value = "admin/exam/mark-hist/{histId}/{mark}", method = RequestMethod.GET)
	public @ResponseBody Message markUserExamHist(@PathVariable("histId") int histId,@PathVariable("mark") int mark){
		
		Message msg = new Message();
		try {
			examService.changeUserExamHistStatus(histId, mark);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			msg.setResult(e.getMessage());
		}
		return msg;
	}
	
	@RequestMapping(value = "admin/exam/delete-hist/{histId}", method = RequestMethod.GET)
	public @ResponseBody Message deleteUserExamHist(@PathVariable("histId") int histId){
		
		Message msg = new Message();
		try {
			examService.deleteUserExamHist(histId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			msg.setResult(e.getMessage());
		}
		return msg;
	}

	/**
	 * 获取答题卡
	 * @param histId
	 * @return
	 */
	@RequestMapping(value = "admin/exam/get-answersheet/{histId}", method = RequestMethod.GET)
	public @ResponseBody AnswerSheet getAnswerSheet(@PathVariable("histId") int histId){
		ExamHistory history = examService.getUserExamHistListByHistId(histId);
		Gson gson = new Gson();
		AnswerSheet answerSheet = gson.fromJson(history.getAnswerSheet(), AnswerSheet.class);
		return answerSheet;
	}
	
	/**
	 * 阅卷
	 * @param answerSheet
	 * @return
	 */
	@RequestMapping(value = "/admin/exam/answersheet", method = RequestMethod.POST)
	public @ResponseBody Message submitAnswerSheet(@RequestBody AnswerSheet answerSheet){
		Gson gson = new Gson();
		float score = 0f;
		for(AnswerSheetItem item : answerSheet.getAnswerSheetItems()){
			score += item.getPoint();
			//TO-DO:模拟考试是否要记录主观题的历史？
		}
		answerSheet.setPointRaw(score);
		examService.updateUserExamHist(answerSheet, gson.toJson(answerSheet),3);
		return new Message();
	}
}















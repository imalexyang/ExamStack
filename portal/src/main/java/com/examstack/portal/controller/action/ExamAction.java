package com.examstack.portal.controller.action;

import java.io.IOException;

import org.codehaus.jackson.JsonProcessingException;
import org.springframework.amqp.AmqpException;
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
import com.examstack.common.domain.exam.Exam;
import com.examstack.common.domain.exam.ExamHistory;
import com.examstack.common.domain.exam.Message;
import com.examstack.portal.security.UserInfo;
import com.examstack.portal.service.ExamService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

@Controller
public class ExamAction {
	
	@Autowired
	private ExamService examService;
	
	@Autowired
	private org.springframework.amqp.core.AmqpTemplate qmqpTemplate;
	/**
	 * 获取答题卡
	 * @param histId
	 * @return
	 */
	@RequestMapping(value = "student/get-answersheet/{histId}", method = RequestMethod.GET)
	public @ResponseBody AnswerSheet getAnswerSheet(@PathVariable("histId") int histId){
		ExamHistory history = examService.getUserExamHistListByHistId(histId);
		Gson gson = new Gson();
		AnswerSheet answerSheet = gson.fromJson(history.getAnswerSheet(), AnswerSheet.class);
		return answerSheet;
	}
	
	/**
	 * 用户申请考试
	 * @param examId
	 * @return
	 */
	@RequestMapping(value = "student/exam/send-apply/{examId}", method = RequestMethod.GET)
	public @ResponseBody Message sendApply(@PathVariable("examId") int examId){
		Message msg = new Message();
		UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try {
			Exam exam = this.checkExam(examId);
			//申请考试默认未审核
			examService.addUserExamHist(userInfo.getUserid(), examId, exam.getExamPaperId(),0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			msg.setResult(e.getMessage());
			e.printStackTrace();
		}
		return msg;
	}
	
	/**
	 * 检查用户是否可以申请该考试
	 * @param examId
	 * @return
	 * @throws Exception
	 */
	public Exam checkExam(int examId) throws Exception{
		Exam exam = examService.getExamById(examId);
		if(exam == null){
			throw new Exception("考试不存在！或已经申请过一次！");
		}
		/*if(exam.getEffTime().before(new Date()))
			throw new Exception("不能在考试开始后申请！");*/
		if(exam.getApproved() != 1){
			throw new Exception("不能申请一个未审核通过的考试！");
		}
		
		return exam;
	}
	
	@RequestMapping(value = "/student/exam-submit", method = RequestMethod.POST)
	public @ResponseBody Message finishExam(@RequestBody AnswerSheet answerSheet) {

		Message message = new Message();
		ObjectMapper om = new ObjectMapper();
		try {
			qmqpTemplate.convertAndSend(Constants.ANSWERSHEET_DATA_QUEUE, om.writeValueAsBytes(answerSheet));
		} catch (AmqpException e) {
			e.printStackTrace();
			message.setResult("交卷失败");
			message.setMessageInfo(e.toString());
		} catch (com.fasterxml.jackson.core.JsonProcessingException e) {
			e.printStackTrace();
			message.setResult("交卷失败");
			message.setMessageInfo(e.toString());
		}
		return message;
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
}

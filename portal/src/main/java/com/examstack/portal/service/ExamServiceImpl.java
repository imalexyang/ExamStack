package com.examstack.portal.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examstack.common.domain.exam.Exam;
import com.examstack.common.domain.exam.ExamHistory;
import com.examstack.common.domain.exam.ExamPaper;
import com.examstack.common.util.Page;
import com.examstack.common.util.StringUtil;
import com.examstack.portal.persistence.ExamMapper;
import com.examstack.portal.persistence.ExamPaperMapper;
import com.examstack.portal.service.ExamService;

@Service("examService")
public class ExamServiceImpl implements ExamService {

	@Autowired
	private ExamMapper examMapper;
	@Autowired
	private ExamPaperMapper examPaperMapper;
	@Override
	public ExamHistory getUserExamHistBySeriNo(String seriNo,int approved) {
		
		return examMapper.getUserExamHistBySeriNo(seriNo,approved);
	}
	@Override
	public Exam getExamById(int examId) {
		
		return examMapper.getExamById(examId);
	}
	@Override
	public ExamHistory getUserExamHistByUserIdAndExamId(int userId, int examId, int ... approved) {
		if(approved != null && approved.length == 0)
			approved = null;
		return examMapper.getUserExamHistByUserIdAndExamId(userId, examId, approved);
	}
	@Override
	public int addUserExamHist(int userId,int examId,int examPaperId,int approved) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		try {
			ExamPaper examPaper = examPaperMapper.getExamPaperById(examPaperId);
			ExamHistory history = new ExamHistory();
			history.setExamId(examId);
			history.setExamPaperId(examPaperId);
			history.setContent(examPaper.getContent());
			history.setDuration(examPaper.getDuration());
			
			history.setApproved(approved);
			Date now = new Date();
			String seriNo = sdf.format(now) + StringUtil.format(userId, 3) + StringUtil.format(examId, 3);
			history.setSeriNo(seriNo);
			
			history.setUserId(userId);
			examMapper.addUserExamHist(history);
			return history.getHistId();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e.getCause());
		}
	}
	@Override
	public ExamHistory getUserExamHistListByHistId(int histId) {
		
		return examMapper.getUserExamHistListByHistId(histId);
	}
	@Override
	public List<Exam> getExamListToApply(int userId, Page<Exam> page) {
		
		return examMapper.getExamListToApply(userId, page);
	}
	@Override
	public List<Exam> getExamListToStart(int userId, Page<Exam> page, int ... typeIdList) {
		
		if(typeIdList != null && typeIdList.length == 0)
			typeIdList = null;
		return examMapper.getExamListToStart(userId, typeIdList, page);
	}
	@Override
	public List<Exam> getExamList(Page<Exam> page, int... typeIdList) {
		
		if(typeIdList != null && typeIdList.length == 0)
			typeIdList = null;
		return examMapper.getExamList(typeIdList,page);
	}
	@Override
	public List<ExamHistory> getUserExamHistByUserId(int userId, Page<ExamHistory> page, int... typeIdList) {
		// TODO Auto-generated method stub
		if(typeIdList != null && typeIdList.length == 0)
			typeIdList = null;
		return examMapper.getUserExamHistByUserId(userId, typeIdList, page);
	}
}

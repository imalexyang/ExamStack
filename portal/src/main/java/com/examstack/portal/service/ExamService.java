package com.examstack.portal.service;

import java.util.List;

import com.examstack.common.domain.exam.Exam;
import com.examstack.common.domain.exam.ExamHistory;
import com.examstack.common.util.Page;

public interface ExamService {
	/**
	 * 根据准考证号获取用户考试历史
	 * @param seriNo
	 * @return
	 */
	public ExamHistory getUserExamHistBySeriNo(String seriNo,int approved);
	
	/**
	 * 根据用户id和考试id查询考试历史
	 * @param userId
	 * @param examId
	 * @return
	 */
	public ExamHistory getUserExamHistByUserIdAndExamId(int userId, int examId, int ... approved);
	
	/**
	 * 
	 * @param userId
	 * @param typeIdList
	 * @return
	 */
	public List<ExamHistory> getUserExamHistByUserId(int userId, Page<ExamHistory> page, int ... typeIdList);
	/**
	 * 根据id获取考试
	 * @param examId
	 * @return
	 */
	public Exam getExamById(int examId);
	
	/**
	 * 考试历史表插入一条记录
	 * @param userId
	 * @param examId
	 * @param examPaperId
	 * @param approved
	 */
	public int addUserExamHist(int userId,int examId,int examPaperId,int approved);
	
	/**
	 * 根据考试历史id获取考试历史
	 * @param histId
	 * @return
	 */
	public ExamHistory getUserExamHistListByHistId(int histId);
	
	/**
	 * 获取可以申请的考试清单，供用户申请
	 * @param userId
	 * @param page
	 * @return
	 */
	public List<Exam> getExamListToApply(int userId, Page<Exam> page);
	
	/**
	 * 获取可以考试的清单，供用户考试
	 * @param userId
	 * @param page
	 * @param typeIdList
	 * @return
	 */
	public List<Exam> getExamListToStart(int userId, Page<Exam> page, int ... typeIdList);
	
	/**
	 * 获取试卷清单
	 * @param page
	 * @param typeIdList 考试类型
	 * @return
	 */
	public List<Exam> getExamList(Page<Exam> page,int ... typeIdList);
}

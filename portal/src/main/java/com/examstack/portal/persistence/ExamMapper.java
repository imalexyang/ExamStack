package com.examstack.portal.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.examstack.common.domain.exam.Exam;
import com.examstack.common.domain.exam.ExamHistory;
import com.examstack.common.util.Page;

public interface ExamMapper {

	/**
	 * 根据准考证号获取用户考试历史
	 * @param seriNo
	 * @return
	 */
	public ExamHistory getUserExamHistBySeriNo(@Param("seriNo") String seriNo,@Param("approved") int approved);
	
	/**
	 * 根据用户id和考试id查询考试历史
	 * @param userId
	 * @param examId
	 * @param approvedType
	 * @return
	 */
	public ExamHistory getUserExamHistByUserIdAndExamId(@Param("userId") int userId,@Param("examId") int examId,@Param("array") int[] approvedType);
	
	/**
	 * 根据用户id查询考试历史
	 * @param userId
	 * @param typeIdList
	 * @param page
	 * @return
	 */
	public List<ExamHistory> getUserExamHistByUserId(@Param("userId") int userId, @Param("array") int[] typeIdList, @Param("page") Page<ExamHistory> page);
	/**
	 * 根据id获取考试
	 * @param examId
	 * @return
	 */
	public Exam getExamById(int examId);
	
	/**
	 * 考试历史表插入一条记录
	 * @param examHistory
	 */
	public void addUserExamHist(ExamHistory examHistory);
	
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
	public List<Exam> getExamListToApply(@Param("userId") int userId,@Param("page") Page<Exam> page);
	
	/**
	 * 获取可以考试的清单，供用户考试
	 * @param userId
	 * @param page
	 * @param typeIdList 考试类型id
	 * @return
	 */
	public List<Exam> getExamListToStart(@Param("userId") int userId,@Param("array") int[] typeIdList, @Param("page") Page<Exam> page);
	
	/**
	 * 获取试卷清单
	 * @param idList 类型id
	 * @param page
	 * @return
	 */
	public List<Exam> getExamList(@Param("array") int[] idList,@Param("page") Page<Exam> page);
}

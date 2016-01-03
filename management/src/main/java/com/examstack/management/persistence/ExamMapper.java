package com.examstack.management.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.examstack.common.domain.exam.AnswerSheet;
import com.examstack.common.domain.exam.Exam;
import com.examstack.common.domain.exam.ExamHistory;
import com.examstack.common.util.Page;

public interface ExamMapper {

	/**
	 * 添加考试
	 * @param exam
	 */
	public void addExam(Exam exam);
	
	/**
	 * 考试历史表插入一条记录
	 * @param examHistory
	 */
	public void addUserExamHist(ExamHistory examHistory);
	
	/**
	 * 获取试卷清单
	 * @param idList 类型id
	 * @param page
	 * @return
	 */
	public List<Exam> getExamList(@Param("array") int[] idList,@Param("page") Page<Exam> page);
	
	/**
	 * 根据考试id获取考试历史记录
	 * @param examId
	 * @param searchStr
	 * @param order
	 * @param limit
	 * @param page
	 * @return
	 */
	public List<ExamHistory> getUserExamHistListByExamId(@Param("examId") int examId,@Param("searchStr") String searchStr,@Param("order") String order,@Param("limit") int limit,@Param("page") Page<ExamHistory> page);
	
	/**
	 * 删除一门考试
	 * @param examId
	 */
	public void deleteExamById(int examId);
	
	/**
	 * 根据id获取考试
	 * @param examId
	 * @return
	 */
	public Exam getExamById(int examId);
	
	/**
	 * 更新考试的审核状态
	 * @param mark
	 */
	public void changeExamStatus(@Param("examId") int examId, @Param("approved") int approved);
	
	/**
	 * 审核用户考试申请
	 * @param histId
	 * @param mark
	 */
	public void changeUserExamHistStatus(@Param("histId") int histId, @Param("approved") int approved);
	
	/**
	 * 更新答题卡及得分
	 * @param answerSheet
	 * @param answerSheetStr
	 */
	public void updateUserExamHist(@Param("answerSheet") AnswerSheet answerSheet,@Param("answerSheetStr") String answerSheetStr, @Param("approved") int approved);
	
	/**
	 * 根据考试历史id获取考试历史
	 * @param histId
	 * @return
	 */
	public ExamHistory getUserExamHistListByHistId(int histId);
	
	/**
	 * 删除一条考试申请（历史记录），只能删除未审核的记录
	 * @param histId
	 */
	public void deleteUserExamHist(int histId);
	
	/**
	 * 删除一条考试申请（历史记录），只能删除未审核的记录
	 * @param examId
	 * @param userId
	 */
	public void deleteUserExamHistByUserId(@Param("examId") int examId,@Param("userId") int userId);
	
	/**
	 * 根据用户id和考试id查询考试历史
	 * @param userId
	 * @param examId
	 * @param approvedType
	 * @return
	 */
	public ExamHistory getUserExamHistByUserIdAndExamId(@Param("userId") int userId,@Param("examId") int examId,@Param("array") int[] approvedType);
	
	/**
	 * 获取考试历史记录列表（所有）
	 * @param approvedType
	 * @param page
	 * @return
	 */
	public List<ExamHistory> getUserExamHistList(@Param("array") int[] approvedType, @Param("page") Page<ExamHistory> page);
		
}

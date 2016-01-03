package com.examstack.management.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.examstack.common.domain.exam.UserQuestionHistory;
import com.examstack.common.domain.question.QuestionStatistic;

public interface QuestionHistoryMapper {

	/**
	 * 插入试题历史
	 * @param historyList
	 */
	public void addUserQuestionHist(@Param("array") List<UserQuestionHistory> historyList);
	
	/**
	 * 获取用户的试题练习历史
	 * @param userId
	 * @param fieldId
	 * @return
	 */
	public List<UserQuestionHistory> getUserQuestionHist(@Param("userId") int userId,@Param("fieldId") int fieldId);
	
	/**
	 * 根据fieldId,pointId分组统计练习历史试题数量
	 * @param fieldId
	 * @param userId
	 * @return
	 */
	public List<QuestionStatistic> getQuestionHistStaticByFieldId(@Param("fieldId") int fieldId,@Param("userId") int userId);
	
	/**
	 * 根据fieldId,pointId,typeId分组统计练习历史试题数量
	 * @param fieldId
	 * @param userId
	 * @return
	 */
	public List<QuestionStatistic> getTypeQuestionHistStaticByFieldId(@Param("fieldId") int fieldId,@Param("userId") int userId);
}

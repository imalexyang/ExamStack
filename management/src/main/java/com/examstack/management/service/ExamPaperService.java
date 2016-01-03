package com.examstack.management.service;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.examstack.common.domain.exam.ExamPaper;
import com.examstack.common.domain.question.QuestionStruts;
import com.examstack.common.util.Page;

public interface ExamPaperService {
	/**
	 * 获取试卷列表
	 * @param page
	 * @param searchStr 查询关键字
	 * @param paperType 试卷类型（专业）
	 * @return
	 */
	public List<ExamPaper> getExamPaperList(String searchStr, String paperType, Page<ExamPaper> page);
	/**
	 * 插入一张试卷
	 * @param examPaper
	 */
	public void insertExamPaper(ExamPaper examPaper);
	
	/**
	 * 创建试卷
	 * @param questionMap
	 * @param questionTypeNum
	 * @param questionTypePoint
	 * @param knowledgePointRate
	 * @param examPaper
	 */
	public void createExamPaper(
			HashMap<Integer, HashMap<Integer, List<QuestionStruts>>> questionMap,
			HashMap<Integer, Integer> questionTypeNum,
			HashMap<Integer, Float> questionTypePoint,
			HashMap<Integer, Float> knowledgePointRate, ExamPaper examPaper);
	
	/**
	 * 获取一张试卷
	 * @param examPaperId
	 * @return
	 */
	public ExamPaper getExamPaperById(int examPaperId);
	
	/**
	 * 更新一张试卷
	 * @param examPaper
	 */
	public void updateExamPaper(ExamPaper examPaper);
	
	/**
	 * 删除一张试卷
	 * @param id
	 */
	public void deleteExamPaper(int id);
	
	/**
	 * 获取当前用户可用的试卷列表
	 * @param userName
	 * @param page
	 * @return is_visiable=1的和当前用户创建的试卷列表
	 */
	public List<ExamPaper> getEnabledExamPaperList(String userName, Page<ExamPaper> page);
	
	/**
	 * 生成导出试卷
	 * @param examPaper
	 * @param path
	 * @throws Exception
	 */
	public void generateDoc(ExamPaper examPaper,String path) throws Exception;
}

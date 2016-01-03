package com.examstack.management.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.examstack.common.domain.exam.AnswerSheet;
import com.examstack.common.domain.exam.Exam;
import com.examstack.common.domain.exam.ExamHistory;
import com.examstack.common.domain.exam.ExamPaper;
import com.examstack.common.domain.user.Role;
import com.examstack.common.domain.user.User;
import com.examstack.common.util.Page;
import com.examstack.common.util.StringUtil;
import com.examstack.management.persistence.ExamMapper;
import com.examstack.management.persistence.ExamPaperMapper;
import com.examstack.management.persistence.UserMapper;

@Service("examService")
public class ExamServiceImpl implements ExamService {

	@Autowired
	private ExamMapper examMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private ExamPaperMapper examPaperMapper;
	@Transactional
	@Override
	public void addExam(Exam exam) {
		// TODO Auto-generated method stub
		try {
			examMapper.addExam(exam);
			if(exam.getGroupIdList() != null && exam.getGroupIdList().size() > 0){
				List<User> userList = userMapper.getUserListByGroupIdList(exam.getGroupIdList(), null);
				ExamPaper examPaper = examPaperMapper.getExamPaperById(exam.getExamPaperId());
				SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
				Date now = new Date();
				for(User user : userList){
					ExamHistory history = new ExamHistory();
					history.setExamId(exam.getExamId());
					history.setExamPaperId(exam.getExamPaperId());
					history.setContent(examPaper.getContent());
					history.setDuration(examPaper.getDuration());
					//默认创建的记录都是审核通过的
					history.setApproved(1);
					//TO-DO:用户名,密码,开始时间,结束时间 进行md5
					String seriNo = sdf.format(now) + StringUtil.format(user.getUserId(), 3) + StringUtil.format(exam.getExamId(), 3);
					history.setSeriNo(seriNo);
					history.setVerifyTime(new Date());
					history.setUserId(user.getUserId());
					examMapper.addUserExamHist(history);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}
	@Transactional
	@Override
	public void addExamUser(int examId,String userNameStr,HashMap<String,Role> roleMap){
		
		try {
			String[] userNames = userNameStr.split(";");
			List<User> userList = userMapper.getUserByNames(userNames, roleMap.get("ROLE_STUDENT").getRoleId());
			Exam exam = examMapper.getExamById(examId);
			ExamPaper examPaper = examPaperMapper.getExamPaperById(exam.getExamPaperId());
			SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
			Date now = new Date();
			for(User user : userList){
				ExamHistory history = this.getUserExamHistByUserIdAndExamId(user.getUserId(), examId, 0,1,2,3);
				if(history == null){
					history = new ExamHistory();
					history.setExamId(exam.getExamId());
					history.setExamPaperId(exam.getExamPaperId());
					history.setContent(examPaper.getContent());
					history.setDuration(examPaper.getDuration());
					//默认创建的记录都是审核通过的
					history.setApproved(1);
					String seriNo = sdf.format(now) + StringUtil.format(user.getUserId(), 3) + StringUtil.format(exam.getExamId(), 3);
					history.setSeriNo(seriNo);
					history.setVerifyTime(new Date());
					history.setUserId(user.getUserId());
					examMapper.addUserExamHist(history);
				}else if(history.getApproved() == 0){
					//审核状态是0的才允许重新添加
					examMapper.deleteUserExamHistByUserId(exam.getExamId(),user.getUserId());
					//批量添加的都是审核通过的记录
					history.setApproved(1);
					examMapper.addUserExamHist(history);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	@Override
	public List<Exam> getExamList(Page<Exam> page,int ... typeIdList) {
		// TODO Auto-generated method stub
		
		if(typeIdList.length == 0)
			typeIdList = null;
		return examMapper.getExamList(typeIdList,page);
	}
	@Override
	public List<ExamHistory> getUserExamHistListByExamId(int examId, String searchStr, String order, int limit, Page<ExamHistory> page) {
		// TODO Auto-generated method stub
		return examMapper.getUserExamHistListByExamId(examId, searchStr, order, limit, page);
	}
	@Override
	public void deleteExamById(int examId) throws Exception {
		// TODO Auto-generated method stub
		Exam exam = examMapper.getExamById(examId);
		if(exam.getApproved() == 0 || exam.getApproved() == 2)
			examMapper.deleteExamById(examId);
		else
			throw new Exception("考试已经审核通过！不允许删除！");
	}
	@Override
	public void changeExamStatus(int examId, int approved) {
		// TODO Auto-generated method stub
		examMapper.changeExamStatus(examId, approved);
	}
	@Override
	public void changeUserExamHistStatus(int histId, int approved) {
		// TODO Auto-generated method stub
		examMapper.changeUserExamHistStatus(histId, approved);
	}
	@Override
	public void updateUserExamHist(AnswerSheet answerSheet, String answerSheetStr, int approved) {
		// TODO Auto-generated method stub
		examMapper.updateUserExamHist(answerSheet, answerSheetStr,  approved);
	}
	@Override
	public ExamHistory getUserExamHistListByHistId(int histId) {
		// TODO Auto-generated method stub
		return examMapper.getUserExamHistListByHistId(histId);
	}
	@Override
	public void deleteUserExamHist(int histId) {
		// TODO Auto-generated method stub
		examMapper.deleteUserExamHist(histId);
	}
	@Override
	public Exam getExamById(int examId) {

		return examMapper.getExamById(examId);
	}
	@Override
	public ExamHistory getUserExamHistByUserIdAndExamId(int userId, int examId, int... approved) {
		// TODO Auto-generated method stub
		if(approved.length == 0)
			approved = null;
		return examMapper.getUserExamHistByUserIdAndExamId(userId, examId, approved);
	}
	
	@Transactional
	@Override
	public void addGroupUser2Exam(List<Integer> groupIdList, int examId) {
		// TODO Auto-generated method stub
		
		try {
			Exam exam = examMapper.getExamById(examId);
			ExamPaper examPaper = examPaperMapper.getExamPaperById(exam.getExamPaperId());
			List<User> userList = userMapper.getUserListByGroupIdList(groupIdList, null);
			SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
			Date now = new Date();
			for(User user : userList){
				ExamHistory history = this.getUserExamHistByUserIdAndExamId(user.getUserId(), examId, 0,1,2,3);
				if(history == null){
					history = new ExamHistory();
					history.setExamId(exam.getExamId());
					history.setExamPaperId(exam.getExamPaperId());
					history.setContent(examPaper.getContent());
					history.setDuration(examPaper.getDuration());
					//默认创建的记录都是审核通过的
					history.setApproved(1);
					String seriNo = sdf.format(now) + StringUtil.format(user.getUserId(), 3) + StringUtil.format(exam.getExamId(), 3);
					history.setSeriNo(seriNo);
					history.setVerifyTime(new Date());
					history.setUserId(user.getUserId());
					examMapper.addUserExamHist(history);
				}else if(history.getApproved() == 0){
					//审核状态是0的才允许重新添加
					examMapper.deleteUserExamHistByUserId(exam.getExamId(),user.getUserId());
					//批量添加的都是审核通过的记录
					history.setApproved(1);
					examMapper.addUserExamHist(history);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	@Override
	public List<ExamHistory> getUserExamHistList(Page<ExamHistory> page, int... approved) {
		// TODO Auto-generated method stub
		if(approved.length == 0)
			approved = null;
		return examMapper.getUserExamHistList(approved, page);
	}

}

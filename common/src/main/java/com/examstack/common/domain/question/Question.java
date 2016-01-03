package com.examstack.common.domain.question;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Question implements Serializable {

	private static final long serialVersionUID = 6335675770371435246L;
	private int id;
	private String name;
	private String content;
	private int duration;
	private float points;
	private boolean is_visible = true;
	private Date create_time;
	private String last_modify;
	private String answer;
	private int group_id;
	private int question_type_id;
	private int expose_times;
	private int right_times;
	private int wrong_times;
	private float difficulty;
	private String analysis;
	private QuestionContent questionContent;
	private String pointName;
	private String fieldName;
	private String questionTypeName;
	private List<Integer> pointList;
	private String referenceName;
	private String creator;
	private String examingPoint;
	private String keyword;
	private String tags;
	private List<QuestionTag> tagList;
	private List<KnowledgePoint> knowledgePoint;
	
	public List<QuestionTag> getTagList() {
		return tagList;
	}
	public void setTagList(List<QuestionTag> tagList) {
		this.tagList = tagList;
	}
	public List<KnowledgePoint> getKnowledgePoint() {
		return knowledgePoint;
	}
	public void setKnowledgePoint(List<KnowledgePoint> knowledgePoint) {
		this.knowledgePoint = knowledgePoint;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getExamingPoint() {
		return examingPoint;
	}
	public void setExamingPoint(String examingPoint) {
		this.examingPoint = examingPoint;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public float getPoints() {
		return points;
	}
	public void setPoints(float points) {
		this.points = points;
	}
	public boolean isIs_visible() {
		return is_visible;
	}
	public void setIs_visible(boolean is_visible) {
		this.is_visible = is_visible;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public String getLast_modify() {
		return last_modify;
	}
	public void setLast_modify(String last_modify) {
		this.last_modify = last_modify;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public int getGroup_id() {
		return group_id;
	}
	public void setGroup_id(int group_id) {
		this.group_id = group_id;
	}
	public int getQuestion_type_id() {
		return question_type_id;
	}
	public void setQuestion_type_id(int question_type_id) {
		this.question_type_id = question_type_id;
	}
	public int getExpose_times() {
		return expose_times;
	}
	public void setExpose_times(int expose_times) {
		this.expose_times = expose_times;
	}
	public int getRight_times() {
		return right_times;
	}
	public void setRight_times(int right_times) {
		this.right_times = right_times;
	}
	public int getWrong_times() {
		return wrong_times;
	}
	public void setWrong_times(int wrong_times) {
		this.wrong_times = wrong_times;
	}
	public float getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(float difficulty) {
		this.difficulty = difficulty;
	}
	public String getAnalysis() {
		return analysis;
	}
	public void setAnalysis(String analysis) {
		this.analysis = analysis;
	}
	public QuestionContent getQuestionContent() {
		return questionContent;
	}
	public void setQuestionContent(QuestionContent questionContent) {
		this.questionContent = questionContent;
	}
	public String getPointName() {
		return pointName;
	}
	public void setPointName(String pointName) {
		this.pointName = pointName;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getQuestionTypeName() {
		return questionTypeName;
	}
	public void setQuestionTypeName(String questionTypeName) {
		this.questionTypeName = questionTypeName;
	}
	public List<Integer> getPointList() {
		return pointList;
	}
	public void setPointList(List<Integer> pointList) {
		this.pointList = pointList;
	}
	public String getReferenceName() {
		return referenceName;
	}
	public void setReferenceName(String referenceName) {
		this.referenceName = referenceName;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	
}

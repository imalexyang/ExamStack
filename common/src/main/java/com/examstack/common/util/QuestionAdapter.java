package com.examstack.common.util;

import java.util.Iterator;

import com.examstack.common.domain.exam.AnswerSheetItem;
import com.examstack.common.domain.question.Question;
import com.examstack.common.domain.question.QuestionContent;
import com.examstack.common.domain.question.QuestionQueryResult;
import com.examstack.common.util.xml.Object2Xml;
import com.google.gson.Gson;



public class QuestionAdapter {

	private Question question;
	private QuestionContent questionContent;
	private AnswerSheetItem answerSheetItem;
	private QuestionQueryResult questionQueryResult;
	private String baseUrl;

	public String pointStrFormat(float point){
		
		if(point > (int)point){
			return point + "";
		}
		return (int)point + "";
	}
	/**
	 * 
	 * @param question
	 *            试题
	 * @param answerSheetItem
	 *            答题卡
	 * @param questionQueryResult
	 *            试题描述
	 */
	public QuestionAdapter(Question question, AnswerSheetItem answerSheetItem,
			QuestionQueryResult questionQueryResult, String baseUrl) {
		
		this.question = question;
		this.answerSheetItem = answerSheetItem;
		this.questionQueryResult = questionQueryResult;
		Gson gson = new Gson();
		this.questionContent = gson.fromJson(question.getContent(), QuestionContent.class);
		
		this.baseUrl = baseUrl;
	}
	
	public QuestionAdapter(AnswerSheetItem answerSheetItem,
			QuestionQueryResult questionQueryResult, String baseUrl) {
		this.answerSheetItem = answerSheetItem;
		this.questionQueryResult = questionQueryResult;
		Gson gson = new Gson();
		this.questionContent = gson.fromJson(question.getContent(), QuestionContent.class);
		this.baseUrl = baseUrl;
	}

	public QuestionAdapter(QuestionQueryResult questionQueryResult,
			String baseUrl) {
		this.questionQueryResult = questionQueryResult;
		Gson gson = new Gson();
		this.questionContent = gson.fromJson(questionQueryResult.getContent(), QuestionContent.class);
		this.baseUrl = baseUrl;
	}

	/**
	 * 组卷专用
	 * 
	 * @return
	 */
	public String getStringFromXML() {
		StringBuilder sb = new StringBuilder();

		switch (questionQueryResult.getQuestionTypeId()) {
		case 1:
			sb.append("<li class=\"question qt-singlechoice\">");
			break;
		case 2:
			sb.append("<li class=\"question qt-multiplechoice\">");
			break;
		case 3:
			sb.append("<li class=\"question qt-trueorfalse\">");
			break;
		case 4:
			sb.append("<li class=\"question qt-fillblank\">");
			break;
		case 5:
			sb.append("<li class=\"question qt-shortanswer\">");
			break;
		case 6:
			sb.append("<li class=\"question qt-essay\">");
			break;
		case 7:
			sb.append("<li class=\"question qt-analytical\">");
			break;
		default:
			break;
		}

		sb.append("<div class=\"question-title\">");
		sb.append("<div class=\"question-title-icon\"></div>");
		sb.append("<span class=\"question-no\">").append("</span>");
		sb.append("<span class=\"question-type\" style=\"display: none;\">");

		switch (questionQueryResult.getQuestionTypeId()) {
		case 1:
			sb.append("singlechoice").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[单选题]</span>");
			sb.append("<span class=\"question-point-content\">");
			sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId())
					.append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			sb.append("<p class=\"question-body-text\">").append(questionContent.getTitle());
			if (questionContent.getTitleImg() != null)
				if (!questionContent.getTitleImg().trim().equals(""))
					sb.append(
							"<img class=\"question-content-img question-img\" src=\"")
							.append(baseUrl)
							.append(questionContent.getTitleImg())
							.append("\" />");
			sb.append("</p>");
			Iterator<String> it1 = questionContent.getChoiceList().keySet()
					.iterator();
			sb.append("<ul class=\"question-opt-list\">");
			while (it1.hasNext()) {
				sb.append("<li class=\"question-list-item\">");
				String key = it1.next();
				String value = questionContent.getChoiceList().get(key);
				sb.append("<input type=\"radio\" value=\"")
						.append(key)
						.append("\" name=\"question-radio1\" class=\"question-input\">");
				sb.append("<span class=\"question-li-text\">");
				sb.append(key).append(": ").append(value);
				if (questionContent.getChoiceImgList() != null)
					if (questionContent.getChoiceImgList().containsKey(key))
						sb.append(
								"<img class=\"question-opt-img question-img\" src=\"")
								.append(baseUrl)
								.append(questionContent.getChoiceImgList().get(
										key)).append("\" />");
				sb.append("</span>");
				sb.append("</li>");
			}
			sb.append("</ul>");
			sb.append("</form>");
			break;
		case 2:
			sb.append("multiplechoice").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[多选题]</span>");
			sb.append("<span class=\"question-point-content\">");
			sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("</span>");
			//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId())
					.append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			sb.append("<p class=\"question-body-text\">").append(questionContent.getTitle());
			if (questionContent.getTitleImg() != null)
				if (!questionContent.getTitleImg().trim().equals(""))
					sb.append(
							"<img class=\"question-content-img question-img\" src=\"")
							.append(baseUrl)
							.append(questionContent.getTitleImg())
							.append("\" />");
			sb.append("</p>");
			Iterator<String> it2 = questionContent.getChoiceList().keySet()
					.iterator();
			sb.append("<ul class=\"question-opt-list\">");
			while (it2.hasNext()) {
				sb.append("<li class=\"question-list-item\">");
				String key = it2.next();
				String value = questionContent.getChoiceList().get(key);
				sb.append("<input type=\"checkbox\" value=\"")
						.append(key)
						.append("\" name=\"question-radio1\" class=\"question-input\">");
				sb.append("<span class=\"question-li-text\">");
				sb.append(key).append(": ").append(value);
				if (questionContent.getChoiceImgList() != null)
					if (questionContent.getChoiceImgList().containsKey(key))
						sb.append(
								"<img class=\"question-opt-img question-img\" src=\"")
								.append(baseUrl)
								.append(questionContent.getChoiceImgList().get(
										key)).append("\" />");
				sb.append("</span>");
				sb.append("</li>");
			}
			sb.append("</ul>");
			sb.append("</form>");
			break;
		case 3:
			sb.append("trueorfalse").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[判断题]</span>");
			sb.append("<span class=\"question-point-content\">");
			sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("</span>");
			//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId())
					.append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			sb.append("<p class=\"question-body-text\">").append(questionContent.getTitle());
			if (questionContent.getTitleImg() != null)
				if (!questionContent.getTitleImg().trim().equals(""))
					sb.append(
							"<img class=\"question-content-img question-img\" src=\"")
							.append(baseUrl)
							.append(questionContent.getTitleImg())
							.append("\" />");
			sb.append("</p>");
			sb.append("<ul class=\"question-opt-list\">");
			
			sb.append("<li class=\"question-list-item\">").append(
					"<input type=\"radio\" value=\"T\" name=\"question-radio2\" class=\"question-input\">")
					.append("<span class=\"question-li-text\">正确</span>").append("</li>");
			
			sb.append("<li class=\"question-list-item\">").append("<input type=\"radio\" value=\"F\" name=\"question-radio2\" class=\"question-input\">")
					.append("<span class=\"question-li-text\">错误</span>").append("</li>");
			
			sb.append("</ul>");
			sb.append("</form>");
			break;
		case 4:
			sb.append("fillblank").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[填空题]</span>");
			sb.append("<span class=\"question-point-content\">");
			sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("</span>");
			//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId())
					.append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			sb.append("<p class=\"question-body-text\">").append(questionContent.getTitle());
			if (questionContent.getTitleImg() != null)
				if (!questionContent.getTitleImg().trim().equals(""))
					sb.append(
							"<img class=\"question-content-img question-img\" src=\"")
							.append(baseUrl)
							.append(questionContent.getTitleImg())
							.append("\" />");
			sb.append("</p>");
			sb.append("<textarea class=\"question-textarea\"></textarea>");
			sb.append("</form>");
			break;
		case 5:
			sb.append("shortanswer").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[简答题]</span>");
			sb.append("<span class=\"question-point-content\">");
			sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("</span>");
			//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId())
					.append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			sb.append("<p class=\"question-body-text\">").append(questionContent.getTitle());
			if (questionContent.getTitleImg() != null)
				if (!questionContent.getTitleImg().trim().equals(""))
					sb.append(
							"<img class=\"question-content-img question-img\" src=\"")
							.append(baseUrl)
							.append(questionContent.getTitleImg())
							.append("\" />");
			sb.append("</p>");
			sb.append("<textarea class=\"question-textarea\"></textarea>");
			sb.append("</form>");
			break;
		case 6:
			sb.append("essay").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[论述题]</span>");
			sb.append("<span class=\"question-point-content\">");
			sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("</span>");
			//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId())
					.append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			sb.append("<p class=\"question-body-text\">").append(questionContent.getTitle());
			if (questionContent.getTitleImg() != null)
				if (!questionContent.getTitleImg().trim().equals(""))
					sb.append(
							"<img class=\"question-content-img question-img\" src=\"")
							.append(baseUrl)
							.append(questionContent.getTitleImg())
							.append("\" />");
			sb.append("</p>");
			sb.append("<textarea class=\"question-textarea\"></textarea>");
			sb.append("</form>");
			break;
		case 7:
			sb.append("analytical").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[分析题]</span>");
			sb.append("<span class=\"question-point-content\">");
			sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("</span>");
			//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId())
					.append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			sb.append("<p>").append(questionContent.getTitle());
			if (questionContent.getTitleImg() != null)
				if (!questionContent.getTitleImg().trim().equals(""))
					sb.append(
							"<img class=\"question-content-img question-img\" src=\"")
							.append(baseUrl)
							.append(questionContent.getTitleImg())
							.append("\" />");
			sb.append("</p>");
			sb.append("<textarea class=\"question-textarea\"></textarea>");
			sb.append("</form>");
			break;
		default:
			break;
		}
		sb.append("<div class=\"answer-desc\">");
		sb.append("<div class=\"answer-desc-summary\">");
		sb.append("<span>正确答案：</span>");
		if (questionQueryResult.getQuestionTypeId() == 3) {
			if (questionQueryResult.getAnswer().equals("T"))
				sb.append("<span class=\"answer_value\">").append("对").append("</span><br>");
			else if (questionQueryResult.getAnswer().equals("F"))
				sb.append("<span class=\"answer_value\">").append("错").append("</span><br>");
			else
				sb.append("<span class=\"answer_value\">").append(questionQueryResult.getAnswer())
						.append("</span><br>");
		} else
			sb.append("<span class=\"answer_value\">").append(questionQueryResult.getAnswer())
					.append("</span><br>");
		sb.append("</div>");
		sb.append("<div class=\"answer-desc-detail\">");
		sb.append("<label class=\"label label-info\">");
		sb.append("<i class=\"fa fa-paw\"></i><span> 来源</span>");
		sb.append("</label>");
		sb.append("<div class=\"answer-desc-content\">");
		sb.append("<p>");
		sb.append(questionQueryResult.getReferenceName());
		sb.append("</p></div></div>");
		sb.append("<div class=\"answer-desc-detail\">");
		sb.append("<label class=\"label label-warning\">");
		sb.append("<i class=\"fa fa-flag\"></i><span> 解析</span>");
		sb.append("</label>");
		sb.append("<div class=\"answer-desc-content\">");
		sb.append("<p>");
		sb.append(questionQueryResult.getAnalysis());
		sb.append("</p></div></div>");
		sb.append("<div class=\"answer-desc-detail\">");
		sb.append("<label class=\"label label-success\">");
		sb.append("<i class=\"fa fa-bookmark\"></i><span> 考点</span>");
		sb.append("</label>");
		sb.append("<div class=\"answer-desc-content\">");
		sb.append("<p>");
		sb.append(questionQueryResult.getPointName());
		sb.append("</p></div></div>");
		sb.append("</div>");

		sb.append("</li>");
		return sb.toString();
	}

	public String getReportStringFromXML(){
		StringBuilder sb = new StringBuilder();

		switch (questionQueryResult.getQuestionTypeId()) {
		case 1:
			sb.append("<li class=\"question qt-singlechoice\">");
			break;
		case 2:
			sb.append("<li class=\"question qt-multiplechoice\">");
			break;
		case 3:
			sb.append("<li class=\"question qt-trueorfalse\">");
			break;
		case 4:
			sb.append("<li class=\"question qt-fillblank\">");
			break;
		case 5:
			sb.append("<li class=\"question qt-shortanswer\">");
			break;
		case 6:
			sb.append("<li class=\"question qt-essay\">");
			break;
		case 7:
			sb.append("<li class=\"question qt-analytical\">");
			break;
		default:
			break;
		}

		sb.append("<div class=\"question-title\">");
		sb.append("<div class=\"question-title-icon\"></div>");
		sb.append("<span class=\"question-no\">").append("</span>");
		sb.append("<span class=\"question-type\" style=\"display: none;\">");

		switch (questionQueryResult.getQuestionTypeId()) {
		case 1:
			sb.append("singlechoice").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[单选题]</span>");
			sb.append("<span class=\"question-point-content\">");
			sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("</span>");
			//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId()).append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			sb.append("<p class=\"question-body-text\">").append(questionContent.getTitle());
			if (questionContent.getTitleImg() != null)
				if (!questionContent.getTitleImg().trim().equals(""))
					sb.append(
							"<img class=\"question-content-img question-img\" src=\"")
							.append(baseUrl)
							.append(questionContent.getTitleImg())
							.append("\" />");
			sb.append("</p>");
			Iterator<String> it1 = questionContent.getChoiceList().keySet()
					.iterator();
			sb.append("<ul class=\"question-opt-list\">");
			while (it1.hasNext()) {
				sb.append("<li class=\"question-list-item\">");
				String key = it1.next();
				String value = questionContent.getChoiceList().get(key);
				sb.append("<input type=\"radio\" value=\"")
						.append(key)
						.append("\" name=\"question-radio1\" class=\"question-input\">");
				sb.append("<span class=\"question-li-text\">");
				sb.append(key).append(": ").append(value);
				if (questionContent.getChoiceImgList() != null)
					if (questionContent.getChoiceImgList().containsKey(key))
						sb.append(
								"<img class=\"question-opt-img question-img\" src=\"")
								.append(baseUrl)
								.append(questionContent.getChoiceImgList().get(
										key)).append("\" />");
				sb.append("</span>");
				sb.append("</li>");
			}
			sb.append("</ul>");
			sb.append("</form>");
			break;
		case 2:
			sb.append("multiplechoice").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[多选题]</span>");
			sb.append("<span class=\"question-point-content\">");
			sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("</span>");
			//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId()).append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			sb.append("<p class=\"question-body-text\">").append(questionContent.getTitle());
			if (questionContent.getTitleImg() != null)
				if (!questionContent.getTitleImg().trim().equals(""))
					sb.append(
							"<img class=\"question-content-img question-img\" src=\"")
							.append(baseUrl)
							.append(questionContent.getTitleImg())
							.append("\" />");
			sb.append("</p>");
			Iterator<String> it2 = questionContent.getChoiceList().keySet()
					.iterator();
			sb.append("<ul class=\"question-opt-list\">");
			while (it2.hasNext()) {
				sb.append("<li class=\"question-list-item\">");
				String key = it2.next();
				String value = questionContent.getChoiceList().get(key);
				sb.append("<input type=\"checkbox\" value=\"")
						.append(key)
						.append("\" name=\"question-radio1\" class=\"question-input\">");
				sb.append(key).append(": ").append(value);
				if (questionContent.getChoiceImgList() != null)
					if (questionContent.getChoiceImgList().containsKey(key))
						sb.append(
								"<img class=\"question-opt-img question-img\" src=\"")
								.append(baseUrl)
								.append(questionContent.getChoiceImgList().get(
										key)).append("\" />");
				sb.append("</span>");
				sb.append("</li>");
			}
			sb.append("</ul>");
			sb.append("</form>");
			break;
		case 3:
			sb.append("trueorfalse").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[判断题]</span>");
			sb.append("<span class=\"question-point-content\">");
			sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("</span>");
			//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId()).append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			sb.append("<p class=\"question-body-text\">").append(questionContent.getTitle());
			if (questionContent.getTitleImg() != null)
				if (!questionContent.getTitleImg().trim().equals(""))
					sb.append(
							"<img class=\"question-content-img question-img\" src=\"")
							.append(baseUrl)
							.append(questionContent.getTitleImg())
							.append("\" />");
			sb.append("</p>");
			sb.append("<ul class=\"question-opt-list\">");
			
			sb.append("<li class=\"question-list-item\">").append(
					"<input type=\"radio\" value=\"T\" name=\"question-radio2\" class=\"question-input\">")
					.append("<span class=\"question-li-text\">正确</span>").append("</li>");
			
			sb.append("<li class=\"question-list-item\">").append("<input type=\"radio\" value=\"F\" name=\"question-radio2\" class=\"question-input\">")
					.append("<span class=\"question-li-text\">错误</span>").append("</li>");
			
			sb.append("</ul>");
			sb.append("</form>");
			break;
		case 4:
			sb.append("fillblank").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[填空题]</span>");
			sb.append("<span class=\"question-point-content\">");
			sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("</span>");
			//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId()).append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			sb.append("<p class=\"question-body-text\">").append(questionContent.getTitle());
			if (questionContent.getTitleImg() != null)
				if (!questionContent.getTitleImg().trim().equals(""))
					sb.append(
							"<img class=\"question-content-img question-img\" src=\"")
							.append(baseUrl)
							.append(questionContent.getTitleImg())
							.append("\" />");
			sb.append("</p>");
			sb.append("<textarea class=\"question-textarea\"></textarea>");
			sb.append("</form>");
			break;
		case 5:
			sb.append("shortanswer").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[简答题]</span>");
			sb.append("<span class=\"question-point-content\">");
			sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("</span>");
			//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId()).append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			sb.append("<p class=\"question-body-text\">").append(questionContent.getTitle());
			if (questionContent.getTitleImg() != null)
				if (!questionContent.getTitleImg().trim().equals(""))
					sb.append(
							"<img class=\"question-content-img question-img\" src=\"")
							.append(baseUrl)
							.append(questionContent.getTitleImg())
							.append("\" />");
			sb.append("</p>");
			sb.append("<textarea class=\"question-textarea\"></textarea>");
			sb.append("</form>");
			break;
		case 6:
			sb.append("essay").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[论述题]</span>");
			sb.append("<span class=\"question-point-content\">");
			sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("</span>");
			//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId()).append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			sb.append("<p class=\"question-body-text\">").append(questionContent.getTitle());
			if (questionContent.getTitleImg() != null)
				if (!questionContent.getTitleImg().trim().equals(""))
					sb.append(
							"<img class=\"question-content-img question-img\" src=\"")
							.append(baseUrl)
							.append(questionContent.getTitleImg())
							.append("\" />");
			sb.append("</p>");
			sb.append("<textarea class=\"question-textarea\"></textarea>");
			sb.append("</form>");
			break;
		case 7:
			sb.append("analytical").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[分析题]</span>");
			sb.append("<span class=\"question-point-content\">");
			sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("</span>");
			//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId()).append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			sb.append("<p class=\"question-body-text\">").append(questionContent.getTitle());
			if (questionContent.getTitleImg() != null)
				if (!questionContent.getTitleImg().trim().equals(""))
					sb.append(
							"<img class=\"question-content-img question-img\" src=\"")
							.append(baseUrl)
							.append(questionContent.getTitleImg())
							.append("\" />");
			sb.append("</p>");
			sb.append("<textarea class=\"question-textarea\"></textarea>");
			sb.append("</form>");
			break;
		default:
			break;
		}
		sb.append("<div class=\"answer-desc\">");
		sb.append("<div class=\"answer-desc-summary\">");
		sb.append("<span>正确答案：</span>");
		if (questionQueryResult.getQuestionTypeId() == 3) {
			if (questionQueryResult.getAnswer().equals("T"))
				sb.append("<span class=\"answer_value\">").append("对").append("</span><br>");
			else if (questionQueryResult.getAnswer().equals("F"))
				sb.append("<span class=\"answer_value\">").append("错").append("</span><br>");
			else
				sb.append("<span class=\"answer_value\">").append(questionQueryResult.getAnswer())
						.append("</span><br>");
		} else
			sb.append("<span class=\"answer_value\">").append(questionQueryResult.getAnswer())
					.append("</span><br>");

		sb.append("<span>  你的解答：</span>");
		if (answerSheetItem.getQuestionTypeId() == 3) {
			if (answerSheetItem.getAnswer().trim().equals("T"))
				sb.append("<span>").append("对").append("</span>");
			else if (answerSheetItem.getAnswer().trim().equals("F"))
				sb.append("<span>").append("错").append("</span>");
			else
				sb.append("<span>").append(answerSheetItem.getAnswer())
						.append("</span>");
		} else
			sb.append("<span>").append(answerSheetItem.getAnswer())
					.append("</span>");
		sb.append("</div>");
		sb.append("<div class=\"answer-desc-detail\">");
		sb.append("<label class=\"label label-info\">");
		sb.append("<i class=\"fa fa-paw\"></i><span> 来源</span>");
		sb.append("</label>");
		sb.append("<div class=\"answer-desc-content\">");
		sb.append("<p>");
		sb.append(questionQueryResult.getReferenceName());
		sb.append("</p></div></div>");
		sb.append("<div class=\"answer-desc-detail\">");
		sb.append("<label class=\"label label-warning\">");
		sb.append("<i class=\"fa fa-flag\"></i><span> 解析</span>");
		sb.append("</label>");
		sb.append("<div class=\"answer-desc-content\">");
		sb.append("<p>");
		sb.append(questionQueryResult.getAnalysis());
		sb.append("</p></div></div>");
		sb.append("<div class=\"answer-desc-detail\">");
		sb.append("<label class=\"label label-success\">");
		sb.append("<i class=\"fa fa-bookmark\"></i><span> 考点</span>");
		sb.append("</label>");
		sb.append("<div class=\"answer-desc-content\">");
		sb.append("<p>");
		sb.append(questionQueryResult.getPointName());
		sb.append("</p></div></div>");
		sb.append("</div>");

		sb.append("</li>");
		return sb.toString();
	}
	/**
	 * 
	 * @param showAnswer
	 *            是否显示正确的答案。如果有答题卡，会显示用户的答案
	 * @param showPoint
	 *            是否显示分数
	 * @param showAnalysis
	 *            是否显示分析和来源
	 * @return
	 */
	public String getStringFromXML(boolean showAnswer, boolean showPoint,
			boolean showAnalysis) {
		StringBuilder sb = new StringBuilder();

		switch (questionQueryResult.getQuestionTypeId()) {
		case 1:
			sb.append("<li class=\"question qt-singlechoice\">");
			break;
		case 2:
			sb.append("<li class=\"question qt-multiplechoice\">");
			break;
		case 3:
			sb.append("<li class=\"question qt-trueorfalse\">");
			break;
		case 4:
			sb.append("<li class=\"question qt-fillblank\">");
			break;
		case 5:
			sb.append("<li class=\"question qt-shortanswer\">");
			break;
		case 6:
			sb.append("<li class=\"question qt-essay\">");
			break;
		case 7:
			sb.append("<li class=\"question qt-analytical\">");
			break;
		default:
			break;
		}

		sb.append("<div class=\"question-title\">");
		sb.append("<div class=\"question-title-icon\"></div>");
		sb.append("<span class=\"question-no\">").append("</span>");
		sb.append("<span class=\"question-type\" style=\"display: none;\">");

		switch (questionQueryResult.getQuestionTypeId()) {
		case 1:
			sb.append("singlechoice").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[单选题]</span>");
			if (showPoint){
				sb.append("<span class=\"question-point-content\">");
				sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
				sb.append("</span>");
			}
				
				//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId()).append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			sb.append("<p class=\"question-body-text\">").append(questionContent.getTitle());
			if (questionContent.getTitleImg() != null)
				if (!questionContent.getTitleImg().trim().equals(""))
					sb.append(
							"<img class=\"question-content-img question-img\" src=\"")
							.append(baseUrl)
							.append(questionContent.getTitleImg())
							.append("\" />");
			sb.append("</p>");
			Iterator<String> it1 = questionContent.getChoiceList().keySet()
					.iterator();
			sb.append("<ul class=\"question-opt-list\">");
			while (it1.hasNext()) {
				sb.append("<li class=\"question-list-item\">");
				String key = it1.next();
				String value = questionContent.getChoiceList().get(key);
				sb.append("<input type=\"radio\" value=\"")
						.append(key)
						.append("\" name=\"question-radio1\" class=\"question-input\">");
				sb.append(key).append(": ").append(value);
				if (questionContent.getChoiceImgList() != null)
					if (questionContent.getChoiceImgList().containsKey(key))
						sb.append(
								"<img class=\"question-opt-img question-img\" src=\"")
								.append(baseUrl)
								.append(questionContent.getChoiceImgList().get(
										key)).append("\" />");
				sb.append("</span>");
				sb.append("</li>");
			}
			sb.append("</ul>");
			sb.append("</form>");
			break;
		case 2:
			sb.append("multiplechoice").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[多选题]</span>");
			if (showPoint){
				sb.append("<span class=\"question-point-content\">");
				sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
				sb.append("</span>");
			}
				//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId()).append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			sb.append("<p class=\"question-body-text\">").append(questionContent.getTitle());
			if (questionContent.getTitleImg() != null)
				if (!questionContent.getTitleImg().trim().equals(""))
					sb.append(
							"<img class=\"question-content-img question-img\" src=\"")
							.append(baseUrl)
							.append(questionContent.getTitleImg())
							.append("\" />");
			sb.append("</p>");
			Iterator<String> it2 = questionContent.getChoiceList().keySet()
					.iterator();
			sb.append("<ul class=\"question-opt-list\">");
			while (it2.hasNext()) {
				sb.append("<li class=\"question-list-item\">");
				String key = it2.next();
				String value = questionContent.getChoiceList().get(key);
				sb.append("<input type=\"checkbox\" value=\"")
						.append(key)
						.append("\" name=\"question-radio1\" class=\"question-input\">");
				sb.append(key).append(": ").append(value);
				if (questionContent.getChoiceImgList() != null)
					if (questionContent.getChoiceImgList().containsKey(key))
						sb.append(
								"<img class=\"question-opt-img question-img\" src=\"")
								.append(baseUrl)
								.append(questionContent.getChoiceImgList().get(
										key)).append("\" />");
				sb.append("</span>");
				sb.append("</li>");
			}
			sb.append("</ul>");
			sb.append("</form>");
			break;
		case 3:
			sb.append("trueorfalse").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[判断题]</span>");
			if (showPoint){
				sb.append("<span class=\"question-point-content\">");
				sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
				sb.append("</span>");
			}
				//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId()).append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			sb.append("<p class=\"question-body-text\">").append(questionContent.getTitle());
			if (questionContent.getTitleImg() != null)
				if (!questionContent.getTitleImg().trim().equals(""))
					sb.append(
							"<img class=\"question-content-img question-img\" src=\"")
							.append(baseUrl)
							.append(questionContent.getTitleImg())
							.append("\" />");
			sb.append("</p>");
			sb.append("<ul class=\"question-opt-list\">");
			
			sb.append("<li class=\"question-list-item\">").append(
					"<input type=\"radio\" value=\"T\" name=\"question-radio2\" class=\"question-input\">")
					.append("<span class=\"question-li-text\">正确</span>").append("</li>");
			
			sb.append("<li class=\"question-list-item\">").append("<input type=\"radio\" value=\"F\" name=\"question-radio2\" class=\"question-input\">")
					.append("<span class=\"question-li-text\">错误</span>").append("</li>");
			
			sb.append("</ul>");
			sb.append("</form>");
			break;
		case 4:
			sb.append("fillblank").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[填空题]</span>");
			if (showPoint){
				sb.append("<span class=\"question-point-content\">");
				sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
				sb.append("</span>");
			}
				//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId()).append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			sb.append("<p class=\"question-body-text\">").append(questionContent.getTitle());
			if (questionContent.getTitleImg() != null)
				if (!questionContent.getTitleImg().trim().equals(""))
					sb.append(
							"<img class=\"question-content-img question-img\" src=\"")
							.append(baseUrl)
							.append(questionContent.getTitleImg())
							.append("\" />");
			sb.append("</p>");
			sb.append("<textarea class=\"question-textarea\"></textarea>");
			sb.append("</form>");
			break;
		case 5:
			sb.append("shortanswer").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[简答题]</span>");
			if (showPoint){
				sb.append("<span class=\"question-point-content\">");
				sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
				sb.append("</span>");
			}
				//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId()).append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			sb.append("<p class=\"question-body-text\">").append(questionContent.getTitle());
			if (questionContent.getTitleImg() != null)
				if (!questionContent.getTitleImg().trim().equals(""))
					sb.append(
							"<img class=\"question-content-img question-img\" src=\"")
							.append(baseUrl)
							.append(questionContent.getTitleImg())
							.append("\" />");
			sb.append("</p>");
			sb.append("<textarea class=\"question-textarea\"></textarea>");
			sb.append("</form>");
			break;
		case 6:
			sb.append("essay").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[论述题]</span>");
			if (showPoint){
				sb.append("<span class=\"question-point-content\">");
				sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
				sb.append("</span>");
			}
				//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId()).append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			sb.append("<p class=\"question-body-text\">").append(questionContent.getTitle());
			if (questionContent.getTitleImg() != null)
				if (!questionContent.getTitleImg().trim().equals(""))
					sb.append(
							"<img class=\"question-content-img question-img\" src=\"")
							.append(baseUrl)
							.append(questionContent.getTitleImg())
							.append("\" />");
			sb.append("</p>");
			sb.append("<textarea class=\"question-textarea\"></textarea>");
			sb.append("</form>");
			break;
		case 7:
			sb.append("analytical").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[分析题]</span>");
			if (showPoint){
				sb.append("<span class=\"question-point-content\">");
				sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
				sb.append("</span>");
			}
				//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId()).append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			sb.append("<p class=\"question-body-text\">").append(questionContent.getTitle());
			if (questionContent.getTitleImg() != null)
				if (!questionContent.getTitleImg().trim().equals(""))
					sb.append(
							"<img class=\"question-content-img question-img\" src=\"")
							.append(baseUrl)
							.append(questionContent.getTitleImg())
							.append("\" />");
			sb.append("</p>");
			sb.append("<textarea class=\"question-textarea\"></textarea>");
			sb.append("</form>");
			break;
		default:
			break;
		}
		sb.append("<div class=\"answer-desc\">");
		sb.append("<div class=\"answer-desc-summary\">");
		if (showAnswer) {

			sb.append("<span>正确答案：</span>");
			if (questionQueryResult.getQuestionTypeId() == 3) {
				if (questionQueryResult.getAnswer().equals("T"))
					sb.append("<span class=\"answer_value\">").append("对").append("</span><br>");
				else if (questionQueryResult.getAnswer().equals("F"))
					sb.append("<span class=\"answer_value\">").append("错").append("</span><br>");
				else
					sb.append("<span class=\"answer_value\">").append(questionQueryResult.getAnswer())
							.append("</span><br>");
			} else
				sb.append("<span class=\"answer_value\">").append(questionQueryResult.getAnswer())
						.append("</span><br>");
		}

		if (answerSheetItem != null) {

			sb.append("<span>  你的解答：</span>");
			if (answerSheetItem.getQuestionTypeId() == 3) {
				if (answerSheetItem.getAnswer().trim().equals("T"))
					sb.append("<span>").append("对").append("</span>");
				else if (answerSheetItem.getAnswer().trim().equals("F"))
					sb.append("<span>").append("错").append("</span>");
				else
					sb.append("<span>").append(answerSheetItem.getAnswer())
							.append("</span>");
			} else
				sb.append("<span>").append(answerSheetItem.getAnswer())
						.append("</span>");

		}
		sb.append("</div>");
		if (showAnalysis) {
			sb.append("<div class=\"answer-desc-detail\">");
			sb.append("<label class=\"label label-info\">");
			sb.append("<i class=\"fa fa-paw\"></i><span> 来源</span>");
			sb.append("</label>");
			sb.append("<div class=\"answer-desc-content\">");
			sb.append("<p>");
			sb.append(questionQueryResult.getReferenceName());
			sb.append("</p></div></div>");
			sb.append("<div class=\"answer-desc-detail\">");
			sb.append("<label class=\"label label-warning\">");
			sb.append("<i class=\"fa fa-flag\"></i><span> 解析</span>");
			sb.append("</label>");
			sb.append("<div class=\"answer-desc-content\">");
			sb.append("<p>");
			sb.append(questionQueryResult.getAnalysis());
			sb.append("</p></div></div>");
			sb.append("<div class=\"answer-desc-detail\">");
			sb.append("<label class=\"label label-success\">");
			sb.append("<i class=\"fa fa-bookmark\"></i><span> 考点</span>");
			sb.append("</label>");
			sb.append("<div class=\"answer-desc-content\">");
			sb.append("<p>");
			sb.append(questionQueryResult.getPointName());
			sb.append("</p></div></div>");
			sb.append("</div>");
		}

		sb.append("</li>");
		return sb.toString();
	}

	public String getUserExamPaper() {
		StringBuilder sb = new StringBuilder();

		switch (questionQueryResult.getQuestionTypeId()) {
		case 1:
			sb.append("<li class=\"question qt-singlechoice\">");
			break;
		case 2:
			sb.append("<li class=\"question qt-multiplechoice\">");
			break;
		case 3:
			sb.append("<li class=\"question qt-trueorfalse\">");
			break;
		case 4:
			sb.append("<li class=\"question qt-fillblank\">");
			break;
		case 5:
			sb.append("<li class=\"question qt-shortanswer\">");
			break;
		case 6:
			sb.append("<li class=\"question qt-essay\">");
			break;
		case 7:
			sb.append("<li class=\"question qt-analytical\">");
			break;
		default:
			break;
		}

		sb.append("<div class=\"question-title\">");
		sb.append("<div class=\"question-title-icon\"></div>");
		sb.append("<span class=\"question-no\">").append("</span>");
		sb.append("<span class=\"question-type\" style=\"display: none;\">");

		switch (questionQueryResult.getQuestionTypeId()) {
		case 1:
			sb.append("singlechoice").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[单选题]</span>");
			sb.append("<span class=\"question-point-content\">");
			sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("</span>");
			//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId())
					.append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			sb.append("<p class=\"question-body-text\">").append(questionContent.getTitle());
			if (questionContent.getTitleImg() != null)
				if (!questionContent.getTitleImg().trim().equals(""))
					sb.append(
							"<img class=\"question-content-img question-img\" src=\"")
							.append(baseUrl)
							.append(questionContent.getTitleImg())
							.append("\" />");
			sb.append("</p>");
			Iterator<String> it1 = questionContent.getChoiceList().keySet()
					.iterator();
			sb.append("<ul class=\"question-opt-list\">");
			while (it1.hasNext()) {
				sb.append("<li class=\"question-list-item\">");
				String key = it1.next();
				String value = questionContent.getChoiceList().get(key);
				sb.append("<input type=\"radio\" value=\"")
						.append(key)
						.append("\" name=\"question-radio1\" class=\"question-input\">");
				sb.append(key).append(": ").append(value);
				if (questionContent.getChoiceImgList() != null)
					if (questionContent.getChoiceImgList().containsKey(key))
						sb.append(
								"<img class=\"question-opt-img question-img\" src=\"")
								.append(baseUrl)
								.append(questionContent.getChoiceImgList().get(
										key)).append("\" />");
				sb.append("</span>");
				sb.append("</li>");
			}
			sb.append("</ul>");
			sb.append("</form>");
			break;
		case 2:
			sb.append("multiplechoice").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[多选题]</span>");
			sb.append("<span class=\"question-point-content\">");
			sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("</span>");
			//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId())
					.append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			sb.append("<p class=\"question-body-text\">").append(questionContent.getTitle());
			if (questionContent.getTitleImg() != null)
				if (!questionContent.getTitleImg().trim().equals(""))
					sb.append(
							"<img class=\"question-content-img question-img\" src=\"")
							.append(baseUrl)
							.append(questionContent.getTitleImg())
							.append("\" />");
			sb.append("</p>");
			Iterator<String> it2 = questionContent.getChoiceList().keySet()
					.iterator();
			sb.append("<ul class=\"question-opt-list\">");
			while (it2.hasNext()) {
				sb.append("<li class=\"question-list-item\">");
				String key = it2.next();
				String value = questionContent.getChoiceList().get(key);
				sb.append("<input type=\"checkbox\" value=\"")
						.append(key)
						.append("\" name=\"question-radio1\" class=\"question-input\">");
				sb.append(key).append(": ").append(value);
				if (questionContent.getChoiceImgList() != null)
					if (questionContent.getChoiceImgList().containsKey(key))
						sb.append(
								"<img class=\"question-opt-img question-img\" src=\"")
								.append(baseUrl)
								.append(questionContent.getChoiceImgList().get(
										key)).append("\" />");
				sb.append("</span>");
				sb.append("</li>");
			}
			sb.append("</ul>");
			sb.append("</form>");
			break;
		case 3:
			sb.append("trueorfalse").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[判断题]</span>");
			sb.append("<span class=\"question-point-content\">");
			sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("</span>");
			//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId())
					.append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			sb.append("<p class=\"question-body-text\">").append(questionContent.getTitle());
			if (questionContent.getTitleImg() != null)
				if (!questionContent.getTitleImg().trim().equals(""))
					sb.append(
							"<img class=\"question-content-img question-img\" src=\"")
							.append(baseUrl)
							.append(questionContent.getTitleImg())
							.append("\" />");
			sb.append("</p>");
			sb.append("<ul class=\"question-opt-list\">");
			
			sb.append("<li class=\"question-list-item\">").append(
					"<input type=\"radio\" value=\"T\" name=\"question-radio2\" class=\"question-input\">")
					.append("<span class=\"question-li-text\">正确</span>").append("</li>");
			
			sb.append("<li class=\"question-list-item\">").append("<input type=\"radio\" value=\"F\" name=\"question-radio2\" class=\"question-input\">")
					.append("<span class=\"question-li-text\">错误</span>").append("</li>");
			
			sb.append("</ul>");
			sb.append("</form>");
			break;
		case 4:
			sb.append("fillblank").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[填空题]</span>");
			sb.append("<span class=\"question-point-content\">");
			sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("</span>");
			//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId())
					.append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			sb.append("<p class=\"question-body-text\">").append(questionContent.getTitle());
			if (questionContent.getTitleImg() != null)
				if (!questionContent.getTitleImg().trim().equals(""))
					sb.append(
							"<img class=\"question-content-img question-img\" src=\"")
							.append(baseUrl)
							.append(questionContent.getTitleImg())
							.append("\" />");
			sb.append("</p>");
			sb.append("<textarea class=\"question-textarea\"></textarea>");
			sb.append("</form>");
			break;
		case 5:
			sb.append("shortanswer").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[简答题]</span>");
			sb.append("<span class=\"question-point-content\">");
			sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("</span>");
			//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId())
					.append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			sb.append("<p class=\"question-body-text\">").append(questionContent.getTitle());
			if (questionContent.getTitleImg() != null)
				if (!questionContent.getTitleImg().trim().equals(""))
					sb.append(
							"<img class=\"question-content-img question-img\" src=\"")
							.append(baseUrl)
							.append(questionContent.getTitleImg())
							.append("\" />");
			sb.append("</p>");
			sb.append("<textarea class=\"question-textarea\"></textarea>");
			sb.append("</form>");
			break;
		case 6:
			sb.append("essay").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[论述题]</span>");
			sb.append("<span class=\"question-point-content\">");
			sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("</span>");
			//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId())
					.append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			sb.append("<p class=\"question-body-text\">").append(questionContent.getTitle());
			if (questionContent.getTitleImg() != null)
				if (!questionContent.getTitleImg().trim().equals(""))
					sb.append(
							"<img class=\"question-content-img question-img\" src=\"")
							.append(baseUrl)
							.append(questionContent.getTitleImg())
							.append("\" />");
			sb.append("</p>");
			sb.append("<textarea class=\"question-textarea\"></textarea>");
			sb.append("</form>");
			break;
		case 7:
			sb.append("analytical").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[分析题]</span>");
			sb.append("<span class=\"question-point-content\">");
			sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("</span>");
			//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId())
					.append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			sb.append("<p class=\"question-body-text\">").append(questionContent.getTitle());
			if (questionContent.getTitleImg() != null)
				if (!questionContent.getTitleImg().trim().equals(""))
					sb.append(
							"<img class=\"question-content-img question-img\" src=\"")
							.append(baseUrl)
							.append(questionContent.getTitleImg())
							.append("\" />");
			sb.append("</p>");
			sb.append("<textarea class=\"question-textarea\"></textarea>");
			sb.append("</form>");
			break;
		default:
			break;

		}
		sb.append("</li>");
		return sb.toString();
	}
}

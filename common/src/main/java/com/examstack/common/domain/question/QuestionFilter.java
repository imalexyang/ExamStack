package com.examstack.common.domain.question;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Ocelot
 * @date 2014年6月8日 下午10:15:55
 */
@XmlRootElement
public class QuestionFilter implements Serializable {

	private static final long serialVersionUID = -8784942836284858739L;

	private int fieldId;

	private int knowledge;

	private int questionType;

	private int tag;

	private String searchParam;

	public int getFieldId() {
		return fieldId;
	}

	public void setFieldId(int fieldId) {
		this.fieldId = fieldId;
	}

	public int getKnowledge() {
		return knowledge;
	}

	public void setKnowledge(int knowledge) {
		this.knowledge = knowledge;
	}

	public int getQuestionType() {
		return questionType;
	}

	public void setQuestionType(int questionType) {
		this.questionType = questionType;
	}

	public String getSearchParam() {
		return searchParam;
	}

	public void setSearchParam(String searchParam) {
		this.searchParam = searchParam;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getTag() {
		return tag;
	}

	public void setTag(int tag) {
		this.tag = tag;
	}
}

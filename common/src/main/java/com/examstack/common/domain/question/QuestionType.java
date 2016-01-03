package com.examstack.common.domain.question;

import java.io.Serializable;

public class QuestionType implements Serializable {

	private static final long serialVersionUID = 8020837656124230840L;
	private int id;
	private String name;
	private boolean subjective ;

	public boolean isSubjective() {
		return subjective;
	}

	public void setSubjective(boolean subjective) {
		this.subjective = subjective;
	}

	public QuestionType() {
	}
	public QuestionType(String name) {
		this.name=name;
	}
	public QuestionType(int id, String name) {
		this.id = id;
		this.name = name;
	}

	@Override
	public String toString() {
		return "QuestionType [id=" + id + ", name=" + name + ", subjective="
				+ subjective + "]";
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

}

package com.examstack.common.domain.question;

import java.util.LinkedHashMap;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("QuestionContent")
public class QuestionContent {

	@XStreamAlias("title")
	private String title;
	@XStreamAlias("titleImg")
	private String titleImg = "";
	@XStreamAlias("choiceList")
	private LinkedHashMap<String, String> choiceList;
	@XStreamAlias("choiceImgList")
	private LinkedHashMap<String, String> choiceImgList;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitleImg() {
		return titleImg;
	}

	public void setTitleImg(String titleImg) {
		this.titleImg = titleImg;
	}

	public LinkedHashMap<String, String> getChoiceList() {
		return choiceList;
	}

	public void setChoiceList(LinkedHashMap<String, String> choiceList) {
		this.choiceList = choiceList;
	}

	public LinkedHashMap<String, String> getChoiceImgList() {
		return choiceImgList;
	}

	public void setChoiceImgList(LinkedHashMap<String, String> choiceImgList) {
		this.choiceImgList = choiceImgList;
	}

}

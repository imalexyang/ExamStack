package com.examstack.common.domain.exam;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ExamFinishParam implements Serializable {
	private static final long serialVersionUID = 4265690784518580278L;
	private int exam_history_id;
	private int duration;
	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	private HashMap<Integer, AnswerSheetItem> as;

	public int getExam_history_id() {
		return exam_history_id;
	}

	public void setExam_history_id(int exam_history_id) {
		this.exam_history_id = exam_history_id;
	}

	public HashMap<Integer, AnswerSheetItem> getAs() {
		return as;
	}

	public void setAs(HashMap<Integer, AnswerSheetItem> as) {
		this.as = as;
	}

	

}

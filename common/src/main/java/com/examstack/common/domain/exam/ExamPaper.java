package com.examstack.common.domain.exam;

import java.io.Serializable;
import java.util.Date;

public class ExamPaper implements Serializable {

	private static final long serialVersionUID = -3878176097815638534L;
	protected int id;
	protected String name;
	protected String content;
	protected int duration;
	protected int pass_point;
	protected float total_point;
	protected Date create_time;
	/**
	 * 0:默认 1：发布
	 */
	protected int status;
	protected String summary;
	protected boolean is_visible;
	protected int group_id;
	protected boolean is_subjective;
	protected String answer_sheet;
	protected String creator;
	protected String paper_type;
	protected int field_id;
	protected int field_name;

	public int getField_id() {
		return field_id;
	}

	public void setField_id(int field_id) {
		this.field_id = field_id;
	}

	public int getField_name() {
		return field_name;
	}

	public void setField_name(int field_name) {
		this.field_name = field_name;
	}

	public String getPaper_type() {
		return paper_type;
	}

	public void setPaper_type(String paper_type) {
		this.paper_type = paper_type;
	}

	public String getAnswer_sheet() {
		return answer_sheet;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public void setAnswer_sheet(String answer_sheet) {
		this.answer_sheet = answer_sheet;
	}

	public boolean isIs_subjective() {
		return is_subjective;
	}

	public void setIs_subjective(boolean is_subjective) {
		this.is_subjective = is_subjective;
	}

	public int getGroup_id() {
		return group_id;
	}

	public void setGroup_id(int group_id) {
		this.group_id = group_id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public String getSummary() {
		return summary;
	}

	public float getTotal_point() {
		return total_point;
	}

	public void setTotal_point(float total_point) {
		this.total_point = total_point;
	}

	public void setSummary(String summary) {
		this.summary = summary;
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

	public int getPass_point() {
		return pass_point;
	}

	public void setPass_point(int pass_point) {
		this.pass_point = pass_point;
	}



	@Override
	public String toString() {
		return "ExamPaper [id=" + id + ", name=" + name + ", content="
				+ content + ", duration=" + duration + ", pass_point="
				+ pass_point + ", total_point=" + total_point
				+ ", create_time=" + create_time + ", status=" + status
				+ ", subjective=" + is_subjective + ", summary=" + summary
				+ ", is_visible=" + is_visible + ", group_id=" + group_id + "]";
	}
	
	
	
}

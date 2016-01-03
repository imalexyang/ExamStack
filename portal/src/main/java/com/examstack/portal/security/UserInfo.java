package com.examstack.portal.security;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.examstack.common.domain.user.Role;
import com.examstack.common.util.MenuItem;


public class UserInfo extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int userid;
	private List<Role> roleList;
	private String trueName;
	private String rolesName;
	private String enabled;
	private int fieldId;
	private String fieldName;
	private String email;
	private int depId;
	private String nationalId;
	private String phoneNum;
	private Date lastLoginTime;
	private Date loginTime;
	private LinkedHashMap<String,MenuItem> menuMap;
	private HashMap<String,Role> roleMap;
	//保存考试历史id
	private int histId;
	//保存考试id
	private int examId;
	//保存试卷id
	private int examPaperId;
	public int getDepId() {
		return depId;
	}

	public void setDepId(int depId) {
		this.depId = depId;
	}

	public String getNationalId() {
		return nationalId;
	}

	public void setNationalId(String nationalId) {
		this.nationalId = nationalId;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public int getExamPaperId() {
		return examPaperId;
	}

	public void setExamPaperId(int examPaperId) {
		this.examPaperId = examPaperId;
	}

	public int getExamId() {
		return examId;
	}

	public void setExamId(int examId) {
		this.examId = examId;
	}

	public int getHistId() {
		return histId;
	}

	public void setHistId(int histId) {
		this.histId = histId;
	}

	public HashMap<String, Role> getRoleMap() {
		return roleMap;
	}

	public void setRoleMap(HashMap<String, Role> roleMap) {
		this.roleMap = roleMap;
	}

	public LinkedHashMap<String, MenuItem> getMenuMap() {
		return menuMap;
	}

	public void setMenuMap(LinkedHashMap<String, MenuItem> menuMap) {
		this.menuMap = menuMap;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public int getFieldId() {
		return fieldId;
	}

	public void setFieldId(int fieldId) {
		this.fieldId = fieldId;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	public String getRolesName() {
		return rolesName;
	}

	public void setRolesName(String rolesName) {
		this.rolesName = rolesName;
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	public UserInfo(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		// TODO Auto-generated constructor stub
	}

}

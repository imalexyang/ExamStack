package com.examstack.common.util;

import java.io.Serializable;
import java.util.LinkedHashMap;

public class MenuItem implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6302881094214911822L;
	private String menuId;
	private String menuHref;
	private String menuName;
	private int menuSeq;
	private String authority;
	private String parentId;
	private String icon;
	private boolean visiable;
	public boolean isVisiable() {
		return visiable;
	}

	public void setVisiable(boolean visiable) {
		this.visiable = visiable;
	}
	private LinkedHashMap<String,MenuItem> childMap;

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public LinkedHashMap<String, MenuItem> getChildMap() {
		return childMap;
	}

	public void setChildMap(LinkedHashMap<String, MenuItem> childMap) {
		this.childMap = childMap;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getMenuHref() {
		return menuHref;
	}

	public void setMenuHref(String menuHref) {
		this.menuHref = menuHref;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public int getMenuSeq() {
		return menuSeq;
	}

	public void setMenuSeq(int menuSeq) {
		this.menuSeq = menuSeq;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public MenuItem(){}
	public MenuItem(String menuId, String menuHref, String menuName, int menuSeq, String authority, String parentId,
			String icon) {
		super();
		this.menuId = menuId;
		this.menuHref = menuHref;
		this.menuName = menuName;
		this.menuSeq = menuSeq;
		this.authority = authority;
		this.parentId = parentId;
		this.icon = icon;
	}

}

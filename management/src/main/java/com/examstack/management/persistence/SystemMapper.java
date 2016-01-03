package com.examstack.management.persistence;

import java.util.List;

import com.examstack.common.util.MenuItem;


public interface SystemMapper {

	public List<MenuItem> getMenuItemsByAuthority(String authority);
}

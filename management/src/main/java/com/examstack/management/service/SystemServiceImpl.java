package com.examstack.management.service;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examstack.common.util.MenuItem;
import com.examstack.management.persistence.SystemMapper;

@Service("SystemService")
public class SystemServiceImpl implements SystemService {

	@Autowired
	private SystemMapper systemMapper;
	@Override
	public LinkedHashMap<String,MenuItem> getMenuItemsByAuthority(String authority) {
		// TODO Auto-generated method stub
		List<MenuItem> ml = systemMapper.getMenuItemsByAuthority(authority);
		
		LinkedHashMap<String,MenuItem> map = new LinkedHashMap<String,MenuItem>();
		for(MenuItem item : ml){
			if(item.getParentId().equals("-1")){
				LinkedHashMap<String,MenuItem> childs = new LinkedHashMap<String,MenuItem>();
				for(MenuItem mi : ml){
					if(mi.getParentId().equals(item.getMenuId())){
						childs.put(mi.getMenuId(), mi);
					}
				}
				item.setChildMap(childs);
				map.put(item.getMenuId(), item);
			}
		}
		return map;
	}

}

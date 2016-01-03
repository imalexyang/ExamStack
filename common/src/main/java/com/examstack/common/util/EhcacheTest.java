package com.examstack.common.util;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

public class EhcacheTest {
	@Cacheable(value = "wordCache")
	public String sayWord(String word) {
		System.out.println("nocache");
		return word;
	}

	@CacheEvict(value = "wordCache", key = "#word")
	public String clearWord(String word) {
		return "Ok";
	}
}

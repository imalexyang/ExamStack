package com.examstack.common.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Roulette<T> {

	public static Log log = LogFactory.getLog(Roulette.class);
	private List<T> resultList;
	private HashMap<Integer,Float> chanceMap;
	
	public Roulette(List<T> resultList,HashMap<Integer,Float> chanceMap){
		
		Iterator<Integer> it = chanceMap.keySet().iterator();
		float sum = 0;
		while(it.hasNext()){
			sum = (float)(Math.round((sum + chanceMap.get(it.next())) * 1000)) / 1000;
			
			log.info("sum = " + sum);
		}
		
		if(sum == 1 && resultList.size() == chanceMap.size()){
			this.resultList = resultList;
			this.chanceMap = chanceMap;
		}
	}
	
	public T getResult() throws Exception{
		double result = Math.random();
		double area = chanceMap.get(0);
		
		if(resultList.size() == 0)
			throw new Exception("");
		
		//不会出现i+1造成数组越界的情况，因为当i=resultList.size()的时候
		//area=1，肯定会大于result，因此函数返回，不会继续执行
		for(int i = 0 ; i < resultList.size() ; i ++){
			if(area > result)
				return resultList.get(i);
			else
				area += chanceMap.get(i + 1);
		}
		return null;
	}
}

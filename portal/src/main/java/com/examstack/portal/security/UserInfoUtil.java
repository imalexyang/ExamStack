package com.examstack.portal.security;

import org.springframework.security.core.context.SecurityContextHolder;


public class UserInfoUtil {
	public static UserInfo getUserInfo(){
		UserInfo userInfo=(UserInfo)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userInfo;
	}
	/*public static void main(String[] args){
		List<Question> initList= new ArrayList<Question>();
		List<Question> resultList= new ArrayList<Question>();
		for(int i=0;i<20;i++){
			initList.add(new Question(i));
		}
		
		System.out.println("未生成之前："+initList);
		//resultList=getQuestionListByRandom(initList,10);
		System.out.println("生成之后："+resultList);
		
	}*/
	
	
	
	
}

package com.examstack.common.util;

public class StringUtil {

	public static String format(String str,int length){
		while(str.length() > length)
			length ++;
		while(str.length() < length){
			str = "0" + str;
		}
		return str;
	}
	
	public static String format(int num,int length){
		String str = Integer.toHexString(num);
		return format(str, length);
	}
}

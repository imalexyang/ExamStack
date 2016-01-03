package com.examstack.common.util.file;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

public class PropertyReaderUtil {

	public static final String PROPERITIES_PATH="/WEB-INF/classes/property.properties";
	public static final String SYS_PROPERITIES_PATH="/WEB-INF/classes/sys-config.properties";
	public static Properties getProperties() throws FileNotFoundException{
		InputStream inputStream = new FileInputStream(PROPERITIES_PATH);
		
		Properties pros = new Properties();
		try {
			pros.load(inputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pros;
	}
	
	public static Properties getSysProperties() throws FileNotFoundException{
		InputStream inputStream = new FileInputStream(SYS_PROPERITIES_PATH);
		
		Properties pros = new Properties();
		try {
			pros.load(inputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pros;
	}
	
	public static Properties getProperties(String path) throws FileNotFoundException{
		InputStream inputStream = new FileInputStream(path);
		
		Properties pros = new Properties();
		try {
			pros.load(inputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pros;
	}
	
	public static Properties getProperties(HttpServletRequest request) throws FileNotFoundException{
		
		String path = request.getSession()
				.getServletContext()
				.getRealPath(PROPERITIES_PATH);
		InputStream inputStream = new FileInputStream(path);
		
		Properties pros = new Properties();
		try {
			pros.load(inputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pros;
	}
	
	public static Properties getSysProperties(HttpServletRequest request) throws FileNotFoundException{
		
		String path = request.getSession()
				.getServletContext()
				.getRealPath(SYS_PROPERITIES_PATH);
		InputStream inputStream = new FileInputStream(path);
		
		Properties pros = new Properties();
		try {
			pros.load(inputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pros;
	}
}

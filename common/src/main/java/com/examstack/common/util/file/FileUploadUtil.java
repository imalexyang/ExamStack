package com.examstack.common.util.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * 上传文件
 * @author scar
 *
 */
public class FileUploadUtil {

	private static Log log = LogFactory.getLog(FileUploadUtil.class);
	public static List<String> uploadFile(HttpServletRequest request, 
			HttpServletResponse response, String username) throws FileNotFoundException{
		List<String> filePathList = new ArrayList<String>();
		
		String strPath = ",webapps,files,training," + username;
		
		String filepath = System.getProperty("catalina.base") + strPath.replace(',', File.separatorChar);
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		String fileName = null;
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {

			MultipartFile mf = entity.getValue();
			fileName = mf.getOriginalFilename();
			String fileType = fileName.substring(fileName.lastIndexOf('.'));
			try {
				String newFileName = MD5FileUtil.getMD5String(mf.getBytes());
				String newfilepath;
				newfilepath = filepath + File.separatorChar + newFileName + fileType;
				String filepathUrl = "files" + File.separatorChar + "training" + File.separatorChar + username + File.separatorChar + newFileName + fileType;
				
				System.out.println("newfilepath=" + newfilepath);
				File dest = new File(filepath);
				if(!dest.exists()){
					dest.mkdirs();
				}
				File uploadFile = new File(newfilepath);
				if(uploadFile.exists()){
					uploadFile.delete();
				}
				log.info("start upload file: " + fileName);
				FileCopyUtils.copy(mf.getBytes(), uploadFile);
				filePathList.add(filepathUrl);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.info("upload failed. filename: " + fileName + e.getMessage());
				return null;
			}
			
			
		}
		
		return filePathList;
	}
	
	public static List<String> uploadImg(HttpServletRequest request, 
			HttpServletResponse response, String username) throws Exception{
		List<String> filePathList = new ArrayList<String>();

		String strPath = ",webapps,files,question," + username;
		
		String filepath = System.getProperty("catalina.base") + strPath.replace(',', File.separatorChar);
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		String fileName = null;
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {

			MultipartFile mf = entity.getValue();
			fileName = mf.getOriginalFilename();
			String file_type = fileName.substring(fileName.indexOf('.'));
			if(!".jpg".equals(file_type.toLowerCase()) && !".png".equals(file_type.toLowerCase()))
				throw new Exception("文件类型错误");
			fileName = String.valueOf(new Date().getTime()) + file_type;
			String newfilepath;
			newfilepath = filepath + File.separatorChar + fileName;
			String filepathUrl = "files" + File.separatorChar + "question" + File.separatorChar + username + File.separatorChar + fileName;

			System.out.println("newfilepath=" + newfilepath);
			File dest = new File(filepath);
			if(!dest.exists()){
				dest.mkdirs();
			}
			File uploadFile = new File(newfilepath);
			if(uploadFile.exists()){
				uploadFile.delete();
			}
			try {

				log.info("start upload file: " + fileName);
				FileCopyUtils.copy(mf.getBytes(), uploadFile);
			} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
				log.info("upload failed. filename: " + fileName + e.getMessage());
				return null;
			}
			filePathList.add(filepathUrl);
			
		}
		
		return filePathList;
	}
	
	public static void copyFile(String oldPath, String newPath) { 
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) { //文件存在时 
				InputStream inStream = new FileInputStream(oldPath); //读入原文件 
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[2000];
				while ( (byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; //字节数 文件大小 
					fs.write(buffer, 0, byteread);
				}
				fs.close();
				inStream.close();
			}
		}catch (Exception e) {
			System.out.println("复制单个文件操作出错");
			e.printStackTrace();
			return;
		}
		File oldfile = new File(oldPath);
		oldfile.delete();
	}
}

package com.examstack.common.util.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;


public class PdfToSwf {

	private HttpServletRequest request;
	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public int convertPDF2SWF(String sourcePath, String destPath,  
            String fileName) throws IOException{
		
		String path = request.getSession().getServletContext().getRealPath("/WEB-INF/classes/property.properties");
		String path_envp = request.getSession().getServletContext().getRealPath("/WEB-INF/classes/sys-config.properties");
		Properties pros = PropertyReaderUtil.getProperties(path);
		Properties pros_envp = PropertyReaderUtil.getProperties(path_envp);
		System.out.println(pros_envp.getProperty("envp"));
		System.out.println(pros.getProperty("command1"));
		File dest = new File(destPath);
        if (!dest.exists()) {
            dest.mkdirs();
        }
        
        File source = new File(sourcePath);
        if(!source.exists()){
        	return 0;
        }
        
        String[] envp = new String[1];
        envp[0] = "PATH=" + pros_envp.getProperty("envp");
        String command1 = pros.getProperty("command1");
        String command = command1.replace("{sourcePath}", sourcePath)
        		.replace("{destPath}", destPath)
        		.replace("{fileName}", fileName);
        System.out.println(envp[0]);
        System.out.println(command);
        /*String command = "cmd /c pdf2swf -z -s flashversion=9 \"" + sourcePath  
                + "\" -o \"" + destPath + fileName + "\"";*/
        Process pro = Runtime.getRuntime().exec(command, envp);
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(pro.getInputStream()));
        while (bufferedReader.readLine() != null) {
            String text = bufferedReader.readLine();
            System.out.println(text);
        }
        try {  
            pro.waitFor();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String command2 = pros.getProperty("command2");
        command = command2.replace("{destPath}", destPath)
        		.replace("{fileName}", fileName);
        System.out.println(pros.getProperty(command));
        /*command = "swfcombine -z -X 720 -Y 540 \"D:/tools/SWFTools/swfs/rfxview.swf\" viewport=\""
        		+ destPath + fileName + "\" -o \"" + destPath + fileName + "\"";*/
        pro = Runtime.getRuntime().exec(command, envp);
        bufferedReader = new BufferedReader(new InputStreamReader(pro
                .getInputStream()));
        while (bufferedReader.readLine() != null) {
            String text = bufferedReader.readLine();
            System.out.println(text);
        }
        try {
            pro.waitFor();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        pro.destroy();
        return pro.exitValue();
	}

}

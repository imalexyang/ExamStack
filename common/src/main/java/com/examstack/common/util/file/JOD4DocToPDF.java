package com.examstack.common.util.file;

import java.io.File;
import java.net.ConnectException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;

public class JOD4DocToPDF{

	private File inputFile;// 需要转换的文件 
    private File outputFile;// 输出的文件
    
	public JOD4DocToPDF(){
    	
    }
    public JOD4DocToPDF(File inputFile, File outputFile) {
        this.inputFile = inputFile;
        this.outputFile = outputFile;
    }
    
    /**
     * 
     * @return
     * @throws ConnectException 
     */
    public boolean docToPdf(HttpServletRequest request) throws ConnectException {
    	
    	Date start = new Date();
    	/*//打开端口
    	String path = request.getSession()
				.getServletContext().getRealPath("/WEB-INF/classes/OpenOffice_Service.bat");
    	path = path.replaceFirst(" ", "\" \"");
    	System.out.println(path);
        try {
        	Process pro = Runtime.getRuntime().exec("d:\\1.bat");
			StreamGobbler errorGobbler = new StreamGobbler(pro.getErrorStream(), "Error");
			StreamGobbler outputGobbler = new StreamGobbler(pro.getInputStream(), "Iutput");
			
			errorGobbler.start();
			outputGobbler.start();
			pro.getOutputStream().close();
			
			try {
				pro.waitFor();
				pro.destroy();
				pro.exitValue();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			return false;
		}*/

        // connect to an OpenOffice.org instance running on port 8100
    	//Process pro = Runtime.getRuntime().e
        OpenOfficeConnection connection = new SocketOpenOfficeConnection(8100);
        try {
            connection.connect();
            // convert
            DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
            converter.convert(inputFile, outputFile);
        } catch (ConnectException cex) {
            if (connection.isConnected()) {
                connection.disconnect();
                connection = null;
            }
            throw cex;
            
        } catch(IllegalArgumentException e){
        	throw new IllegalArgumentException("文件类型错误！上传文档格式不要高于office2003");
        }
        
        if (connection != null) {
            connection.disconnect();
            connection = null;
        }
        
        long l = (start.getTime() - new Date().getTime());
        long day = l / (24 * 60 * 60 * 1000);
        long hour = (l / (60 * 60 * 1000) - day * 24);
        long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        System.out.println("生成" + outputFile.getName() + "耗费：" + min + "分" + s
                + "秒");
        return true;
    }
    
    public File getInputFile() {
        return inputFile;
    }
    
    public void setInputFile(File inputFile) {
        this.inputFile = inputFile;
    }
    
    public File getOutputFile() {
        return outputFile;
    }
    
    public void setOutputFile(File outputFile) {
        this.outputFile = outputFile;
    }
    
    
}

package com.examstack.common.util.file;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.DocumentEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Html2Doc {

	public static boolean writeWordFile(String content,String filename,String path,String examPaperName) throws IOException{
		
        String head = "<html><div style=\"text-align: center\"><span style=\"font-size: 28px\"><span style=\"font-family: 黑体\">" + examPaperName + 
                "<br /> <br /> </span></span></div>";
        String tail = "</html>";
        content = head + content + tail;
        content = exampaper_formater(content);
        ByteArrayInputStream bais = null;
        FileOutputStream ostream = null;
        try{
        	if (!"".equals(path)){
        		File fileDir = new File(path);
        		if(!fileDir.exists())
        			fileDir.mkdirs();
                if (fileDir.exists()) {
                	
                    String fileName = filename;
                    byte b[] = content.getBytes("GBK");
                    bais = new ByteArrayInputStream(b);
                    POIFSFileSystem poifs = new POIFSFileSystem();
                    DirectoryEntry directory = poifs.getRoot();
                    DocumentEntry documentEntry = directory.createDocument("WordDocument", bais);
                    ostream = new FileOutputStream(path+ fileName);
                    poifs.writeFilesystem(ostream);
                    bais.close();
                    ostream.close();
                }
        	}
        }catch(IOException e){
        	bais.close();
            ostream.close();
        	e.printStackTrace();
        	throw e;
        }
        return true;
	}
	
	private static String exampaper_formater(String content){
		Document doc = Jsoup.parse(content,"UTF-8");
		
		Elements lis= doc.select("li");
		Elements textareas = doc.select("textarea");
		Elements inputs = doc.select("input");
		
		for(Element e:lis){
			e.after("<br/>");
		}
		
		for(Element e:inputs){
			String tmp_text = e.text();
			e.after("<p>" + tmp_text + "</p>");
			e.remove();
		}
		
		for(Element e:textareas){
			e.after("<br /><br /><br /><br /><br />");
			e.remove();
		}
		
		return doc.toString();
	}
}

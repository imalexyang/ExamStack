package com.examstack.common.util.xml;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class Object2Xml {
	public static String toXml(Object obj){
		XStream xstream=new XStream();
		xstream.processAnnotations(obj.getClass());
		
		return xstream.toXML(obj);
	}
	
	public static <T> T toBean(String xmlStr,Class<T> cls){
		XStream xstream=new XStream(new DomDriver());
		xstream.processAnnotations(cls);
		@SuppressWarnings("unchecked")
		T obj=(T)xstream.fromXML(xmlStr);
		return obj;
	}
}

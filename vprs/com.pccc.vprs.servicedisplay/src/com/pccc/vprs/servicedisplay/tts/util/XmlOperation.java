package com.pccc.vprs.servicedisplay.tts.util;

import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class XmlOperation {
	/**
	 * 将自定义数据对象转化为XML字符串
	 * @param clazz 自定义数据类型
	 * @param object  自定义数据对象
	 * @return XML字符串
	 * @throws JAXBException 异常
	 */
	public static String objectToXML(Class clazz, Object object)
			throws JAXBException {
		String xml = null;
		JAXBContext context = JAXBContext.newInstance(clazz);
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		Writer w = new StringWriter();
		m.marshal(object, w);
		xml = w.toString();
		return xml;
	}

	/**
	 * 将XML字符串转化为自定义数据对象
	 * @param clazz  自定义数据类型
	 * @param xml XML字符串
	 * @return 自定义数据对象
	 * @throws JAXBException 异常
	 */
	public static Object xmlToObject(Class clazz, String xml)
			throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(clazz);
		Unmarshaller um = context.createUnmarshaller();
		return um.unmarshal(new StringReader(xml));
	}
    
	/**
	 * 判断是否为XML文件
	 * @param fileName
	 * @return
	 */
	public static boolean isXmlFile(String fileName) {
		String itype = fileName.substring(  
				fileName.lastIndexOf("."));
		if (itype.equals(".xml")){
			return true;
		}
		return false;
	}
	
	
	/**
	 * 去掉xml文件头
	 * @param xml
	 * @return
	 */
	public static String removeXmlHead(String xml) {
		return xml.replaceFirst("(?s)<?.*?\\?>","");
	}
	/**
	 * 取得文件夹下的所有xml文件
	 * @param dirName
	 * @param resultFileName
	 * @return
	 */
	public static List<String> getAllXmlFile(String dirName,List<String> resultFileName){
		File[] files = new File(dirName).listFiles();
        if(files==null)return resultFileName;// 判断目录下是不是空的
        for (File f : files) {
            if(f.isDirectory()){// 判断是否文件夹
            	getAllXmlFile(f.getPath(),resultFileName);// 调用自身,查找子目录
            }else{
            	if(isXmlFile(f.getName())){
            		 resultFileName.add(f.getPath());
            	}
            }
        }
        return resultFileName;
    }
	
}

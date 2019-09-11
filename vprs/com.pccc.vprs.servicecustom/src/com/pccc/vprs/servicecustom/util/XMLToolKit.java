package com.pccc.vprs.servicecustom.util;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import com.primeton.btp.api.core.logger.ILogger;
import com.primeton.btp.api.core.logger.LoggerFactory;

public class XMLToolKit {
	
	private static final ILogger logger=LoggerFactory.getLogger(XMLToolKit.class);
	
	public static String ATTRIBUTE_TYPE = "__type";  //报文类型
	
	public static String IS_NULL_OR_EMPTY = "__isNullOrEmpty"; //是否为空
	
	public static String IS_COLLECTION = "__collection";  //是否为集合
	
	public static String parseStringXml (String transMsg) {
		try {
			Document document = DocumentHelper.parseText(transMsg);
			return parseElement(document.getRootElement());
		} catch (DocumentException e) {
			logger.error("将String转成XML出现异常，transMsg: " + transMsg + ",异常信息为："  + e.getMessage(),e);
			return null;
		}
	}
	/**
	 * 递归解析xml文档
	 * @param element
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String parseElement(Element element) {
		StringBuffer returnStr = new StringBuffer("");
		String elementType = element.attributeValue(ATTRIBUTE_TYPE);
		String isNull = element.attributeValue(IS_NULL_OR_EMPTY);
//		String isCollection = element.attributeValue(IS_COLLECTION);
		String elementName = element.getName();
		if (StringUtils.isBlank(elementType)) {
			if ("null".equals(isNull)) {
				returnStr.append("<" + elementName + " _type=\"null\"/>");
			}
		} else {
			if (elementType.startsWith("java")) {
				String elementData = (String) element.getData();
				if (StringUtils.isBlank(elementData)) {
					returnStr.append("<" + elementName + " _type=\"null\"/>");
				} else {
					returnStr.append("<" + elementName + " _type=\"java\">" + elementData + "</" + elementName + ">");
				}
			} else if (elementType.startsWith("sdo")) {
				returnStr.append("<" + elementName + " _type=\"list\">");
				List<Element> elements = element.elements();
				for (Iterator<Element> iterator = elements.iterator(); iterator
						.hasNext();) {
					Element element2 = iterator.next();
					returnStr.append(parseElement(element2));
					
				}
				returnStr.append("</" + elementName + ">");
			}
			
		}
		return returnStr.toString();
	}
	
}

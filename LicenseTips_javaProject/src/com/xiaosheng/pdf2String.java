package com.xiaosheng;

import java.io.File;
import java.io.IOException;

import com.xiaosheng.utils.POIUtil;

public class pdf2String {
	public static void main(String[] args) {
		try {
			String pdf2String = POIUtil.pdf2String(new File("C:\\Users\\leoli\\Desktop\\效生声纹引擎开发手册.pdf"));
			
			System.out.println(pdf2String);
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}
}

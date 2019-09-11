package cn.productivetech.cmos.zhongbao.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * 功能：Java读取txt文件的内容 步骤：
 * 1：先获得文件句柄 
 * 2：获得文件句柄当做是输入一个字节码流，需要对这个输入流进行读取
 * 3：读取到输入流后，需要读取生成字节流 
 * 4：一行一行的输出。readline()。 
 * 备注：需要考虑的是异常情况
 * @author kenny_peng
 *
 */
public class GetTxtInfoUtils {

	/**
	 * 读取txt文件内容
	 * @param filePath txt文件路径
	 */
	public static Map<String, String> readTxtFile(String filePath) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			String encoding = "UTF-8";
			File file = new File(filePath);
			
			// 判断文件是否存在
			if (file.isFile() && file.exists()) {
				
				// 考虑到编码格式
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					
					/*
					 * 根据txt文本解析出需要的内容
					 */
					
					//当读取行不为空时，解析该行，得到坐席id和语音存储位置
					if (lineTxt.trim() != null && lineTxt.trim() != "" && lineTxt != "") {
						String[] lineStrings = lineTxt.split(",");
						String id = lineStrings[0];						//坐席id
						String location = lineStrings[1];				//音频位置
						if (id != null && id != "" && location != null && location != "") {
							map.put(lineStrings[0], lineStrings[1]);
						}
					}
				}//End while
				read.close();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
		return map;
	}
	
	public static void main(String[] args) {
		GetTxtInfoUtils.readTxtFile("F:/cms/1/1.txt");
	}
}

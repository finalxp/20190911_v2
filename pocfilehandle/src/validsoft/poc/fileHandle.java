package validsoft.poc;

import java.util.ArrayList;
import java.util.List;

import validsoft.utils.FileUtils;

public class fileHandle {
	

	
	public static void main(String[] args) {
		
		String path ="Z:\\test20191029\\info\\";
		
		List<String> files = FileUtils.getFiles(path,true);
		
		ArrayList<String> arrayList = new ArrayList<String>();
		ArrayList<String> ID = new ArrayList<String>();
		ArrayList<String> ID2 = new ArrayList<String>();
		ArrayList<String> ID3 = new ArrayList<String>();
		
		for (String string : files) {
			
			String substring = string.substring(21);
			System.out.println(substring);
			
			ID.add(substring);
	
		}
		
		/*截取?之前字符串
			    String str1=str.substring(0, str.indexOf("?"));*/
		for (String string : ID) {
			String substring = string.substring(0,string.indexOf("\\"));
			if (!ID2.contains(substring)) {
				ID2.add(substring);
			}
			
			System.out.println("--"+substring);
		}
		
		for (String string : ID2) {
			string = path + string;
			ID3.add(string);
			System.out.println("===="+string);
		}
		
		for (String string : ID2) {
			List<String> files2 = FileUtils.getFiles(path +string+"\\");

			for (int i = 0; i < files2.size(); i++) {
				FileUtils.copy(files2.get(2), "Z:\\test20191029\\verify\\"+string+".wav");
				FileUtils.copy(files2.get(1), "Z:\\test20191029\\enrol\\"+string+"\\"+string+"-1.wav");
				FileUtils.copy(files2.get(0), "Z:\\test20191029\\enrol\\"+string+"\\"+string+"-2.wav");
				FileUtils.copy(files2.get(3), "Z:\\test20191029\\enrol\\"+string+"\\"+string+"-3.wav");
			}
		}

	}
}

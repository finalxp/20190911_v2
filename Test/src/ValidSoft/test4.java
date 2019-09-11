package ValidSoft;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class test4 {
	public static void main(String args[]) throws Exception {
		File file = new File("C:\\Users\\leoli\\Desktop\\testbestpay.txt");// Text文件
		BufferedReader br = new BufferedReader(new FileReader(file));// 构造一个BufferedReader类来读取文件
		String s = null;
		while ((s = br.readLine()) != null) {// 使用readLine方法，一次读一行
			System.out.println(s);
		}
		br.close();
		;
	}
}
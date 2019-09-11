package cn.xs.erp.core;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.api.ShellCallback;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;


public class GenSQL {
	
	public static void main(String[] args) {
		new GenSQL().test();
	}
	
	private void test(){
		List<String> warnings = new ArrayList<>();
		Boolean ovrWrite = true;
		URL configUrl = ClassLoader.getSystemResource("generatorConfig.xml");
		File file = new File(configUrl.getFile());
		ConfigurationParser cp = new ConfigurationParser(warnings);
		try {
			Configuration config = cp.parseConfiguration(file);
			ShellCallback back = new ShellCallbackEx(ovrWrite);
			MyBatisGenerator my = new MyBatisGenerator(config, back, warnings);
			my.generate(null);
			for (String item : warnings) {
				System.out.println(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("ok!!");
	}
}

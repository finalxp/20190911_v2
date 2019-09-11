package cn.productivetech.cmos.zhongbao.config;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.api.ShellCallback;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自动生成model，mybatis SQL文件
 * @author: kenny_peng
 * @created: 2019-04-12
 */
public class GenSQL {
	
	private Logger logger = LoggerFactory.getLogger(GenSQL.class);
	
	/**
	 * main方法
	 * @param args
	 */
	public static void main(String[] args) {
		new GenSQL().test();
	}
	
	/**
	 * 根据配置文件，生成对应model，mybatis.xml SQL文件
	 */
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
		logger.info("ok!!!");
	}
}

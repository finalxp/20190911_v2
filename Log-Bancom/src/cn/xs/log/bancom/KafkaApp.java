package cn.xs.log.bancom;

import org.slf4j.LoggerFactory;


public class KafkaApp {
	
	
	private final static org.slf4j.Logger logger = LoggerFactory.getLogger(KafkaApp.class);
	
	public static void main(String[] args) {
		NodeLogger.getInstance().log("log test1");
		NodeLogger.getInstance().log("log test2");
		NodeLogger.getInstance().log("log test3");
		NodeLogger.getInstance().log("log test4");
		

	}
}

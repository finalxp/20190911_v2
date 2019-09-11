package cn.xs.log.bancom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NodeLogger {

	private Logger logger = LoggerFactory.getLogger(NodeLogger.class);
	private final static NodeLogger instance = new NodeLogger();

	private NodeLogger() {
	}

	public static NodeLogger getInstance() {
		return instance;
	}

	public void log(String lg) {
		this.logger.info(lg);
	}

	public void logWarn(String lg) {
		this.logger.warn(lg);
	}

	public void logError(String lg) {
		this.logger.error(lg);
	}

	public void logError(Exception e) {
		this.logger.error("NodeLogger", e);
	}

	public void logError(Throwable e) {
		this.logger.error("NodeLogger", e);
	}
}
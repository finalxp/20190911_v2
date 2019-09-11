package cn.xs.erp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EngineConfig {

	public static String resPath;
	public static String engineMode;

	public String getResPath() {
		return resPath;
	}

	@Value("${engine.resPath}")
	public void setResPath(String resPath) {
		EngineConfig.resPath = resPath;
	}

	public String getEngineMode() {
		return engineMode;
	}

	@Value("${engine.engineMode}")
	public void setEngineMode(String engineMode) {
		EngineConfig.engineMode = engineMode;
	}

}

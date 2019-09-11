package cn.productivetech.cmos.zhongbao.config;

import static org.mybatis.generator.internal.util.messages.Messages.getString;

import java.io.File;

import org.mybatis.generator.exception.ShellException;
import org.mybatis.generator.internal.DefaultShellCallback;

/**
 * 集成MyBatis提供生成代码的工具DefaultShellCallback，实现自动生成model，mybatis.xml文件工具类
 * @author kenny_peng
 * @created 2019-04-12
 */
public class ShellCallbackEx extends DefaultShellCallback {

	public ShellCallbackEx(boolean overwrite) {
		super(overwrite);
	}

	@Override
	public File getDirectory(String targetProject, String targetPackage) throws ShellException {

		File project = new File(targetProject);
		if (!project.isDirectory()) {
			throw new ShellException(getString("Warning.9", //$NON-NLS-1$
					targetProject));
		}
		return project;
	}

}

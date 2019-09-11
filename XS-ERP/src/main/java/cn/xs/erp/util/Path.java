package cn.xs.erp.util;

import java.io.File;

public class Path {

	public static String getFileNameWithoutExtension(String filePath) {

		if (filePath.isEmpty())
			return filePath;
		String fileName = getFileName(filePath);
		int idx = fileName.lastIndexOf('.');
		if (idx >= 0) {
			fileName = fileName.substring(0, idx);
		}
		return fileName;
	}

	public static String getFileExt(String filePath) {

		if (filePath.isEmpty())
			return filePath;
		String fileName = getFileName(filePath);
		int idx = fileName.lastIndexOf('.');
		if (idx >= 0) {
			fileName = fileName.substring(idx);
		}
		return fileName;
	}

	public static String getFileName(String filePath) {

		if (filePath.isEmpty())
			return filePath;
		String fileName = filePath;
		int idx = filePath.lastIndexOf(File.separatorChar);
		if (idx >= 0) {
			fileName = filePath.substring(idx + 1);
		}
		return fileName;
	}
}

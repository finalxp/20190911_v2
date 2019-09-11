package text1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author 作者 Andy.xu(许心宇)
 * 
 * @version 创建时间：2019年5月28日 下午2:47:41
 * 
 *          类说明
 */
public class fileutils {
	public static void copy(File sourceFile, File targetDir) {
		System.out.println("copying " + sourceFile);
		if (!targetDir.exists()) {
			targetDir.mkdir();
		}
		try {
			FileInputStream fis = new FileInputStream(sourceFile);
			FileOutputStream fos = new FileOutputStream(new File(targetDir,
					sourceFile.getName()));
			byte[] buf = new byte[102400];
			int available = 0;
			while ((available = fis.available()) > buf.length) {
				fis.read(buf);
				fos.write(buf);
			}
			fis.read(buf, 0, available);
			fos.write(buf, 0, available);
			fis.close();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	//
	// public static void copy2(String srcPath,String path){
	//
	// byte[] data = AudioUtils.readFile(srcPath);
	// AudioUtils.saveBinaryFile(path, data);
	// }

	public static String getFileName(String path) {
		int index = path.lastIndexOf("\\");
		if (index >= 0)
			return path.substring(index + 1);
		return path;
	}

	public static String getFileNameWithOutExt(String path) {
		int index = path.lastIndexOf("\\");
		if (index >= 0)
			path = path.substring(index + 1);
		int index2 = path.lastIndexOf(".");
		if (index2 > 0) {
			path = path.substring(0, index2);
		}
		return path;
	}

	public static List<String> getFiles(String path, boolean includeSubPath) {
		List<String> fileList = new ArrayList<>();
		listFiles(path, fileList, includeSubPath);
		return fileList;
	}

	public static List<String> getFiles(String Path) {
		return getFiles(Path, false);
	}

	private static void listFiles(String path, List<String> fileList,
			boolean includeSubPath) {
		File file = new File(path);
		if (!file.isDirectory()) {
			fileList.add(path);
			return;
		}

		File[] files = file.listFiles();
		if (files.length == 0)
			return;

		for (File f : files) {
			if (!f.isDirectory()) {
				fileList.add(f.getAbsolutePath());
			} else {
				if (includeSubPath) {
					listFiles(f.getAbsolutePath(), fileList, includeSubPath);
				}
			}
		}
	}

}

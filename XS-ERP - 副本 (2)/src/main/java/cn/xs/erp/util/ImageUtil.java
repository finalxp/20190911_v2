package cn.xs.erp.util;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import org.springframework.web.multipart.MultipartFile;

public class ImageUtil {
	public static String saveImage(String dir, String type, MultipartFile imageFile) {
		String targetFile = dir + "/upload/" + type;
		File saveDir = new File(targetFile);
		if (!saveDir.exists())
			saveDir.mkdirs();

		String fileName = imageFile.getOriginalFilename();// 获取文件名加后缀
		String ext = Path.getFileExt(fileName);//获取文件名后缀
		String newFileName = System.currentTimeMillis() + new Random().nextInt(1000) + ext;
		String filePath = targetFile + "/" + newFileName;
		try {
			imageFile.transferTo(new File(filePath));
		} catch (IllegalStateException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
		return "/upload/" + type + "/" + newFileName;
	}
}

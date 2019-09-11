package com.validsoft.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
	
	public static void copy(String srcPath,String path){
		
		byte[] data = AudioUtils.readFile(srcPath);
		AudioUtils.saveBinaryFile(path, data);
	}

	public static String getFileName(String path) {
		int index = path.lastIndexOf("\\");
		if (index >= 0)
			return path.substring(index + 1);
		return path;
	}

	public static String getFileNameWithOutExt(String path) {
		int index = path.lastIndexOf("\\");
		if (index >= 0)
			path= path.substring(index + 1);
		int index2 = path.lastIndexOf(".");
		if(index2 > 0){
			path = path.substring(0,index2);
		}
		return path;
	}
	
	public static List<String> getFiles(String path, boolean includeSubPath) {
		List<String> fileList = new ArrayList<>();
		listFiles(path,fileList,includeSubPath);
		return fileList;
	}
	
	public static List<String> getFiles(String Path) {
		return getFiles(Path, false);
	}
	
	private static void listFiles(String path, List<String> fileList, boolean includeSubPath){
		File file = new File(path);
		if(!file.isDirectory()){
			fileList.add(path);
			return;
		}
		
		File[] files = file.listFiles();
		if(files.length == 0)
			return;
		
		for(File f : files) {
			if(!f.isDirectory()){
				fileList.add(f.getAbsolutePath());
			}else {
				if (includeSubPath) {
					listFiles(f.getAbsolutePath(), fileList, includeSubPath);
				}
			}
		}	
	}	
}

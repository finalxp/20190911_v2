/*package cn.productivetech.cmos.zhongbao.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;

import redis.clients.jedis.Jedis;

*//**
 * 语音接续文件夹监听工具类，
 * 监听语音接续文件夹文件的变化
 * @author: kenny_peng
 * @created:2019-04-09
 *//*

public class FileListenerUtils {

	//语音接续文件夹路径
	private String filePath;
	
	//redis host地址
	private final String REDITS_IP = "192.168.18.154";
	
	//redis端口
	private final int REDITS_PORE = 6379;
	
	//存放已读文件
//	private static Map<String, File> map = new HashMap<String, File>();
	Jedis jedisMap = new Jedis(REDITS_IP, REDITS_PORE);
	
	*//**
	 * 构造方法
	 *//*
	public FileListenerUtils(String filePath){
		this.filePath= filePath;
	}
	
	*//**
	 * 文件监听
	 *//*
	public void fileMonitor(){
		
		//获取语音接续文件夹所有文件
		File[] files = getFiles(filePath,null);
		
		//获取缓存中以及存在的所有文件名集合
		List<String> existFileNames = jedisMap.lrange("fileNames", 0, -1);
		
		if (files != null && files.length>0) {
			String fName = "";//存放缓存文件名字
			
			//如果缓存中文件与读取的个数不一样时
			if (files.length != existFileNames.size()) {
				
				//当existFileNames大小为0说明缓存中没有文件
				if (existFileNames.size() == 0) {
					
					//为缓存中添加已有文件对象
					for (File file : files) {
						addFileNameToCache(file);
						
						
						 * 新增了txt，在此解析txt文件获取坐席ID和音频存放路径
						 
						
					}//End for
				}else {
					
					//如果文件减少，删除缓存中对应的文件名
					if (existFileNames.size() > files.length) {
						List<String> removeFileList = new ArrayList<String>();		//要删除的文件名集合
						Iterator<String> iterator = existFileNames.iterator();		//遍历缓存中的文件名
						
						while (iterator.hasNext()) {
							boolean isRemove = true;
							String inCacheFileName = iterator.next();
							if (inCacheFileName != null && inCacheFileName.length() > 0) {
								for (File file : files) {
									fName = file.getName();
									if (fName.equals(inCacheFileName)) {
										isRemove=false;
									}//End if
								}//End for
								if (isRemove) {
									removeFileList.add(inCacheFileName);
								}//End if
							}//End if
						}//End while
						
						//删除缓存中删除的文件名
						removeDeleteFileNameInCache(removeFileList);
					}else {
						
						//当缓存中有文件对象时，判断缓存文件跟读取文件是否相同
						for (File file : files) {
							fName = file.getName().trim();
							boolean isCacheContainsFile = existFileNames.contains(fName);
							if (!isCacheContainsFile) {
								addFileNameToCache(file);
								
								
								 * 新增了txt，在此解析txt文件获取坐席ID和音频存放路径
								 
								
							}//End if
						}//End for

public class FileListenerUtils extends FileAlterationListenerAdaptor{   
	    @Override
	    public void onFileCreate(File file) {
	    	
	    	//添加问件时，判断文件是否可读（即判断文件是否传输完毕），可读时，发送解析txt请求
	    	if (Files.isReadable(file.toPath())) {//Files.isReadable(file.toPath())
	    		String param = "path=" + file.getAbsolutePath().replace("\\", "//");
	    		
	    		//发送url解析txt请求
	    		SendUrlUtils.sendPost("http://192.168.18.154:8080/processTxt", param);
	    		System.out.println("[新建]:" + file.getAbsolutePath());
			}
	    }
	    
	    @Override
	    public void onFileChange(File file) {
	    	
	    	//传输完毕后，会触发文件被修改，此事判断文件是否可读（即判断文件是否传输完毕），可读时，发送解析txt请求
	    	if (Files.isReadable(file.toPath())) {
	    		System.out.println("[修改]:" + file.getAbsolutePath());
	    	}
	    }
	    
	    *//**
	     * 判断文件是否可读写
	     * @param file
	     * @return
	     *//*
		@SuppressWarnings({ "static-access", "resource", "unused" })
		private boolean isFileCanReadAndWrite(File file){
	    	try {
				RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
				FileChannel fileChannel = randomAccessFile.getChannel();
				FileLock fileLock = null;
				while (true) {
					try {
						fileLock = fileChannel.tryLock();
						break;
					} catch (Exception e) {
						try {
							Thread.currentThread().sleep(1000);
						} catch (InterruptedException e1) {
							return false;
						}

					}
				}
				return true;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return false;
			}
	    }
	    
	}*/
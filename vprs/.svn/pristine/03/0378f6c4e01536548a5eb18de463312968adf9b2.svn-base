package com.pccc.vprs.servicecustom.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.FileInfo;
import org.csource.fastdfs.ProtoCommon;
import org.csource.fastdfs.StorageClient;
import org.slf4j.LoggerFactory;

import com.pccc.touda.common.util.ConfigUtils;

public class FastDFSClientAlone {
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(FastDFSClientAlone.class);
//	private static TrackerClient trackerClient;
//	private static TrackerServer trackerServer;
//	private static StorageClient storageClient;
//	private static StorageServer storageServer;
	public final static String SPLIT="/";
	public final static String FILE_NAME="fileName";

	static {
		try {
			ClientGlobal.initByProperties(ConfigUtils.getProperties());
		} catch (Exception e) {
			logger.error("FastDFS Client Init Fail!",e);
		}
	}
	
	
	private static StorageClient getStorageClient(String groupName) throws IOException{
//		TrackerClient	trackerClient = new TrackerClient();
//		TrackerServer   trackerServer = trackerClient.getConnection();
//		StorageServer storageServer = null;
//		if(StringUtils.isEmpty(groupName)){
//			 storageServer = trackerClient.getStoreStorage(trackerServer);
//		}else{
//			 storageServer = trackerClient.getStoreStorage(trackerServer,groupName);
//		}
		return new StorageClient(null, null);
	}
	
	/**
	 * 上传文件
	 * @param fileName 文件名 
	 * @param file_buff 字节数组
	 * @param ext  扩展名,不填将取 fileName的.后面的内容,但长度不超过6个字符
	 * @param metaInfo  元数据信息，如果文件大小，长宽等信息
	 * @return
	 */
	public static String upload(String groupName,String fileName,byte[] file_buff,String ext,Map<String, String> metaInfo) {
		logger.info("File Name: " + fileName + " File Length:" + file_buff.length+" ext:"+ext);
		
		if(StringUtils.isEmpty(groupName)){
			logger.error("uploading the groupName is empty and return ");
			return null;
		}
		
		if(StringUtils.isEmpty(fileName)){
			logger.error("uploading the fileName is empty and return ");
			return null;
		}
		
		ext=getExtName(fileName, ext);
		/**写入元数据信息**/
		NameValuePair[] meta_list=null;
		List<NameValuePair> names=new ArrayList<NameValuePair>();
		NameValuePair fileNameValue=new NameValuePair(FILE_NAME,fileName);//文件名
		
		if(null!=metaInfo&&metaInfo.size()>0){
			Set<Map.Entry<String, String>> sets=metaInfo.entrySet();
			Iterator<Map.Entry<String, String>> ob=sets.iterator();
			while(ob.hasNext()){
				Entry<String, String> entry=ob.next();
				String key=entry.getKey();
				String value=entry.getValue();
				NameValuePair temp=new NameValuePair(key,value);
				names.add(temp);
			}
			names.add(fileNameValue);
		}
		meta_list=names.toArray(new NameValuePair[names.size()]);
		
		long startTime = System.currentTimeMillis();
		String[] uploadResults = null;
		try {
			StorageClient storageClient =getStorageClient(groupName);
			
			uploadResults =storageClient.upload_file(groupName, file_buff, ext, meta_list);
//			uploadResults = storageClient.upload_file(file_buff, ext, meta_list);
		
			if (uploadResults == null) {
				logger.error("upload file fail, error code:" + storageClient.getErrorCode());
			}
			
		} catch (IOException e) {
			logger.error("IO Exception when uploadind the file:" + fileName, e);
		} catch (Exception e) {
			logger.error("Non IO Exception when uploadind the file:" + fileName, e);
		}
		logger.info("upload_file time used:" + (System.currentTimeMillis() - startTime) + " ms");

		//返回组名
		String groupNameReslut = uploadResults[0];
		//返回文件名
		String remoteFileName = uploadResults[1];

		logger.info("upload file successfully!!!" + "group_name:" + groupNameReslut + ", remoteFileName:" + " " + remoteFileName);
		StringBuffer sb=new StringBuffer("");
		sb.append(groupNameReslut).append(SPLIT).append(remoteFileName);
		
		return sb.toString();
	}

	
	
	/**
	 * 获取FastDFS的文件信息
	 * @param remoteFileName
	 * @return
	 */
	public static FileInfo getFile(String remoteFileName) {
		try {
			String[] remote=getFileDetail(remoteFileName);
			StorageClient storageClient =getStorageClient("");
			
			FileInfo result=storageClient.get_file_info(remote[0], remote[1]);
			return result;
		} catch (IOException e) {
			logger.error("IO Exception: Get File from Fast DFS failed", e);
		} catch (Exception e) {
			logger.error("Non IO Exception: Get File from Fast DFS failed", e);
		}
		return null;
	}

	
	
	/**
	 * 下载并写到本地文件
	 * @param localFileName 本地的绝对路径
	 * @param remoteFileName
	 * @throws IOException
	 */
	public static void downloadAndWriteLocal(String localFileName,String remoteFileName) throws IOException {
		if(StringUtils.isEmpty(localFileName)) throw new RuntimeException("LocalFileName cant not empty");
		byte[]  input=download(remoteFileName);
		
		if(input==null || (input.length == 0)){
			logger.warn("Get File from Fast DFS failed,may be deleted and please check fileName="+remoteFileName);
			return ;
		}
		FileUtils.writeByteArrayToFile(new File(localFileName), input);
	}
	
	
	/**
	 * 下载文件
	 * @param remoteFileName
	 * @return
	 */
	public static byte[] download(String remoteFileName) {
		try {
			String[] remote=getFileDetail(remoteFileName);
			StorageClient storageClient =getStorageClient("");
			byte[] fileByte = storageClient.download_file(remote[0], remote[1]);
			return fileByte;
		} catch (IOException e) {
			logger.error("IO Exception: Get File from Fast DFS failed", e);
		} catch (Exception e) {
			logger.error("Non IO Exception: Get File from Fast DFS failed", e);
		}
		return null;
	}

	
	
	/**
	 * 删除文件
	 * @param remoteFileName
	 * @return
	 * @throws Exception
	 */
	public static boolean deleteFile(String remoteFileName)throws Exception {
		String[] remote=getFileDetail(remoteFileName);
		StorageClient storageClient =getStorageClient("");
		int i = storageClient.delete_file(remote[0], remote[1]);
		boolean result=i==0?true:false;
		logger.info("delete file result=" + result);
		return  result;
	}

	
	/**
	 * 获取文件的元数据信息
	 * 例子：MetaInfo ={author=kermit, fileName=1.png}
	 * @param remoteFileName
	 * @return
	 * @throws Exception
	 */
	public static Map<String,String> getMetaInfo(String remoteFileName)throws Exception {
		String[] remote=getFileDetail(remoteFileName);
		StorageClient storageClient =getStorageClient("");
		NameValuePair[]  pairs = storageClient.get_metadata(remote[0], remote[1]);
		Map<String,String> result=new HashMap<String,String>();

		if(pairs!=null && pairs.length>0){
			for(NameValuePair pair:pairs){
				result.put(pair.getName(), pair.getValue());
			}
		}
		logger.info("remoteFileName ="+remoteFileName+" MetaInfo =" + result);
		return  result;
	}
	
	
	/**
	 * 拆分远程路径
	 * @param remoteFileName
	 * @return
	 */
	private static String[] getFileDetail(String remoteFileName){
		if(StringUtils.isEmpty(remoteFileName)) throw new RuntimeException("RemoteFileName cant not empty");
		int beginIndex=remoteFileName.indexOf(SPLIT);
		String group=remoteFileName.substring(0,beginIndex);
		String remote=remoteFileName.substring(beginIndex+1,remoteFileName.length());
		return new String[]{group,remote};
	}

	
	/**
	 * 扩展名为空,从fileName再检查一遍扩展名
	 * @param fileName
	 * @param file_ext_name
	 * @return
	 */
	private static String getExtName(String fileName, String file_ext_name) {
		String result = file_ext_name;
		
		if (StringUtils.isEmpty(file_ext_name)) {
			int nPos = fileName.lastIndexOf('.');
			if (nPos > 0 && fileName.length() - nPos <= ProtoCommon.FDFS_FILE_EXT_NAME_MAX_LEN + 1) {
				result = fileName.substring(nPos + 1);
			}
		}
		return result;
	}
	
}
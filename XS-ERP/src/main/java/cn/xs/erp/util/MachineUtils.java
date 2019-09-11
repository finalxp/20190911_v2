package cn.xs.erp.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Properties;

public class MachineUtils {
	public static String getLoginIP(){
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String getClientOS(){
		Properties props=System.getProperties(); //获得系统属性集    
		String osName = props.getProperty("os.name"); //操作系统名称    
		String osArch = props.getProperty("os.arch"); //操作系统构架    
		String osVersion = props.getProperty("os.version"); //操作系统版本 
		
		return "系统名称:"+osName.replace(" ", "") + ",系统构架:" +osArch +",系统版本:"+osVersion;
	}
	
	
	public static String getClientDevice() throws SocketException, UnknownHostException {
		InetAddress ia=InetAddress.getLocalHost();
		//获取网卡，获取地址
		byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
		StringBuffer sb = new StringBuffer("");
		for(int i=0; i<mac.length; i++) {
			if(i!=0) {
				sb.append("-");
			}
			//字节转换为整数
			int temp = mac[i]&0xff;
			String str = Integer.toHexString(temp);
			if(str.length()==1) {
				sb.append("0"+str);
			}else {
				sb.append(str);
			}
		}
		String computerName =InetAddress.getLocalHost().getHostName();
		return "设备名:"+computerName+ ",Mac地址:" +sb.toString().toUpperCase();
	}
	
}

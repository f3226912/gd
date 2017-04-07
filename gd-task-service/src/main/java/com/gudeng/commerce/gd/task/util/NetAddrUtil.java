package com.gudeng.commerce.gd.task.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * @Description 计算机网卡工具
 * @Project gd-task-service
 * @ClassName NetAddrUtil.java
 * @Author lidong(dli@gdeng.cn)
 * @CreationDate 2015年12月25日 上午10:19:36
 * @Version V2.0
 * @Copyright 谷登科技 2015-2015
 * @ModificationHistory
 */
public class NetAddrUtil {

	/**
	 * @Description 判断系统是否是window
	 * @return
	 * @CreationDate 2015年12月25日 上午10:19:34
	 * @Author lidong(dli@gdeng.cn)
	 */
	public static boolean isWindowsOS() {
		boolean isWindowsOS = false;
		String osName = System.getProperty("os.name");
		if (osName.toLowerCase().indexOf("windows") > -1) {
			isWindowsOS = true;
		}
		return isWindowsOS;
	}

	/**
	 * @Description 获取本机IP
	 * @return
	 * @CreationDate 2015年12月25日 上午10:20:09
	 * @Author lidong(dli@gdeng.cn)
	 */
	public static String getLocalIP() {
		String sIP = "";
		InetAddress ip = null;
		try {
			// 如果是Windows操作系统
			if (isWindowsOS()) {
				ip = InetAddress.getLocalHost();
			}
			// 如果是Linux操作系统
			else {
				boolean bFindIP = false;
				Enumeration<NetworkInterface> netInterfaces = (Enumeration<NetworkInterface>) NetworkInterface.getNetworkInterfaces();
				while (netInterfaces.hasMoreElements()) {
					if (bFindIP) {
						break;
					}
					NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
					// ----------特定情况，可以考虑用ni.getName判断
					// 遍历所有ip
					Enumeration<InetAddress> ips = ni.getInetAddresses();
					while (ips.hasMoreElements()) {
						ip = (InetAddress) ips.nextElement();
						if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress() // 127.开头的都是lookback地址
								&& ip.getHostAddress().indexOf(":") == -1) {
							bFindIP = true;
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (null != ip) {
			sIP = ip.getHostAddress();
		}
		return sIP;
	}

	public static void main(String[] args) {
		System.out.println(isWindowsOS());
		System.out.println(getLocalIP());
	}

}

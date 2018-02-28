package com.yunwenlong.utils;

import java.io.Serializable;
import java.net.InetAddress;

/** 
*
* Description: 
* UUID生成主键
* @version 1.0
* <pre>
* Modification History: 
* Date         Author      Version     Description 
* ------------------------------------------------------------------ 
* 2013-10-21    Administrator       1.0        1.0 Version 
* </pre>
*/
public class UUIDGenerator {

	/**
	 * 类变量：IP地址
	 */
	private static final int IP;

	/**
	 * ip地址转换成INT类型
	 * @param bytes bytes数组
	 * @return int类型ip地址
	 */
	public static int iptoInt(byte[] bytes) {
		int result = 0;
		for (int i = 0; i < 4; i++) {
			result = (result << 8) - Byte.MIN_VALUE + (int) bytes[i];
		}
		return result;
	}

	/**
	 * 将ip址转换成INT类型
	 */
	static {
		int ipadd;
		try {
			ipadd = iptoInt(InetAddress.getLocalHost().getAddress());
		} catch (Exception e) {
			ipadd = 0;
		}
		IP = ipadd;
	}
	
	/**
	 * 类变量：counter
	 */
	private static short counter = (short) 0;
	
	/**
	 * 类变量：JVM
	 */
	private static final int JVM = (int) (System.currentTimeMillis() >>> 8);

	/**
	 * 实例化GcsSysUUIDGenerator对象
	 */
	public UUIDGenerator() {
	}

	/**
	 * Unique across JVMs on this machine (unless they load this class in the
	 * same quater second - very unlikely)
	 */
	protected int getJVM() {
		return JVM;
	}

	/**
	 * Unique in a millisecond for this JVM instance (unless there are >
	 * Short.MAX_VALUE instances created in a millisecond)
	 */
	protected short getCount() {
		synchronized (UUIDGenerator.class) {
			if (counter < 0)
				counter = 0;
			return counter++;
		}
	}

	/**
	 * Unique in a local network
	 */
	protected int getIP() {
		return IP;
	}

	/**
	 * Unique down to millisecond
	 */
	protected short getHiTime() {
		return (short) (System.currentTimeMillis() >>> 32);
	}

	/**
	 * 获得Int类型当前时间
	 * @return
	 */
	protected int getLoTime() {
		return (int) System.currentTimeMillis();
	}

	/**
	 * 类变量：JVM
	 */
	private final static String sep = "";

	/**
	 * 格式化数字<br>
	 * 数字格式化成前面补0的8位字符串
	 * @param intval 数值
	 * @return 格式化后的字符串
	 */
	protected String format(int intval) {
		String formatted = Integer.toHexString(intval);
		StringBuffer buf = new StringBuffer("00000000");
		buf.replace(8 - formatted.length(), 8, formatted);
		return buf.toString();
	}

	/**
	 * 格式化short<br>
	 * short格式化成前面补0的4位字符串
	 * @param intval 数值
	 * @return 格式化后的字符串
	 */
	protected String format(short shortval) {
		String formatted = Integer.toHexString(shortval);
		StringBuffer buf = new StringBuffer("0000");
		buf.replace(4 - formatted.length(), 4, formatted);
		return buf.toString();
	}

	/**
	 * 获取32位唯一UUID<br>
	 * @return UUID
	 */
	public Serializable generate() {
		return new StringBuffer(36).append(format(getIP())).append(sep).append(
				format(getJVM())).append(sep).append(format(getHiTime()))
				.append(sep).append(format(getLoTime())).append(sep).append(
						format(getCount())).toString();
	}

	public static void main(String args[]) {
		// System.out.println(UUIDGenerator.IP);
		UUIDGenerator aa = new UUIDGenerator();
		//String tcr0_uuid = (String) aa.generate();
		for(int i=0;i<420;i++){
		    System.out.println(aa.generate());
		}
	}
}

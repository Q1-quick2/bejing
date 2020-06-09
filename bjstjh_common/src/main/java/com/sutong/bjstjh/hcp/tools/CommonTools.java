package com.sutong.bjstjh.hcp.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.UUID;

public class CommonTools {

	/**
	 * 得到一个唯一序列号
	 * 
	 * @return
	 */
	public static String getGUID() {
		UUID guid = UUID.randomUUID();
		;
		return guid.toString();
	}

	/**
	 * gb成byte
	 * 
	 * @param gb
	 * @return
	 */
	public static long GB2Byte(long gb) {
		return gb * 1024 * 1024 * 1024;
	}

	/**
	 * byte 2 gb
	 * 
	 * @param b
	 * @return
	 */
	public static long Byte2Gb(long b) {
		return b / (1024 * 1024 * 1024);
	}

	/**
	 * //转换文件大小
	 * 
	 * @param fileS
	 * @return
	 */
	public static String GetFileSize(long fileS) {
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString = "";
		if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "K";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "M";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "G";
		}
		return fileSizeString;
	}

	/**
	 * 将输入流转成字符串
	 * 
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static String inputStream2Str(InputStream is) throws IOException {
		BufferedReader rd = new BufferedReader(new InputStreamReader(is));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
			// System.out.println(line);
		}
		String res = result.toString();

		res = new String(res.getBytes(), "utf-8");
		// System.out.print(res);
		return res;
	}

	/**
	 * 判断是否是整型
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isInt(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * 字符串是不是长整型
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isLong(String str) {
		try {

			Long.parseLong(str);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	/**
	 * 字符串，转长整型
	 * 
	 * @param l
	 * @return
	 */
	public static long string2Long(String str) {
		if (isLong(str) == false)
			return 0;
		else
			return Long.parseLong(str);
	}
}

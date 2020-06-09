package com.sutong.bjstjh.hcp.tools;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;

/**
 * Created by japark on 1/26/15.
 */
public class Utils {

	/**
	 * 生成guid
	 * 
	 * @return
	 */
	public static String getGUID() {
		UUID guid = UUID.randomUUID();
		return guid.toString();
	}

	/**
	 * 将输入流转成字符串
	 * 
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static String convertIs2Str(InputStream is) throws IOException {
		BufferedReader rd = new BufferedReader(new InputStreamReader(is));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
			// System.out.println(line);
		}

		return result.toString();
	}

	/**
	 * 文件转成输入流
	 * 
	 * @param txt
	 * @return
	 */
	public static InputStream convertText2InputStream(String txt) {
		InputStream is = new ByteArrayInputStream(txt.getBytes());
		return is;
	}

	/**
	 * 得到认证信息 帐号密码要加到配置文件中
	 * 
	 * @return
	 * 
	 * 		public static String GetAuth() { //从配置文件中读取帐号信息 //用户名转base64 String userName =
	 *         getBase64Value("netskill"); //密码转md5 String pwd =getMD5Value("libin19800818"); return
	 *         "HCP "+userName+":"+pwd; }
	 */

	/**
	 * 字符到base64
	 * 
	 * @param input
	 * @return
	 */
	public static String getBase64Value(String input) {
		Base64.Encoder encoder = Base64.getEncoder();
		String encodedString = encoder.encodeToString(input.getBytes(StandardCharsets.UTF_8));
		return encodedString;
	}

	/**
	 * 字符到md5
	 * 
	 * @param input
	 * @return
	 */
	public static String getMD5Value(String input) {
		String md5 = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(input.getBytes());
			byte[] digest = md.digest();
			StringBuffer sb = new StringBuffer();
			for (byte b : digest) {
				sb.append(String.format("%02x", b & 0xff));
			}

			md5 = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			System.err.println("I'm sorry, but MD5 is not a valid message digest algorithm");
		}
		return md5;
	}
}

package com.sutong.bjstjh.hcp.tools;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class HCPTools {

	/**
	 * HCP用户帐号编码
	 * 
	 * @param namespaceUsername
	 * @param namespacePwd
	 * @return
	 */
	public static String GetAuth(String namespaceUsername, String namespacePwd) {
		// 从配置文件中读取帐号信息
		// 用户名转base64
		String userName = getBase64Value(namespaceUsername);
		// 密码转md5
		String pwd = getMD5Value(namespacePwd);
		return "HCP " + userName + ":" + pwd;
	}

	public static String GetAuthS3(String namespaceUsername, String namespacePwd) {
		// 从配置文件中读取帐号信息
		// 用户名转base64
		String userName = getBase64Value(namespaceUsername);
		// 密码转md5
		String pwd = getMD5Value(namespacePwd);
		return "AWS " + userName + ":" + pwd;
	}

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

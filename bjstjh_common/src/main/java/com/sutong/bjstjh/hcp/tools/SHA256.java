package com.sutong.bjstjh.hcp.tools;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

public class SHA256 {

	public static void main(String[] args) throws IOException, NoSuchAlgorithmException {

		/**
		 * 方式一：一次读入内存，适用于小文件 File file = new File("C:\\test\\icbc.rar"); byte[] data = new byte[ (int)
		 * file.length()]; FileInputStream fis = new FileInputStream(file); fis.read(data); byte[] byteHash
		 * = hash(data);
		 * 
		 * String sha256Hash = toHex(byteHash); System.out.println(sha256Hash.toUpperCase());
		 */

		/**
		 * 方式二：读取流，适用于大文件
		 * 
		 * byte[] byteHash = hash("C:\\test\\icbc.rar"); String sha256Hash = toHex(byteHash);
		 * System.out.println(sha256Hash.toUpperCase());
		 */

		/**
		 * 方式三：读取流，适用于大文件，更简单
		 */
		byte[] byteHash = hashV2("C:\\VDisk\\Virtual Machines\\Windows7SP1x64.zip");
		String sha256Hash = toHex(byteHash);
		System.out.println(sha256Hash.toUpperCase());

	}

	public static byte[] hashV2(String filePath) throws IOException, NoSuchAlgorithmException {
		File file = new File(filePath);
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));

		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		int bufferSize = 16384;
		byte[] buffer = new byte[bufferSize];
		int sizeRead = -1;
		while ((sizeRead = in.read(buffer)) != -1) {
			digest.update(buffer, 0, sizeRead);
		}
		in.close();

		byte[] hash = null;
		hash = new byte[digest.getDigestLength()];
		hash = digest.digest();
		return hash;
	}

	/**
	 * 大文件计算方法
	 * 
	 * @param filePath
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public static byte[] hash(String filePath) throws NoSuchAlgorithmException, IOException {
		RandomAccessFile file = new RandomAccessFile(filePath, "r");
		MessageDigest hashSum = MessageDigest.getInstance("SHA-256");
		int buff = 16384;
		byte[] buffer = new byte[buff];
		byte[] partialHash = null;

		long read = 0;

		// calculate the hash of the hole file for the test
		long offset = file.length();
		int unitsize;
		while (read < offset) {
			unitsize = (int) (((offset - read) >= buff) ? buff : (offset - read));
			file.read(buffer, 0, unitsize);

			hashSum.update(buffer, 0, unitsize);

			read += unitsize;
		}

		file.close();
		partialHash = new byte[hashSum.getDigestLength()];
		partialHash = hashSum.digest();

		return partialHash;
	}

	/**
	 * 小文件计算方法
	 * 
	 * @param data
	 * @return
	 */
	public static byte[] hash(byte[] data) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");

			md.update(data);
			return md.digest();
		} catch (Exception e) {
			throw new RuntimeException("Unable to compute hash while signing request: " + e.getMessage(), e);
		}
	}

	public static String toHex(byte[] data) {
		StringBuilder sb = new StringBuilder(data.length * 2);
		for (int i = 0; i < data.length; i++) {
			String hex = Integer.toHexString(data[i]);
			if (hex.length() == 1) {
				// Append leading zero.
				sb.append("0");
			} else if (hex.length() == 8) {
				// Remove ff prefix from negative numbers.
				hex = hex.substring(6);
			}
			sb.append(hex);
		}
		return sb.toString().toLowerCase(Locale.getDefault());
	}

}

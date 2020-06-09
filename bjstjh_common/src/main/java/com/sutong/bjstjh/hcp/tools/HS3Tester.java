package com.sutong.bjstjh.hcp.tools;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.HeadBucketRequest;
import com.amazonaws.services.s3.model.HeadBucketResult;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class HS3Tester {

	public static String getBase64Value(String input) {
		Base64.Encoder encoder = Base64.getEncoder();
		String encodedString = encoder.encodeToString(input.getBytes(StandardCharsets.UTF_8));
		return encodedString;
	}

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

	public static void main(String[] args) throws UnsupportedEncodingException {

		String username = getBase64Value("admin");
		String userpwd = getMD5Value("P@ssw0rd");

		System.out.println("@= " + username + " " + userpwd);

		ClientConfiguration myClientConfig = new ClientConfiguration();
		myClientConfig.setProtocol(Protocol.HTTPS);
		// 设置使用V4签名
		myClientConfig.setSignerOverride("AWS4SignerType");
		AmazonS3 hs3Client = null;
		// 指定帐号，密码
		hs3Client = new AmazonS3Client(new BasicAWSCredentials(username, userpwd), myClientConfig);
		// hs3Client = new AmazonS3Client( new ProfileCredentialsProvider(),myClientConfig);

		// 指定endpoint
		hs3Client.setEndpoint("testaw.hcp1.pingan.lab:8000");
		// 指定桶
		com.amazonaws.services.s3.model.HeadBucketRequest req = new HeadBucketRequest("n1");
		HeadBucketResult res = hs3Client.headBucket(req);
		if (res != null)
			System.out.println("true");
	}
}

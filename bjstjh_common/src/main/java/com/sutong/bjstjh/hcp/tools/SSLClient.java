package com.sutong.bjstjh.hcp.tools;

import com.aliyun.oss.common.utils.BinaryUtil;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class SSLClient extends DefaultHttpClient {

	public static void main(String args[]) throws Exception {
		// HttpClient client = HttpClients.createDefault();
		SSLClient client = new SSLClient();
		HttpPost post = new HttpPost("https://test.ys7.com:8081/api/callback/picture");

		ArrayList<BasicNameValuePair> data = new ArrayList<BasicNameValuePair>();
		data.add(new BasicNameValuePair("bucket", "b11"));
		data.add(new BasicNameValuePair("size", "3751526"));
		data.add(new BasicNameValuePair("mimeType", "video/mpeg"));
		data.add(new BasicNameValuePair("object", "12435"));
		data.add(new BasicNameValuePair("devid", "66666666666"));
		data.add(new BasicNameValuePair("channel", "1"));
		data.add(new BasicNameValuePair("cloudType", "18"));
		data.add(new BasicNameValuePair("extjson", "eyJleHBpcmVBZnRlckRheXMiOjcsIm93bmVySWQiOiJ4Y2YifQ=="));
		data.add(new BasicNameValuePair("sid", "sid"));
		data.add(new BasicNameValuePair("crypt", "crypt"));
		data.add(new BasicNameValuePair("checksum", "checksum"));
		data.add(new BasicNameValuePair("createtime", ""));
		data.add(new BasicNameValuePair("fileSize", "filesize"));
		data.add(new BasicNameValuePair("tzoffset", "28800000"));

		HttpEntity entity = new UrlEncodedFormEntity(data, "UTF-8");

		post.setEntity(entity);

		// 进行回调鉴权
		Map<String, Object> keyMap = RSAUtils.genKeyPair();
		String publicKey = RSAUtils.getPublicKey(keyMap);
		String privateKey = RSAUtils.getPrivateKey(keyMap);

		StringBuilder sb = new StringBuilder();
		sb.append("/api/callback/picture");
		sb.append("\n");
		sb.append(
				"bucket=b11&size=3751526&mimeType=video/mpeg&object=12435&devid=66666666666&channel=1&cloudType=18&extjson=eyJleHBpcmVBZnRlckRheXMiOjcsIm93bmVySWQiOiJ4Y2YifQ==&sid=sid&crypt=crypt&checksum=checksum&createtime=&fileSize=filesize&tzoffset=28800000");
		System.out.println(sb.toString());
		// 进行签名
		String sign = RsaUtil.sign(sb.toString(), privateKey);

		post.addHeader("content-Type", "application/x-www-form-urlencoded");
		post.addHeader("authorization", sign);
		post.addHeader("x-oss-pub-key-url", publicKey);

		CloseableHttpResponse response = (CloseableHttpResponse) client.execute(post);
		System.out.println(response.getStatusLine().getStatusCode());

		// 签名验证
		// boolean bool = RsaUtil.verify(sb.toString(), sign, publicKey);
		// sb 是签名串
		// BinaryUtil.fromBase64String(sign) 将签名由字符串转成byte[]
		// publickey 是base64编码的，传到docheck()
		boolean bool = doCheck(sb.toString(), BinaryUtil.fromBase64String(sign), publicKey);

	}

	public static boolean doCheck(String content, byte[] sign, String publicKey) {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			byte[] encodedKey = BinaryUtil.fromBase64String(publicKey);
			PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
			Signature signature = Signature.getInstance("MD5withRSA");
			signature.initVerify(pubKey);
			signature.update(content.getBytes());
			boolean bverify = signature.verify(sign);
			return bverify;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	private static final String KEY_ALGORITHM = "RSA";
	private static final String PUBLIC_KEY = "publicKey";
	private static final String PRIVATE_KEY = "privateKey";

	public static Map<String, String> genKey() throws NoSuchAlgorithmException {
		Map<String, String> keyMap = new HashMap<String, String>();
		KeyPairGenerator keygen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
		SecureRandom random = new SecureRandom();
		// random.setSeed(keyInfo.getBytes());
		// 初始加密，512位已被破解，用1024位,最好用2048位
		keygen.initialize(1024, random);
		// 取得密钥对
		KeyPair kp = keygen.generateKeyPair();
		RSAPrivateKey privateKey = (RSAPrivateKey) kp.getPrivate();

		Base64.Encoder encoder = Base64.getEncoder();

		String privateKeyString = encoder.encodeToString(privateKey.getEncoded()); // Base64.encode(privateKey.getEncoded());
		RSAPublicKey publicKey = (RSAPublicKey) kp.getPublic();
		String publicKeyString = encoder.encodeToString(publicKey.getEncoded());

		keyMap.put(PUBLIC_KEY, publicKeyString);
		keyMap.put(PRIVATE_KEY, privateKeyString);
		return keyMap;
	}

	public static RSAPublicKey getPublicKey(String publicKey) throws Exception {
		byte[] keyBytes = Base64.getDecoder().decode(publicKey);
		X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		return (RSAPublicKey) keyFactory.generatePublic(spec);
	}

	public static RSAPrivateKey getPrivateKey(String privateKey) throws Exception {
		byte[] keyBytes = Base64.getDecoder().decode(privateKey);
		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		return (RSAPrivateKey) keyFactory.generatePrivate(spec);
	}

	public SSLClient() throws Exception {
		super();
		SSLContext ctx = SSLContext.getInstance("SSL");
		X509TrustManager tm = new X509TrustManager() {
			@Override
			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}

			@Override
			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}

			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};
		ctx.init(null, new TrustManager[] { tm }, null);
		SSLSocketFactory ssf = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		ClientConnectionManager ccm = this.getConnectionManager();
		SchemeRegistry sr = ccm.getSchemeRegistry();
		sr.register(new Scheme("https", 443, ssf));
	}
}

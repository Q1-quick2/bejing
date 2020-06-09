package com.sutong.bjstjh.hcp.tools;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.*;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.*;

public class SITVHCP {

	public SITVHCP() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String args[]) {
		String authStr = HCPTools.GetAuth("netskill", "hcp123");

		/*
		 * //测试检测对象 String hcpUrl="http://n1.t1.hcp-demo.hcpdemo.com/rest/t1/123.txt"; try { boolean flag
		 * =new SITVHCP().checkFileExists(hcpUrl, authStr); if(flag==true) System.out.println("对象存在"); else
		 * System.out.println("对象不存在"); } catch (IOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */

		/*
		 * //删除对象 String hcpUrl="http://n1.t1.hcp-demo.hcpdemo.com/rest/t1/1469586294717.zip"; try { boolean
		 * flag = new SITVHCP().deleteObject(hcpUrl, authStr); if(flag==true) System.out.println("删除对象成功");
		 * else System.out.println("删除对象失败"); } catch (IOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */

		/*
		 * //上传对象 String hcpUrl="http://n1.t1.hcp-demo.hcpdemo.com/rest/t1/changeFile12.xls"; String
		 * localFile ="C:/test/changeFile12.xls"; try { boolean flag = new SITVHCP().uploadObject(hcpUrl,
		 * authStr,localFile); if(flag==true) System.out.println("创建对象成功"); else
		 * System.out.println("创建对象失败"); } catch (IOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */

		// 读对 象
		String hcpUrl = "http://n1.t1.hcp-demo.hcpdemo.com/rest/t1/changeFile12.xls";
		String localFile = "C:/test/changeFile12_bak.xls";
		try {
			boolean flag = new SITVHCP().readObject(hcpUrl, authStr, localFile);
			if (flag == true)
				System.out.println("读对象成功");
			else
				System.out.println("读对象失败");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 得到对象的MD5值
	 * 
	 * @param hcpUrl
	 * @param authStr
	 * @return ERROR表示不正常；返回的其它值，请和本地文件MD5进行对比
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public String getMd5Code(String hcpUrl, String authStr) throws ClientProtocolException, IOException {
		String md5Code = "ERROR";
		CloseableHttpClient client = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		HttpHead head = new HttpHead(hcpUrl);
		head.addHeader("Connection", "close");
		head.addHeader("Authorization", authStr);

		response = client.execute(head);
		if (response.getStatusLine().getStatusCode() == 200) {
			Header[] headArray = response.getAllHeaders();
			for (Header h : headArray) {
				if (h.getName().trim().equals("X-HCP-Hash")) {
					// logger.info("读取MD5值 X-HCP-Hash:"+h.getValue());
					md5Code = h.getValue().substring(4);
					break;
				}
			}
		}

		EntityUtils.consume(response.getEntity());
		response.close();
		client.close();
		return md5Code;
	}

	/**
	 * 
	 * @param hcpUrl
	 *            对象url
	 * @param authStr
	 * @param localPath
	 *            要保存到本地的路径
	 * @return
	 * @throws IOException
	 */
	public boolean readObject(String hcpUrl, String authStr, String localPath) throws IOException {
		CloseableHttpClient client = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		OutputStream os = null;
		InputStream is = null;

		HttpGet request = new HttpGet(hcpUrl);
		request.addHeader("Connection", "close");
		request.addHeader("Authorization", authStr);
		try {
			response = client.execute(request);

			if (response.getStatusLine().getStatusCode() == 200) {
				is = response.getEntity().getContent();
				os = new FileOutputStream(localPath);

				byte[] b = new byte[1024];
				int f;
				while ((f = is.read(b)) != -1) {
					os.write(b, 0, f);
				}
				return true;
			} else
				return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
			if (os != null)
				os.close();
			if (is != null)
				is.close();
			EntityUtils.consume(response.getEntity());
			response.close();
			client.close();
		}

	}

	/**
	 * 上传对象
	 * 
	 * @param hcpUrl
	 * @param authStr
	 * @param localFile
	 *            本地文件路径
	 * @return
	 * @throws IOException
	 */
	public boolean uploadObject(String hcpUrl, String authStr, String localFile) throws IOException {
		InputStream is = new FileInputStream(localFile);
		CloseableHttpClient client = HttpClients.createDefault();
		CloseableHttpResponse response = null;

		HttpPut put = new HttpPut(hcpUrl);
		put.addHeader("Connection", "close");
		put.addHeader("Authorization", authStr);
		InputStreamEntity ise = new InputStreamEntity(is);
		put.setEntity(ise);
		try {
			response = client.execute(put);
			if (response.getStatusLine().getStatusCode() == 201)
				return true;
			else
				return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
			EntityUtils.consume(response.getEntity());
			response.close();
			client.close();
		}
	}

	/**
	 * 删除对象
	 * 
	 * @param hcpUrl
	 * @param authStr
	 * @return
	 * @throws IOException
	 */
	public boolean deleteObject(String hcpUrl, String authStr) throws IOException {
		CloseableHttpClient client = HttpClients.createDefault();
		CloseableHttpResponse response = null;

		HttpDelete head = new HttpDelete(hcpUrl);
		head.addHeader("Connection", "close");
		head.addHeader("Authorization", authStr);
		try {
			response = client.execute(head);
			if (response.getStatusLine().getStatusCode() == 200)
				return true;
			else
				return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
			EntityUtils.consume(response.getEntity());
			response.close();
			client.close();
		}
	}

	/**
	 * 检测对象是否存在
	 * 
	 * @param hcpUrl
	 * @param authStr
	 * @return
	 * @throws IOException
	 */
	public boolean checkFileExists(String hcpUrl, String authStr) throws IOException {
		CloseableHttpClient client = HttpClients.createDefault();
		CloseableHttpResponse response = null;

		HttpHead head = new HttpHead(hcpUrl);
		head.addHeader("Connection", "close");
		head.addHeader("Authorization", authStr);
		try {
			response = client.execute(head);
			if (response.getStatusLine().getStatusCode() == 200)
				return true;
			else
				return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
			EntityUtils.consume(response.getEntity());
			response.close();
			client.close();
		}
	}

}

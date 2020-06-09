package com.sutong.bjstjh.hcp;

import com.hds.commons.util.Base64Utils;
import com.sutong.bjstjh.hcp.tools.*;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.*;

/**
 * @Description: HCP 文件操作管理工具类
 * @author： Mr.Kong
 * @date: 2019/12/27 19:31
 */
public class HCPUtils {

    // 命名空间用户名
    private static String uName = "admin";
    // 命名空间密码
    private static String uPwd = "2wsx@WSX";
    // 认证头
    private static String auth = HCPTools.GetAuth(uName, uPwd);
    // 命名空间地址
    private static String namespaceUrl = "http://h2.tn1.bjetchcp.hds.com/rest/";

    // 测试用本地文件路径
    //private static String localFilePath = "C:\\Users\\pactera\\Desktop\\文档\\svn-doc\\服务设计文档\\1.5图像表mongodb.docx";


    private static String localFilePath = "C:\\Users\\29728\\Desktop\\2.png";


    // 删除hcp服务器的图片，王希给的本地路劲
    private static String localFileTxt = "C:\\Users\\pactera\\Desktop\\222.txt";

    public static void main(String args[]) throws Exception {

        // ----对象操作------
        // 1,创建对象
        createObject(namespaceUrl + "0000/test/2.png",localFilePath);
        // 下面几个方法用于测试通过IP访问指定机器
        // CreateObjectV2("http://10.129.214.121/rest/"+"changeFile121.xls",localFilePath);
        // CreateObjectV2("http://10.129.214.122/rest/"+"changeFile122.xls",localFilePath);
        // CreateObjectV2("http://10.129.214.123/rest/"+"changeFile123.xls",localFilePath);
        // CreateObjectV2("http://10.129.214.124/rest/"+"changeFile124.xls",localFilePath);
        // 2,创建对象通过HTTPS协议l
        // CreateObjectHTTPS(namespaceUrl+"changeFile.xls",localFilePath);
        // 3,检测对象是否存在
        // checkObject(namespaceUrl+"wangxiaofei/serverPort.png");

        /*File file = new File(localFileTxt);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                System.out.println("line " + line + ": " + tempString);
                line++;
                deleteObject(namespaceUrl + tempString);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }*/

        // 4，删除对象
        //deleteObject(namespaceUrl + "serverPort1.png");
        // deleteObject("http://mail.arcmail.hcp.sinosig.com/rest/email/10698/2016/05/24/18/41/18-41-42.352-703694A1-10698-28@sinosig.com-108807_{4DCB876C(05-24-18-39-22).png");

        // 5,删除对象所有版本
        // deleteObjectAllVersions(namespaceUrl+"0sv5xlfh.txt");
        // 6,读取对象
        //readObject("http://h2.tn1.bjetchcp.hds.com/rest/"+"0000/test/2.png","D:\\test\\2.png");
        // 7,根据版本号读取对象
        // readObjectByVersionCode(namespaceUrl+"0ebl25ow.txt","C:\\test\\abcccc.txt","92552996033601");
        // 8,列出对象的所有版本
        // listObjectVersions(namespaceUrl+"serverPort.png");
        // 9,对象复制
        // copyObject("n1.t1.hcp-demo.hcpdemo.com/120170423106770240001100024..pdf",namespaceUrl+"120170423106770240001100024.pdf");
        // 10,创建目录对象
        // createDirObject(namespaceUrl+"newDir2");
        // 11,检测目录对象是否存在
        // checkDirObject(namespaceUrl+"1111111");
        // 12,列出目录中对象
        // readDirObjects(namespaceUrl+"exposure3/day=20170809/hour=17/_temporary/0/_temporary/attempt_20170830144858_0000_m_000001_0/");
        // 13,删除目录对象，如果目录非空，不能删除
        // deleteDirObject(namespaceUrl+"exposure3/day=20170809/hour=17/_temporary/0/_temporary/attempt_20170823100900_0000_m_000003_0");

        // ----元数据操作----
        // 1，得到对象的系统元数据
        // getObjSystemMetadata(namespaceUrl+"wangxiaofei/serverPort.png");
        // 2,创建用户自定义元数据
        // createObjectAnnotation(namespaceUrl+"123.docx", "");

        // 3,列出对象的所有用户自定义元数据
        // listObjectAnnotations(namespaceUrl+"1kv0plih.txt");
        // 4,检测对象的元数据是否存在
        // checkObjAnnotationExist(namespaceUrl + "wangxiaofei/serverPort.png",
        // "default");
        // 5,读取对象元数据
        // readObjectsAnnotation(namespaceUrl+"wangxiaofei/serverPort.png","");
        // 6,删除用户自定义元数据
        // deleteObjectsAnnotation(namespaceUrl+"0ebl25ow.txt","");

        // rangeReader("http://n1.t1.hcp-demo.hcpdemo.com/rest/com.a",4764834,617845);
        // rangeReader("http://n1.t1.hcp-demo.hcpdemo.com/rest/com.a",5382679,1160024);
    }

    /**
     * 分片读取
     *
     * @param hcpUrl
     * @param offset
     * @param filelen
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static void rangeReader(String hcpUrl, long offset, long filelen)
            throws ClientProtocolException, IOException {
        CloseableHttpResponse response = null;
        CloseableHttpClient client = HttpClients.createDefault();

        // 文件大小<块大小，全读取
        HttpGet request = new HttpGet(hcpUrl);
        request.addHeader("Authorization", auth);
        request.addHeader("Range", "bytes=" + offset + "-" + (offset + filelen - 1));
        response = client.execute(request);
        // 一次性写到本地
        OutputStream os = new FileOutputStream("C:\\cpationline\\IK_.jar");

        byte[] b = new byte[1024];
        int f;
        while ((f = response.getEntity().getContent().read(b)) != -1) {
            os.write(b, 0, f);
        }
        os.close();
        EntityUtils.consume(response.getEntity());
    }

    /**
     * 删除用户自定义元数据
     *
     * @param hcpUrl
     * @param annotationName
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static void deleteObjectsAnnotation(String hcpUrl, String annotationName)
            throws ClientProtocolException, IOException {
        CloseableHttpResponse response = null;
        CloseableHttpClient client = HttpClients.createDefault();

        hcpUrl += "?type=custom-metadata";
        HttpDelete del = null;
        if (annotationName.isEmpty())
            del = new HttpDelete(hcpUrl + "&annotation=default");
        else
            del = new HttpDelete(hcpUrl + "&annotation=" + annotationName);
        del.addHeader("Authorization", auth);

        response = client.execute(del);

        Header[] headers = response.getAllHeaders();
        // 输出http信息头
        for (Header h : headers) {
            System.out.println(h.getName() + "|" + h.getValue());
        }

        // 关闭
        EntityUtils.consume(response.getEntity());

        // 状态码
        System.out.println(response.getStatusLine().getStatusCode());
    }

    /**
     * 读取对象元数据
     *
     * @param hcpUrl
     * @param annotationName
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static void readObjectsAnnotation(String hcpUrl, String annotationName)
            throws ClientProtocolException, IOException {
        CloseableHttpResponse response = null;
        CloseableHttpClient client = HttpClients.createDefault();

        hcpUrl += "?type=custom-metadata";
        HttpGet get = null;
        if (annotationName.isEmpty())
            get = new HttpGet(hcpUrl + "&annotation=default");
        else
            get = new HttpGet(hcpUrl + "&annotation=" + annotationName);
        get.addHeader("Authorization", auth);

        response = client.execute(get);
        InputStream is = response.getEntity().getContent();

        Header[] headers = response.getAllHeaders();
        // 输出http信息头
        for (Header h : headers) {
            System.out.println(h.getName() + "|" + h.getValue());
        }

        String xmlStr = CommonTools.inputStream2Str(response.getEntity().getContent());
        System.out.println(xmlStr);

        // 关闭
        EntityUtils.consume(response.getEntity());

        // 状态码
        System.out.println(response.getStatusLine().getStatusCode());
    }

    /**
     * 检测对象的元数据是否存在
     *
     * @param hcpUrl
     * @param annotationName
     *            如果此值为空，则检测默认元数据是否存在；如果此值不为空，则检测指定名称的元数所据是否存在
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static void checkObjAnnotationExist(String hcpUrl, String annotationName)
            throws ClientProtocolException, IOException {
        CloseableHttpResponse response = null;
        CloseableHttpClient client = HttpClients.createDefault();

        hcpUrl += "?type=custom-metadata";
        HttpHead head = null;
        if (annotationName.isEmpty())
            head = new HttpHead(hcpUrl);
        else
            head = new HttpHead(hcpUrl + "&annotation=" + annotationName);

        head.addHeader("Authorization", auth);
        response = client.execute(head);
        Header[] headers = response.getAllHeaders();
        // 输出http信息头
        for (Header h : headers) {
            System.out.println(h.getName() + "|" + h.getValue());
        }
        // 关闭
        EntityUtils.consume(response.getEntity());

        // 对象存在的状态码是200，不存在的状态码是204
        System.out.println(response.getStatusLine().getStatusCode());
    }

    /**
     * 列出对象的所有用户自定义元数据
     *
     * @param hcpUrl
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static void listObjectAnnotations(String hcpUrl) throws ClientProtocolException, IOException {
        CloseableHttpResponse response = null;
        CloseableHttpClient client = HttpClients.createDefault();

        HttpGet get = new HttpGet(hcpUrl + "?type=custom-metadata-info");
        get.addHeader("Authorization", auth);
        response = client.execute(get);
        InputStream is = response.getEntity().getContent();

        Header[] headers = response.getAllHeaders();
        // 输出http信息头
        for (Header h : headers) {
            System.out.println(h.getName() + "|" + h.getValue());
        }
        // 解析此xml即可提取对象的所有元数据
        String xmlStr = CommonTools.inputStream2Str(response.getEntity().getContent());
        System.out.println(xmlStr);

        // 关闭
        EntityUtils.consume(response.getEntity());

        // 状态码
        System.out.println(response.getStatusLine().getStatusCode());
    }

    /**
     * 创建用户自定义元数据
     *
     * @param hcpUrl
     * @param annotationName
     *            用户可以通过此参数指定元数据文件的名称。一个对象可以有多个不同的元数据信息
     * @throws IOException
     */
    public static void createObjectAnnotation(String hcpUrl, String annotationName) throws IOException {

        InputStream is;
        CloseableHttpResponse response = null;
        CloseableHttpClient client = HttpClients.createDefault();

        String webUrlXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>";

        webUrlXml += "<ddo dsType=\"ICM\" entityName=\"PAEAECM_20101Q\" xdoType=\"DKImageICM\">";
        webUrlXml += "<pid dsName=\"icmnlsdb\" dsType=\"ICM\" objectType=\"PAEAECM_20101Q\" pidString=\"94 3 ICM8 icmnlsdb14 PAEAECM_20101Q59 26 A1001001A17H24C01205C6029918 A17H24C01205C602991 14 1028\"/>";
        webUrlXml += "<propertyCount>8</propertyCount>";
        webUrlXml += "<property propertyId=\"2\" propertyName=\"semantic-type\" propertyType=\"long\" propertyValue=\"4\"/>";
        webUrlXml += "<property propertyId=\"3\" propertyName=\"item-type\" propertyType=\"short\" propertyValue=\"item\"/>";
        webUrlXml += "<property propertyId=\"5\" propertyName=\"SYSROOTATTRS.CREATEUSERID\" propertyType=\"string\" propertyValue=\"ICMADMIN\"/>";
        webUrlXml += "<property propertyId=\"7\" propertyName=\"SYSROOTATTRS.LASTCHANGEDUSERID\" propertyType=\"string\" propertyValue=\"ICMADMIN\"/>";
        webUrlXml += "<property propertyId=\"8\" propertyName=\"SYSROOTATTRS.ICMMANAGEDRECORD\" propertyType=\"short\" propertyValue=\"0\"/>";
        webUrlXml += "<dataCount>11</dataCount>";
        webUrlXml += "<dataItem dataId=\"1\" dataName=\"PAEA_ARC_CODE\" dataNameSpace=\"ATTR\">";
        webUrlXml += "<dataPropertyCount>2</dataPropertyCount>";
        webUrlXml += "<dataProperty propertyId=\"1\" propertyName=\"type\" propertyType=\"short\" propertyValue=\"string\"/>";
        webUrlXml += "<dataProperty propertyId=\"2\" propertyName=\"nullable\" propertyType=\"long\" propertyValue=\"true\"/>";
        webUrlXml += "<dataValue>001</dataValue>";
        webUrlXml += "</dataItem>";
        webUrlXml += "<dataItem dataId=\"4\" dataName=\"PAEA_IMAGE_FILE_NAME\" dataNameSpace=\"ATTR\">";
        webUrlXml += "<dataPropertyCount>2</dataPropertyCount>";
        webUrlXml += "<dataProperty propertyId=\"1\" propertyName=\"type\" propertyType=\"short\" propertyValue=\"string\"/>";
        webUrlXml += "<dataProperty propertyId=\"2\" propertyName=\"nullable\" propertyType=\"long\" propertyValue=\"true\"/>";
        webUrlXml += "<dataValue>test001.jpg</dataValue>";
        webUrlXml += "</dataItem>";
        webUrlXml += "</ddo>";

        // webUrlXml="http://n1.t1.hcp-demo.hcpdemo.com/rest/1234.docx";

        HttpPut put = null;
        if (annotationName.isEmpty())
            put = new HttpPut(hcpUrl + "?type=custom-metadata");
        else
            put = new HttpPut(hcpUrl + "?type=custom-metadata&annotation=" + annotationName);
        put.addHeader("Authorization", auth);
        is = new ByteArrayInputStream(webUrlXml.getBytes());
        InputStreamEntity ise = new InputStreamEntity(is);
        put.setEntity(ise);

        response = client.execute(put);
        Header[] headers = response.getAllHeaders();
        // 显示所有头部信息
        for (Header h : headers) {
            System.out.println("k =" + h.getName() + " ;v = " + h.getValue());
        }

        int resultCode = response.getStatusLine().getStatusCode();
        // 状态码。根据状态码可以知道成功，失败
        System.out.println("状态码 = " + resultCode);
        EntityUtils.consume(response.getEntity());
        response.close();
        client.close();

    }

    /**
     * 得到对象的系统元数据
     *
     * @param hcpUrl
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static void getObjSystemMetadata(String hcpUrl) throws ClientProtocolException, IOException {
        CloseableHttpResponse response = null;
        CloseableHttpClient client = HttpClients.createDefault();

        HttpHead head = new HttpHead(hcpUrl);
        head.addHeader("Authorization", auth);
        response = client.execute(head);
        Header[] headers = response.getAllHeaders();
        // 输出获取到的对象系统元数据
        for (Header h : headers) {
            System.out.println(h.getName() + "|" + h.getValue());
        }
        // 关闭
        EntityUtils.consume(response.getEntity());

        // 对象存在的状态码是200，不存在的状态码是404
        System.out.println(response.getStatusLine().getStatusCode());
    }

    /**
     * 删除目录对象
     *
     * @param hcpUrl
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static void deleteDirObject(String hcpUrl) throws ClientProtocolException, IOException {
        CloseableHttpResponse response = null;
        CloseableHttpClient client = HttpClients.createDefault();

        HttpDelete del = new HttpDelete(hcpUrl);
        del.addHeader("Authorization", auth);
        response = client.execute(del);
        Header[] headers = response.getAllHeaders();
        // 输出http信息头
        /*
         * for(Header h:headers) {
         * System.out.println(h.getName()+"|"+h.getValue()); }
         */
        // 关闭
        EntityUtils.consume(response.getEntity());

        // 状态码
        System.out.println(response.getStatusLine().getStatusCode());
    }

    /**
     * 列出目录中对象
     *
     * @param hcpUrl
     * @param hcpUrl
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static void readDirObjects(String hcpUrl) throws ClientProtocolException, IOException {
        CloseableHttpResponse response = null;
        CloseableHttpClient client = HttpClients.createDefault();

        HttpGet get = new HttpGet(hcpUrl);
        get.addHeader("Authorization", auth);
        response = client.execute(get);
        InputStream is = response.getEntity().getContent();

        Header[] headers = response.getAllHeaders();
        // 输出http信息头
        /*
         * for(Header h:headers) {
         * System.out.println(h.getName()+"|"+h.getValue()); }
         */
        String xmlStr = CommonTools.inputStream2Str(response.getEntity().getContent());
        System.out.println(xmlStr);

        // 关闭
        EntityUtils.consume(response.getEntity());

        // 状态码
        System.out.println(response.getStatusLine().getStatusCode());
    }

    /**
     * 检测目录对象是否存在
     *
     * @param hcpUrl
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static void checkDirObject(String hcpUrl) throws ClientProtocolException, IOException {
        CloseableHttpResponse response = null;
        CloseableHttpClient client = HttpClients.createDefault();

        HttpHead head = new HttpHead(hcpUrl);
        head.addHeader("Authorization", auth);
        response = client.execute(head);
        Header[] headers = response.getAllHeaders();
        // 输出http信息头
        for (Header h : headers) {
            System.out.println(h.getName() + "|" + h.getValue());
        }
        // 关闭
        EntityUtils.consume(response.getEntity());

        // 对象存在的状态码是200，不存在的状态码是404
        System.out.println(response.getStatusLine().getStatusCode());
    }

    /**
     * 创建目录对象
     *
     * @throws IOException
     */
    public static void createDirObject(String hcpUrl) throws IOException {

        InputStream is;
        CloseableHttpResponse response = null;
        CloseableHttpClient client = HttpClients.createDefault();

        HttpPut put = new HttpPut(hcpUrl + "?type=directory");
        put.addHeader("Authorization", auth);

        response = client.execute(put);
        Header[] headers = response.getAllHeaders();
        // 显示所有头部信息
        for (Header h : headers) {
            System.out.println("k =" + h.getName() + " ;v = " + h.getValue());
        }

        int resultCode = response.getStatusLine().getStatusCode();
        // 状态码。根据状态码可以知道成功，失败
        System.out.println("状态码 = " + resultCode);
        EntityUtils.consume(response.getEntity());
        response.close();
        client.close();

    }

    /**
     * 对象复制。必须拥有源对象的读取权，目标对象的写权
     *
     * @param sourceObjUrl
     * @param targetObjUrl
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static void copyObject(String sourceObjUrl, String targetObjUrl)
            throws ClientProtocolException, IOException {
        CloseableHttpResponse response = null;
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPut put = new HttpPut(targetObjUrl);
        put.addHeader("Authorization", auth);
        put.addHeader("X-HCP-CopySource", sourceObjUrl);
        put.addHeader("X-HCP-MetadataDirective", "ALL");
        response = client.execute(put);
        // 输出http信息头
        Header[] headers = response.getAllHeaders();
        for (Header h : headers) {
            System.out.println(h.getName() + "|" + h.getValue());
        }
        // 状态码
        System.out.println(response.getStatusLine().getStatusCode());

        // 关闭
        EntityUtils.consume(response.getEntity());
        client.close();
    }

    /**
     * 列出对象的所有版本信息
     *
     * @param hcpUrl
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static void listObjectVersions(String hcpUrl) throws ClientProtocolException, IOException {
        CloseableHttpResponse response = null;
        CloseableHttpClient client = HttpClients.createDefault();

        HttpGet get = new HttpGet(hcpUrl + "?version=list");
        get.addHeader("Authorization", auth);
        response = client.execute(get);
        InputStream is = response.getEntity().getContent();

        Header[] headers = response.getAllHeaders();
        // 输出http信息头
        for (Header h : headers) {
            System.out.println(h.getName() + "|" + h.getValue());
        }
        // 解析此xml即可提取version号
        String xmlStr = CommonTools.inputStream2Str(response.getEntity().getContent());
        System.out.println(xmlStr);

        // 关闭
        EntityUtils.consume(response.getEntity());

        // 状态码
        System.out.println(response.getStatusLine().getStatusCode());
    }

    /**
     * 根据版本号读取对象
     *
     * @param hcpUrl
     * @param localPath
     * @param versionCode
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static void readObjectByVersionCode(String hcpUrl, String localPath, String versionCode)
            throws ClientProtocolException, IOException {
        CloseableHttpResponse response = null;
        CloseableHttpClient client = HttpClients.createDefault();

        HttpGet get = new HttpGet(hcpUrl + "?version=" + versionCode);
        get.addHeader("Authorization", auth);
        response = client.execute(get);
        InputStream is = response.getEntity().getContent();

        Header[] headers = response.getAllHeaders();
        // 输出http信息头
        for (Header h : headers) {
            System.out.println(h.getName() + "|" + h.getValue());
        }

        OutputStream os = new FileOutputStream(localPath);

        byte[] b = new byte[1024];
        int f;
        while ((f = is.read(b)) != -1) {
            os.write(b, 0, f);
        }
        os.close();

        // 关闭
        EntityUtils.consume(response.getEntity());

        // 状态码
        System.out.println(response.getStatusLine().getStatusCode());
    }

    /**
     * 读取对象保存到本地
     *
     * @param hcpUrl
     * @param localPath
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static void readObject(String hcpUrl, String localPath) throws ClientProtocolException, IOException {
        CloseableHttpResponse response = null;
        CloseableHttpClient client = HttpClients.createDefault();

        HttpGet get = new HttpGet(hcpUrl);
        // get.addHeader("Authorization",auth);
        get.addHeader("Authorization", "auth");
        response = client.execute(get);
        InputStream is = response.getEntity().getContent();

        Header[] headers = response.getAllHeaders();
        // 输出http信息头
        for (Header h : headers) {
            System.out.println(h.getName() + "|" + h.getValue());
        }

        OutputStream os = new FileOutputStream(localPath);

        byte[] b = new byte[1024];
        int f;
        while ((f = is.read(b)) != -1) {
            os.write(b, 0, f);
        }
        os.close();

        // 关闭
        EntityUtils.consume(response.getEntity());

        // 状态码
        System.out.println(response.getStatusLine().getStatusCode());
    }

    /**
     * 删除对象所有版本，彻底删除
     *
     * @param hcpUrl
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static void deleteObjectAllVersions(String hcpUrl) throws ClientProtocolException, IOException {
        CloseableHttpResponse response = null;
        CloseableHttpClient client = HttpClients.createDefault();

        HttpDelete del = new HttpDelete(hcpUrl + "?purge=true");
        del.addHeader("Authorization", auth);
        response = client.execute(del);
        Header[] headers = response.getAllHeaders();
        // 输出http信息头
        for (Header h : headers) {
            System.out.println(h.getName() + "|" + h.getValue());
        }
        // 关闭
        EntityUtils.consume(response.getEntity());

        // 状态码
        System.out.println(response.getStatusLine().getStatusCode());
    }

    /**
     * 删除单个对象，如果没有开启版本功能，则对象删除；如果开启版本功能，则开成一个新的版本
     *
     * @param hcpUrl
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static void deleteObject(String hcpUrl) throws ClientProtocolException, IOException {
        CloseableHttpResponse response = null;
        CloseableHttpClient client = HttpClients.createDefault();

        HttpDelete del = new HttpDelete(hcpUrl);
        del.addHeader("Authorization", auth);
        response = client.execute(del);
        Header[] headers = response.getAllHeaders();
        // 输出http信息头
        for (Header h : headers) {
            System.out.println(h.getName() + "|" + h.getValue());
        }
        // 关闭
        EntityUtils.consume(response.getEntity());

        // 状态码
        System.out.println(response.getStatusLine().getStatusCode());
    }

    /**
     * 删除对象
     *
     * @param hcpUrl
     * @throws Exception
     */
    public static void deleteObjectHTTPS(String hcpUrl) throws Exception {
        CloseableHttpResponse response = null;
        SSLClient client = new SSLClient();

        HttpDelete del = new HttpDelete(hcpUrl);
        del.addHeader("Authorization", auth);
        response = client.execute(del);

        // 状态码
        System.out.println(response.getStatusLine().getStatusCode());

        // 关闭
        EntityUtils.consume(response.getEntity());
        response.close();
    }

    private static String encodeUrl(String hcpUrl) throws UnsupportedEncodingException {

        hcpUrl = hcpUrl.replace("%", "%25");
        hcpUrl = hcpUrl.replace(" ", "%20");
        hcpUrl = hcpUrl.replace("+", "%2B");
        hcpUrl = hcpUrl.replace("#", "%23");
        hcpUrl = hcpUrl.replace("?", "%3F");
        hcpUrl = hcpUrl.replace("&", "%26");
        hcpUrl = hcpUrl.replace("[", "%5B");
        hcpUrl = hcpUrl.replace("]", "%5D");

        hcpUrl = hcpUrl.replace("!", "%21");

        hcpUrl = hcpUrl.replace("*", "%2A");

        hcpUrl = hcpUrl.replace("\"", "%22");

        hcpUrl = hcpUrl.replace("'", "%27");

        hcpUrl = hcpUrl.replace("(", "%28");

        hcpUrl = hcpUrl.replace(")", "%29");

        hcpUrl = hcpUrl.replace(";", "%3B");

        hcpUrl = hcpUrl.replace("@", "%40");

        hcpUrl = hcpUrl.replace("=", "%3D");
        hcpUrl = hcpUrl.replace("$", "%24");
        hcpUrl = hcpUrl.replace(",", "%2C");

        return hcpUrl;
    }

    /**
     * 检测对象是否存在
     *
     * @param hcpUrl
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static void checkObject(String hcpUrl) throws ClientProtocolException, IOException {
        CloseableHttpResponse response = null;
        CloseableHttpClient client = HttpClients.createDefault();

        HttpHead head = new HttpHead(hcpUrl);
        head.addHeader("Authorization", auth);
        response = client.execute(head);
        Header[] headers = response.getAllHeaders();
        // 输出http信息头
        for (Header h : headers) {
            System.out.println(h.getName() + "|" + h.getValue());
        }
        // 关闭
        EntityUtils.consume(response.getEntity());

        // 对象存在的状态码是200，不存在的状态码是404
        System.out.println(response.getStatusLine().getStatusCode());
    }

    public static String getBase64FromInputStream(InputStream in) {
        // 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        byte[] data = null;
        // 读取图片字节数组
        try {
            ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
            byte[] buff = new byte[100];
            int rc = 0;
            while ((rc = in.read(buff, 0, 100)) > 0) {
                swapStream.write(buff, 0, rc);
            }
            data = swapStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return new String(Base64Utils.encode(data));
    }

    /**
     * 创建对象
     *
     * @throws IOException
     */
    public static void createObject(String hcpUrl, String localPath) throws IOException {
        // auth=tools.HCPTools.GetAuth("admin", "hcpinsta11");
        InputStream is;
        CloseableHttpResponse response = null;
        CloseableHttpClient client = HttpClients.createDefault();
        is = new FileInputStream(localPath);
        HttpPut put = new HttpPut(hcpUrl);
        put.addHeader("Authorization", auth);
        InputStreamEntity ise = new InputStreamEntity(is);
        put.setEntity(ise);

        Header[] headers = put.getAllHeaders();
        // 显示所有头部信息
        for (Header h : headers) {
            System.out.println("k =" + h.getName() + " ;v = " + h.getValue());
        }
        response = client.execute(put);
        /*
         * Header[] headers = response.getAllHeaders(); // 显示所有头部信息 for (Header
         * h : headers) { System.out.println("k =" + h.getName() + " ;v = " +
         * h.getValue()); }
         */

        int resultCode = response.getStatusLine().getStatusCode();
        // 状态码。根据状态码可以知道成功，失败
        System.out.println("状态码 = " + resultCode);
        EntityUtils.consume(response.getEntity());
        response.close();
        client.close();

    }

    public static void createObjectV2(String hcpUrl, String localPath) throws IOException {

        InputStream is;
        CloseableHttpResponse response = null;
        CloseableHttpClient client = HttpClients.createDefault();
        is = new FileInputStream(localPath);
        HttpPut put = new HttpPut(hcpUrl);
        put.addHeader("Host", "n1.t1.hcp1.hds.poc");
        put.addHeader("Authorization", auth);
        InputStreamEntity ise = new InputStreamEntity(is);
        put.setEntity(ise);

        response = client.execute(put);
        Header[] headers = response.getAllHeaders();
        // 显示所有头部信息
        for (Header h : headers) {
            System.out.println("k =" + h.getName() + " ;v = " + h.getValue());
        }

        int resultCode = response.getStatusLine().getStatusCode();
        // 状态码。根据状态码可以知道成功，失败
        System.out.println("状态码 = " + resultCode);
        EntityUtils.consume(response.getEntity());
        response.close();
        client.close();

    }

    public static void createObjectHTTPS(String hcpUrl, String localPath) throws Exception {
        InputStream is;
        CloseableHttpResponse response = null;
        SSLClient client = new SSLClient();
        is = new FileInputStream(localPath);

        HttpPut put = new HttpPut(hcpUrl);

        put.addHeader("Authorization", auth);
        InputStreamEntity ise = new InputStreamEntity(is);
        put.setEntity(ise);

        response = client.execute(put);
        Header[] headers = response.getAllHeaders();
        // 显示所有头部信息
        for (Header h : headers) {
            System.out.println("k =" + h.getName() + " ;v = " + h.getValue());
        }

        int resultCode = response.getStatusLine().getStatusCode();
        // 状态码。根据状态码可以知道成功，失败
        System.out.println("状态码 = " + resultCode);

        EntityUtils.consume(response.getEntity());
        response.close();
        client.close();
    }

    public Integer createMetaData(String annotationName, String hcpUrl, InputStream metaDataIs, String auth) {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPut put = new HttpPut(hcpUrl + "?type=custom-metadata");
        put.addHeader("Authorization", auth);
        InputStreamEntity ise = new InputStreamEntity(metaDataIs);
        put.setEntity(ise);
        try {
            HttpResponse response = client.execute(put);
            return response.getStatusLine().getStatusCode();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 删除用户自定义元数据
     */
    public static void deleteMetaDataTester() {
        String auth = HCPTools.GetAuth(uName, uPwd);
        HCPUtils admin = new HCPUtils();

        try {
            String hcpUrl = "http://n1.t1.hcp-demo.hcpdemo.com/rest/fghj/123.txt";
            ResultMsg resultMsg = admin.deleteMetaData(hcpUrl, auth, "abc");
            System.out.println("状态码 = " + resultMsg.getCode());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 删除用户自定义元数据
     *
     * @param hcpUrl
     * @param auth
     * @return
     * @throws IOException
     * @throws ClientProtocolException
     */
    public ResultMsg deleteMetaData(String hcpUrl, String auth, String annotationName)
            throws ClientProtocolException, IOException {
        HttpClient client = HttpClientBuilder.create().build();
        String requestUrl = "";
        if (annotationName != null && annotationName.length() > 0)
            requestUrl = hcpUrl + "?type=custom-metadata&annotation=" + annotationName;
        else
            requestUrl = hcpUrl + "?type=custom-metadata";

        HttpDelete request = new HttpDelete(requestUrl);
        request.addHeader("Authorization", auth);
        HttpResponse response;
        response = client.execute(request);
        ResultMsg resultMsg = new ResultMsg();
        resultMsg.setCode(response.getStatusLine().getStatusCode());
        return resultMsg;
    }

    public static String getSysMeta(String url) {
        String s = "";

        HttpClient client = HttpClientBuilder.create().build();
        String auth = HCPTools.GetAuth(uName, uPwd);
        HttpHead head = new HttpHead(url);
        head.addHeader("Authorization", auth);
        HttpResponse response;
        try {
            response = client.execute(head);
            System.out.print("--------------------------------------------");
            System.out.print(response.getStatusLine().getStatusCode());
            Header[] heads = response.getAllHeaders();
            for (Header h : heads) {
                if (h.getName().equals("X-HCP-IngestTime")) {
                    System.out.println("name = " + h.getName() + ": value = " + h.getValue());
                    Long l = Long.parseLong(h.getValue()) * 1000;
                    s = DateTools.ms2DateTime(l);
                }
                // System.out.println("name = "+h.getName() +": value =
                // "+h.getValue());
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return s;
    }

    /**
     * 读取系统元数据
     */
    public static void getSysMeta() {
        HttpClient client = HttpClientBuilder.create().build();
        String auth =HCPTools.GetAuth(uName, uPwd);
        HttpHead head = new HttpHead("http://n1.t1.hcp-demo.hcpdemo.com/rest/123.txt");
        head.addHeader("Authorization", auth);
        HttpResponse response;
        try {
            response = client.execute(head);
            System.out.print(response.getStatusLine().getStatusCode());
            Header[] heads = response.getAllHeaders();
            for (Header h : heads) {
                System.out.println("name = " + h.getName() + ": value = " + h.getValue());
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


}

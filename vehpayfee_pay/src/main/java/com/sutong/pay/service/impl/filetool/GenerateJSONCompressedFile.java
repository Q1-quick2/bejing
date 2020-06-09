package com.sutong.pay.service.impl.filetool;

import org.springframework.stereotype.Component;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Component
public class GenerateJSONCompressedFile {
    /**
     * 将json字符串生成json文件到指定路径并压缩到指定的路径下
     * @param jsonString json字符串
     * @param filepath 生成的json文件目的路径
     * @param fileName json文件和zip文件名
     * @param zipFilePath 生成的zip文件目的路径
     */
    public String jsonCompressedFild(String jsonString,String filepath,String fileName,String zipFilePath){
        if (filepath.endsWith("\\")){
            filepath = filepath.substring(0,fileName.lastIndexOf("\\"));
        }
        if ( filepath.endsWith("/")){
            filepath = filepath.substring(0,fileName.lastIndexOf("/"));
        }
        if (fileName.endsWith(".") || fileName.endsWith(".json") || fileName.endsWith(".zip")){
            fileName = fileName.substring(0,fileName.lastIndexOf("."));
        }
        if (!zipFilePath.endsWith("\\") && !zipFilePath.endsWith("/")){
            zipFilePath = zipFilePath + File.separator;
        }

        String jsonFilePath = createJsonFile(jsonString,filepath,fileName);

        boolean bool = createZipFile(jsonFilePath,fileName,zipFilePath);
        if (bool){//如果压缩文件成功，删除json文件
            deleteFile(jsonFilePath);
        }
        return zipFilePath + File.separator + ".zip";
    }
    /**
     * 压缩文件
     * @param jsonfilePath json文件全路径名(包括后缀名)
     * @param filename 压缩文件名
     * @param zipFilePath zip文件路径
     *
     */
    private boolean createZipFile(String jsonfilePath,String filename,String zipFilePath){
        boolean iszip = true;
        ZipOutputStream out = null;
        BufferedInputStream bis = null;
        try{
            File file = new File(jsonfilePath);

            if (!filename.endsWith(".zip")){
                filename = filename + ".zip";
            }
            out = new ZipOutputStream(new FileOutputStream(new File(zipFilePath + filename)));
            bis = new BufferedInputStream(new FileInputStream(file));
            ZipEntry entry = new ZipEntry(file.getName());//压缩单个文件
            out.putNextEntry(entry);
            int count;
            byte[] buf = new byte[1024];
            while ((count = bis.read(buf)) != -1) {
                out.write(buf, 0, count);
            }
            out.closeEntry();
        }catch (Exception e){
            iszip = false;
            e.printStackTrace();
        }finally {
            try {
                if (bis != null){
                    bis.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            try {
                if (out != null){
                    out.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        return iszip;
    }

    /**
     * 生成.json格式文件
     * @param jsonString json字符串
     * @param filePath 文件路(不带/)
     * @param fileName 要生成的文件名
     */
    private String createJsonFile(String jsonString, String filePath, String fileName){

        // 拼接文件路径
        String fullPath = filePath + File.separator + fileName + ".json";
        Writer write = null;
        // 生成json格式文件
        try {
            // 保证创建一个新文件
            File file = new File(fullPath);
            if (!file.getParentFile().exists()) { // 如果父目录不存在，创建父目录
                file.getParentFile().mkdirs();
            }else { // 如果已存在,删除旧文件
                file.delete();
            }
            file.createNewFile();
            //校验json字符串并转义特殊字符
            jsonString = checkJson(jsonString);
            // 格式化json字符串
            jsonString = formatJson(jsonString);
            // 将格式化后的字符串写入文件
            write = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
            write.write(jsonString);
            write.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try{
                if (write != null){
                    write.close();
                }
            }catch (Exception e){e.printStackTrace();}

        }
        // 返回文件全路径名
        return fullPath;
    }
    /**
     * 返回符合JSON格式的字符串
     * @param jsonString 未校验的JSON字符串。
     * @return 校验的JSON字符串。
     */
    private String checkJson(String jsonString){
        if(jsonString.indexOf("'") != -1){
            //将单引号转义一下，因为JSON串中的字符串类型可以单引号引起来的
            jsonString = jsonString.replaceAll("'", "\\'");
        }
        if(jsonString.indexOf("\"")!=-1){
            //将双引号转义一下，因为JSON串中的字符串类型可以单引号引起来的
            jsonString = jsonString.replaceAll("\"", "\\\"");
        }
        if(jsonString.indexOf("\r\n")!=-1){
            //将回车换行转换一下，因为JSON串中字符串不能出现显式的回车换行
            jsonString = jsonString.replaceAll("\r\n", "\\u000d\\u000a");
        }
        if(jsonString.indexOf("\n")!=-1){
            //将换行转换一下，因为JSON串中字符串不能出现显式的换行
            jsonString = jsonString.replaceAll("\n", "\\u000a");
        }
        return jsonString;
    }
    /**
     * 返回格式化JSON字符串
     * @param json 未格式化的JSON字符串。
     * @return 格式化的JSON字符串。
     */
    private String formatJson(String json) {
        StringBuffer result = new StringBuffer();

        int length = json.length();
        int number = 0;
        char key = 0;

        // 遍历输入字符串。
        for (int i = 0; i < length; i++) {
            // 1、获取当前字符。
            key = json.charAt(i);
            // 2、如果当前字符是前方括号、前花括号做如下处理：
            if ((key == '[') || (key == '{')) {
                // （1）如果前面还有字符，并且字符为“：”，打印：换行和缩进字符字符串。
                if ((i - 1 > 0) && (json.charAt(i - 1) == ':')) {
                    result.append('\n');
                    result.append(indent(number));
                }
                // （2）打印：当前字符。
                result.append(key);
                // （3）前方括号、前花括号，的后面必须换行。打印：换行。
                result.append('\n');
                // （4）每出现一次前方括号、前花括号；缩进次数增加一次。打印：新行缩进。
                number++;
                result.append(indent(number));
                // （5）进行下一次循环。
                continue;
            }
            // 3、如果当前字符是后方括号、后花括号做如下处理：
            if ((key == ']') || (key == '}')) {
                // （1）后方括号、后花括号，的前面必须换行。打印：换行。
                result.append('\n');
                // （2）每出现一次后方括号、后花括号；缩进次数减少一次。打印：缩进。
                number--;
                result.append(indent(number));
                // （3）打印：当前字符。
                result.append(key);
                // （4）如果当前字符后面还有字符，并且字符不为“，”，打印：换行。
                if (((i + 1) < length) && (json.charAt(i + 1) != ',')) {
                    result.append('\n');
                }
                // （5）继续下一次循环。
                continue;
            }
            // 4、如果当前字符是逗号。逗号后面换行，并缩进，不改变缩进次数。
            /*if ((key == ',')) {
                result.append(key);
                result.append('\n');
                result.append(indent(number));
                continue;
            }*/
            // 5、打印：当前字符。
            result.append(key);
        }

        return result.toString();
    }

    /**
     * 返回指定次数的缩进字符串。每一次缩进三个空格
     * @param number 缩进次数。
     * @return 指定缩进次数的字符串。
     */
    private String indent(int number) {
        /**
         * 单位缩进字符串。
         */
        String SPACE = "   ";
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < number; i++) {
            result.append(SPACE);
        }
        return result.toString();
    }
    private void deleteFile(String filePath){
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
    }
}

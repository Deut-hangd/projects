package org.zero.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

// 封装 http 的类
public class HttpUtils {

    /**
     * 获取一个 http 连接
     * @param url     用户请求的 http 地址
     * @param headers 一次请求所包含的请求头
     * @return 返回一个连接
     * @throws IOException
     */
    public static HttpURLConnection getConnection(String url,
                                                  Map<String, String> headers) throws IOException {
        // 创建远程 url 连接对象
        URL httpUrl = new URL(url);
        // 通过远程 url 连接对象打开一个连接, 强转成 httpURLConnection 类
        HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
        // 设置连接主机服务器的超时时间: 15000 毫秒
        conn.setConnectTimeout(15000);
        // 设置读取远程返回的数据时间: 60000毫秒
        conn.setReadTimeout(60000);
        // 设置请求头
        if (headers != null) {
            for (String key : headers.keySet()) {
                conn.addRequestProperty(key, headers.get(key));
            }
        }
        // 返回连接
        return conn;
    }

    /**
     * 处理响应结果
     * @param connection 一个连接对象
     * @return 返回经过处理的响应结果
     * @throws IOException
     */
    public static String handleResult(HttpURLConnection connection) throws IOException {
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;

        try {
            // 获取响应状态码
            int code = connection.getResponseCode();
            // 若响应状态码为 200, 则请求成功, 进行数据读取
            if (code == 200) {
                // 打开读取响应流的缓冲区字符输入流
                is = connection.getInputStream();
                isr = new InputStreamReader(is);
                br = new BufferedReader(isr);
                // 创建 StringBuffer 对象, 用来进行字符串拼接
                StringBuffer sbf = new StringBuffer();
                String temp = null;
                while ((temp = br.readLine()) != null) {
                    sbf.append(temp);
                    sbf.append("\r\n");
                }
                return sbf.toString();
            } else {
                // 服务器响应失败, 答应错误码
                throw new RuntimeException("handleResult error, responseCode= " + code);
            }
        } finally {
            // 关闭输入流
            if (is != null) {
                is.close();
                is = null;
            }

            if (isr != null) {
                isr.close();
                isr = null;
            }

            if (br != null) {
                br.close();
                br = null;
            }
        }
    }

    /**
     * 表单提交
     * @param urlStr 统一资源定位符
     * @param textMap 表单中 type = text 的项
     * @param filePaths 表单中 type = file 的项
     * @return 经过处理的响应流
     * @throws IOException
     */
    public static String formUpload(String urlStr, Map<String, String> textMap, String[] filePaths) throws IOException {
        String res = null;
        HttpURLConnection conn = null;
        DataOutputStream dataOutputStream = null;
        DataInputStream dataInputStream = null;
        Map<String, String> headers = new HashMap<>();
        // 设置分隔符
        String BOUNDARY = "---------------------------123821742118716"; //boundary就是request头和上传文件内容的分隔符
        // 设置请求头
        headers.put("Connection", "Keep-Alive");
        headers.put("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
        headers.put("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
        try {
//            URL url = new URL(urlStr);
            // 获取一个连接
            conn = getConnection(urlStr, headers);
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(30000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            // 以数据输出流的方式打开服务器输出流
            dataOutputStream = new DataOutputStream(conn.getOutputStream());

            // 处理表单中 type = text 的内容
            if (textMap != null) {
                StringBuffer stringBuffer = new StringBuffer();
                Iterator<Map.Entry<String, String>> iter = textMap.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry<String, String> entry = iter.next();
                    String inputName = (String) entry.getKey();
                    String inputValue = (String) entry.getValue();
                    if (inputValue == null) {
                        continue;
                    }
                    stringBuffer.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
                    stringBuffer.append("Content-Disposition: form-data; name=\"" + inputName + "\"\r\n\r\n");
                    stringBuffer.append(inputValue);
                }
                dataOutputStream.write(stringBuffer.toString().getBytes());
            }

            // 处理表单中 type = file 的内容
            for (int i = 0; i < filePaths.length; i++) {
                File file = new File(filePaths[i]);
                String filename = file.getName();
                String contentType = "application/octet-stream";
                // 创建 StringBuffer 对象, 用来拼接请求体中的数据
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
                stringBuffer.append("Content-Disposition: form-data; name=\"" + "filename" + "\"; filename=\"" + filename + "\"\r\n");
                stringBuffer.append("Content-Type:" + contentType + "\r\n\r\n");
                // 将数据写入服务器输出流中
                dataOutputStream.write(stringBuffer.toString().getBytes());
                // 以数据输入流的方式打开文件流
                dataInputStream = new DataInputStream(new FileInputStream(file));
                int bytes = 0;
                // 数据缓冲区
                byte[] bufferOut = new byte[1024];
                // 读取和发送文件数据
                while ((bytes = dataInputStream.read(bufferOut)) != -1) {
                    dataOutputStream.write(bufferOut, 0, bytes);
                }
            }
            byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
            dataOutputStream.write(endData);
            // 刷新数据输出流
            dataOutputStream.flush();
            res = handleResult(conn);
            return res;
        } catch (Exception e) {
            throw new RuntimeException("文件上传失败。" + urlStr, e);
        } finally {
            // 释放资源
            close(dataOutputStream, dataInputStream, conn);
        }
    }

    /**
     * 文件下载
     * @param urlStr 统一资源定位符
     * @param savePath 下载文件保存路径
     * @param fileName 下载文件名
     * @return 经过处理的响应流
     * @throws IOException
     */
    public static String download(String urlStr, String savePath, String fileName) throws IOException {
        HttpURLConnection conn = null;
        DataOutputStream dataOutputStream = null;
        DataInputStream dataInputStream = null;
        Map<String, String> headers = new HashMap<>();
        try {
            // 设置请求头
            headers.put("Connection", "Keep-Alive");
            headers.put("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
            // 获取一个连接
            conn = getConnection(urlStr + "/" + fileName, headers);
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(30000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("GET");
//            conn.setRequestProperty("Connection", "Keep-Alive");
//            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
            // 根据响应头获取文件类型
            String fileType = conn.getHeaderField("File-Type");
            // 获取响应流中的文件大小
            int fileLength = conn.getContentLength();
            int length = 0;
            // 以数据输入流的方式打开服务器输入流
            dataInputStream = new DataInputStream(conn.getInputStream());
            // 创建文件句柄
            File file = new File(savePath);
            // 若文件保存路径不存在, 则创建路径
            if (!file.exists()) {
                file.mkdirs();
            }
            // 创建数据输出流
            dataOutputStream = new DataOutputStream(
                    new FileOutputStream(new File(savePath + "/" + fileName + "." + fileType)));
            int bytes = 0;
            // 数据缓冲区
            byte[] bufferOut = new byte[1024];
            // 读取并保存文件数据
            while ((bytes = dataInputStream.read(bufferOut)) != -1) {
                length += bytes;
                dataOutputStream.write(bufferOut, 0, bytes);
            }
            // 刷新数据输出流
            dataOutputStream.flush();
            System.out.println("Finished : " + length / fileLength * 100 + "%");
            return "success";
        } catch (Exception e) {
        throw new RuntimeException("文件下载失败!" + urlStr, e);
    } finally {
            // 释放资源
            close(dataOutputStream, dataInputStream, conn);
        }
    }

    /**
     *  查看文件元信息
     * @param urlStr 统一资源定位符
     * @param fileName 文件在数据库中的 id
     * @return 文件元信息
     * @throws IOException
     */
    public static String selectMetaInfo(String urlStr, String fileName) throws IOException {
        // http 连接对象和数据输入输出流
        HttpURLConnection conn = null;
        DataOutputStream dataOutputStream = null;
        DataInputStream dataInputStream = null;
        String res = null;
        // 请求头
        Map<String, String> headers = new HashMap<>();
        try {
            // 设置请求头
            headers.put("Connection", "Keep-Alive");
            headers.put("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
            // 获取一个连接
            conn = getConnection(urlStr + "/" + fileName, headers);
            // 设置连接参数
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(30000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("GET");
            res = handleResult(conn);
        } catch (Exception e) {
                throw new RuntimeException("文件元信息获取失败!" + urlStr, e);
            } finally {
            // 释放资源
            close(dataOutputStream, dataInputStream, conn);
        }
        return res;
    }

    /**
     *  释放资源
     * @param outputStream 输出字节流
     * @param inputStream 输入字节流
     * @param conn 连接对象
     * @throws IOException
     */
    public static void close(OutputStream outputStream, InputStream inputStream, HttpURLConnection conn) throws IOException {

        // 关闭数据输出流
        if (outputStream!= null) {
            outputStream.close();
            outputStream = null;
        }

        // 关闭数据输入流
        if (inputStream != null) {
            inputStream.close();
            inputStream = null;
        }

        // 关闭连接
        if (conn != null) {
            conn.disconnect();
            conn = null;
        }
    }
}

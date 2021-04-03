package org.zero.utils;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 *  客户端单元测试类
 */
public class HttpUtilsTest {

    // url上下文路径
    private String baseUrl = null;

    // 请求头
    private Map<String, String> headers = null;


    @Before
    public void setup() {
        baseUrl = "https://www.baidu.com";
        headers = new HashMap<>();
    }

    // 测试使用 HttpUtils 向百度发送连接请求 (通过)
    @Test
    public void getConnectionBaiDu() {
        try {
            HttpURLConnection connection = HttpUtils.getConnection(baseUrl, headers);
            System.out.println(connection.getResponseCode());
            System.out.println(connection.getResponseMessage());
            assertNotEquals(connection, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 测试使用 HttpUtils 向文件服务器发送链接请求  (通过, 状态码 200)
    @Test
    public void getConnectionFileServer() {
        baseUrl = "http://localhost:8080/file/welcome";
        try {
            HttpURLConnection connection = HttpUtils.getConnection(baseUrl, headers);
            System.out.println(connection.getResponseCode());
            assertNotEquals(connection, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // 测试上传单个文件 (通过无乱码)
    @Test
    public void formUpload() {
        uploadFormat("E:\\Idea\\workspaces\\java12\\file_client\\src\\test\\resources\\file\\controller1.txt");
    }

    // 测试上传多个文件  (通过, 返回 UUID 数组)
    @Test
    public void multiFormUpload() {
        String url = "http://localhost:8080/file/upload";
        // 表单文本选项
        Map<String, String> textMap = null;
        String[] filePath = new String[3];
        // 上传 3 个文件
        filePath[0] = "E:\\Idea\\workspaces\\java12\\file_client\\src\\test\\resources\\file\\service1.txt";
        filePath[1] = "E:\\Idea\\workspaces\\java12\\file_client\\src\\test\\resources\\file\\service2.txt";
        filePath[2] = "E:\\Idea\\workspaces\\java12\\file_client\\src\\test\\resources\\file\\service3.txt";
        String result = null;
        try {
            // 调用文件上传函数
            result = HttpUtils.formUpload(url, textMap, filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(result);
        assertNotEquals(null, result);
    }

    // 测试上传多个不同文件 (通过, 返回 UUID 数组)  (通过无乱码)
    @Test
    public void multiTypeFormUpload() {
        String url = "http://localhost:8080/file/upload";
        // 表单文本选项
        Map<String, String> textMap = null;
        String[] filePath = new String[3];
        // 上传 3 个文件
        filePath[0] = "E:\\Idea\\workspaces\\java12\\file_client\\src\\test\\resources\\file\\test01.js";
        filePath[1] = "E:\\Idea\\workspaces\\java12\\file_client\\src\\test\\resources\\file\\db_table.sql";
        filePath[2] = "E:\\Idea\\workspaces\\java12\\file_client\\src\\test\\resources\\file\\main.css";
        String result = null;
        try {
            // 调用文件上传函数
            result = HttpUtils.formUpload(url, textMap, filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(result);
        assertNotEquals(null, result);
    }


    // 测试上传单个 doc 文件  (通过无乱码)
    @Test
    public void formUploadDoc() {
        uploadFormat("E:\\Idea\\workspaces\\java12\\file_client\\src\\test\\resources\\file\\业务1d.doc");
    }

    // 测试上传单个 wps 文件  (通过无乱码)
    @Test
    public void formUploadWps() {
        uploadFormat("E:\\Idea\\workspaces\\java12\\file_client\\src\\test\\resources\\file\\业务1w.wps");
    }

    // 测试文件上传接口, 文件类型为 pdf (通过无乱码)
    @Test
    public void formUploadPDF() {
        uploadFormat("E:\\Idea\\workspaces\\java12\\file_client\\src\\test\\resources\\file\\业务1p.pdf");
    }

    // 测试文件上传接口, 文件类型为 xml (通过无乱码)
    @Test
    public void formUploadXML() {
        uploadFormat("E:\\Idea\\workspaces\\java12\\file_client\\src\\test\\resources\\file\\file_client_pom.xml");
    }

    // 测试文件上传接口, 文件类型为 rar (通过无乱码)
    @Test
    public void formUploadRAR() {
        uploadFormat("E:\\Idea\\workspaces\\java12\\file_client\\src\\test\\resources\\file\\业务1r.rar");
    }

    // 测试文件上传接口, 文件类型为 html (通过无乱码)
    @Test
    public void formUploadHtml() {
        uploadFormat("E:\\Idea\\workspaces\\java12\\file_client\\src\\test\\resources\\file\\hello.html");
    }

    // 测试下载文件接口, 待下载文件存在  (通过无乱码)
    @Test
    public void downloadExists() {
       downloadFormat("E:\\Idea\\workspaces\\java12\\file_client\\src\\test\\resources\\file\\controller2.txt");
    }


    // 测试下载文件接口, 文件类型为 doc  (通过无乱码)
    @Test
    public void downloadDoc() {
        downloadFormat("E:\\Idea\\workspaces\\java12\\file_client\\src\\test\\resources\\file\\业务2d.doc");
    }

    // 测试下载文件接口, 文件类型为 wps (通过无乱码)
    @Test
    public void downloadWps() {
        downloadFormat("E:\\Idea\\workspaces\\java12\\file_client\\src\\test\\resources\\file\\业务2w.wps");
    }

    // 测试文件下载接口, 文件类型为 PDF (通过无乱码)
    @Test
    public void downloadPDF() {
        downloadFormat("E:\\Idea\\workspaces\\java12\\file_client\\src\\test\\resources\\file\\业务2p.pdf");
    }

    // 测试文件下载接口, 文件类型为 xml (通过无乱码)
    @Test
    public void downloadXML() {
        downloadFormat("E:\\Idea\\workspaces\\java12\\file_client\\src\\test\\resources\\file\\file_server_pom.xml");
    }

    // 测试文件下载接口, 文件类型为 rar (通过无乱码)
    @Test
    public void downloadRAR() {
        downloadFormat("E:\\Idea\\workspaces\\java12\\file_client\\src\\test\\resources\\file\\业务2r.rar");
    }

    // 测试文件下载接口, 文件类型为 html (通过无乱码)
    @Test
    public void downloadHtml() {
        downloadFormat("E:\\Idea\\workspaces\\java12\\file_client\\src\\test\\resources\\file\\welcom.html");
    }

    // 查询文件元信息, 且文件元信息存在  (通过)
    @Test
    public void selectMetaInfoExists() {
        baseUrl = "http://localhost:8080/file/get";
        String filename = uploadFormat("E:\\Idea\\workspaces\\java12\\file_client\\src\\test\\resources\\file\\业务3d.doc");
        String result = "";

        try {
            // 调用文件元信息查询函数
            result = HttpUtils.selectMetaInfo(baseUrl, filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertNotEquals("", result);
    }

    // 查询文件元信息, 且文件元信息不存在  (通过)
    @Test
    public void selectMetaInfoNotExists() {
        baseUrl = "http://localhost:8080/file/get";
        String filename = "0d6b3852-ccf9-4de4-9b93-afe16dcc7dfs";
        String result = null;

        try {
            // 调用文件元信息查询函数, result = ""
            result = HttpUtils.selectMetaInfo(baseUrl, filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals("", result);  // 通过
    }


    // 上传文件格式
    private static String uploadFormat(String fileName) {
        String url = "http://localhost:8080/file/upload";
        // 表单文本选项
        Map<String, String> textMap = null;
        String[] filePath = new String[1];
        filePath[0] = fileName;
        String result = null;
        try {
            // 调用文件上传函数
            result = HttpUtils.formUpload(url, textMap, filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertNotEquals(null, result);
        return result.substring(result.indexOf("\"") + 1, result.lastIndexOf("\""));
    }

    // 下载文件格式
    private static String downloadFormat(String fileName) {
        String url = "http://localhost:8080/file/download";
        String filename = uploadFormat(fileName);
        String savePath = "E:/TMP2";
        String result = "";
        try {
            // 调用文件下载函数
            result = HttpUtils.download(url, savePath, filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals("success", result);
        return result;
    }
}
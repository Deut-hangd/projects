package org.zero.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.zero.service.FilesService;

import java.io.*;

/**
 *  Controller 层单元测试
 */
@RunWith(SpringRunner.class)
@WebMvcTest(FilesController.class)
public class FilesControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FilesService filesService;

    // 输入流
    private InputStream inputStream = null;

    @Before
    public void setup() throws FileNotFoundException {
        // 打开流
        inputStream = new FileInputStream(new File("F:/study/桌面/技术study/redis/Redis集群.txt"));
    }

    // 测试 Controller 层文件上传接口  (响应成功, 响应状态码 200)
    @Test
    public void testUpload() throws Throwable{
        // 模拟文件上传的请求
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.fileUpload("http://localhost:8080/file/upload").
                file(new MockMultipartFile("filename", "Redis集群.txt", "application/octet-stream", inputStream)));
        MvcResult mvcResult = resultActions.andDo(MockMvcResultHandlers.print())  // 处理器, 对结果进行处理, 当前为输出响应结果
                .andExpect(MockMvcResultMatchers.status().isOk())  // 执行完成后的断言
                .andReturn();  // 返回响应的结果
        String result = mvcResult.getResponse().getContentAsString();
        System.out.println("==========结果为：==========\n" + result + "\n");
    }

    // 测试 Controller 层文件下载接口, url 中包含 UUID (成功, 响应状态码 200)
    @Test
    public void testDownloadIncludeId() throws Exception {
        // 模拟一个 GET 请求
        mockMvc.perform(MockMvcRequestBuilders.get("/file/download" + "/" + "8bfec37e-2bde-4968-b46e-9ed97fac9c67"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    // 测试 Controller 层文件下载接口, url 中包含 UUID (失败, 响应状态码 404)
    @Test
    public void testDownloadUnincludeId() throws Exception {
        // 模拟一个 GET 请求
        mockMvc.perform(MockMvcRequestBuilders.get("/file/download"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    // 测试 Controller 层文件元信息获取接口, url 包含 UUID  (成功, 响应状态码 200)
    @Test
    public void testSelectMetaInfoIncludeId() throws Exception {
        // 模拟一个 GET 请求
        mockMvc.perform(MockMvcRequestBuilders.get("/file/get" + "/" + "8bfec37e-2bde-4968-b46e-9ed97fac9c67"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    // 测试 Controller 层文件元信息获取接口, url 不包含 UUID  (失败, 响应状态码 404)
    @Test
    public void testSelectMetaInfoUnincludeId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/file/get"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    // 测试 Controller 层文件元信息获取接口, url 包含数据库中不存在的 UUID   (成功, 响应状态码 200)
    @Test
    public void testSelectMetaInfoIncludeErrorId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/file/get" + "/" + "5gfec37e-2bde-4968-b46e-9ed97fac9c67"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    // 释放资源
    @After
    public void tearDown() throws IOException {
        // 关闭流
        if (inputStream != null) {
            inputStream.close();
            inputStream = null;
        }
    }


}

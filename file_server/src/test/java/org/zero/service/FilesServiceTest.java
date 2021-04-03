package org.zero.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.multipart.MultipartFile;
import org.zero.mapper.MyFileMapper;
import org.zero.model.MyFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;

/**
 * service 层单元测试
 */
@RunWith(MockitoJUnitRunner.class)
public class FilesServiceTest {

    @InjectMocks
    private FilesService filesService;

    @Mock
    private MyFileMapper myFileMapper;

    // 验证 upload 时所用的参数
    private MultipartFile[] file = null;

    private static String[] fileNames = null;

    // 自定义日期格式
    private final static DateFormat DF = new SimpleDateFormat("yyyyMMdd");

    @Before
    public void setup() throws IOException {
        // 创建一个包含三个文件名的数组, 并进行赋值
        fileNames = new String[3];
        fileNames[0] = "E:/TMP/20210403/5e79f7af-7c28-4748-af68-7b1f5b70400c.txt";
        fileNames[1] = "E:/TMP/20210403/6e0cfa73-e49e-492d-939d-2816512cbe2f.txt";
        fileNames[2] = "E:/TMP/20210403/8bfec37e-2bde-4968-b46e-9ed97fac9c67.txt";
    }


    // 测试上传单个文件操作 (通过)
    @Test
    public void upload() throws IOException {
        file = new MultipartFile[1];
        initMultiPartFile(file, file.length);
        // 调用文件上传方法
        List<String> result = filesService.upload(file);
        System.out.println(result);
        // 验证 getOriginalFilename 调用次数(4次)
        Mockito.verify(file[0], times(4)).getOriginalFilename();
    }

    // 测试上传空文件操作 (失败, 空指针异常)
    @Test
    public void uploadNull()  {
        file = null;
        // 调用文件上传方法
        List<String> result = filesService.upload(file);
        System.out.println(result);
        // 验证 getOriginalFilename 调用次数(4次)
        Mockito.verify(file[0], times(4)).getOriginalFilename();
    }

    // 测试上传多个文件操作 (通过)
    @Test
    public void multiUpload() throws IOException {
        file = new MultipartFile[3];
        initMultiPartFile(file, file.length);
        // 调用文件上传方法
        List<String> result = filesService.upload(file);
        System.out.println(result);
        // 验证 getOriginalFilename 调用次数(12次)
        Mockito.verify(file[0], times(4)).getOriginalFilename();
        Mockito.verify(file[1], times(4)).getOriginalFilename();
        Mockito.verify(file[2], times(4)).getOriginalFilename();
    }

    // 以数据库中有的 UUID 查询一条文件元信息记录 (通过, 查询结果为 null)
    @Test
    public void selectMetaInfoExists() {
        String id = "31d77763-ae75-446b-8905-0e920479e08f";
        MyFile myFile = myFileMapper.selectByPrimaryKey(id);
        System.out.println(myFile);
        // 返回结果为空
    }

    // 以数据库中没有的 UUID 查询一条文件元信息记录 (通过, 查询结果为 null)
    @Test
    public void selectMetaInfoNotExists() {
        String id = UUID.randomUUID().toString();
        MyFile myFile = myFileMapper.selectByPrimaryKey(id);
        System.out.println(myFile);
        // 返回结果为空
    }

    // 预设值, 模拟返回值
    public static void initMultiPartFile(MultipartFile[] file, int n) throws IOException {
        for (int i = 0; i < n; i++) {
            // mock 一个 MultipeartFile 对象, 起到参数补齐的作用, 以便测试
            file[i] = Mockito.mock(MultipartFile.class);
            // 设置预期, 即当 getOriginalFilename 调用时返回 raw.txt
            Mockito.doReturn(fileNames[i].substring(fileNames[i].lastIndexOf("/") + 1))
                    .when(file[i]).getOriginalFilename();
            // 设置预期, 即当 getInputStream 调用时返回一个 InputStream 输入流
            FileInputStream fileInputStream = new FileInputStream(new File(fileNames[i]));
            Mockito.doReturn(fileInputStream).when(file[i]).getInputStream();
        }
    }


}
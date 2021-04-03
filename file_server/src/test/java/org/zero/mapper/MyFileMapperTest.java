package org.zero.mapper;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.zero.Application;
import org.zero.model.MyFile;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Mapper 层单元测试
 */
//指定为Spring环境中的单元测试
@RunWith(SpringRunner.class)
//指定为SpringBoot环境的单元测试，Application为启动类
@SpringBootTest(classes = Application.class)
//使用事务，在SpringBoot的单元测试中会自动回滚
@Transactional
public class MyFileMapperTest {
    @Autowired
    private MyFileMapper myFileMapper;

    // 定义日期格式
    private static final DateFormat DF = new SimpleDateFormat("yyyyMMdd");

    // 用来测试的数据库记录
    private MyFile file = null;

    // 每测一个方法, 生成一条数据库记录
    @Before
    public void setup() {
        String id = UUID.randomUUID().toString();
        // 创建并初始化文件元信息对象
        file = new MyFile();
        file.setId(id);
        file.setName("raw.txt");
        file.setType("txt");
        file.setSize(1024L);
        file.setSaveAddress("E/TMP" + "/" + DF.format(new Date()) + "txt");
    }

    // 测试数据库插入操作  (通过)
    @Test
    public void insertTest() {

        Integer result = myFileMapper.insert(file);
        //Assert.assertNotEquals(new Integer(1), result);
        System.out.println(result);
    }

    // 测试向数据库中插入空数据的情况  (失败)
    @Test
    public void insertNullTest() {
        Integer result = myFileMapper.insert(null);
        System.out.println(result);
        // 数据库报错, 主键不能为空
    }

    // 测试数据库插入操作, 主键为 null (失败)
    @Test
    public void insertIdNullTest() {
        file.setId(null);
        Integer result = myFileMapper.insert(file);
        //Assert.assertNotEquals(new Integer(1), result);
        System.out.println(result);
        // 数据库报错, 主键不能为空
    }

    // 测试根据 UUID 查询数据库记录操作
    @Test
    public void selectByPrimaryKey1() {
        // 数据无数据, 先插入一条记录, 再查询
        myFileMapper.insert(file);
        file = myFileMapper.selectByPrimaryKey(file.getId());
        System.out.println(file.toString());
    }

    // 测试 UUID 为空的时候, 查询情况
    @Test
    public void selectByPrimaryKey2() {
        String id = null;
        file = myFileMapper.selectByPrimaryKey(id);
        // file 的值为 null, 即查询结果为空
        System.out.println(file);
    }

}

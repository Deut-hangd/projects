package org.zero.tool;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Generator {

    private static final boolean OVERWRITE = true;

    private static final String CONFIG_PATH = "generator/config.xml";

    public static void main(String[] args) throws IOException, XMLParserException, SQLException, InterruptedException, InvalidConfigurationException {
        System.out.println("--------------------start generator------------- ------");
        List<String> warnings = new ArrayList<>();

        // 获取当前线程的类加载器
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        // 打开CONFIG_PATH指定的文件的输入流
        InputStream is = classLoader.getResourceAsStream(CONFIG_PATH);
        // 创建配置文件的解析器
        ConfigurationParser cp = new ConfigurationParser(warnings);
        // 解析配置文件
        Configuration config = cp.parseConfiguration(is);
        // 默认回调脚本
        DefaultShellCallback callback = new DefaultShellCallback(OVERWRITE);
        // 创建mybatis生成器
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        // 调用生成器的生成方法
        myBatisGenerator.generate(null);
        warnings.forEach(System.out::println);
        System.out.println("--------------------end generator--------------- ----");
    }
}

package org.zero.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.zero.mapper.MyFileMapper;
import org.zero.model.MyFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 *  文件业务
 */
@Service
public class FilesService {

    @Autowired
    private MyFileMapper myFileMapper;

    // 设置日期格式
    private static final DateFormat DF = new SimpleDateFormat("yyyyMMdd");

    // 获取文件保存的本地路径上下文
    @Value("${user.files.local-path}")
    private String fileLocalPath;

    // 开启事务, 所有文件成功插入数据库, 则 commit, 否则 rollback
    @Transactional
    public List<String> upload(MultipartFile[] file) {
        List<String> idList = new ArrayList<>(file.length);
        // 循环保存所有文件
        for (int i = 0; i < file.length; i++) {
            // 未成功获取的文件, 不进行处理
            if ("".equals(file[i].getOriginalFilename())) {
                continue;
            }
            // 生成 UUID, 即唯一文件名
            idList.add(UUID.randomUUID().toString());
            // 获取当前时间
            Date date = new Date();
            // 拼接文件的本地保存目录
            String dirUrl = fileLocalPath + "/" + DF.format(date);
            File saveFile = new File(dirUrl);
            // 若文件的本地保存目录不存在, 则创建该目录结构
            if (!saveFile.exists()) saveFile.mkdirs();
            // 获取文件后缀起始位置
            int indexOfSuffix = file[i].getOriginalFilename().lastIndexOf(".");
            String suffix = null;
            if (indexOfSuffix != -1) {
                // 获取文件后缀
                suffix = file[i].getOriginalFilename()
                        .substring(indexOfSuffix);
            }
            // 结合 UUID 生成新的文件名, 以保证文件名唯一
            String fileName = dirUrl + "/" + idList.get(i) + suffix;
            DataInputStream dataInputStream = null;
            DataOutputStream dataOutputStream = null;
            // 将文件保存到本地
            try {
                // 打开数据输入输出流
                InputStream j = file[i].getInputStream();
                dataInputStream = new DataInputStream(file[i].getInputStream());
                dataOutputStream = new DataOutputStream(new FileOutputStream(new File(fileName)));
                int bytes = 0;
                // 数据缓冲区
                byte[] bufferOut = new byte[1024];
                while ((bytes = dataInputStream.read(bufferOut)) != -1) {
                    dataOutputStream.write(bufferOut, 0, bytes);
                }
            } catch (Exception e) {
                // 文件保存出错, 抛出异常
                throw new RuntimeException(e);
            } finally {
                // 关闭流
                close(dataInputStream, dataOutputStream);
            }
            // 生成文件元信息对象, 并将文件的元信息保存到数据库中
            MyFile myFile = new MyFile();
            myFile.setId(idList.get(i));
            myFile.setName(file[i].getOriginalFilename()
                    .substring(0, indexOfSuffix));
            myFile.setType(suffix.substring(1));
            myFile.setSize(file[i].getSize());
            myFile.setSaveAddress(fileName);
            myFileMapper.insert(myFile);
        }
        return idList;
    }

    // 下载本地文件
    public void download(String id, HttpServletResponse response) {
        // 依据UUID查询数据库, 获取文件元信息
        MyFile myFile = myFileMapper.selectByPrimaryKey(id);
        // 指定的时间格式获取文件所在目录
        String dirName = DF.format(myFile.getCreateTime());
        // 拼接合成文件的完整本地目录
        String fileName = fileLocalPath + "/" + dirName + "/" + id + "." + myFile.getType();
        DataInputStream dataInputStream = null;
        DataOutputStream dataOutputStream = null;
        // 打开文件的读取流
        try {
            dataInputStream = new DataInputStream(new FileInputStream(new File(fileName)));
            byte[] data = new byte[dataInputStream.available()];
            String diskFileName = myFile.getName() + myFile.getType();
            // 设置响应头和响应体
            response.setContentType("application/octet-stream;");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + diskFileName + "\"");
            response.setContentLength(data.length);
            response.setHeader("Content-Range", "" + Integer.valueOf(data.length - 1));
            response.setHeader("Accept-Ranges", "bytes");
            response.setHeader("File-Type", myFile.getType());
            // 获取响应输出流
            dataOutputStream = new DataOutputStream(response.getOutputStream());
            int bytes = 0;
            // 数据缓冲区
            byte[] bufferOut = new byte[1024];
            // 读取并保存文件数据
            while ((bytes = dataInputStream.read(bufferOut)) != -1) {
                dataOutputStream.write(bufferOut, 0, bytes);
            }
            // 刷新数据输出流
            dataOutputStream.flush();
        } catch (Exception e) {
            // 设置响应状态码为 410
            response.setStatus(410);
            throw new RuntimeException(e);
        } finally {
            close(dataInputStream, dataOutputStream);
        }

    }

    // 获取文件的元信息
    public Object selectMetaInfo(String id) {
        // 根据文件的 UUID 查询数据库中的一条记录(即文件元信息)
        return myFileMapper.selectByPrimaryKey(id);
    }


    // 关闭流
    private void close (InputStream inputStream, OutputStream outputStream) {
        // 关闭输入流
        try {
            if (inputStream != null) {
                inputStream.close();
                inputStream = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 关闭输出流
        try {
            if (outputStream != null) {
                outputStream.close();
                outputStream = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

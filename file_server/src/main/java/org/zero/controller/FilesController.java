package org.zero.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.zero.service.FilesService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 *  文件业务控制器
 */
@RestController
@RequestMapping("/file")
public class FilesController {

    @Autowired
    private FilesService filesService;

    @GetMapping("/welcome")
    public String welcome(HttpServletResponse response) {
        return "";
    }

    // 控制文件上传
    @PostMapping("/upload")
    public Object uploadFile(@RequestParam("filename")MultipartFile[] file) {
        // 上传文件
        return filesService.upload(file);
    }

    // 控制文件下载
    @GetMapping("/download/{id}")
    public Object download(@PathVariable String id, HttpServletResponse response) {
        // 下载服务器本地文件到客户端
        filesService.download(id, response);
        return null;
    }

    // 获取文件元信息
    @GetMapping("/get/{id}")
    public Object selectMetaInfo(@PathVariable String id) {
        // 根据 URL 提供的 id 查询数据库中的数据
        return filesService.selectMetaInfo(id);
    }
}

package org.zero.model;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 文件表
 */
@Getter
@Setter
@ToString
public class MyFile {
    /**
     * 当前文件名(UUID)
     */
    private String id;

    /**
     * 原始文件名
     */
    private String name;

    /**
     * 文件大小
     */
    private Long size;

    /**
     * 文件类型
     */
    private String type;

    /**
     * 文件保存地址
     */
    private String saveAddress;

    /**
     * 文件创建时间
     */
    private Date createTime;
}
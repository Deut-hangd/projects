package zero.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class Article {
    // 文章 id
    private Long id;
    // 文章拥有者 id
    private Long userId;
    // 文章背景图片
    private String coverImage;
    // 文章所属分类的 id
    private Integer categoryId;
    // 文章的状态
    private Byte status;
    // 文章的标题
    private String title;
    // 文章的内容
    private String content;
    // 观看数量
    private Long viewCount;
    // 创建时间
    private Date createdAt;
    // 修改时间
    private Date updatedAt;
    // 文章作者
    private User author;
    // 评论数量
    private Integer commentCount;
    // 评论表
    private List<Comment> commentList;
}

package zero.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.Date;

@Getter
@Setter
@ToString
public class Comment {
    // 评论 id
    private Long id;
    // 与评论关联用户的 id
    private Long userId;
    // 与评论关联文章的 id
    private Long articleId;
    // 评论内容
    private String content;
    // 创建时间
    private Date createdAt;
    // 用户
    private User user;
}
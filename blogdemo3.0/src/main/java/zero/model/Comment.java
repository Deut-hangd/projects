package zero.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
@Getter
@Setter
@ToString
public class Comment {
    private Long id;

    private Long userId;

    private Long articleId;

    private String content;

    private Date createdAt;

    private User user;

}
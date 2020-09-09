package zero.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Category {
    // 分类的 id
    private Long id;
    // 用户 id
    private Long userId;
    // 分类名
    private String name;
}
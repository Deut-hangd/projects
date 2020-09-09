package zero.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString

public class User {
    // 用户 id
    private Long id;
    // 用户姓名
    private String username;
    // 用户密码
    private String password;
    // 用户昵称
    private String nickname;
    // 副标题
    private String avatar;
    // 用户
    private User user;

}
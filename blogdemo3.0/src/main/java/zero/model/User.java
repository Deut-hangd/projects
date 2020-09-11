package zero.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class User {
    private Long id;

    private String username;

    private String password;

    private String nickname;

    private String avatar;

}
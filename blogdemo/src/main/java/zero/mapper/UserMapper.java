package zero.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import zero.model.User;

@Mapper
public interface UserMapper {
    // 通过用户 id 删除用户
    int deleteByPrimaryKey(Long id);

    // 插入用户
    int insert(User record);

    // 通过 id 删除 用户
    User selectByPrimaryKey(Long id);

    // 查询所有用户
    List<User> selectAll();

    // 修改用户
    int updateByPrimaryKey(User record);

    // 通过用户的 username 和 password 查询用户的登录信息
    User login(@Param("username") String username, @Param("password") String password);
}
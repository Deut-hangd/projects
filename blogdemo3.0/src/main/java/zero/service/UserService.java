package zero.service;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zero.mapper.UserMapper;
import zero.model.User;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;  //自动装配依赖
    // 根据用户名和用密码验证用户信息
    public User login(@Param("username")String username, @Param("password") String password) {
        return userMapper.login(username, password);
    }

    // 查询所有用户
    public List<User> selectAll(){ return userMapper.selectAll();}

    // 添加用户信息
    public int insert(User user){ return userMapper.insert(user);}
}

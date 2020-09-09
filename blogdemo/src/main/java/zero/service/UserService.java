package zero.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zero.mapper.UserMapper;
import zero.model.User;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;  //自动装配依赖
    // 根据用户名和用密码验证用户信息
    public User login(String username, String password) {
        return userMapper.login(username, password);
    }
}

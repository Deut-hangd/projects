package zero.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import zero.model.Category;
import zero.model.User;
import zero.service.CategoryService;
import zero.service.UserService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    //登录校验
    @RequestMapping("/login")
    public String login(String username, String password, HttpServletRequest request){
        // 若 username 和 password 都为空,返回到登录界面
        if (username == null || password == null)
            return "login";
        // 到数据库中验证用户信息
        User user = userService.login(username, password);
        // 若该用户的信息为空,返回到登录界面
        if (user == null)
            return "login";
        else{
            // 将用户的信息保存到 session 中
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            return "/";
        }

    }

    // 用户注册

    @RequestMapping("/register")
    public String register(){
        return "register";
    }

    @RequestMapping("/register/helper")
    public String registerHelper(String username, String password, String nickname){
        // 用户输入信息有空
        if (username == null || password == null || nickname == null){
            return "register";
        }
        List<User> users = userService.selectAll();
        // 若用户信息已存在返回登录
        for (User user : users){
            if (user.getUsername().equals(username.trim())){
                return "login";
            }
        }
        // 若用户信息不存在,将用户信息加入注册表
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setNickname(nickname);
        user.setAvatar("https://picsum.photos/id/1/200/200");
        userService.insert(user);
        return "login";
    }
}

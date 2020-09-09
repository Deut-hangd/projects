package zero.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import zero.model.User;
import zero.service.UserService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

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
}

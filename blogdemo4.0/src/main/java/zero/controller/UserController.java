package zero.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
    public String registerHelper(User user, Model model){
        // 用户输入信息有空
        if (user.getUsername() == null || user.getPassword() == null || user.getNickname() == null){
            model.addAttribute("mes", "注册信息存在空值.");
            return "forward:/register";
        }
        List<User> users = userService.selectAll();
        // 若用户信息已存在返回登录
        for (User user1 : users){
            if (user1.getUsername().equals(user.getUsername().trim())){
                model.addAttribute("mes", "该用户名已存在,请更换用户名.");
                return "forward:/register";
            }
        }
        // 若用户信息不存在,将用户信息加入注册表
//        User user = new User();
//        user.setUsername(username);
//        user.setPassword(password);
//        user.setNickname(nickname);
        user.setAvatar("https://picsum.photos/id/1/200/200");
        userService.insert(user);
        model.addAttribute("mes", "注册成功请登录.");
        return "login";
    }

    @RequestMapping("/exit")
    public String exit(HttpSession session){
        session.invalidate();
        return "redirect:/login";
    }


}

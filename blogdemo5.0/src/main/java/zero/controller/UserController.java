package zero.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.servlet.mvc.method.annotation.HttpEntityMethodProcessor;
import zero.model.User;
import zero.service.UserService;
import zero.util.VerifyCode;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    //登录校验
    @RequestMapping("/login")
    public String login(User user1, String verifyInput, HttpSession session){
        // 若 username 和 password 都为空,返回到登录界面
        if (user1.getUsername() == null || user1.getPassword() == null) {
            return "login";
        }
        // 验证验证码是否正确(忽略大小写)
        if (!verifyInput.toLowerCase().equals(session.getAttribute("verifyText"))){
            return "login";
        }
        // 到数据库中验证用户信息
        User user = userService.login(user1.getUsername(), user1.getPassword());
        // 若该用户的信息为空,返回到登录界面
        if (user == null) {
            return "login";
        }else{
            // 将用户的信息保存到 session 中
            session.setAttribute("user", user);
//            model.addAttribute("user", user);
            return "/";
        }

    }

    // 用户注册
    @RequestMapping("/register")
    public String register(){
        return "register";
    }


    @RequestMapping("/register/helper")
    public String registerHelper(User user, Model model,
                                 HttpSession session, String verifyInput) throws IOException {
        if (verifyInput.toLowerCase().equals(session.getAttribute("verifyText"))){
            // 用户输入信息有空
            if (user.getUsername() == null || user.getPassword() == null || user.getNickname() == null){
                model.addAttribute("mes", "注册信息存在空值.");
                return "redirect:/register";
            }
            List<User> users = userService.selectAll();
            // 若用户信息已存在返回登录
            for (User user1 : users){
                if (user1.getUsername().equals(user.getUsername().trim())){
                    model.addAttribute("mes", "该用户名已存在,请更换用户名.");
                    return "redirect:/register";
                }
            }
            // 若用户信息不存在,将用户信息加入注册表
//        User user = new User();
//        user.setUsername(username);
//        user.setPassword(password);
//        user.setNickname(nickname);
            // 设置用户的化身
            user.setAvatar("https://picsum.photos/id/1/100/100");
            //插入用户信息
            userService.insert(user);
            model.addAttribute("mes", "注册成功请登录.");
            return "login";
        }
        return "redirect:/register";
    }

    @RequestMapping("/getVerify")
    public void getVerify(HttpSession session, HttpServletResponse resp){
        try {
            int width = 150;
            int height = 46;
            // 生成对应宽高的初始图片
            BufferedImage verifyImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            // 生成验证码字符并加上噪点和干扰线,返回值为字符串
            String verifyText = VerifyCode.drawRandomText(width, height, verifyImg);
            // 忽略大小写
            session.setAttribute("verifyText", verifyText.toLowerCase());
            // 设置响应格式
            resp.setContentType("image/png");
            // 获取响应输出流
            OutputStream os = resp.getOutputStream();
            // 将生成的验证码响应给浏览器
            ImageIO.write(verifyImg, "png", os);
            // 刷新输出流缓冲区
            os.flush();
            // 关闭流缓冲区
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @RequestMapping("/exit")
    public String exit(HttpSession session){
        // 无效化 session
        session.invalidate();
        return "redirect:/login";
    }



}

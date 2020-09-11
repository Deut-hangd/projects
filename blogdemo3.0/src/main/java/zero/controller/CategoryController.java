package zero.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import zero.model.Category;
import zero.model.User;
import zero.service.CategoryService;
import javax.servlet.http.HttpSession;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;  // 自动装配依赖

    //添加分类
    @RequestMapping(value = "/c/add", method = RequestMethod.POST)
    public String addCategory(HttpSession session, Category category){
        // 获取 session 中的用户信息
        User user = (User)session.getAttribute("user");
        // 将本次的分类与进行操作的用户关联
        category.setUserId(user.getId());
        // 将新的分类加入 category 表
        int num = categoryService.insert(category);
        // 重定向
        return "redirect:/writer";
    }
}

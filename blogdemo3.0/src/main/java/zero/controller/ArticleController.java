package zero.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import zero.model.Article;
import zero.model.Category;
import zero.model.Comment;
import zero.model.User;
import zero.service.ArticleService;
import zero.service.CategoryService;
import zero.service.CommentService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class ArticleController {

    @Autowired
    private ArticleService articleService;  // 自动装配依赖

    @Autowired
    private CommentService commentService;  // 自动装配依赖

    @Autowired
    private CategoryService categoryService;  // 自动装配依赖

    // 目录页面
    @RequestMapping(value = "/", method = {RequestMethod.POST, RequestMethod.GET})
    public String index(Model model, HttpServletRequest request){
        // 查询所有文章
        List<Article> allArticles = articleService.queryArticles();
        // 添加前端所需要的数据 articles
        model.addAttribute("articleList", allArticles);
        // 返回到 index 页面
        return "index";
    }

    // 添加评论后重新打印所有评论
    @RequestMapping("/a/{id}")
    public String detail(@PathVariable("id") Long id, Model model){
        // 通过文章 id 查询文章
        Article article = articleService.queryArticle(id);
        // 更新文章访问量
        article.setViewCount(article.getViewCount() + 1);
        articleService.updateByCondition(article);
        // 通过文章 id 查询所有的评价
        List<Comment> comments = commentService.queryComments(id);
        // 将与当前文章有关的评论都加入文章
        article.setCommentList(comments);
        // 添加前端所需要的的数据 article
        model.addAttribute("article", article);
        // 返回到 info 页面
        return "info";
    }

    @RequestMapping("/writer")
    public String writer(HttpSession session, Long activeCid, Model model){
        // 通过 session 获取用户信息
        User user = (User) session.getAttribute("user");
        // 根据用户的 id 查询其所有的文章信息
        List<Article> articles = articleService.queryArticlesByUserId(user.getId());
        // 添加前端所需的文章列表信息
        model.addAttribute("articleList", articles);
        // 根据用户的 id 查询其所有的分类信息
        List<Category> categories = categoryService.queryCategoriesByUserId(user.getId());
        // 添加前端所需的信息 (评论表和文章 id)
        model.addAttribute("categoryList", categories);
        Long defaultCategory = categoryService.queryCategoryById(new Long(1)).getId();
        model.addAttribute("activeCid",
                activeCid == null ? defaultCategory : activeCid);
        // 返回到前端 writer 页面
        return "writer";
    }

    /**
     * 跳转到新增/修改文章页面(editor页面)
     * @param type 若 type 为 1,新增文章; 若 type 为 2,修改文章
     * @param id 新增时,代表 categoryId, 修改时,代表 articleId
     * @param model editor页面需要 type, category 和 article, 当新增时需要 activeCid, 修改时需要 article.id
     * @return
     */
    // 进入新建或修改文章界面
    @RequestMapping("/writer/forward/{type}/{id}/editor")
    public String editor(@PathVariable("type") Integer type,
                         @PathVariable("id") Long id, Model model){
        Category category;
        // 若 type 等于 1,查询有关 id 的文章分类
        if (type == 1){
            category = categoryService.queryCategoryById(id);
            model.addAttribute("activeCid", id);
        }else{  // 若 type 不等于 1,根据 id 查询文章 (修改文章)
            Article article = articleService.queryArticle(id);
            model.addAttribute("article", article);
            // 根据之前查询到的文章所属分类的 id, 查询其分类信息
            category = categoryService.queryCategoryById(new Long(article.getCategoryId()));
        }
        // 添加前端所需的内容
        model.addAttribute("type", type);
        model.addAttribute("category", category);
        // 返回到前端页面 editor
        return "editor";
    }

    // 执行新建文章或修改文章
    @RequestMapping(value = "/writer/article/{type}/{id}", method = RequestMethod.POST)
    public String publish(@PathVariable("type") Integer type,
                          @PathVariable("id") Long id,
                          HttpSession session,
                          Article article,
                          Model model){
        // 设置文章的创建时间
        article.setUpdatedAt(new Date());
        if (type == 1){  // 若 type 等于 1,即添加文章
            // 设置文章的分类 id
            article.setCategoryId(id.intValue());
            // 通过 session 获取用户信息
            User user = (User)session.getAttribute("user");
            // 设置文章的用户 id
            article.setUserId(user.getId());
            // 设置文章的背景图片
            article.setCoverImage("https://picsum.photos/id/1/400/300");
            // 设置文章的创建时间
            article.setCreatedAt(new Date());
            // 设置文章的状态
            article.setStatus((byte)0);
            // 设置文章的访问量
            article.setViewCount(0L);
            // 设置评论的数量
            article.setCommentCount(0);
            // 将文章保存到文章表中
            int num = articleService.insert(article);
            // 获取文章 id
            id = article.getId();

        }else {
            // 设置文章 id
            article.setId(id);
            // 根据 article 修改文章内容
            int num = articleService.updateByCondition(article);
        }
        // 重定向到前端 editor 页面
        return String.format("redirect:/writer/forward/2/%s/editor", id);
    }

    // 删除文章
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        // 删除操作
        int result = articleService.deleteByPrimaryKey(id);
        // 使主键连续
        articleService.orderedPrimaryKey();
        return "forward:/writer";
    }

}

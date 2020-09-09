package zero.controller;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import zero.model.Comment;
import zero.service.CommentService;
import java.util.Date;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;  // 自动装配依赖

    // 添加评论
    @RequestMapping(value = "/a/{id}/comments", method = RequestMethod.POST)
    public String addComment(@PathVariable("id") Long id, @Param("content")String content){
        // 创建一个 Comment 实例,用以保存新添加的评论
        Comment comment = new Comment();
        // 保存被评论的文章 id
        comment.setArticleId(id);
        // 保存评论的内容
        comment.setContent(content);
        // 保存评论创建的时间
        comment.setCreatedAt(new Date());
        // 将评论插入 comment 表
        int num = commentService.insert(comment);
        // 重定向到评论文章的页面
        return "redirect:/a/" + id;
    }
}

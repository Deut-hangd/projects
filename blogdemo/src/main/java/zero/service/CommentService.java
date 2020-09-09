package zero.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zero.mapper.CommentMapper;
import zero.model.Comment;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;  // 自动装配依赖
    // 根据 id 查询所有评价
    public List<Comment> queryComments(Long id) {
        return commentMapper.queryComments(id);
    }
    // 插入新的评价
    public int insert(Comment comment) {
        return commentMapper.insert(comment);
    }
}

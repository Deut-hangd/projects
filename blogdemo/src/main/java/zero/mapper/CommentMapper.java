package zero.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import zero.model.Comment;

@Mapper
public interface CommentMapper {
    // 通过文章 id 删除评论
    int deleteByPrimaryKey(Long id);

    // 插入评论
    int insert(Comment record);

    // 通过文章 id 删除评论
    Comment selectByPrimaryKey(Long id);

    // 查询所有评论
    List<Comment> selectAll();

    // 修改评论
    int updateByPrimaryKey(Comment record);

    // 查询某个文章的所有评论
    List<Comment> queryComments(Long id);
}
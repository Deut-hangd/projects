package zero.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import zero.model.Article;

@Mapper
public interface ArticleMapper {
    // 根据主键删除文章
    int deleteByPrimaryKey(Long id);

    // 插入文章
    int insert(Article record);

    // 根据主键查询文章
    Article selectByPrimaryKey(Long id);

    // 查询所有文章
    List<Article> selectAll();

    // 根据主键修改文章
    int updateByPrimaryKey(Article record);

    // 根据用户 id 查询文章
    List<Article> queryArticlesByUserId(Long id);

    // 根据己知情况修改文章
    int updateByCondition(Article article);
}
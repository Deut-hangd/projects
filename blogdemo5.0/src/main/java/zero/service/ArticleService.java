package zero.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zero.mapper.ArticleMapper;
import zero.model.Article;
import java.util.List;

@Service
public class ArticleService {
    @Autowired
    private ArticleMapper articleMapper;  // 装配依赖

    // 查询所有文章
    public List<Article> queryArticles() {
        return articleMapper.selectAll();
    }
    // 根据文章 id 查询文章
    public Article queryArticle(Long id) {
       return articleMapper.selectByPrimaryKey(id);
    }
    // 根据用户 id 查询文章
    public List<Article> queryArticlesByUserId(Long id) {
        return articleMapper.queryArticlesByUserId(id);
}
    // 插入文章
    public int insert(Article article) {
        return articleMapper.insert(article);
    }
    // 根据新的文章对象修改就的文章对象
    public int updateByCondition(Article article) {
        return articleMapper.updateByCondition(article);
    }
    // 删除文章
    public int deleteByPrimaryKey(Long id){ return articleMapper.deleteByPrimaryKey(id);}

    public void orderedPrimaryKey(){ articleMapper.orderedPrimaryKey();}
}

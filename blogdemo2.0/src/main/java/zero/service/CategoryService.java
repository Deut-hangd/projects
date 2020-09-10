package zero.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zero.mapper.CategoryMapper;
import zero.model.Category;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;  // 自动装配依赖

    // 根据用的 id 查询其所有的文章分类
    public List<Category> queryCategoriesByUserId(Long id) {
        return categoryMapper.queryCategoriesByUserId(id);
    }
    // 插入新的分类
    public int insert(Category category) {
        return categoryMapper.insert(category);
    }
    // 通过文章 id 查询其所属分类
    public Category queryCategoryById(Long id) {
        return categoryMapper.selectByPrimaryKey(id);
    }
}

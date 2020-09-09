package zero.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import zero.model.Category;

@Mapper
public interface CategoryMapper {
    // 根据主键删除分类
    int deleteByPrimaryKey(Long id);

    // 插入分类
    int insert(Category record);

    // 根据主键查询分类
    Category selectByPrimaryKey(Long id);

    // 查询所有分类
    List<Category> selectAll();

    // 根据主键修改分类
    int updateByPrimaryKey(Category record);

    // 查询同一个用户的所有文章分类
    List<Category> queryCategoriesByUserId(Long id);
}
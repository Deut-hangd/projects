package zero.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import zero.model.Category;
@Mapper
public interface CategoryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Category record);

    Category selectByPrimaryKey(Long id);

    List<Category> selectAll();

    int updateByPrimaryKey(Category record);

    List<Category> queryCategoriesByUserId(Long id);
}
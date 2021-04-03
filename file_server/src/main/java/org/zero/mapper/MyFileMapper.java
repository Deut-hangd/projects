package org.zero.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.zero.base.BaseMapper;
import org.zero.model.MyFile;

@Mapper
public interface MyFileMapper extends BaseMapper<MyFile> {
    int deleteByPrimaryKey(String id);

    int insert(MyFile record);

    MyFile selectByPrimaryKey(String id);

    List<MyFile> selectAll();

    int updateByPrimaryKey(MyFile record);
}
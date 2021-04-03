package org.zero.base;

import java.util.List;

public interface BaseMapper<T> {

    int deleteByPrimaryKey(Integer id);

    int insert(T record);

    List<T> selectAll();

    int insertSelective(T record);

    T selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(T record);
}

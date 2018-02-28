package com.yunwenlong.dao;

import com.yunwenlong.model.BlogDictionary;

public interface BlogDictionaryMapper {
    int deleteByPrimaryKey(String id);

    int insert(BlogDictionary record);

    int insertSelective(BlogDictionary record);

    BlogDictionary selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BlogDictionary record);

    int updateByPrimaryKey(BlogDictionary record);
}
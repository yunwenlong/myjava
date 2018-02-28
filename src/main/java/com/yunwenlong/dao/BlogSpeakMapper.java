package com.yunwenlong.dao;

import com.yunwenlong.model.BlogSpeak;

public interface BlogSpeakMapper {
    int deleteByPrimaryKey(String id);

    int insert(BlogSpeak record);

    int insertSelective(BlogSpeak record);

    BlogSpeak selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BlogSpeak record);

    int updateByPrimaryKey(BlogSpeak record);
}
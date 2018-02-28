package com.yunwenlong.dao;

import com.yunwenlong.model.BlogInfo;

public interface BlogInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(BlogInfo record);

    int insertSelective(BlogInfo record);

    BlogInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BlogInfo record);

    int updateByPrimaryKeyWithBLOBs(BlogInfo record);

    int updateByPrimaryKey(BlogInfo record);
}
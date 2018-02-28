package com.yunwenlong.dao;

import com.yunwenlong.model.BlogLink;

public interface BlogLinkMapper {
    int insert(BlogLink record);

    int insertSelective(BlogLink record);
}
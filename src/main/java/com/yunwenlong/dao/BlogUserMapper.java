package com.yunwenlong.dao;

import java.util.List;
import java.util.Map;

import com.yunwenlong.model.BlogUser;

public interface BlogUserMapper {
	
    void deleteByPrimaryKey(String id);

    void insertSelective(BlogUser record);

    BlogUser selectByPrimaryKey(String id);
    
    long countUserByStatus(Map<String, Object> map);
    
    List<BlogUser> listUserByStatus(Map<String, Object> map);

    void updateUser(BlogUser record);

}
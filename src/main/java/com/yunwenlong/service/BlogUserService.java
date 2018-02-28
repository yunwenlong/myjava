package com.yunwenlong.service;

import java.util.List;
import java.util.Map;

import com.yunwenlong.model.BlogUser;

public interface BlogUserService {
	
    void removeById(String id);

    void saveUser(BlogUser user);

    BlogUser getByPrimaryKey(String id);
    
    long countUserByStatus(Map<String, Object> map);
    
    List<BlogUser> listUserByStatus(Map<String, Object> map);

    void updateUser(BlogUser user);
    

}
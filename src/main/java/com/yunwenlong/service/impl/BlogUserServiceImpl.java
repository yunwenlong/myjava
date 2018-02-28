package com.yunwenlong.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yunwenlong.dao.BlogUserMapper;
import com.yunwenlong.model.BlogUser;
import com.yunwenlong.service.BlogUserService;


@Service
@Transactional
public class BlogUserServiceImpl implements BlogUserService {
	
	@Autowired
	BlogUserMapper blogUserMapper;

	@Override
	public void removeById(String id) {
		blogUserMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void saveUser(BlogUser user) {
		blogUserMapper.insertSelective(user);
	}

	@Override
	public BlogUser getByPrimaryKey(String id) {
		return blogUserMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<BlogUser> listUserByStatus(Map<String, Object> map) {
		return blogUserMapper.listUserByStatus(map);
	}

	@Override
	public void updateUser(BlogUser user) {
		blogUserMapper.updateUser(user);
	}

	@Override
	public long countUserByStatus(Map<String, Object> map) {
		return blogUserMapper.countUserByStatus(map);
	}


}

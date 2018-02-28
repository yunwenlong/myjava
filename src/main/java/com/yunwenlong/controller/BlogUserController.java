package com.yunwenlong.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunwenlong.model.BlogUser;
import com.yunwenlong.model.PageBean;
import com.yunwenlong.model.ResponseBean;
import com.yunwenlong.service.BlogUserService;

@RequestMapping("user")
@Controller
public class BlogUserController {
	
	@Autowired
	private BlogUserService blogUserService;

	/**
	 * 
	 * @INFO 进入添加页面
	 * 2017年11月6日下午11:42:15
	 * @author 云文龙
	 * @return ResponseBean
	 * @return
	 */
	@RequestMapping("toSaveUser")
	public String toSaveUser(){
		return "";
	}
	
	/**
	 * 
	 * @INFO 保存用户信息
	 * 2017年11月6日下午11:42:15
	 * @author 云文龙
	 * @return ResponseBean
	 * @return
	 */
	@RequestMapping("saveUser")
	@ResponseBody
	public ResponseBean saveUser(){
		ResponseBean responseBean = new ResponseBean(false);
		return responseBean;
	}
	
	/**
	 * 
	 * @INFO 保存用户信息
	 * 2017年11月6日下午11:42:15
	 * @author 云文龙
	 * @return ResponseBean
	 * @return
	 */
	@RequestMapping("listUser")
	public String listUser(HttpServletRequest request,Model model){
		String page = request.getParameter("page");//页码
		String rows = request.getParameter("rows");//每页数量
		Map<String, Object> map = new HashMap<String, Object>();
		long total = blogUserService.countUserByStatus(map);
		PageBean pageBean=new PageBean(page,rows,total);
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getPageSize());
		List<BlogUser> list = blogUserService.listUserByStatus(map);
		model.addAttribute("list", list);
		return "after/users";
	}
	
}

package com.yunwenlong.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
	
	/*************************************************前台进入页面的方法************************************************/

	/**
	 * 
	 * @INFO 项目启动时，默认进入首页
	 * 2017年10月28日下午5:36:12
	 * @author 云文龙
	 * @return String
	 * @return
	 */
	@RequestMapping("/")
    public String index() {
        return "before/index";
    }
	
	/**
	 * 
	 * @INFO	进入主页
	 * 2017年6月30日下午10:47:47
	 * @author 云文龙
	 * @return String
	 * @return
	 */
	@RequestMapping("login")
	public String toLogin(){
		return "before/index";
	}
	
	/**
	 * 
	 * @INFO  进入about页面
	 * 2017年6月30日下午10:48:26
	 * @author 云文龙
	 * @return String
	 * @return
	 */
	@RequestMapping("about")
	public String toAbout(){
		return "before/about";
	}
	
	/**
	 * 
	 * @INFO  进入blog页面
	 * 2017年6月30日下午10:48:46
	 * @author 云文龙
	 * @return String
	 * @return
	 */
	@RequestMapping("blog")
	public String toBlog(){
		return "before/blog";
	}
	
	/**
	 * 
	 * @INFO  进入contact页面
	 * 2017年6月30日下午10:48:55
	 * @author 云文龙
	 * @return String
	 * @return
	 */
	@RequestMapping("contact")
	public String toContact(){
		return "before/contact";
	}
	
	/**
	 * 
	 * @INFO  进入gallery页面
	 * 2017年6月30日下午10:49:12
	 * @author 云文龙
	 * @return String
	 * @return
	 */
	@RequestMapping("gallery")
	public String toGallery(){
		return "before/gallery";
	}
	
	/*************************************************后台进入管理页面的方法************************************************/
	/**
	 * 
	 * @INFO 
	 * 2017年10月28日下午8:08:51
	 * @author 云文龙
	 * @return String
	 * @return
	 */
	@RequestMapping("admin")
	public String admin(){
		return "after/sign-in";
	}
	
	/**
	 * 
	 * @INFO 
	 * 2017年10月28日下午8:08:51
	 * @author 云文龙
	 * @return String
	 * @return
	 */
	@RequestMapping("sign")
	public String sign(){
		return "after/index";
	}
	
}

package com.yunwenlong.common;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController {

	@RequestMapping("500")
	public String internaServerError(){
		return "error/500";
	}
	
	@RequestMapping("404")
	public String notFound(){
		return "error/404";
	}
	
	@RequestMapping("400")
	public String badRequest(){
		return "error/400";
	}
	
	@RequestMapping("200")
	public String okRequest(){
		return "error/200";
	}
	
}

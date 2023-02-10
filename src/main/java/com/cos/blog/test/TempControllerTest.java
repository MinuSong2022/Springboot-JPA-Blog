package com.cos.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TempControllerTest {
	
	//http://localhost:8000/blog/temp/home
	@GetMapping("/temp/home")
	public String tempHome() {
		System.out.println("tempHome()");
		// 파일을 리턴할때 기본경로가 : src/main/resources/static 
		// 리턴명을 : /home.html 
		// 풀 경로가 : src/main/resources/static/home.html 
		return "/home.html";
	}
	
	@GetMapping("/temp/jsp")
	public String tempJSP() {
		// prefix : /WEB-INF/vies/
		// Suffix : jsp
		// 풀네임 : /WEB-INF/views//test.jsp
		
		return "test"; 
	}
}

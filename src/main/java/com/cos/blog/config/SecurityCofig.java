package com.cos.blog.config;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.FormLoginBeanDefinitionParser;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.cos.blog.config.auth.PrincipalDetailService;


// 빈 등록: 스프링 컨테이너에서 객체를 관리할 수 있게 하는것.
@Configuration // 빈등록 (IOC관리)
@EnableWebSecurity // 스프링 시큐리티가 이미 활성화 되어있는 유저만 접근을 허용 하려면 @preAuthorize 어노테이션을 사용하는데 , 해당 
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityCofig{

	@Autowired
	private PrincipalDetailService principalDetailService;
	
	@Bean // @Bean 어노테이션을 걸어주면 그때부터 IOC 가 된다 
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http
		.csrf().disable() // csrf토큰 비활성화 (테스트시 걸어두는게 좋음)
		  .authorizeHttpRequests()
		    .antMatchers("/","/auth/**","/js/**","/css/**","/image/**")
			.permitAll()
			.anyRequest()
			.authenticated()
		  .and()
		    .formLogin()
		    .loginPage("/auth/loginForm")
			.loginProcessingUrl("/auth/loginProc")
			.defaultSuccessUrl("/"); // 스프링 시큐리티가 해당 주소로 요청오는 로그인을 가로채서 대신 로그인 해준다.
		return http.build();
	                


}



		
	}

	

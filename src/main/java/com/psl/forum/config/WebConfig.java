package com.psl.forum.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
         .allowedOrigins("http://localhost:4200")
         .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
         .allowedHeaders("Origin", "Access-Control-Allow-Origin", "Content-Type",
 				"Jwt-Token", "Authorization", "Accept", "X-Requested-With", 
 				"Access-Control-Request-Method", "Access-Control-Request-Headers")
         .exposedHeaders("Origin", "Access-Control-Allow-Origin", "Content-Type",
 				"Jwt-Token", "Authorization", "Accept", "Access-Control-Allow-Credencials")
         .allowCredentials(true);
	}
}

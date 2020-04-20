package com.cmpe275Lab2.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan(basePackages= "com.cmpe275project.model")
@ComponentScan({"com.cmpe275project","com.cmpe275project.config","com.cmpe275project.model","com.cmpe275project.dao","com.cmpe275project.service","com.cmpe275project.controller"})
public class WebApp extends SpringBootServletInitializer{ //Note we initialize the Servlet context required by Tomcat by implementing the SpringBootServletInitializer interface

	public static void main(String[] args) {
 
		SpringApplication.run(WebApp.class, args);
	}

}

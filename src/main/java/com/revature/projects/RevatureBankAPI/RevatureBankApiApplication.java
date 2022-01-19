package com.revature.projects.RevatureBankAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
//@ComponentScan({"com.revature.projects"})
//@EntityScan("com.revature.projects.RevatureBankAPI.models")
//@EnableJpaRepositories("com.revature.projects.RevatureBankAPI.repositories")
public class RevatureBankApiApplication  {

//	@Override protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//		return builder.sources(WebServiceApplication.class);
//	}

	public static void main(String[] args) {
		SpringApplication.run(RevatureBankApiApplication.class, args);
	}

}

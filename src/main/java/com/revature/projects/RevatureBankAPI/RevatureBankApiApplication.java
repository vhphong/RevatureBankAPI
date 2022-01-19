package com.revature.projects.RevatureBankAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;




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

package com.revature.projects.RevatureBankAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//Consider defining a bean of type 'com.revature.projects.RevatureBankAPI.repositories.CustomerRepository' in your configuration.

@SpringBootApplication
@ComponentScan({"com.revature.projects.request"})
@EntityScan("com.revature.projects.models")
//@EnableJpaRepositories("com.revature.projects.repositories")
public class RevatureBankApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RevatureBankApiApplication.class, args);
	}

}

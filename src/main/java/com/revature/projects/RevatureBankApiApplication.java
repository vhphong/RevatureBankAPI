package com.revature.projects;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.revature.")
public class RevatureBankApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RevatureBankApiApplication.class, args);
	}

}

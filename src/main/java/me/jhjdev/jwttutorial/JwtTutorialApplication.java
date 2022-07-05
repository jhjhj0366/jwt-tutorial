package me.jhjdev.jwttutorial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class JwtTutorialApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtTutorialApplication.class, args);
	}

}

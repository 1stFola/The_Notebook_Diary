package com.technophiles.thenotebook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;


@Configuration
@SpringBootApplication
public class TheNotebookApplication {

	public static void main(String[] args) {
		SpringApplication.run(TheNotebookApplication.class, args);
	}

}

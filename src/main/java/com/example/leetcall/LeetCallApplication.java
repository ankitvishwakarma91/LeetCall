package com.example.leetcall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LeetCallApplication {

	public static void main(String[] args) {
		SpringApplication.run(LeetCallApplication.class, args);
	}

}

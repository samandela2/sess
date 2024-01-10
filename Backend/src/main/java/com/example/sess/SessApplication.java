package com.example.sess;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.example.sess.models")  
public class SessApplication {

	public static void main(String[] args) {
		SpringApplication.run(SessApplication.class, args);
	}

}

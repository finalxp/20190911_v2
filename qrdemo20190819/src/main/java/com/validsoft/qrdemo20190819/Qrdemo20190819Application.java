package com.validsoft.qrdemo20190819;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.validsoft.*")
public class Qrdemo20190819Application {

	public static void main(String[] args) {
		SpringApplication.run(Qrdemo20190819Application.class, args);
	}

}

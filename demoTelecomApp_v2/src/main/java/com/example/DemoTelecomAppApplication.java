package com.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@MapperScan("com.example.mapper")
@MapperScan("com.example.dao")
@ComponentScan("com.example.*")
public class DemoTelecomAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoTelecomAppApplication.class, args);
	}

}

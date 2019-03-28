package com.douzone.hellospring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class RestBootApp {
	
	//빠른 실행결과
	
	@RestController
	public class MyController {
		
		@Autowired
		Myservice myService;
		
		@GetMapping("/hello")
		public String hello() {
			
			return myService.hello();
		}
	}
	
	@Component
	public class Myservice {
		
		public String hello() {
			return "Hello Yahoo!!!";
		}
	}
	/*
	public static void main(String[] args) {
		SpringApplication.run(BootApplication.class, args);

	}
	*/
}

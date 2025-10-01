package com.starkIndustries.webSocket_Mk2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class WebSocketMk2Application {

	public static void main(String[] args) {
		SpringApplication.run(WebSocketMk2Application.class, args);
	}

}

package com.kaba4cow.imgxiv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class ImgxivApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImgxivApplication.class, args);
	}

}

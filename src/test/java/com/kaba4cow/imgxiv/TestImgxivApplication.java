package com.kaba4cow.imgxiv;

import org.springframework.boot.SpringApplication;

public class TestImgxivApplication {

	public static void main(String[] args) {
		SpringApplication.from(ImgxivApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}

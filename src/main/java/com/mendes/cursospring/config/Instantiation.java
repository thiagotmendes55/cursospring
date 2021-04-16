package com.mendes.cursospring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.mendes.cursospring.services.S3Service;

@Configuration
public class Instantiation implements CommandLineRunner {
	@Autowired
	private S3Service s3Service;
	
	@Override
	public void run(String... args) throws Exception {
		s3Service.uploadFile("C:\\Users\\thiag\\Downloads\\cga_etg.png");
	}
}

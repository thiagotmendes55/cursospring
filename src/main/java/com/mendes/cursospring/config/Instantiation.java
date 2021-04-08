package com.mendes.cursospring.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.mendes.cursospring.domain.Categoria;
import com.mendes.cursospring.repositories.CategoriaRepository;

@Configuration
public class Instantiation implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Override
	public void run(String... args) throws Exception {
		Categoria c1 = new Categoria(null, "Informática");
		Categoria c2 = new Categoria(null, "Escritório");
		
		categoriaRepository.saveAll(Arrays.asList(c1, c2));
	}

}

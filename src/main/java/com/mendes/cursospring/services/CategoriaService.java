package com.mendes.cursospring.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mendes.cursospring.domain.Categoria;
import com.mendes.cursospring.repositories.CategoriaRepository;
import com.mendes.cursospring.services.exception.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repositorio;
	
	public List<Categoria> findAll() {
		return repositorio.findAll();
	}
	
	public Categoria findById(Integer id) {
		Optional<Categoria> categoria = repositorio.findById(id);
		return categoria.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
	}
}

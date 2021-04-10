package com.mendes.cursospring.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.mendes.cursospring.domain.Categoria;
import com.mendes.cursospring.domain.Produto;
import com.mendes.cursospring.repositories.CategoriaRepository;
import com.mendes.cursospring.repositories.ProdutoRepository;
import com.mendes.cursospring.services.exception.ObjectNotFoundException;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository repositorio;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public List<Produto> findAll() {
		return repositorio.findAll();
	}
	
	public Produto findById(Integer id) {
		Optional<Produto> pedido = repositorio.findById(id);
		return pedido.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
	}
	
	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		List<Categoria> categorias = categoriaRepository.findAllById(ids); 
		return repositorio.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
	}
}

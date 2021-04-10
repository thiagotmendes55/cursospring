package com.mendes.cursospring.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.mendes.cursospring.domain.Categoria;
import com.mendes.cursospring.repositories.CategoriaRepository;
import com.mendes.cursospring.services.exception.DataIntegrityException;
import com.mendes.cursospring.services.exception.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repositorio;

	public List<Categoria> findAll() {
		return repositorio.findAll();
	}

	public Categoria findById(Integer id) {
		Optional<Categoria> obj = repositorio.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
	}

	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return repositorio.save(obj);
	}

	public Categoria update(Categoria obj) {
		this.findById(obj.getId());

		return repositorio.save(obj);
	}

	public void delete(Integer id) {
		this.findById(id);
		try {
			repositorio.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir uma categoria que tem produtos!");
		}
	}
}

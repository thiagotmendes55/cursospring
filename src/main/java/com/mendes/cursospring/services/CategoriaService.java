package com.mendes.cursospring.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.mendes.cursospring.domain.Categoria;
import com.mendes.cursospring.dto.CategoriaDTO;
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
		Categoria newObj = this.findById(obj.getId());
		updateData(newObj, obj);		
		return repositorio.save(newObj);
	}

	public void delete(Integer id) {
		this.findById(id);
		try {
			repositorio.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir uma categoria que tem produtos!");
		}
	}
	
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repositorio.findAll(pageRequest);
	}
	
	public Categoria fromDTO(CategoriaDTO objDto) {
		return new Categoria(objDto.getId(), objDto.getNome());
	}

	private void updateData(Categoria newObj, Categoria obj) {
		newObj.setNome(obj.getNome());
	}
}

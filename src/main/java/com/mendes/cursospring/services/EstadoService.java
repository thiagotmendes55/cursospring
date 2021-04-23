package com.mendes.cursospring.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mendes.cursospring.domain.Estado;
import com.mendes.cursospring.dto.EstadoDTO;
import com.mendes.cursospring.repositories.EstadoRepository;

@Service
public class EstadoService {
	@Autowired
	private EstadoRepository repo;

	public List<EstadoDTO> findAll() {
		return repo.findAllByOrderByNome().stream().map(x -> toDTO(x)).collect(Collectors.toList());
	}
	
	public EstadoDTO toDTO(Estado obj) {
		return new EstadoDTO(obj.getId(), obj.getNome());
	}
}

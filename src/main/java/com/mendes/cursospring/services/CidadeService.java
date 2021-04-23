package com.mendes.cursospring.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mendes.cursospring.domain.Cidade;
import com.mendes.cursospring.dto.CidadeDTO;
import com.mendes.cursospring.repositories.CidadeRepository;

@Service
public class CidadeService {
	
	@Autowired
	private CidadeRepository repo;
	
	public List<CidadeDTO> findByEstadoId(Integer id) {
		return repo.findByEstadoId(id).stream().map(x -> toDTO(x)).collect(Collectors.toList());
	}
	
	private CidadeDTO toDTO(Cidade obj) {
		return new CidadeDTO(obj.getId(), obj.getNome());
	}
}

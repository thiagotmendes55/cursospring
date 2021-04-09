package com.mendes.cursospring.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mendes.cursospring.domain.Cliente;
import com.mendes.cursospring.repositories.ClienteRepository;
import com.mendes.cursospring.services.exception.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repositorio;
	
	public List<Cliente> findAll() {
		return repositorio.findAll();
	}
	
	public Cliente findById(Integer id) {
		Optional<Cliente> cliente = repositorio.findById(id);
		return cliente.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
	}
}

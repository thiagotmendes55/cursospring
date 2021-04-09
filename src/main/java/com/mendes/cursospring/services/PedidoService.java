package com.mendes.cursospring.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mendes.cursospring.domain.Pedido;
import com.mendes.cursospring.repositories.PedidoRepository;
import com.mendes.cursospring.services.exception.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repositorio;
	
	public List<Pedido> findAll() {
		return repositorio.findAll();
	}
	
	public Pedido findById(Integer id) {
		Optional<Pedido> pedido = repositorio.findById(id);
		return pedido.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
	}
}

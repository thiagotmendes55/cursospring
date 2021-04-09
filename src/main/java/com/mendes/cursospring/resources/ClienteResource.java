package com.mendes.cursospring.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mendes.cursospring.domain.Cliente;
import com.mendes.cursospring.services.ClienteService;

@RestController
@RequestMapping(value="/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteService service;
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<Cliente> findById(@PathVariable Integer id) {
		Cliente cliente = service.findById(id);
		
		return ResponseEntity.ok().body(cliente);
	}
}

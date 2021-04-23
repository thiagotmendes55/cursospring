package com.mendes.cursospring.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mendes.cursospring.dto.CidadeDTO;
import com.mendes.cursospring.dto.EstadoDTO;
import com.mendes.cursospring.services.CidadeService;
import com.mendes.cursospring.services.EstadoService;

@RestController
@RequestMapping(value = "/estados")
public class EstadoResource {

	@Autowired
	private EstadoService service;
	
	@Autowired
	private CidadeService cidadeService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<EstadoDTO>> findAll() {
		return ResponseEntity.ok().body(service.findAll());
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}/cidades")
	public List<CidadeDTO> findByEstadoId(@PathVariable(value = "id") Integer id) {
		return cidadeService.findByEstadoId(id);
	}
}

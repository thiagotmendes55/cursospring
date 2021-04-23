package com.mendes.cursospring.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mendes.cursospring.domain.Cliente;
import com.mendes.cursospring.dto.ClienteDTO;
import com.mendes.cursospring.dto.ClienteNewDTO;
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

	@RequestMapping(method = RequestMethod.GET, value = "/email")
	public ResponseEntity<Cliente> findById(@RequestParam(value = "value") String email) {
		Cliente cliente = service.findByEmail(email);
		
		return ResponseEntity.ok().body(cliente);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO objDto) {
		Cliente obj = service.fromDTO(objDto);
		obj = service.insert(obj);	
	 	URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
	 	return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<Void> update(@PathVariable(name = "id") Integer id, @Valid @RequestBody ClienteDTO objDto) {
		Cliente obj = service.fromDTO(objDto);
		obj.setId(id);
		obj = service.update(obj);
		
		return ResponseEntity.noContent().build();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {		
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ClienteDTO>> findAll() {
		List<Cliente> lista = service.findAll();
		List<ClienteDTO> listaDTO = lista.stream().map(x -> new ClienteDTO(x)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(listaDTO);
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method = RequestMethod.GET, value = "/page")
	public ResponseEntity<Page<ClienteDTO>> findPage(
			@RequestParam(value = "page", defaultValue = "0") Integer page, 
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage, 
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy, 
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		Page<Cliente> lista = service.findPage(page, linesPerPage, orderBy, direction);
		Page<ClienteDTO> listaDTO = lista.map(x -> new ClienteDTO(x));
		
		return ResponseEntity.ok().body(listaDTO);
	}
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/picture")
	public ResponseEntity<Void> uploadProfilePicture(@RequestParam(name = "file") MultipartFile file) {
		URI uri = service.uploadProfilePicture(file);
		
		return ResponseEntity.created(uri).build();
	}
}

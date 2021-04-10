package com.mendes.cursospring.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.mendes.cursospring.domain.Cliente;
import com.mendes.cursospring.dto.ClienteDTO;
import com.mendes.cursospring.repositories.ClienteRepository;
import com.mendes.cursospring.services.exception.DataIntegrityException;
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

	public Cliente update(Cliente obj) {
		Cliente newObj = this.findById(obj.getId());
		updateData(newObj, obj);		
		return repositorio.save(newObj);
	}

	public void delete(Integer id) {
		this.findById(id);
		try {
			repositorio.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir um cliente com pedidos!");
		}
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repositorio.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO objDto) {
	    return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);
	}

	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());		
	}
}

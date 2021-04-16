package com.mendes.cursospring.services;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.mendes.cursospring.domain.Cidade;
import com.mendes.cursospring.domain.Cliente;
import com.mendes.cursospring.domain.Endereco;
import com.mendes.cursospring.domain.enums.Perfil;
import com.mendes.cursospring.domain.enums.TipoCliente;
import com.mendes.cursospring.dto.ClienteDTO;
import com.mendes.cursospring.dto.ClienteNewDTO;
import com.mendes.cursospring.repositories.ClienteRepository;
import com.mendes.cursospring.repositories.EnderecoRepository;
import com.mendes.cursospring.security.UserSS;
import com.mendes.cursospring.services.exception.AuthorizationException;
import com.mendes.cursospring.services.exception.DataIntegrityException;
import com.mendes.cursospring.services.exception.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repositorio;
	
	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private S3Service s3Service;
	
	@Autowired
	private ImageService imageService;
	
	@Value("${img.prefix.client.profile}")
	private String prefix;
	
	@Value("${img.profile.size}")
	private Integer size;
	
	public List<Cliente> findAll() {
		return repositorio.findAll();
	}
	
	public Cliente findById(Integer id) {
		UserSS user = UserService.authenticated();
		if (user == null || !(user.hasRole(Perfil.ADMIN)) && !(id.equals(user.getId()))) {
			throw new AuthorizationException("Acesso negado");
		}
		
		Optional<Cliente> cliente = repositorio.findById(id);
		return cliente.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
	}

	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		try {
			obj = repositorio.save(obj);
			enderecoRepository.saveAll(obj.getEnderecos());
			return obj;
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Violação da integridade de dados!");
		}
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
	    return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null, null);
	}
	
	public Cliente fromDTO(ClienteNewDTO objDto) {
		Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipo()), pe.encode(objDto.getSenha()));
		Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDto.getTelefone1());
		
		if (objDto.getTelefone2() != null) {
			cli.getTelefones().add(objDto.getTelefone2());
		}
		
		if (objDto.getTelefone3() != null) {
			cli.getTelefones().add(objDto.getTelefone3());
		}

		return cli;
	}

	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());		
	}
	
	public URI uploadProfilePicture(MultipartFile multiPartFile) {
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		
		BufferedImage jpgImage = imageService.getJpgFromFile(multiPartFile);
		jpgImage = imageService.cropSquare(jpgImage);
		jpgImage = imageService.resize(jpgImage, size);
		
		String filename = prefix + user.getId() + ".jpg";
		
		return s3Service.uploadFile(imageService.getInputStream(jpgImage, "jpg"), filename, "image");
	}
}

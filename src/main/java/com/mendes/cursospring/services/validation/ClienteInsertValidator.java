package com.mendes.cursospring.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.mendes.cursospring.domain.enums.TipoCliente;
import com.mendes.cursospring.dto.ClienteNewDTO;
import com.mendes.cursospring.resources.exception.FieldMessage;
import com.mendes.cursospring.services.validation.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
	@Override
	public void initialize(ClienteInsert ann) {
	}

	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();

		if ((objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCodigo())) && (!(BR.isCPF(objDto.getCpfOuCnpj())))) {
			list.add(new FieldMessage("cpfOuCnpj", "CPF Inválido"));
		}
		
		if ((objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCodigo())) && (!(BR.isCNPJ(objDto.getCpfOuCnpj())))) {
			list.add(new FieldMessage("cpfOuCnpj", "CNPJ Inválido"));
		}
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}
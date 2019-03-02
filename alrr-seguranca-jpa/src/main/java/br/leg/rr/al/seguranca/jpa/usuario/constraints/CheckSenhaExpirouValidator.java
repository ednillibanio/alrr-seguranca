package br.leg.rr.al.seguranca.jpa.usuario.constraints;

import javax.inject.Named;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.leg.rr.al.core.utils.DataUtils;
import br.leg.rr.al.seguranca.domain.SegurancaValidationMessages;
import br.leg.rr.al.seguranca.jpa.Usuario;

@Named
public class CheckSenhaExpirouValidator implements ConstraintValidator<CheckSenhaExpirouConstraint, Object> {

	@Override
	public void initialize(CheckSenhaExpirouConstraint constraintAnnotation) {

	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {

		Usuario usuario = (Usuario) value;

		// Caso a data seja nula, ou seja, está o sistema começou a
		// exigir expirasenha, então está vazio pela primeria vez.
		if (usuario.getDataExpiraSenha() == null || DataUtils.isAntesHoje(usuario.getDataExpiraSenha())) {
			context.buildConstraintViolationWithTemplate(SegurancaValidationMessages.SENHA_EXPIROU)
					.addPropertyNode("senha").addConstraintViolation();

			// Indica que o usuário deve informar uma nova senha, e
			// exibe os campos para preencher.
			return false;
		}

		return true;

	}
}

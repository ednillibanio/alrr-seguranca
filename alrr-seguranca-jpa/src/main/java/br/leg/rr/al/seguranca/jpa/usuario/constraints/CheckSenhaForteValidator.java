package br.leg.rr.al.seguranca.jpa.usuario.constraints;

import java.util.regex.Pattern;

import javax.inject.Named;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import br.leg.rr.al.seguranca.domain.SegurancaValidationMessages;
import br.leg.rr.al.seguranca.jpa.Usuario;

@Named
public class CheckSenhaForteValidator implements ConstraintValidator<SenhaConstraint, Object> {

	// (?=.*\d) deve ter dois digitos de 0-9;
	// (?=.*[a-z]) deve ter duas letras minusculas;
	// (?=.*[A-Z]) deve ter duas letras maiusculas;
	// . combina com qualquer condição anterior;
	// {6,20} a senha deve ter de 6 a 20 digitos.
	private static final String PASSWORD_PATTERN = "((?=.*\\d{2})(?=.*[a-z]{2})(?=.*[A-Z]{2}).{6,20})";
	private static Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

	@Override
	public void initialize(SenhaConstraint constraintAnnotation) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {

		Usuario usuario = (Usuario) value;
		String senha = usuario.getSenha();
		String login = usuario.getLogin();

		// Verifica se senha é fraca, ou seja, fora do padrão.
		if (!pattern.matcher(senha).matches()) {

			context.buildConstraintViolationWithTemplate(SegurancaValidationMessages.USUARIO_ERROR_SENHA_FRACA)
					.addPropertyNode("senha").addConstraintViolation();

			return false;
		}

		// FIXME Antes verificava se existia o ano atual na senha. Acho que deve
		// manter, mas como opcao.
		// Verifica se tem o ano atual.
		/*
		 * if (StringUtils.contains(senha, Integer.toString(DataHelper.getAnoAtual())))
		 * {
		 * 
		 * context.buildConstraintViolationWithTemplate(
		 * SecurityValidationMessages.USUARIO_ERROR_SENHA_ANO_ATUAL)
		 * .addPropertyNode("senha").addConstraintViolation();
		 * 
		 * return false; }
		 */

		// Se tem o login na senha
		if (StringUtils.containsIgnoreCase(senha, login)) {
			context.buildConstraintViolationWithTemplate(SegurancaValidationMessages.USUARIO_ERROR_SENHA_IGUAL_LOGIN)
					.addPropertyNode("senha").addConstraintViolation();

			return false;
		}

		return true;

	}

}

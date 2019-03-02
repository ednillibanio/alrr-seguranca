package br.leg.rr.al.seguranca.ejb.constraints;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.leg.rr.al.seguranca.ejb.UsuarioLocal;
import br.leg.rr.al.seguranca.jpa.Usuario;

/**
 * 
 * @author ednil
 *
 */
@Named
public class LoginConstraintValidator implements ConstraintValidator<LoginConstraint, Usuario> {

	@EJB
	private UsuarioLocal bean;

	@Override
	public void initialize(LoginConstraint constraint) {
		// TODO Auto-generated method stub

	}

	/**
	 * Faz as seguintes validações no campo 'login' do Usuario.
	 * 
	 * <pre>
	 * - Verifica se o login informado existe; <br>
	 * - Verifica se o usuário está ativo; <br>
	 * - Verifica se usuário possui pelo menos um perfil.
	 * </pre>
	 */
	@Override
	public boolean isValid(Usuario usuario, ConstraintValidatorContext context) {

		// Verifica se o login informado existe.
		// FIXME: refatorar, pois mudou a regra para usario perfil.
		/*
		 * if (usuario == null || usuario.getSituacao() != StatusType.ATIVO ||
		 * usuario.getPerfis().size() < 1) {
		 * context.buildConstraintViolationWithTemplate(
		 * SegurancaValidationMessages.USUARIO_ERROR_SENHA_IGUAL_LOGIN)
		 * .addPropertyNode("login").addConstraintViolation(); return false;
		 * 
		 * }
		 */
		return true;
	}

}

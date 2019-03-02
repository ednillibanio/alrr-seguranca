package br.leg.rr.al.seguranca.jpa.usuario.constraints;

import javax.inject.Named;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.leg.rr.al.seguranca.domain.UsuarioType;
import br.leg.rr.al.seguranca.jpa.Usuario;

/**
 * 
 * @author ednil
 *
 */
@Named
public class UsuarioConstraintValidator implements ConstraintValidator<ValidUsuario, Usuario> {

	@Override
	public void initialize(ValidUsuario constraint) {
		// TODO Auto-generated method stub

	}

	/**
	 * Faz as seguintes validações de acordo com o <code>{@link UsuarioType}</code>.
	 * 
	 * <pre>
	 * - Se o {@code UsuarioType} for igual a {@code UsuarioType#SISTEMA} , nâo deve ter Pessoa vinculada ao {@code Usuario};<br/>
	 * - Se o {@code UsuarioType} for igual a {@code UsuarioType#SERVIDOR_ALE}, a instância de {@code Pessoa} deve ser igual a {@code ServidorPublico};<br/>
	 * - Se o {@code UsuarioType} {@link UsuarioType#} for igual a {@code UsuarioType#EXTERNO}, a instância de {@code Pessoa} deve ser igual a {@code PessoaFisica};<br/>
	 * </pre>
	 */
	@Override
	public boolean isValid(Usuario usuario, ConstraintValidatorContext context) {

		// Verifica se o login informado existe.
		if (usuario != null) {

			if (usuario.getTipoUsuario().getTipo().equals(UsuarioType.SISTEMA)) {
				if (usuario.getTipoUsuario().getPessoa() != null) {
					context.buildConstraintViolationWithTemplate(
							"Erro de sistema: Usuário do tipo 'Sistema' nâo pode ter pessoa vinculada.");
					return false;
				}

			} else if (usuario.getTipoUsuario().getTipo().equals(UsuarioType.SERVIDOR_ALE)
					|| usuario.getTipoUsuario().getTipo().equals(UsuarioType.EXTERNO)) {

				/*
				 * if (usuario.getPessoa() == null) {
				 * context.buildConstraintViolationWithTemplate(
				 * "Erro de sistema: O atributo PessoaFisica não pode ser null quando o tipo do usário é 'Servidor Público' ou 'Externo'."
				 * ); return false;
				 * 
				 * } else if (usuario.getTipoUsuario().equals(UsuarioType.SERVIDOR_ALE) &&
				 * !(usuario.getPessoa() instanceof ServidorPublico)) {
				 * context.buildConstraintViolationWithTemplate(
				 * "Erro de sistema: A instância do atributo PessoaFisica da entidade Usuario deve ser do tipo 'Servidor Público'."
				 * ) .addConstraintViolation(); return false;
				 * 
				 * } else if (usuario.getTipoUsuario().equals(UsuarioType.EXTERNO) &&
				 * (usuario.getPessoa() instanceof ServidorPublico)) {
				 * context.buildConstraintViolationWithTemplate(
				 * "Erro de sistema: A instância do atributo PessoaFisica da entidade Usuario deve ser do tipo 'Pessoa Física'."
				 * ) .addConstraintViolation(); return false; }
				 */

			}
		}
		return true;

	}
}

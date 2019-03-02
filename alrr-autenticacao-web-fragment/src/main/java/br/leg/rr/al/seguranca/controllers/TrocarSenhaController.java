package br.leg.rr.al.seguranca.controllers;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.leg.rr.al.core.dao.BeanException;
import br.leg.rr.al.core.web.util.FacesMessageUtils;
import br.leg.rr.al.seguranca.ejb.UsuarioLocal;
import br.leg.rr.al.seguranca.jpa.Sistema;
import br.leg.rr.al.seguranca.jpa.Usuario;

@Named
@ViewScoped
public class TrocarSenhaController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5233133684339705345L;

	// @Inject
	// private SistemaManagerController sistemaController;

	private Sistema sistema;

	@Inject
	private Validator validator;

	private Usuario usuario;

	@EJB
	private UsuarioLocal usuarioBean;

	/**
	 * Verifica se a nova senha atual do usuário é valida.
	 */
	private void verificaSeNovaSenhaIgualSenhaAtual(String value) throws ValidatorException {
		// FIXME tirei seguranca do sistema. tem que ver onde vai ficar.
		/*
		 * if (!getSistema().getSeguranca().getPermiteMesmaSenha()) { UIInput
		 * compSenhaAtual = (UIInput) FacesUtils.findComponent("senhaAtual");
		 * 
		 * if (value.equals(compSenhaAtual.getValue().toString())) { FacesMessage msg =
		 * FacesMessageUtils
		 * .createError(SegurancaValidationMessages.NOVA_SENHA_IGUAL_SENHA_ATUAL); throw
		 * new ValidatorException(msg); } }
		 */
	}

	/**
	 * Valida a nova senha e troca caso não haja restrição.
	 * 
	 * <pre>
	 * - Verifica se a nova senha é igual a senha atual, caso o sistema esteja configurado para validar.
	 * - Verifica se a senha é forte conforme o padrão, caso o sistema esteja configurado para validar.
	 * - Define mas não salva a nova senha como padrão, caso passe pelas validações acima.
	 * </pre>
	 * 
	 * A nova senha será salva quando a autenticação for realizada com sucesso no
	 * método login().
	 * 
	 * @param context
	 * @param validate
	 * @param value
	 * @param propertyName
	 * @throws ValidatorException
	 */
	public void validarNovaSenha(FacesContext context, UIComponent validate, Object value) throws ValidatorException {
		verificaSeNovaSenhaIgualSenhaAtual((String) value);
		verificaSeSenhaForte((String) value);
	}

	/**
	 * Se Sistema foi configurado para senha expirar, então será verificado se a
	 * senha expirou.
	 */
	private void verificaSeSenhaForte(String value) {
		/**
		 * TODO: Refatorar o metodo, pois mudei em alguns lugares a parte de senha
		 */
		/*
		 * setValidator(HibernateUtil.getValidator(SegurancaValidationMessages.
		 * RESOURCE_BUNDLE_NAME)); usuario.setSenha(value);
		 * Set<ConstraintViolation<Usuario>> violations =
		 * HibernateUtil.getViolations(getValidator(), usuario,
		 * SenhaConstraintGroup.class);
		 * 
		 * if (violations.size() > 0) { List<FacesMessage> msgs =
		 * FacesMessageUtil.createError(violations); throw new ValidatorException(msgs);
		 * }
		 */

	}

	/**
	 * Troca a senha do usuário caso tenha expirado ou esteja configurado para
	 * trocar no primeiro acesso. FIXME: Arrumar.
	 */
	private void trocarSenha() {

		try {
			/*
			 * usuario.setSenha(usuarioBean.cifrarSenha(login, confirmaNovaSenha));
			 * 
			 * // passa a nova senha para senha onde será autenticado na // session. senha =
			 * novaSenha; // Define a data que a senha expira caso esteja configurado para
			 * // expirar. usuario.setDataExpiraSenha(sistemaBean.getDataExpiraSenha());
			 * JsfMessageUtil.addInfoMessage(SecurityResourceBundleMessage.
			 * MESSAGES_BUNDLE_NAME,
			 * SecurityResourceBundleMessage.SENHA_MODIFICADA_COM_SUCESSO);
			 */
		} catch (BeanException e) {

			FacesMessageUtils.addFatal(e.getMessage());
			e.printStackTrace();
		}

	}

	public Sistema getSistema() {
		return sistema;
	}

	public void setSistema(Sistema sistema) {
		this.sistema = sistema;
	}

	public Validator getValidator() {
		return validator;
	}

	public void setValidator(Validator validator) {
		this.validator = validator;
	}
}

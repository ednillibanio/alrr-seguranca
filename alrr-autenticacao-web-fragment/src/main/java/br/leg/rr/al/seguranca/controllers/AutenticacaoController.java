package br.leg.rr.al.seguranca.controllers;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.leg.rr.al.core.dao.BeanException;
import br.leg.rr.al.core.domain.NavigationOutcomeDefault;
import br.leg.rr.al.core.domain.StatusType;
import br.leg.rr.al.core.web.util.FacesMessageUtils;
import br.leg.rr.al.core.web.util.FacesUtils;
import br.leg.rr.al.seguranca.domain.SegurancaValidationMessages;
import br.leg.rr.al.seguranca.ejb.AutenticacaoLocal;
import br.leg.rr.al.seguranca.ejb.SistemaLocal;
import br.leg.rr.al.seguranca.ejb.UsuarioLocal;
import br.leg.rr.al.seguranca.jpa.PolicitaSenha;
import br.leg.rr.al.seguranca.jpa.Sistema;
import br.leg.rr.al.seguranca.jpa.Usuario;

@Named
@ViewScoped
public class AutenticacaoController implements Serializable {

	private static final long serialVersionUID = -3115986206115972184L;

	@NotNull
	@Size(max = 20)
	private String login;

	@NotNull
	@Size(max = 20)
	private String senha;

	// @Inject
	// private SessaoController sessaoController;

	// @Inject
	// private SistemaManagerController sistemaManagerController;

	@EJB
	private SistemaLocal sistemaBean;

	@EJB
	private UsuarioLocal usuarioBean;

	@EJB
	private AutenticacaoLocal autenticacaoBean;

	private Usuario usuario;

	private Sistema sistema;

	@Inject
	private Validator validator;

	@PostConstruct
	public void init() {
		limpar();
	}

	/**
	 * Responsável por: <br>
	 * 1 - autenticar o usuário; <br>
	 * 2 - conceder as permissões e aplicar as regras de segurança. As regras
	 * encontram na entidade Sistema.
	 * 
	 * <pre>
	 * 1 - Registra a data do último acesso.
	 * 2 - Define a data que expira a senha, caso o sistema esteja configurado para expirar senha.
	 * 3 - Troca a senha caso o sistema tenha obrigado a informar uma nova senha.
	 * </pre>
	 * 
	 * As validações são feitas na fase de validation pelos métodos validarSenha,
	 * validarNovaSenha e ValidarUsuarioLogin.
	 * 
	 * @return Direciona para a view index.xhtml
	 */
	public String autenticar() {

		// TODO: Colocar 3 tentativas de senha e em seguida bloquear o usuário.
		// Verificar se está bloqueado.Colocar na config do sistema a opção de
		// ativar ou desativar. PENDENTE
		// TODO: Tratar erro, quando perfil_funcionalidade está vazia.
		// redirecionar para página de trocar-senha se estiver configurado para
		// trocar, caso expire. Ver se o sistema da a opcao de configurar
		// obrigatoriedade para trocar senha.

		try {
			autenticacaoBean.autenticar(getLogin(), getSenha());
		} catch (Exception e) {
			FacesMessageUtils.addError(e.getMessage());
			return null;
		}

		// Subject subject = SecurityUtils.getSubject();

		// List<Usuario> lista = usuarioBean.buscar(usuario.getPrincipals());

		/*
		 * 
		 * 
		 * // Define a data do ultimo acesso do usuário.
		 * usuario.setDataUltimoAcesso(DataHelper.hoje()); usuarioBean.save(usuario);
		 * 
		 * // sessaoController.setUsuario(usuarioBean.find(usuario)); //
		 * sessaoController.load(); }
		 * 
		 * } catch (ServletException e) { e.printStackTrace();
		 * FacesMessageUtil.addErrorMessage( SecurityResourceBundleMessage.
		 * MESSAGES_BUNDLE_NAME, SecurityResourceBundleMessage.FALHA_NA_AUTENTICACAO);
		 * return null; } catch (ControllerException e) { e.printStackTrace();
		 * addErrorMessage(e.getMessage()); }
		 */
		return "pagina-inicial";
	}

	/**
	 * Se Sistema foi configurado para trocar senha no primeiro acesso, então será
	 * solicitado uma nova senha.
	 */
	// TODO: Tem que ver um campo que indique que tem que trocar a senha no
	// proximo acesso em vez da data do ultimo acesso. que nem no joomla.
	private void verificaSePrimeiroAcesso() {
		// Se Sistema configurado para trocar senha no primeiro acesso
		if (getSeguranca().getTrocaSenhaPrimeiroAcesso() && usuario.getDataUltimoAcesso() == null) {
			FacesMessage msg = FacesMessageUtils.createError(SegurancaValidationMessages.TROCAR_SENHA_PRIMEIRO_ACESSO);
			throw new ValidatorException(msg);
		}
	}

	/**
	 * Caso o sistema esteja configurado para expirar senha, então verifica se senha
	 * do usuário expirou. Caso esteja expirada, redireciona para página de
	 * trocar-senha.
	 */
	private void verificaSeSenhaExpirou() {
		// TODO Terminar. Tem que fazer o redirect;
		/*
		 * if (getSeguranca().getPeriodoExpiraSenha() && isUsuarioValido()) {
		 * 
		 * Set<ConstraintViolation<Usuario>> violations =
		 * HibernateUtil.getViolations(getValidator(), usuario,
		 * CheckSenhaExpirouConstraintGroup.class);
		 * 
		 * if (violations.size() > 0) { List<FacesMessage> msgs =
		 * FacesMessageUtil.createErrorMessages(violations); setTrocarSenha(true); throw
		 * new ValidatorException(msgs); } }
		 */
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
	public void validarLogin(FacesContext context, UIComponent validate, Object value) throws ValidatorException {

		FacesMessage msg = null;

		// Verifica se é usuário
		try {
			usuario = usuarioBean.buscarPorLogin((String) value);

			// Verifica se o login informado existe.
			if (usuario == null) {
				msg = FacesMessageUtils.createError(SegurancaValidationMessages.LOGIN_INVALIDO);
				throw new ValidatorException(msg);
			} else {

				usuarioBean.detached(usuario);
				// Verifica se usuário está ativo.
				if (usuario.getSituacao() != StatusType.ATIVO) {
					msg = FacesMessageUtils.createError(SegurancaValidationMessages.USUARIO_ESTA_BLOQUEADO);
					throw new ValidatorException(msg);
				}

				// Verifica se usuário possui pelo menos um perfil
				// FIXME: refatorar, pois mudou a regra de negocio. agora está
				// em usuario-sistema.
				/*
				 * if (usuario.getPerfis().size() < 1) { msg =
				 * FacesMessageUtils.createError(SegurancaValidationMessages.
				 * USUARIO_SEM_PERFIL); throw new ValidatorException(msg); }
				 */
			}
		} catch (BeanException e) {
			FacesMessageUtils.addFatal(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Faz as seguintes validações no campo 'senha' do Usuario:
	 * 
	 * 
	 * <pre>
	 * - Verifica se a senha informada está correta; <br>
	 * - Verifica se o sistema está configurado para trocar senha no primeiro acesso. Caso esteja, redireciona para página de trocar-senha; 
	 * - Verifica se a senha expirou. Case tenha expirado, redireciona para página de trocar-senha.
	 * </pre>
	 */
	public void validarSenha(FacesContext context, UIComponent validate, Object value) throws ValidatorException {

		verificaSeSenhaCorreta(context, validate, value);
		verificaSePrimeiroAcesso();
		verificaSeSenhaExpirou();

	}

	/**
	 * Verifica se a senha atual do usuário está correta. Caso seja invalida, lança
	 * exceção.
	 */
	public void verificaSeSenhaCorreta(FacesContext context, UIComponent validate, Object value)
			throws ValidatorException {

		String senhaCifrada = null; // PasswordHash.hashPassword(login, (String)
									// value);

		if (!usuario.getSenha().equals(senhaCifrada)) {
			FacesMessage msg = FacesMessageUtils.createError(SegurancaValidationMessages.SENHA_INCORRETA);

			throw new ValidatorException(msg);
		}

	}

	/**
	 * Limpa a View.
	 */
	public void limpar() {
		FacesUtils.resetView();
	}

	/**
	 * Fecha a sessão do usuário.
	 * 
	 * @return
	 */
	public String logout() {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		HttpServletRequest htp = (HttpServletRequest) ec.getRequest();
		try {
			htp.getSession().invalidate();
			htp.logout();
		} catch (ServletException e) {
			e.printStackTrace();
			FacesMessageUtils.addFatal(e.getMessage());
		}
		return NavigationOutcomeDefault.SUCCESSO.toString();
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Sistema getSistema() {
		return sistema;
	}

	public PolicitaSenha getSeguranca() {
		// FIXME tirei seguranca do sistema. tem que ver onde vai ficar.
		return null; // sistema.getSeguranca();
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

package br.leg.rr.al.seguranca.jpa;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import br.leg.rr.al.core.domain.StatusType;
import br.leg.rr.al.core.domain.StatusTypeConverter;
import br.leg.rr.al.core.jpa.BaseEntityStatus;
import br.leg.rr.al.core.utils.DataUtils;
import br.leg.rr.al.seguranca.core.jpa.Sistema;
import br.leg.rr.al.seguranca.domain.SegurancaValidationMessages;
import br.leg.rr.al.seguranca.jpa.usuario.constraints.SenhaConstraint;
import br.leg.rr.al.seguranca.jpa.usuario.constraints.SenhaConstraintGroup;
import br.leg.rr.al.seguranca.jpa.usuario.constraints.ValidUsuario;

/**
 * TODO: Ver a parte de segurança nesses modelos.
 * https://directory.apache.org/fortress/user-guide/1.3-what-rbac-is.html RBAC0
 * - Users, Roles, Permissions (Objects-Operations), Sessions - Form the Core of
 * ANSI RBAC. Role activation and Permissions mapped to Object->Operation
 * pairing are key facets of the basic ANSI RBAC model.
 * https://en.wikipedia.org/wiki/Role-based_access_control
 */
@Entity
@Table(indexes = { @Index(name = "usuario_idx1", columnList = "login", unique = true) })
@SenhaConstraint(groups = SenhaConstraintGroup.class)
@ValidUsuario
public class Usuario extends BaseEntityStatus<Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8197229632967877856L;

	@NotNull(message = "Preenchimento obrigatório do campo: Login")
	@Column(nullable = false, length = 20, unique = true)
	private String login;

	@Column(name = "login_ldap", nullable = true, length = 20, unique = true)
	private String loginLDAP;

	@NotNull(message = "Preenchimento obrigatório do campo: Senha")
	@Column(nullable = false, length = 300)
	private String senha;

	@Transient
	private String confirmaSenha;

	@NotNull(message = SegurancaValidationMessages.INFORME_A_SITUACAO)
	@Convert(converter = StatusTypeConverter.class)
	@Column(name = "situacao", length = 1, nullable = false)
	private StatusType situacao = StatusType.ATIVO;

	@Column(nullable = false)
	private Boolean isCertificado = false;

	@Temporal(TemporalType.DATE)
	@Column(name = "data_cadastro", nullable = false, updatable = false)
	private Date dataCadastro;

	// FIXME Mudar essa data ultimo acesso para data ultima autenticação. Criar uma
	// tabela que vai auditar as tentativas de acesso e será registrado para
	// auditoria. Esse campo data ultimo acesso já está presenta na tabela
	// UsuárioSistema.
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_ultimo_acesso", nullable = true)
	private Date dataUltimoAcesso;

	// TODO: ainda não sei se vou usar data limite. deixar mais pra frente.
	@Temporal(TemporalType.DATE)
	@Column(name = "data_limite_bloqueado")
	private Date dataLimiteBloqueado;

	@Temporal(TemporalType.DATE)
	@Column(name = "data_expira_senha", nullable = true)
	private Date dataExpiraSenha;

	@Column(nullable = true, length = 30)
	private String theme;

	// @Valid
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinTable(name = "usuario_sistema", joinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "sistema_id", referencedColumnName = "id"))
	private List<Sistema> sistemas = new ArrayList<Sistema>();

	/**
	 * Caso o UsuarioType seja igual a 'Externo', então deve vincular o sistema ao
	 * usuário.
	 */
	@OneToOne(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER, orphanRemoval = true)
	@JoinColumn(name = "usuario_tipo_id", unique = true, foreignKey = @ForeignKey(name = "usuario_tipo_fk"))
	private UsuarioTipo tipoUsuario;

	@PrePersist
	void onPrePersist() {
		dataCadastro = DataUtils.agora();
	}

	@PreUpdate
	public void onPreUpdate() {
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

	public Date getDataExpiraSenha() {
		return dataExpiraSenha;
	}

	public void setDataExpiraSenha(Date dataExpiraSenha) {
		this.dataExpiraSenha = dataExpiraSenha;
	}

	public Boolean isCertificado() {
		return isCertificado;
	}

	public void setCertificado(Boolean isCertificado) {
		this.isCertificado = isCertificado;
	}

	public Date getDataLimiteBloqueado() {
		return dataLimiteBloqueado;
	}

	public void setDataLimiteBloqueado(Date dataLimiteBloqueado) {
		this.dataLimiteBloqueado = dataLimiteBloqueado;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public StatusType getSituacao() {
		return situacao;
	}

	public void setSituacao(StatusType situacao) {
		this.situacao = situacao;
	}

	public Date getDataUltimoAcesso() {
		return dataUltimoAcesso;
	}

	public void setDataUltimoAcesso(Date dataUltimoAcesso) {
		this.dataUltimoAcesso = dataUltimoAcesso;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getConfirmaSenha() {
		return confirmaSenha;
	}

	public void setConfirmaSenha(String confirmaSenha) {
		this.confirmaSenha = confirmaSenha;
	}

	public String getLoginLDAP() {
		return loginLDAP;
	}

	public void setLoginLDAP(String loginLDAP) {
		this.loginLDAP = loginLDAP;
	}

	public List<Sistema> getSistemas() {
		return sistemas;
	}

	public void setSistemas(List<Sistema> sistemas) {
		this.sistemas = sistemas;
	}

	/**
	 * @return the tipoUsuario
	 */
	public UsuarioTipo getTipoUsuario() {
		return tipoUsuario;
	}

	/**
	 * @param tipoUsuario the tipoUsuario to set
	 */
	public void setTipoUsuario(UsuarioTipo tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

}

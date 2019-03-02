package br.leg.rr.al.seguranca.jpa;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import br.leg.rr.al.core.domain.StatusType;
import br.leg.rr.al.core.domain.StatusTypeConverter;
import br.leg.rr.al.core.jpa.BaseEntityStatus;
import br.leg.rr.al.seguranca.core.jpa.Sistema;
import br.leg.rr.al.seguranca.domain.SegurancaValidationMessages;

@Entity
@Table(name = "usuario_sistema", uniqueConstraints = @UniqueConstraint(name = "uq_usuario_sistema", columnNames = {
		"sistema_id", "usuario_id" }))
public class UsuarioSistema extends BaseEntityStatus<Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7824283750235574999L;

	@NotNull(message = SegurancaValidationMessages.INFORME_A_SITUACAO)
	@Convert(converter = StatusTypeConverter.class)
	@Column(name = "situacao", length = 1, nullable = false)
	private StatusType situacao = StatusType.ATIVO;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_ultimo_acesso", nullable = true)
	private Date dataUltimoAcesso;

	@NotNull(message = SegurancaValidationMessages.INFORME_UM_USUARIO)
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "usuario_id", nullable = false, foreignKey = @ForeignKey(name = "usuario_fk"), updatable = false)
	private Usuario usuario;

	@NotNull(message = SegurancaValidationMessages.INFORME_UM_SISTEMA)
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "sistema_id", nullable = false, foreignKey = @ForeignKey(name = "sistema_fk"), updatable = false)
	private Sistema sistema;

	@Valid
	@NotNull(message = SegurancaValidationMessages.USUARIO_SEM_PERFIL)
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "usuario_sistema_perfil", joinColumns = @JoinColumn(name = "usuario_sistema_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "usuario_sistema_fk")), inverseJoinColumns = @JoinColumn(name = "sistema_perfil_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "sistema_perfil_fk")))
	private List<SistemaPerfil> perfis = new ArrayList<SistemaPerfil>();

	public StatusType getSituacao() {
		return situacao;
	}

	public void setSituacao(StatusType situacao) {
		this.situacao = situacao;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Sistema getSistema() {
		return sistema;
	}

	public void setSistema(Sistema sistema) {
		this.sistema = sistema;
	}

	public List<SistemaPerfil> getPerfis() {
		return perfis;
	}

	public void setPerfis(List<SistemaPerfil> perfis) {
		this.perfis = perfis;
	}

}

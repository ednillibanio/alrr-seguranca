package br.leg.rr.al.seguranca.jpa;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import br.leg.rr.al.core.domain.StatusType;
import br.leg.rr.al.seguranca.core.jpa.SistemaEntityStatus;
import br.leg.rr.al.seguranca.domain.SegurancaValidationMessages;

/**
 * @author Ednil Libanio da Costa Junior
 * @date 17-04-2018
 */
@Entity
@Table(name = "sistema_perfil", uniqueConstraints = @UniqueConstraint(name = "uq_sistema_perfil", columnNames = {
		"sistema_id", "perfil_id" }))
public class SistemaPerfil extends SistemaEntityStatus<Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8129877512315212527L;

	@NotNull(message = SegurancaValidationMessages.INFORME_UM_PERFIL)
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "perfil_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "perfil_fk"), updatable = false)
	private Perfil perfil;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "sistema_perfil_permissao", joinColumns = @JoinColumn(name = "sistema_perfil_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "sistema_permissao_id", referencedColumnName = "id"))
	private Set<SistemaPermissao> permissoes = new HashSet<SistemaPermissao>();

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public StatusType getSituacao() {
		return situacao;
	}

	public void setSituacao(StatusType situacao) {
		this.situacao = situacao;
	}

	public Set<SistemaPermissao> getPermissoes() {
		return permissoes;
	}

	public void setPermissoes(Set<SistemaPermissao> permissoes) {
		this.permissoes = permissoes;
	}

}

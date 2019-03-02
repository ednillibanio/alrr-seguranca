package br.leg.rr.al.seguranca.jpa;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import br.leg.rr.al.seguranca.core.jpa.SistemaDominio;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "uq_modulo_nome", columnNames = { "sistema_id", "modulo_id",
		"nome" }))
public class SistemaObjeto extends SistemaDominio {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6682609811660410146L;

	@NotNull(message = "Módulo: preenchimento obrigatório")
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "modulo_id", foreignKey = @ForeignKey(name = "modulo_fk"))
	private SistemaModulo sistemaModulo;

	@OneToMany(mappedBy = "sistemaObjeto")
	private Set<SistemaPermissao> permissoes;

	public SistemaModulo getModulo() {
		return sistemaModulo;
	}

	public void setModulo(SistemaModulo sistemaModulo) {
		this.sistemaModulo = sistemaModulo;
	}

	public Set<SistemaPermissao> getPermissoes() {
		return permissoes;
	}

	public void setPermissoes(Set<SistemaPermissao> permissoes) {
		this.permissoes = permissoes;
	}

}

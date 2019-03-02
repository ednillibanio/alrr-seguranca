package br.leg.rr.al.seguranca.jpa;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "uq_nome", columnNames = { "sistema_id", "nome" }))
public class Operacao extends SistemaDominio {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8174647483573811318L;

	@Column(length = 250, nullable = true)
	private String url;

	@OneToMany(mappedBy = "operacao")
	private Set<SistemaPermissao> permissoes;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Set<SistemaPermissao> getPermissoes() {
		return permissoes;
	}

	public void setPermissoes(Set<SistemaPermissao> permissoes) {
		this.permissoes = permissoes;
	}

}

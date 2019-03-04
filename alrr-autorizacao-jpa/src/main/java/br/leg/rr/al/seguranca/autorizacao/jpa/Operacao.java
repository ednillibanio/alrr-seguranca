package br.leg.rr.al.seguranca.autorizacao.jpa;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.leg.rr.al.core.jpa.Dominio;

@Entity
@Table
public class Operacao extends Dominio {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = -57579453703000612L;

	@Column(length = 250, nullable = true)
	private String url;

	@OneToMany(mappedBy = "operacao", fetch = FetchType.LAZY)
	private Set<Permissao> permissoes;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Set<Permissao> getPermissoes() {
		return permissoes;
	}

	public void setPermissoes(Set<Permissao> permissoes) {
		this.permissoes = permissoes;
	}

}

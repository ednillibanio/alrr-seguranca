package br.leg.rr.al.seguranca.autorizacao.jpa;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "objeto_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "objeto_fk"))
	private Objeto objeto;

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

	public Objeto getObjeto() {
		return objeto;
	}

	public void setObjeto(Objeto objeto) {
		this.objeto = objeto;
	}

}

package br.leg.rr.al.seguranca.autorizacao.jpa;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.search.annotations.Indexed;

import br.leg.rr.al.core.jpa.DominioIndexado;

@Indexed
@Entity
@Table
public class Modulo extends DominioIndexado {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7826110709692328396L;

	@Column(nullable = true, length = 250)
	private String descricao;

	@OneToMany(mappedBy = "modulo", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
	private Set<Objeto> objetos;

	public Set<Objeto> getObjetos() {
		return objetos;
	}

	public void setObjetos(Set<Objeto> objetos) {
		this.objetos = objetos;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}

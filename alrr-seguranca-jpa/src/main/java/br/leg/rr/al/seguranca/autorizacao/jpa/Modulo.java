package br.leg.rr.al.seguranca.autorizacao.jpa;

import java.util.Set;

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
	private static final long serialVersionUID = -7757420959952259509L;

	@OneToMany(mappedBy = "modulo", fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<Objeto> objetos;

	public Set<Objeto> getObjetos() {
		return objetos;
	}

	public void setObjetos(Set<Objeto> objetos) {
		this.objetos = objetos;
	}

}

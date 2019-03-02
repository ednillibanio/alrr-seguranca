package br.leg.rr.al.seguranca.jpa;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "uq_sistema_nome", columnNames = { "sistema_id", "nome" }))
public class Modulo extends SistemaDominio {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7757420959952259509L;

	@OneToMany(mappedBy = "modulo", fetch = FetchType.LAZY)
	private Set<Objeto> objetos;

	public Set<Objeto> getObjetos() {
		return objetos;
	}

	public void setObjetos(Set<Objeto> objetos) {
		this.objetos = objetos;
	}

}

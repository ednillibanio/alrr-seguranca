package br.leg.rr.al.seguranca.jpa;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import br.leg.rr.al.seguranca.core.jpa.SistemaDominioIndexado;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "uq_sistema_nome", columnNames = { "sistema_id", "nome" }))
public class SistemaModulo extends SistemaDominioIndexado {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7757420959952259509L;

	@OneToMany(mappedBy = "sistemaModulo", fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<SistemaObjeto> sistemaObjetos;

	public Set<SistemaObjeto> getObjetos() {
		return sistemaObjetos;
	}

	public void setObjetos(Set<SistemaObjeto> sistemaObjetos) {
		this.sistemaObjetos = sistemaObjetos;
	}

}

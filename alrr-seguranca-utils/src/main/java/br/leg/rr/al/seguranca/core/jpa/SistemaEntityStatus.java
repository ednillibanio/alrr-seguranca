package br.leg.rr.al.seguranca.jpa;

import java.io.Serializable;

import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import br.leg.rr.al.core.jpa.BaseEntityStatus;

@MappedSuperclass
public abstract class SistemaEntityStatus<ID extends Serializable> extends BaseEntityStatus<ID> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8148267451241755568L;

	@NotNull(message = "Sistema: preenchimento obrigatório. Informe o sistema.")
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "sistema_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "sistema_fk"), updatable = false)
	private Sistema sistema;

	public Sistema getSistema() {
		return sistema;
	}

	public void setSistema(Sistema sistema) {
		this.sistema = sistema;
	}

}

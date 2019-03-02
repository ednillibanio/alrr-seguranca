package br.leg.rr.al.seguranca.jpa;

import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import br.leg.rr.al.core.jpa.Dominio;
import br.leg.rr.al.seguranca.domain.SegurancaValidationMessages;

@MappedSuperclass
public abstract class SistemaDominio extends Dominio {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6203526793762603730L;

	@NotNull(message = SegurancaValidationMessages.INFORME_UM_SISTEMA)
	@ManyToOne(optional = false)
	@JoinColumn(name = "sistema_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_sistema"), updatable = false)
	private Sistema sistema;

	public Sistema getSistema() {
		return sistema;
	}

	public void setSistema(Sistema sistema) {
		this.sistema = sistema;
	}

}

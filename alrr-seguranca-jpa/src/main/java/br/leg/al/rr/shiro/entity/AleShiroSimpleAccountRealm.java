/**
 *
 */
package br.leg.al.rr.shiro.entity;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import br.leg.rr.al.core.domain.StatusType;
import br.leg.rr.al.core.domain.StatusTypeConverter;

/**
 * @author <a href="mailto:ednil.libanio@gmail.com"> Ednil Libanio da Costa
 *         Junior</a>
 * @since 1.0.0
 */
@Embeddable
public class AleShiroSimpleAccountRealm {

	@NotNull(message = "Preenchimento obrigatório do campo Situação do Simple Account Realm.")
	@Convert(converter = StatusTypeConverter.class)
	@Column(name = "simple_account_realm_situacao", length = 1, nullable = false)
	private StatusType situacao;

	/**
	 * @return the situacao
	 */
	public StatusType getSituacao() {
		return situacao;
	}

	/**
	 * @param situacao the situacao to set
	 */
	public void setSituacao(StatusType situacao) {
		this.situacao = situacao;
	}
}

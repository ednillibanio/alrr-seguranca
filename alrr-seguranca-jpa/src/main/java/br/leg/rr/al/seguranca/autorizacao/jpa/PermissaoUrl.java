package br.leg.rr.al.seguranca.autorizacao.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.leg.rr.al.core.jpa.BaseEntityStatus;

@Entity
@Table
public class PermissaoUrl extends BaseEntityStatus<Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5007886053785293013L;

	@NotNull(message = "Url: preenchimento obrigatório")
	@Column(length = 500, nullable = false)
	private String url;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "permissao_id", foreignKey = @ForeignKey(name = "permissao_fk"), nullable = false)
	private Permissao permissao;

}

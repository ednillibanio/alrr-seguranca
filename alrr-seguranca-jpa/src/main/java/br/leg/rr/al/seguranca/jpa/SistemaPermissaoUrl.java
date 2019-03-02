package br.leg.rr.al.seguranca.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import br.leg.rr.al.core.jpa.BaseEntityStatus;

@Entity
@Table(name = "sistema_permissao_url", uniqueConstraints = @UniqueConstraint(name = "uq_permissao_url", columnNames = {
		"sistema_permissao_id", "url" }))
public class SistemaPermissaoUrl extends BaseEntityStatus<Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5007886053785293013L;

	@NotNull(message = "Preenchimento obrigatório do campo url.")
	@Column(length = 500, nullable = false)
	private String url;

	@ManyToOne
	@JoinColumn(name = "sistema_permissao_id", foreignKey = @ForeignKey(name = "sistema_permissao_fk"), nullable = false)
	private SistemaPermissao sistemaPermissao;

}

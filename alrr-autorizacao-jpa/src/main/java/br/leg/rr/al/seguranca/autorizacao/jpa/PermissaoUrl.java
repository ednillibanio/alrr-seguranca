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
@Table(name = "permissao_url")
public class PermissaoUrl extends BaseEntityStatus<Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7438093619066433105L;

	@NotNull(message = "Url: preenchimento obrigatório")
	@Column(length = 500, nullable = false)
	private String url;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "permissao_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "permissao_fk"), nullable = false)
	private Permissao permissao;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Permissao getPermissao() {
		return permissao;
	}

	public void setPermissao(Permissao permissao) {
		this.permissao = permissao;
	}

}

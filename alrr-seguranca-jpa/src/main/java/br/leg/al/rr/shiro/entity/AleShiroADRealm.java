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
public class AleShiroADRealm {

	@Column(name = "ad_realm_search_base", length = 400)
	private String searchBase;

	@Column(name = "ad_realm_url", length = 500)
	private String url;

	@Column(name = "ad_realm_principal_suffix", length = 250)
	private String principalSuffix;

	@NotNull(message = "Preenchimento obrigatório do campo Situação do Active Directory Realm.")
	@Convert(converter = StatusTypeConverter.class)
	@Column(name = "ad_realm_situacao", length = 1, nullable = false)
	private StatusType situacao;

	/**
	 * @return the searchBase
	 */
	public String getSearchBase() {
		return searchBase;
	}

	/**
	 * @param searchBase the searchBase to set
	 */
	public void setSearchBase(String searchBase) {
		this.searchBase = searchBase;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the principalSuffix
	 */
	public String getPrincipalSuffix() {
		return principalSuffix;
	}

	/**
	 * @param principalSuffix the principalSuffix to set
	 */
	public void setPrincipalSuffix(String principalSuffix) {
		this.principalSuffix = principalSuffix;
	}

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

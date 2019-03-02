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
public class AleShiroJdbcRealm {

	@Column(name = "jdbc_realm_authentication_query", length = 500)
	private String authenticationQuery;

	@Column(name = "jdbc_realm_user_roles_query", length = 500)
	private String userRolesQuery;

	@Column(name = "jdbc_realm_permissions_query", length = 500)
	private String permissionsQuery;

	@Column(name = "jdbc_realm_permissions_lookup_enabled")
	private Boolean permissionsLookupEnabled;

	@NotNull(message = "Preenchimento obrigatório do campo Situação do Jdbc Realm.")
	@Convert(converter = StatusTypeConverter.class)
	@Column(name = "jdbc_realm_situacao", length = 1, nullable = false)
	private StatusType situacao;

	/**
	 * @return the authenticationQuery
	 */
	public String getAuthenticationQuery() {
		return authenticationQuery;
	}

	/**
	 * @param authenticationQuery the authenticationQuery to set
	 */
	public void setAuthenticationQuery(String authenticationQuery) {
		this.authenticationQuery = authenticationQuery;
	}

	/**
	 * @return the userRolesQuery
	 */
	public String getUserRolesQuery() {
		return userRolesQuery;
	}

	/**
	 * @param userRolesQuery the userRolesQuery to set
	 */
	public void setUserRolesQuery(String userRolesQuery) {
		this.userRolesQuery = userRolesQuery;
	}

	/**
	 * @return the permissionsQuery
	 */
	public String getPermissionsQuery() {
		return permissionsQuery;
	}

	/**
	 * @param permissionsQuery the permissionsQuery to set
	 */
	public void setPermissionsQuery(String permissionsQuery) {
		this.permissionsQuery = permissionsQuery;
	}

	/**
	 * @return the permissionsLookupEnabled
	 */
	public Boolean getPermissionsLookupEnabled() {
		return permissionsLookupEnabled;
	}

	/**
	 * @param permissionsLookupEnabled the permissionsLookupEnabled to set
	 */
	public void setPermissionsLookupEnabled(Boolean permissionsLookupEnabled) {
		this.permissionsLookupEnabled = permissionsLookupEnabled;
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

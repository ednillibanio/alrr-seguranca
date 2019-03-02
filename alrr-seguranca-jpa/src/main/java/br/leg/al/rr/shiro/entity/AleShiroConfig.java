/**
 *
 */
package br.leg.al.rr.shiro.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.leg.rr.al.core.jpa.BaseEntity;

/**
 * @author <a href="mailto:ednil.libanio@gmail.com"> Ednil Libanio da Costa
 *         Junior</a>
 * @since 1.0.0
 */
@Entity
@Table(name = "shiro_config")
public class AleShiroConfig extends BaseEntity<Integer> {

	public static String NOME_SHIRO_CONFIG = "AleShiroConfig";
	/**
	 * 
	 */
	private static final long serialVersionUID = -4343151120406013428L;

	@NotNull(message = "Preenchimento obrigatório do campo nome do Shiro Configuração.")
	@Column(name = "nome", length = 20, unique = true, nullable = false)
	private String nome = NOME_SHIRO_CONFIG;

	@Embedded
	private AleShiroDataSource dataSource;

	@Embedded
	private AleShiroAuthenticationFilter authc;

	@Embedded
	private AleShiroSimpleAccountRealm simpleAccountRealm;

	@Embedded
	private AleShiroJdbcRealm jdbcRealm;

	@Embedded
	private AleShiroADRealm adRealm;

	@Embedded
	private AleShiroJpaRealm jpaRealm;

	@Embedded
	private AleShiroSessionManager sessionManager;

	@Embedded
	private AleShiroSessionMgtCookie cookie;

	/**
	 * @return the authc
	 */
	public AleShiroAuthenticationFilter getAuthc() {
		return authc;
	}

	/**
	 * @param authc
	 *            the authc to set
	 */
	public void setAuthc(AleShiroAuthenticationFilter authc) {
		this.authc = authc;
	}

	/**
	 * @return the dataSource
	 */
	public AleShiroDataSource getDataSource() {
		return dataSource;
	}

	/**
	 * @param dataSource
	 *            the dataSource to set
	 */
	public void setDataSource(AleShiroDataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * @return the sessionManager
	 */
	public AleShiroSessionManager getSessionManager() {
		return sessionManager;
	}

	/**
	 * @param sessionManager
	 *            the sessionManager to set
	 */
	public void setSessionManager(AleShiroSessionManager sessionManager) {
		this.sessionManager = sessionManager;
	}

	/**
	 * @return the simpleAccountRealm
	 */
	public AleShiroSimpleAccountRealm getSimpleAccountRealm() {
		return simpleAccountRealm;
	}

	/**
	 * @param simpleAccountRealm
	 *            the simpleAccountRealm to set
	 */
	public void setSimpleAccountRealm(AleShiroSimpleAccountRealm simpleAccountRealm) {
		this.simpleAccountRealm = simpleAccountRealm;
	}

	/**
	 * @return the jdbcRealm
	 */
	public AleShiroJdbcRealm getJdbcRealm() {
		return jdbcRealm;
	}

	/**
	 * @param jdbcRealm
	 *            the jdbcRealm to set
	 */
	public void setJdbcRealm(AleShiroJdbcRealm jdbcRealm) {
		this.jdbcRealm = jdbcRealm;
	}

	/**
	 * @return the adRealm
	 */
	public AleShiroADRealm getAdRealm() {
		return adRealm;
	}

	/**
	 * @param adRealm
	 *            the adRealm to set
	 */
	public void setAdRealm(AleShiroADRealm adRealm) {
		this.adRealm = adRealm;
	}

	/**
	 * @return the jpaRealm
	 */
	public AleShiroJpaRealm getJpaRealm() {
		return jpaRealm;
	}

	/**
	 * @param jpaRealm
	 *            the jpaRealm to set
	 */
	public void setJpaRealm(AleShiroJpaRealm jpaRealm) {
		this.jpaRealm = jpaRealm;
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome
	 *            the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the cookie
	 */
	public AleShiroSessionMgtCookie getCookie() {
		return cookie;
	}

	/**
	 * @param cookie
	 *            the cookie to set
	 */
	public void setCookie(AleShiroSessionMgtCookie cookie) {
		this.cookie = cookie;
	}

}

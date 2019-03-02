/**
 *
 */
package br.leg.al.rr.shiro.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author <a href="mailto:ednil.libanio@gmail.com"> Ednil Libanio da Costa
 *         Junior</a>
 * @since 1.0.0
 */
@Embeddable
public class AleShiroAuthenticationFilter {

	@Column(name = "authc_login_url", length = 500)
	private String loginUrl = "/autenticacao.xhtml";

	@Column(name = "authc_sucess_url", length = 500)
	private String sucessUrl = "/pagina-inicial.xhtml?redirect=true";

	@Column(name = "authc_redirect_url", length = 500)
	private String redirectUrl = "/autenticacao.xhtml";

	@Column(name = "authc_unauthorized_url", length = 500)
	private String unauthorizedUrl;

	/**
	 * @return the loginUrl
	 */
	public String getLoginUrl() {
		return loginUrl;
	}

	/**
	 * @param loginUrl
	 *            the loginUrl to set
	 */
	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	/**
	 * @return the sucessUrl
	 */
	public String getSucessUrl() {
		return sucessUrl;
	}

	/**
	 * @param sucessUrl
	 *            the sucessUrl to set
	 */
	public void setSucessUrl(String sucessUrl) {
		this.sucessUrl = sucessUrl;
	}

	/**
	 * @return the redirectUrl
	 */
	public String getRedirectUrl() {
		return redirectUrl;
	}

	/**
	 * @param redirectUrl
	 *            the redirectUrl to set
	 */
	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	/**
	 * @return the unauthorizedUrl
	 */
	public String getUnauthorizedUrl() {
		return unauthorizedUrl;
	}

	/**
	 * @param unauthorizedUrl
	 *            the unauthorizedUrl to set
	 */
	public void setUnauthorizedUrl(String unauthorizedUrl) {
		this.unauthorizedUrl = unauthorizedUrl;
	}
}

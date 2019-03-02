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
public class AleShiroSessionMgtCookie {

	@Column(name = "session_mgt_cookie_name", length = 25)
	private String name = "sid";

	@Column(name = "session_mgt_cookie_max_age", length = 10)
	private String maxAge = "1800";

	@Column(name = "session_mgt_cookie_http_only")
	private Boolean httpOnly = true;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the maxAge
	 */
	public String getMaxAge() {
		return maxAge;
	}

	/**
	 * @param maxAge
	 *            the maxAge to set
	 */
	public void setMaxAge(String maxAge) {
		this.maxAge = maxAge;
	}

	/**
	 * @return the httpOnly
	 */
	public Boolean getHttpOnly() {
		return httpOnly;
	}

	/**
	 * @param httpOnly
	 *            the httpOnly to set
	 */
	public void setHttpOnly(Boolean httpOnly) {
		this.httpOnly = httpOnly;
	}

}

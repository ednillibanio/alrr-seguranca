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
public class AleShiroSessionManager {

	@Column(name = "session_mgt_global_session_timeout", length = 15)
	private String globalSessionTimeout = "1800000";

	@Column(name = "session_mgt_session_id_cookie_enabled")
	private Boolean sessionIdCookieEnabled = true;

	/**
	 * @return the sessionIdCookieEnabled
	 */
	public Boolean getSessionIdCookieEnabled() {
		return sessionIdCookieEnabled;
	}

	/**
	 * @param sessionIdCookieEnabled
	 *            the sessionIdCookieEnabled to set
	 */
	public void setSessionIdCookieEnabled(Boolean sessionIdCookieEnabled) {
		this.sessionIdCookieEnabled = sessionIdCookieEnabled;
	}

	/**
	 * @return the globalSessionTimeout
	 */
	public String getGlobalSessionTimeout() {
		return globalSessionTimeout;
	}

	/**
	 * @param globalSessionTimeout
	 *            the globalSessionTimeout to set
	 */
	public void setGlobalSessionTimeout(String globalSessionTimeout) {
		this.globalSessionTimeout = globalSessionTimeout;
	}

}

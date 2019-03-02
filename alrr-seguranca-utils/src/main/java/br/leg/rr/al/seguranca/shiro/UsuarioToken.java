package br.leg.rr.al.seguranca.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

public class UsuarioToken extends UsernamePasswordToken {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5703047268866145823L;

	/*---------------------------------------------------
	|    V A R I Á V E I S   D E   I N S T Â N C I A    |
	===================================================*/

	/**
	 * email do usuário
	 */
	private String email;

	/**
	 * Id do usuário cadastrado na base de dados local.
	 */
	private String id;

	public UsuarioToken() {
		super();
	}

	public UsuarioToken(String username, char[] password, boolean rememberMe, String host, String id, String email) {
		super(username, password, rememberMe, host);
		this.setId(id);
		this.setEmail(email);
	}

	public UsuarioToken(String username, char[] password, String host, String id, String email) {
		super(username, password, false, host);
		this.setId(id);
		this.setEmail(email);
	}

	public UsuarioToken(String username, char[] password, String host, String id) {
		super(username, password, false, host);
		this.setId(id);
		this.setEmail(null);
	}

	public UsuarioToken(String username, char[] password, String id) {
		super(username, password, false, null);
		this.setId(id);
		this.setEmail(null);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}

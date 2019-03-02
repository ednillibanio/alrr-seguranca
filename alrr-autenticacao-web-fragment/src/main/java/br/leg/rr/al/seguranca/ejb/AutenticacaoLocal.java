package br.leg.rr.al.seguranca.ejb;

import javax.ejb.Local;
import javax.validation.constraints.NotNull;

import br.leg.rr.al.core.dao.BeanException;

@Local
public interface AutenticacaoLocal {

	/**
	 * Método que tenta autenticar o usuário informado. A tentativa ocorre em
	 * diversos ambientes de autenticação, dependendo da configuração do
	 * shiro.ini.<br>
	 * A autenticação pode ocorrer:
	 * <ul>
	 * <li>Acessando o banco de dados que gerencia o controle de acesso;</li>
	 * <li>Acessando o LDAP Linux;</li>
	 * <li>Por último, o Active Directory da Microsoft.</li>
	 * </ul>
	 * <br>
	 * Para mais informações consulte as configurações do shiro.ini.
	 * 
	 * @param login
	 *            nome do usuário a ser autenticado.
	 * @param senha
	 *            senha do usuário a ser autenticado.
	 */
	void autenticar(@NotNull(message = "Argumento login não pode ser nulo.") String login,
			@NotNull(message = "Argumento senha não pode ser nulo.") String senha) throws BeanException;

	/**
	 * Médoto que finaliza a sessão do usuário autenticado.
	 */
	void sair();

}

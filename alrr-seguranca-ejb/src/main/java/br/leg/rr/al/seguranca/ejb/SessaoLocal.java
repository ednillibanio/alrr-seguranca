/**
 * 
 */
package br.leg.rr.al.seguranca.ejb;

import java.util.List;

import javax.ejb.Local;

import br.leg.rr.al.core.dao.BeanException;
import br.leg.rr.al.core.dao.JPADaoStatus;
import br.leg.rr.al.seguranca.jpa.Sessao;

/**
 * @author ednil
 *
 */
@Local
public interface SessaoLocal extends JPADaoStatus<Sessao, Integer> {

	/**
	 * Busca Sessão pelo <code>id</code> da entidade PessoaFisica.
	 * 
	 * @param pessoaFisicaId
	 *            id da pessoa fisica
	 * @return retorna usuário ou <code>null</code> caso não encontre o usuário
	 *         conforme id informado.
	 */
	Sessao findByPessoaFisica(Long pessoaFisicaId) throws BeanException;

	/**
	 * Busca os usuários por nome da pessoa.
	 * 
	 * @param nome
	 *            deve ser convertido para upperCase e contatenado com %.
	 * @return lista de usuários encontrados.
	 * @throws ControllerException
	 */
	List<Sessao> findByPessoaFisica(String nome) throws BeanException;

}

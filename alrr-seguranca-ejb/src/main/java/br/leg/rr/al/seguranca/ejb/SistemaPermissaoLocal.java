package br.leg.rr.al.seguranca.ejb;

import java.util.List;

import javax.ejb.Local;

import br.leg.rr.al.core.dao.JPADaoStatus;
import br.leg.rr.al.seguranca.jpa.Objeto;
import br.leg.rr.al.seguranca.jpa.Operacao;
import br.leg.rr.al.seguranca.jpa.Sistema;
import br.leg.rr.al.seguranca.jpa.SistemaPermissao;

@Local
public interface SistemaPermissaoLocal extends JPADaoStatus<SistemaPermissao, Integer> {

	/**
	 * Busca pelo campo sistema da entidade {@code Permissao}.<br>
	 * 
	 * @value {@literal Sistema};
	 */
	public static final String PESQUISAR_PARAM_SISTEMA = "sistema";

	/**
	 * Busca pela lista de Modulo.<br>
	 * 
	 * @value {@literal List<Modulo>};
	 * 
	 */
	public static final String PESQUISAR_PARAM_MODULOS = "modulos";

	/**
	 * Indica se deve carregar os dados do sistemas que a permissao faz
	 * parte.<br>
	 * 
	 * @value {@literal Boolean}. Se {@code true}, carrega os dados do
	 *        {@code Sistema};
	 * 
	 */
	public static final String PESQUISAR_PARAM_FETCH_SISTEMA = "fetch-sistema";

	/**
	 * Indica se deve carregar os dados da entidade {@code Operacao} que está
	 * associada a {@code Permissão}.<br>
	 * 
	 * @value {@literal Boolean}. Se {@code true}, carrega os dados do
	 *        {@code Operacao};
	 * 
	 */
	public static final String PESQUISAR_PARAM_FETCH_OPERACAO = "fetch-operacao";

	/**
	 * Indica se deve carregar os dados da entidade {@code Objeto} que está
	 * associada a {@code Permissão}.<br>
	 * 
	 * @value {@literal Boolean}. Se {@code true}, carrega os dados do
	 *        {@code Objeto};
	 * 
	 */
	public static final String PESQUISAR_PARAM_FETCH_OBJETO = "fetch-objeto";

	/**
	 * Indica se deve carregar os dados da entidade {@code Modulo} que está
	 * associada a {@code Objeto}.<br>
	 * 
	 * @value {@literal Boolean}. Se {@code true}, carrega os dados do
	 *        {@code Modulo};
	 * 
	 */
	public static final String PESQUISAR_PARAM_FETCH_MODULO = "fetch-modulo";

	List<SistemaPermissao> buscarTodos(Sistema sistema);

	/**
	 * Busca todas as operações que estão cadastradas como permissão de acesso
	 * para um objeto no sistema selecionado.
	 * 
	 * @param sistema
	 *            entidade que detem o objeto que compoem a permissão
	 * @param objeto
	 *            entidade que tem as operação associadas.
	 * @return lista de operações que satisfazem o {@value sistema} e
	 *         {@value objetoSelecionado}. Se não houver operações, retorna
	 *         nulo.
	 */
	List<Operacao> buscarOperacoesPorObjeto(Sistema sistema, Objeto objeto);

}

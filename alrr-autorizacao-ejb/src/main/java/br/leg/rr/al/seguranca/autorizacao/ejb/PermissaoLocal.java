package br.leg.rr.al.seguranca.autorizacao.ejb;

import java.util.List;

import javax.ejb.Local;

import br.leg.rr.al.core.dao.JPADaoStatus;
import br.leg.rr.al.seguranca.autorizacao.jpa.Modulo;
import br.leg.rr.al.seguranca.autorizacao.jpa.Objeto;
import br.leg.rr.al.seguranca.autorizacao.jpa.Operacao;
import br.leg.rr.al.seguranca.autorizacao.jpa.Permissao;

@Local
public interface PermissaoLocal extends JPADaoStatus<Permissao, Integer> {

	/**
	 * Busca pela lista de SistemaModulo.<br>
	 * 
	 * @value {@literal List<SistemaModulo>};
	 * 
	 */
	public static final String PESQUISAR_PARAM_MODULOS = "modulos";

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

	List<Permissao> buscarTodos(Modulo modulo);

	/**
	 * Busca todas as operações que estão cadastradas como permissão de acesso para
	 * um sistemaObjeto no sistema selecionado.
	 * 
	 * @param sistema       entidade que detem o objeto que compoem a permissão
	 * @param sistemaObjeto entidade que tem as operação associadas.
	 * @return lista de operações que satisfazem o {@value sistema} e
	 *         {@value objetoSelecionado}. Se não houver operações, retorna nulo.
	 */
	List<Operacao> buscarOperacoesPorObjeto(Objeto Objeto);

}

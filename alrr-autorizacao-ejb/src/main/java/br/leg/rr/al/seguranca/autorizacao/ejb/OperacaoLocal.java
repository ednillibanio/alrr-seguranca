package br.leg.rr.al.seguranca.autorizacao.ejb;

import javax.ejb.Local;

import br.leg.rr.al.core.dao.DominioJPADao;
import br.leg.rr.al.seguranca.autorizacao.jpa.Operacao;

@Local
public interface OperacaoLocal extends DominioJPADao<Operacao> {

	/**
	 * Filtro de busca "objetos" da entidade {@code Operacao}. <br>
	 * 
	 * @value {@literal List<Objeto>};
	 */
	public static final String PESQUISAR_PARAM_OBJETOS = "objetos";

	/**
	 * Indica se deve carregar as permissões associada a {@code Operacao}. <br>
	 * 
	 * @value {@literal Boolean}. Se {@code true}, carrega permissoes;
	 * 
	 */
	public static final String PESQUISAR_PARAM_FETCH_PERMISSOES = "fetchPermissoes";

	/**
	 * Filtro de busca "modulos" da entidade {@code Operacao}. Apesar de Objeto não
	 * ter vinculo com Modulo, exibe o valor caso haja um Objeto associado a
	 * Operacao<br>
	 * 
	 * @value {@literal List<Modulo>};
	 */
	public static final String PESQUISAR_PARAM_MODULOS = "modulos";

	/**
	 * Indica se deve carregar a entidade {@code Modulo} associada a
	 * {@code Operacao}. <br>
	 * 
	 * @value {@literal Boolean}. Se {@code true}, carrega Modulo;
	 * 
	 */
	public static final String PESQUISAR_PARAM_FETCH_MODULO = "fetchModulo";
}

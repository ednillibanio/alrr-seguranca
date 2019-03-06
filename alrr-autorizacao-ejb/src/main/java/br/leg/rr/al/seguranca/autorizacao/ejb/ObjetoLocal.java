package br.leg.rr.al.seguranca.autorizacao.ejb;

import java.util.List;

import javax.ejb.Local;
import javax.validation.constraints.NotNull;

import br.leg.rr.al.core.dao.DominioJPADao;
import br.leg.rr.al.core.domain.StatusType;
import br.leg.rr.al.seguranca.autorizacao.jpa.Modulo;
import br.leg.rr.al.seguranca.autorizacao.jpa.Objeto;

@Local
public interface ObjetoLocal extends DominioJPADao<Objeto> {

	/**
	 * Indica se deve carregar as permissões associada ao {@code Objeto}. <br>
	 * 
	 * @value {@literal Boolean}. Se {@code true}, carrega permissoes;
	 * 
	 */
	public static final String PESQUISAR_PARAM_FETCH_PERMISSOES = "fetchPermissoes";

	/**
	 * Filtro de busca "modulos" da entidade {@code Objeto}.<br>
	 * 
	 * @value {@literal List<Modulo>};
	 */
	public static final String PESQUISAR_PARAM_MODULOS = "modulos";

	/**
	 * Indica se deve carregar a entidade {@code Modulo} associada ao
	 * {@code Objeto}. <br>
	 * 
	 * @value {@literal Boolean}. Se {@code true}, carrega Modulo;
	 * 
	 */
	public static final String PESQUISAR_PARAM_FETCH_MODULO = "fetchModulo";

	/**
	 * Método que busca os objetos pelo módulo. Neste caso, é feito fetch do módulo.
	 * 
	 * @param modulo   filtro usado para buscar os objetos desse modulo
	 * @param situacao filtro de situacao dos objetos
	 * @return lista de objetos que satisfazem os valores dos parâmetros modulo e
	 *         situacao.
	 */
	public List<Objeto> buscarPorModulo(@NotNull(message = "Param modulo não pode ser nulo.") Modulo modulo,
			StatusType situacao);

	/**
	 * 
	 * @param fetchModulo
	 * @return
	 */
	List<Objeto> getAtivos(Boolean fetchModulo);
}

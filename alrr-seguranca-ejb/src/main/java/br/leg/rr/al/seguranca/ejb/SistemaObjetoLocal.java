package br.leg.rr.al.seguranca.ejb;

import java.util.List;

import javax.ejb.Local;
import javax.validation.constraints.NotNull;

import br.leg.rr.al.core.domain.StatusType;
import br.leg.rr.al.seguranca.core.dao.SistemaDominioDao;
import br.leg.rr.al.seguranca.jpa.Modulo;
import br.leg.rr.al.seguranca.jpa.Objeto;
import br.leg.rr.al.seguranca.jpa.Sistema;

@Local
public interface ObjetoLocal extends SistemaDominioDao<Objeto> {

	/**
	 * Indica se deve carregar as permissões associada ao {@code Objeto}. <br>
	 * 
	 * @value {@literal Boolean}. Se {@code true}, carrega permissoes;
	 * 
	 */
	public static final String PESQUISAR_PARAM_FETCH_PERMISSOES = "fetchPermissoes";

	/**
	 * Indica se deve carregar a entidade {@code Modulo} associada ao
	 * {@code Objeto}. <br>
	 * 
	 * @value {@literal Boolean}. Se {@code true}, carrega modulo;
	 * 
	 */
	public static final String PESQUISAR_PARAM_FETCH_MODULO = "fetchModulo";

	/**
	 * 
	 * @param modulo
	 * @return
	 */
	public List<Objeto> getAtivos(@NotNull(message = "O valor do param modulo não pode ser nulo.") Modulo modulo);

	/**
	 * 
	 * @param modulo
	 * @param situacao
	 * @return
	 */
	public List<Objeto> buscarPorModulo(@NotNull(message = "O valor do param modulo não pode ser nulo.") Modulo modulo,
			StatusType situacao);

	/**
	 * 
	 * @param sistema
	 * @param fetchModulo
	 * @return
	 */
	List<Objeto> getAtivos(@NotNull(message = "O valor do param sistema não pode ser nulo.") Sistema sistema,
			Boolean fetchModulo);
}

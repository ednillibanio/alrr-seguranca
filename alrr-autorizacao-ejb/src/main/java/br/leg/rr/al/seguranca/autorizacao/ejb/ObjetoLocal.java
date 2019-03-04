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
	 * Indica se deve carregar as permissões associada ao {@code Objeto}.
	 * <br>
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
	String PESQUISAR_PARAM_MODULOS = "modulos";
	
	
	/**
	 * Indica se deve carregar a entidade {@code Modulo} associada ao
	 * {@code Objeto}. <br>
	 * 
	 * @value {@literal Boolean}. Se {@code true}, carrega Modulo;
	 * 
	 */
	public static final String PESQUISAR_PARAM_FETCH_MODULO = "fetchModulo";

	
	
	/**
	 * 
	 * @param modulo
	 * @param situacao
	 * @return
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

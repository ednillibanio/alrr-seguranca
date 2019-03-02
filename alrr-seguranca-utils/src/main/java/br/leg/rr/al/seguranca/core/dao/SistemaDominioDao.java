package br.leg.rr.al.seguranca.core.dao;

import java.util.List;

import br.leg.rr.al.core.dao.DominioJPADao;
import br.leg.rr.al.core.domain.StatusType;
import br.leg.rr.al.seguranca.jpa.Sistema;
import br.leg.rr.al.seguranca.jpa.SistemaDominio;

/**
 * 
 * @author ednil
 *
 * @param <T>
 */
public interface SistemaDominioDao<T extends SistemaDominio> extends DominioJPADao<T> {

	public static final String PESQUISAR_PARAM_SISTEMA = "sistema";

	List<T> buscarPorNome(Sistema sistema, String nome);

	List<T> buscarPorSituacao(Sistema sistema, StatusType situacao);

	List<T> getAtivos(Sistema sistema);

	List<T> getAtivosPorNome(Sistema sistema, String nome);

	List<T> getInativos(Sistema sistema);

}

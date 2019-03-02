package br.leg.rr.al.seguranca.ejb;

import java.util.List;

import javax.ejb.Local;
import javax.validation.constraints.NotNull;

import br.leg.rr.al.seguranca.core.dao.SistemaDominioDao;
import br.leg.rr.al.seguranca.jpa.Modulo;
import br.leg.rr.al.seguranca.jpa.Sistema;
import br.leg.rr.al.seguranca.jpa.SistemaPermissao;

@Local
public interface ModuloLocal extends SistemaDominioDao<Modulo> {

	/**
	 * Busca todas as permissões por módulo. A pesquisa é realizada na entidade
	 * {@link SistemaPermissao}. As permissões são carregadas em cada módulo.
	 * 
	 * @param sistema
	 * @return lista de módulos com as suas respectivas permissões. <br>
	 *         Módulo sem permissão não faz parte do retorno. <br>
	 *         Retorna <code>nulo</code> se nenhum módulo com permissão for
	 *         encontrado.
	 */
	List<Modulo> buscarPermissoes(@NotNull(message = "Valor do param sistema não pode ser nulo.") Sistema sistema);

}

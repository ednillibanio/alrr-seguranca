package br.leg.rr.al.seguranca.autorizacao.ejb;

import java.util.List;

import javax.ejb.Local;

import br.leg.rr.al.core.dao.DominioIndexadoJPADao;
import br.leg.rr.al.seguranca.autorizacao.jpa.GrupoPerfil;
import br.leg.rr.al.seguranca.autorizacao.jpa.Perfil;

/**
 * @author Ednil Libanio da Costa Junior
 * @date 16-04-2018
 */
@Local
public interface PerfilLocal extends DominioIndexadoJPADao<Perfil> {

	// TODO: FALTA COMENTAR AS CONSTANTES ABAIXO.
	public static final String PESQUISAR_PARAM_GRUPOS_PERFIL = "grupos";
	public static final String PESQUISAR_PARAM_GRUPO_PERFIL = "grupo";

	/**
	 * Busca todos os perfis ativos por grupo.
	 * 
	 * @param grupo Grupo que contém os perfis.
	 * @return retorna uma lista de perfis ativos do grupo informado.
	 */
	public List<Perfil> getAtivos(GrupoPerfil grupo);

	/**
	 * Busca todos os perfis ativos por grupo que não estejam em excluidos.
	 * 
	 * @param grupo     Grupo Perfil que contém os perfis.
	 * @param excluidos Lista com os perfis que não devem fazer parte da pesquisa.
	 * @return Retorna uma lista de perfis ativos do grupo informado e que não fazem
	 *         parte da lista de perfis.
	 */
	List<Perfil> getAtivos(GrupoPerfil grupo, List<Perfil> excluidos);

}

package br.leg.rr.al.seguranca.ejb;

import java.util.List;

import javax.ejb.Local;

import br.leg.rr.al.core.dao.BeanException;
import br.leg.rr.al.core.dao.DominioJPADao;
import br.leg.rr.al.core.jpa.Dominio;
import br.leg.rr.al.seguranca.domain.UsuarioType;
import br.leg.rr.al.seguranca.jpa.GrupoPerfil;
import br.leg.rr.al.seguranca.jpa.Perfil;

/**
 * @author Ednil Libanio da Costa Junior
 * @date 16-04-2018
 */
@Local
public interface PerfilLocal extends DominioJPADao<Perfil> {

	// TODO: FALTA COMENTAR AS CONSTANTES ABAIXO.
	public static final String PESQUISAR_PARAM_GRUPOS_PERFIL = "grupos";
	public static final String PESQUISAR_PARAM_GRUPO_PERFIL = "grupo";
	public static final String PESQUISAR_PARAM_TIPOS_USUARIO = "tipos-usuario";

	/**
	 * Busca todos os perfis ativos por grupo.
	 * 
	 * @param grupo Grupo que contém os perfis.
	 * @return retorna uma lista de perfis ativos do grupo informado.
	 */
	public List<Perfil> getAtivos(GrupoPerfil grupo);

	public List<Perfil> getAtivos(UsuarioType grupoUsuarios);

	/**
	 * Busca todos os perfis ativos por grupo, que não estejam em perfis.
	 * 
	 * @param grupo  Grupo que contém os perfis.
	 * @param perfis Lista com os perfis que não devem fazer parte da pesquisa.
	 * @return Retorna uma lista de perfis ativos do grupo informado e que não fazem
	 *         parte da lista de perfis.
	 */
	List<Perfil> getAtivos(GrupoPerfil grupo, List<Perfil> perfis);

	/**
	 * Busca todos os registros que a situação seja <code>ativa</code>, pelo nome
	 * informado e grupo de usuários informados . Método util para carregar
	 * componentes de combo. A cada caractere que o usuário informar/digitar, o
	 * sistema fará uma busca na lista. <br>
	 * Dominio.situacao= StatusType.ATIVO;
	 * 
	 * @see UsuarioType
	 * @param nome campo que contém o valor a ser pesquisado no atributo
	 *             <code>nome</nome>.
	 * @return  Lista de entidades do tipo <code>{@link Dominio} </code> ativos e
	 *         que satisfaça a condição do @param nome. Caso nenhum valor seja
	 *         encontra, retorna <code>null</code>;
	 */
	List<Perfil> getAtivosPorNome(UsuarioType grupoUsuarios, String nome);

	/**
	 * Altera a situação do perfil. Caso perfil esteja ativo, passará para inativo.
	 * Caso contrário, passará para ativo. Neste caso, todos os usuários que possuem
	 * esse perfil, não terão mais acesso.
	 * 
	 * @param entity Perfil que sofrerá alteração na situação.
	 * @throws ControllerException
	 */
	void trocarSituacao(Perfil entity) throws BeanException;
}

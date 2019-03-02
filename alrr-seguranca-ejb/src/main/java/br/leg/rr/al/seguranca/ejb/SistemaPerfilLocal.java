package br.leg.rr.al.seguranca.ejb;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.validation.constraints.NotNull;

import br.leg.rr.al.core.dao.BeanException;
import br.leg.rr.al.seguranca.core.dao.SistemaJPADao;
import br.leg.rr.al.seguranca.domain.UsuarioType;
import br.leg.rr.al.seguranca.jpa.GrupoPerfil;
import br.leg.rr.al.seguranca.jpa.Perfil;
import br.leg.rr.al.seguranca.jpa.Sistema;
import br.leg.rr.al.seguranca.jpa.SistemaPerfil;

@Local
public interface SistemaPerfilLocal extends SistemaJPADao<SistemaPerfil, Integer> {

	// TODO: COMENTAR TODAS AS CONSTANTES.

	/**
	 * Busca os sistema-perfis pelo sistema selecionado.<br>
	 * 
	 * @value {@literal Sistema};
	 * 
	 */
	public static final String PESQUISAR_PARAM_SISTEMA = "sistema";

	public static final String PESQUISAR_PARAM_GRUPO_PERFIL = "grupo-perfil";

	public static final String PESQUISAR_PARAM_GRUPOS_PERFIS = "grupos-perfis";

	/**
	 * Busca pelos tipos de usuário.<br>
	 * 
	 * @value {@literal List<UsuarioType>};
	 * 
	 */
	public static final String PESQUISAR_PARAM_TIPOS_USUARIO = "tipos-usuario";

	/**
	 * Busca pelos perfis.<br>
	 * 
	 * @value {@literal List<Perfil>};
	 * 
	 */
	public static final String PESQUISAR_PARAM_PERFIS = "perfis";

	/**
	 * Lista de perfis que não devem ser usados na busca.<br>
	 * 
	 * @value {@literal List<Perfil>};
	 * 
	 */
	public static final String PESQUISAR_PARAM_PERFIS_EXCLUIDOS = "perfis-excluidos";

	/**
	 * Busca pelo atributo nome da entidade {@code Perfil}.<br>
	 * 
	 * @value {@literal String};
	 */
	public static final String PESQUISAR_PARAM_NOME = "nome";

	/**
	 * Indica se deve carregar a entidade {@code GrupoPerfil} associada a
	 * {@code Perfil}. <br>
	 * 
	 * @value {@literal Boolean}. Se {@code true}, carrega grupo do perfil;
	 * 
	 */
	public static final String PESQUISAR_PARAM_FETCH_GRUPO_PERFIL = "fetchGrupo";

	/**
	 * Altera a situação do perfil. Caso perfil esteja ativo, passará para inativo.
	 * Caso contrário, passará para ativo. Neste caso, todos os usuários que possuem
	 * esse perfil, não terão mais acesso.
	 * 
	 * @param entity
	 *            Perfil que sofrerá alteração na situação.
	 * @throws ControllerException
	 */
	void trocarSituacao(SistemaPerfil entity) throws BeanException;

	SistemaPerfil buscar(@NotNull(message = "Valor do param sistema nâo pode ser nulo.") Sistema sistema, Perfil perfil,
			Boolean fetchPerfil);

	SistemaPerfil buscar(@NotNull(message = "Valor do param entidade nâo pode ser nulo.") SistemaPerfil entidade,
			@NotNull(message = "Valor do param fetchSistema não pode ser nulo.") Boolean fetchSistema,
			@NotNull(message = "Valor do param fetchPerfil não pode ser nulo.") Boolean fetchPerfil,
			@NotNull(message = "Valor do param fetchPermissoes não pode ser nulo.") Boolean fetchPermissoes);

	/**
	 * Busca todos os grupos que contém algum perfil associado a entidade
	 * {@link SistemaPerfil}. Somente os grupos que possuem pelo menos um perfil
	 * associado ao sistema informado serão buscados.
	 * 
	 * @param sistema
	 *            parametro que informa qual o sistema que será buscado os perfis
	 *            associados ao SistemaPerfil.
	 * 
	 * @return lista de grupos que possuem pelo menos um perfil associao ao sistema
	 *         informado. Nulo caso nenhum seja encontrado.
	 */
	List<GrupoPerfil> buscarGrupos(@NotNull(message = "Valor do param sistema nâo pode ser nulo.") Sistema sistema);

	/**
	 * Busca todos os grupos que contém algum perfil associado a entidade
	 * {@link SistemaPerfil}. Somente os grupos que possuem pelo menos um perfil
	 * associado ao sistema informado serão buscados. <br>
	 * Método util para filtrar por grupos já selecionados. Por exemplo, retornar
	 * uma lista de grupos que ainda não foram selecionados, deve-se informar o
	 * parametro {@code notIn}
	 * 
	 * @param sistema
	 *            parametro que informa qual o sistema que será buscado os perfis
	 *            associados ao SistemaPerfil.
	 * @param gruposExcluidos
	 *            lista de grupos que não devem fazer parte da pesquisa. Parametro
	 *            util quando está selecionando grupos
	 * @return lista de grupos que não fazem parte da lista {@code notIn}, e que
	 *         possuem pelo menos um perfil associao ao sistema informado. Nulo caso
	 *         nenhum seja encontrado.
	 */
	List<GrupoPerfil> buscarGruposPerfis(
			@NotNull(message = "Valor do param sistema nâo pode ser nulo.") Sistema sistema,
			List<GrupoPerfil> gruposExcluidos);

	List<GrupoPerfil> buscarGruposPerfis(Map<String, Object> params);

	List<GrupoPerfil> buscarGruposPerfisPorNome(
			@NotNull(message = "Valor do param sistema nâo pode ser nulo.") Sistema sistema, String query,
			List<GrupoPerfil> gruposExcluidos);

	List<Perfil> buscarPerfis(@NotNull(message = "Valor do param sistema nâo pode ser nulo.") Sistema sistema);

	List<Perfil> buscarPerfis(@NotNull(message = "Valor do param sistema nâo pode ser nulo.") Sistema sistema,
			List<Perfil> perfisExcluidos);

	List<Perfil> buscarPerfis(Map<String, Object> params);

	List<Perfil> buscarPerfisPorNome(@NotNull(message = "Valor do param sistema nâo pode ser nulo.") Sistema sistema,
			String query, List<Perfil> perfisExcluidos);

	List<Perfil> buscarPerfisPorNome(@NotNull(message = "Valor do param sistema nâo pode ser nulo.") Sistema sistema,
			String query, List<Perfil> perfisExcluidos, Boolean fetchGrupoPerfil);

	List<Perfil> buscarPerfisPorGrupo(
			@NotNull(message = "Valor do param sistema nâo pode ser nulo.") Sistema sistemaSelecionado, String query,
			GrupoPerfil grupoSelecionado);

	List<SistemaPerfil> buscarTodos(Sistema sistema);

	List<SistemaPerfil> buscarTodos(@NotNull(message = "Valor do param sistema nâo pode ser nulo.") Sistema sistema,
			Boolean carregarEager);

	/**
	 * 
	 * @param sistema
	 *            somente os registros do sistema informado serão pesquisados. Caso
	 *            queira que busque de todos os sistemas, deve informar
	 *            <code>null</code>.
	 * @param grupoUsuario
	 * @param carregarEager
	 * @return
	 */
	List<SistemaPerfil> buscarTodos(Sistema sistema, UsuarioType grupoUsuario, Boolean carregarEager);

	List<SistemaPerfil> getAtivos(@NotNull(message = "Valor do param sistema não pode ser nulo.") Sistema sistema,
			@NotNull(message = "Valor do param grupoUsuario não pode ser nulo.") UsuarioType grupoUsuario,
			@NotNull(message = "Valor do param fetchSistema não pode ser nulo.") Boolean fetchSistema,
			@NotNull(message = "Valor do param fetchPerfil não pode ser nulo.") Boolean fetchPerfil,
			@NotNull(message = "Valor do param fetchGrupo não pode ser nulo.") Boolean fetchGrupo);

	/**
	 * Busca todos os perfis que possuem associação ao sistema e ao tipo de usuário
	 * informados como argumentos.
	 * 
	 * @param sistema
	 *            informa qual o sistema que os perfis estão associados.
	 * @param tipoDeUsuario
	 *            informa qual o grupo do tipo de usuário o perfil pertence.
	 * @return lista de perfis que possuem associação com o sistema e o tipo de
	 *         usuário informados. Retorna null se nenhum for encontrado.
	 */
	List<Perfil> buscarPerfis(@NotNull(message = "Valor do param sistema não pode ser nulo.") Sistema sistema,
			UsuarioType tipoDeUsuario);

}

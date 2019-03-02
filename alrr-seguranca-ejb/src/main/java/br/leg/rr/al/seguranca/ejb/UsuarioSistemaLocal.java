package br.leg.rr.al.seguranca.ejb;

import java.util.List;

import javax.ejb.Local;
import javax.validation.constraints.NotNull;

import br.leg.rr.al.core.dao.BeanException;
import br.leg.rr.al.seguranca.core.dao.SistemaJPADao;
import br.leg.rr.al.seguranca.jpa.Sistema;
import br.leg.rr.al.seguranca.jpa.SistemaPermissao;
import br.leg.rr.al.seguranca.jpa.Usuario;
import br.leg.rr.al.seguranca.jpa.UsuarioSistema;

@Local
public interface UsuarioSistemaLocal extends SistemaJPADao<UsuarioSistema, Integer> {

	public static final String PESQUISAR_PARAM_SISTEMA = "sistema";
	public static final String PESQUISAR_PARAM_PERFIS = "usuario-perfis";
	public static final String PESQUISAR_PARAM_SITUACAO = "situacao";

	/**
	 * Busca todos os registros {@code UsuarioSistema} de um <code>Usuario</code>.
	 * 
	 * @param usuario
	 *            entidade que possui os registros de <code>UsuarioSistema</code>
	 * @param fetch
	 *            indica que deve carregar os valores das entidades
	 *            {@code Usuario e Sistema}. <code>true</code> carrega.
	 * @return lista de <code>UsuarioSistema</code>.
	 */
	List<UsuarioSistema> buscar(Usuario usuario,
			@NotNull(message = "Valor do param fetch não pode ser nulo.") Boolean fetch);

	/**
	 * 
	 * @param usuario
	 * @param fetchUsuario
	 * @param fetchSistema
	 * @param fetchPerfis
	 * @param fetchPermissoes
	 * @return
	 */
	List<UsuarioSistema> buscar(@NotNull(message = "Valor do param usuario não pode ser nulo.") Usuario usuario,
			@NotNull(message = "Valor do param fetchUsuario não pode ser nulo.") Boolean fetchUsuario,
			@NotNull(message = "Valor do param fetchSistema não pode ser nulo.") Boolean fetchSistema,
			@NotNull(message = "Valor do param fetchPerfis não pode ser nulo.") Boolean fetchPerfis,
			@NotNull(message = "Valor do param fetchPermissoes não pode ser nulo.") Boolean fetchPermissoes);

	/**
	 * Busca a entidade <code>UsuarioSistema</code> de acordo com os argumentos
	 * informados na base de dados.
	 * 
	 * @param usuario
	 * @param sistema
	 * @return Retorna a entidade encontrada ou nulo, caso contrário. As entidades
	 *         dependentes, por exemplo, perfis e sistema, não são carregadas. Para
	 *         carrega-las deve usar o método
	 *         {@link #buscar(Usuario, Sistema, Boolean, Boolean, Boolean, Boolean)};
	 */
	UsuarioSistema buscar(Usuario usuario, Sistema sistema);

	/**
	 * <p>
	 * Busca a entidade <code>UsuarioSistema</code> de acordo com os argumentos
	 * informados.
	 * </p>
	 * 
	 * @param usuario
	 * @param sistema
	 * @param fetchUsuario
	 *            Se <code>true</code>, carrega as informações da entidade
	 *            <code>Usuario</code>.
	 * @param fetchSistema
	 *            Se <code>true</code>, carrega as informações da entidade
	 *            <code>Sistema</code>.
	 * @param fetchPerfis
	 *            Se <code>true</code>, carrega todos os perfis vinculados ao
	 *            <code>UsuarioSistema</code>.
	 * @param fetchPermissoes
	 *            Se <code>true</code>, carrega todas as permissões vinculadas ao
	 *            <code>Perfil</code>. Para buscar as permissões, o param
	 *            fetchPerfis deve ser true. Caso contrário, não irá buscar as
	 *            permissões.
	 * @return Retorna a entidade UsuarioSistema de acordo com os parametros
	 *         informados: Usuario e Sistema. Retorna nulo, caso não encontre.
	 */
	UsuarioSistema buscar(@NotNull(message = "Valor do param usuario não pode ser nulo.") Usuario usuario,
			Sistema sistema, @NotNull(message = "Valor do param fetchUsuario não pode ser nulo.") Boolean fetchUsuario,
			@NotNull(message = "Valor do param fetchSistema não pode ser nulo.") Boolean fetchSistema,
			@NotNull(message = "Valor do param fetchPerfis não pode ser nulo.") Boolean fetchPerfis,
			@NotNull(message = "Valor do param fetchPermissoes não pode ser nulo.") Boolean fetchPermissoes);

	/**
	 * 
	 * @param entidade
	 * @param fetchUsuario
	 * @param fetchSistema
	 * @param fetchPerfis
	 * @param fetchPermissoes
	 * @return
	 */
	UsuarioSistema buscar(UsuarioSistema entidade,
			@NotNull(message = "Valor do param fetchUsuario não pode ser nulo.") Boolean fetchUsuario,
			@NotNull(message = "Valor do param fetchSistema não pode ser nulo.") Boolean fetchSistema,
			@NotNull(message = "Valor do param fetchPerfis não pode ser nulo.") Boolean fetchPerfis,
			@NotNull(message = "Valor do param fetchPermissoes não pode ser nulo.") Boolean fetchPermissoes);

	/**
	 * Altera a situação do {@code UsuarioSistema}. Caso esteja ativo, passará para
	 * inativo. Caso contrário, passará para ativo.
	 * 
	 * @param entity
	 *            {@code UsuarioSistema} que sofrerá alteração na situação.
	 * @throws ControllerException
	 */
	void trocarSituacao(@NotNull(message = "Valor do param usuarioSistema não pode ser nulo.") UsuarioSistema entity)
			throws BeanException;

	/**
	 * <p>
	 * Busca todas as permissões que estão associadas ao
	 * <code>UsuarioSistema</code>. Se as entidades sistema e perfis não estiverem
	 * carregadas ainda, o método irá carregá-las para realizar a operação.
	 * </p>
	 * 
	 * @param entidade
	 *            Entidade a ser pesquisada na base de dados.
	 * @return retorna as permissões do param entidade, ou nulo caso não encontre
	 *         pelo menos uma permissão associada ao argumento
	 *         <code>entidade</code>.
	 */
	List<SistemaPermissao> buscarPermissoes(
			@NotNull(message = "Valor do param usuarioSistema não pode ser nulo.") UsuarioSistema entidade);

	/**
	 * <p>
	 * Busca todas as permissões que estão associadas ao
	 * <code>UsuarioSistema</code>. Se as entidades sistema e perfis não estiverem
	 * carregadas ainda, o método irá carregá-las para realizar a operação.
	 * </p>
	 * 
	 * @param sistema
	 *            Entidade a ser usada para encontrar o UsuarioSistema e suas
	 *            permissões.
	 * @param usuario
	 *            Entidade a ser usada para encontrar o UsuarioSistema e suas
	 *            permissões.
	 * @return retorna as permissões do <code>UsuarioSistema</code>, ou nulo caso
	 *         não encontre pelo menos uma permissão associada ao sistema e o
	 *         usuário informados.
	 */
	List<SistemaPermissao> buscarPermissoes(
			@NotNull(message = "Valor do param sistema não pode ser nulo.") Sistema sistema,
			@NotNull(message = "Valor do param usuario não pode ser nulo.") Usuario usuario);

}

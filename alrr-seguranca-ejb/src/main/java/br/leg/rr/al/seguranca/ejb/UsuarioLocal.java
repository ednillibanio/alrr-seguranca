package br.leg.rr.al.seguranca.ejb;

import java.util.List;

import javax.ejb.Local;
import javax.validation.constraints.NotNull;

import br.leg.rr.al.core.dao.BeanException;
import br.leg.rr.al.core.dao.JPADaoStatus;
import br.leg.rr.al.core.domain.StatusType;
import br.leg.rr.al.pessoa.jpa.PessoaFisica;
import br.leg.rr.al.seguranca.domain.UsuarioType;
import br.leg.rr.al.seguranca.jpa.Usuario;
import br.leg.rr.al.servidor.jpa.ServidorPublico;

@Local
public interface UsuarioLocal extends JPADaoStatus<Usuario, Integer> {

	/**
	 * Indica se deve carregar a entidade {@code PessoaFisica} associada ao
	 * {@code Usuario}. <br>
	 * 
	 * @value {@literal Boolean}. Se {@code true}, carrega sistemas;
	 * 
	 */
	public static final String PESQUISAR_PARAM_FETCH_PESSOA = "fetch-pessoa";

	
	/**
	 * Indica se deve carregar todos os sistemas que o usuário faz parte.<br>
	 * 
	 * @value {@literal Boolean}. Se {@code true}, carrega sistemas;
	 * 
	 */
	public static final String PESQUISAR_PARAM_FETCH_SISTEMAS = "fetch-sistemas";

	/**
	 * Busca os usuários pelos sistemas selecionados.<br>
	 * 
	 * @value {@literal List<Sistema>};
	 * 
	 */
	public static final String PESQUISAR_PARAM_SISTEMAS = "sistemas";

	/**
	 * Busca pelo atributo nome da entidade {@code PessoaFisica}.<br>
	 * 
	 * @value {@literal String};
	 */
	public static final String PESQUISAR_PARAM_NOME = "nome";

	/**
	 * Busca pelo atributo login.<br>
	 * 
	 * @value {@literal String};
	 * 
	 */
	public static final String PESQUISAR_PARAM_LOGIN = "login";

	/**
	 * Busca pelos tipos do usuário selecionado.<br>
	 * 
	 * @value {@literal List<UsuarioType>}
	 * 
	 * @see UsuarioType
	 */
	public static final String PESQUISAR_PARAM_TIPOS_USUARIO = "tipos-usuario";

	/**
	 * Altera a situação do usuário. Caso usuário esteja ativo, passará para
	 * bloquedo. Caso contrário, passará para ativo.
	 * 
	 * @param entity Usuário que sofrerá alteração na situação.
	 * @throws ControllerException
	 */
	void trocarSituacao(Usuario entity) throws BeanException;

	List<Usuario> pesquisar(Usuario usuario) throws BeanException;

	List<Usuario> pesquisar(Usuario usuario, List<StatusType> situacoes) throws BeanException;

	/**
	 * Define a data de cadastro do usuário. É utilizado no método save(), caso o
	 * usuário seja novo.
	 * 
	 * @param entity entidade que deseja que a data de cadastro seja definida.
	 */
	void setDataCadastro(Usuario entity) throws BeanException;

	/**
	 * Grava a data e hora do ultimo acesso do usuário.
	 * 
	 * @param entity usuário que será salvo a data e hora do acesso.
	 * @throws ControllerException
	 */
	void saveDataUltimoAcesso(Usuario entity) throws BeanException;

	/**
	 * Altera a senha do usuário. Neste momento a senha é cifrada.
	 * 
	 * @param entity
	 * @param novaSenha nova senha que será salva e cifrada.
	 * @throws ControllerException
	 */
	void trocarSenha(Usuario entity, String novaSenha) throws BeanException;

	/**
	 * 
	 * @param perfil
	 * @param entity
	 * @throws ControllerException
	 */
	/*
	 * void removePerfil(SistemaPerfil perfil, Usuario entity) throws
	 * ControllerException;
	 * 
	 *//**
		 * 
		 * @param perfil
		 * @param entity
		 * @throws ControllerException
		 *//*
			 * void addPerfil(SistemaPerfil perfil, Usuario entity) throws
			 * ControllerException;
			 */

	/**
	 * Cifra a senha do usuário.
	 * 
	 * @param login login do usuário.
	 * @param senha senha do usuário não cifrada.
	 * @return retorna senha cifrada.
	 */
	String cifrarSenha(@NotNull(message = "Valor do param login não pode ser nulo.") String login,
			@NotNull(message = "Valor do param senha não pode ser nulo.") String senha) throws BeanException;

	/**
	 * Busca o usuário na base de dados local de acordo com o login informado. O
	 * login do usuário pode ser tanto o atributo {@code Usuario#getLogin()} quanto
	 * o {@code Usuario#getLoginLDAP()}. A consulta será realizada pelos dois
	 * atributos.
	 * 
	 * @param login deve ser todo em <code>lowercase</code>.
	 * @return retorna o usuário ou <code>null</code> caso não encontre o usuário
	 *         conforme login informado.
	 */
	Usuario buscarPorLogin(@NotNull(message = "Valor do param login não pode ser nulo.") String login)
			throws BeanException;

	Usuario buscar(PessoaFisica pessoa) throws BeanException;

	/**
	 * 
	 * @param id
	 * @param fetchSistemas
	 * @param fetchPessoa
	 * @return
	 */
	Usuario buscar(Integer id, Boolean fetchSistemas, Boolean fetchPessoa) throws BeanException;

	/**
	 * @param servidor
	 * @return
	 * @throws BeanException
	 */
	Usuario buscar(ServidorPublico servidor) throws BeanException;;

}

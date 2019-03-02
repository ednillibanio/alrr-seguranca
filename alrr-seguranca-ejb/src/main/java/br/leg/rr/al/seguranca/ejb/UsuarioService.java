package br.leg.rr.al.seguranca.ejb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import br.leg.rr.al.core.dao.BaseJPADaoStatus;
import br.leg.rr.al.core.dao.BeanException;
import br.leg.rr.al.core.domain.StatusType;
import br.leg.rr.al.core.jpa.BaseEntity_;
import br.leg.rr.al.core.utils.DataUtils;
import br.leg.rr.al.pessoa.jpa.PessoaFisica;
import br.leg.rr.al.pessoa.jpa.PessoaFisica_;
import br.leg.rr.al.seguranca.domain.UsuarioType;
import br.leg.rr.al.seguranca.jpa.Sistema;
import br.leg.rr.al.seguranca.jpa.Usuario;
import br.leg.rr.al.seguranca.jpa.UsuarioTipo;
import br.leg.rr.al.seguranca.jpa.UsuarioTipo_;
import br.leg.rr.al.seguranca.jpa.Usuario_;
import br.leg.rr.al.servidor.jpa.ServidorPublico;

@Named
@Stateless
// TODO: refatorar a classe toda que está desatualizada.
public class UsuarioService extends BaseJPADaoStatus<Usuario, Integer> implements UsuarioLocal {

	private static final long serialVersionUID = -200754164133624560L;

	@Override
	// TODO: Modificar para que aceite outros valores em vez de automático.
	// Deixar mais opcoes, nao deixar rigido.
	public void trocarSituacao(Usuario entity) throws BeanException {

		renovar(entity);
		if (entity.getSituacao() == StatusType.ATIVO) {
			entity.setSituacao(StatusType.INATIVO);
		} else {
			entity.setSituacao(StatusType.ATIVO);
		}
		salvar(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.leg.al.rr.core.dao.BaseJPADao#pesquisar(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Usuario> pesquisar(Map<String, Object> params) {

		String nome = null;
		List<UsuarioType> tiposUsuario = null;
		List<Sistema> sistemas = null;
		StatusType situacao = null;
		String login = null;
		Boolean fetchPessoa = null;
		Boolean fetchSistema = null;
		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<Usuario> cq = createCriteriaQuery();
		Root<Usuario> root = cq.from(Usuario.class);
		List<Predicate> predicates = new ArrayList<Predicate>();
		Join<Usuario, UsuarioTipo> tipoJoin = root.join(Usuario_.tipoUsuario, JoinType.LEFT);
		Join<UsuarioTipo, PessoaFisica> pfJoin = tipoJoin.join(UsuarioTipo_.pessoa, JoinType.LEFT);
		// Join<Usuario, PessoaFisica> pfJoin = root.join(Usuario_.pessoa,
		// JoinType.LEFT);
		Join<Usuario, Sistema> sistemaJoin = root.join(Usuario_.sistemas, JoinType.LEFT);

		cq.select(root).distinct(true);

		if (params.size() > 0) {

			if (params.containsKey(PESQUISAR_PARAM_FETCH_PESSOA)) {
				fetchPessoa = (Boolean) params.get(PESQUISAR_PARAM_FETCH_PESSOA);
				if (fetchPessoa) {
					tipoJoin.fetch(UsuarioTipo_.pessoa, JoinType.LEFT);
					// pfJoin.fetch(Usuario_.pessoa, JoinType.LEFT);
				}
			}
			if (params.containsKey(PESQUISAR_PARAM_FETCH_SISTEMAS)) {
				fetchSistema = (Boolean) params.get(PESQUISAR_PARAM_FETCH_SISTEMAS);
				if (fetchSistema) {
					root.fetch(Usuario_.sistemas, JoinType.LEFT);
				}
			}

			if (params.containsKey(UsuarioLocal.PESQUISAR_PARAM_NOME)) {
				nome = (String) params.get(UsuarioLocal.PESQUISAR_PARAM_NOME);
				if (StringUtils.isNotBlank(nome)) {

					Predicate cond = cb.like(cb.lower(pfJoin.get(PessoaFisica_.nome)), "%" + nome.toLowerCase() + "%");
					predicates.add(cond);
				}
			}

			if (params.containsKey(PESQUISAR_PARAM_LOGIN)) {
				login = (String) params.get(PESQUISAR_PARAM_LOGIN);
				if (StringUtils.isNotBlank(login)) {
					Predicate cond = cb.like(cb.lower(root.get(Usuario_.login)), "%" + login.toLowerCase() + "%");
					predicates.add(cond);
				}
			}

			if (params.containsKey(PESQUISAR_PARAM_SITUACAO)) {
				situacao = (StatusType) params.get(PESQUISAR_PARAM_SITUACAO);
				if (situacao != null) {
					Predicate cond = cb.equal(root.get(Usuario_.situacao), situacao);
					predicates.add(cond);
				}
			}

			if (params.containsKey(PESQUISAR_PARAM_SISTEMAS)) {
				sistemas = (List<Sistema>) params.get(PESQUISAR_PARAM_SISTEMAS);
				// FIXME: falta testar
				if (sistemas != null && sistemas.size() > 0) {
					List<Integer> ids = new ArrayList<Integer>();
					for (Sistema sistema : sistemas) {
						ids.add(sistema.getId());
					}

					Expression<Serializable> exp = sistemaJoin.get(BaseEntity_.id);
					Predicate cond = exp.in(ids);
					predicates.add(cond);

				}
			}

			if (params.containsKey(PESQUISAR_PARAM_TIPOS_USUARIO)) {
				tiposUsuario = (List<UsuarioType>) params.get(PESQUISAR_PARAM_TIPOS_USUARIO);
				if (tiposUsuario != null && tiposUsuario.size() > 0) {
					Expression<UsuarioType> exp = null; // root.get(Usuario_.tipoUsuario);
					Predicate cond = exp.in(Arrays.asList(tiposUsuario));
					predicates.add(cond);
				}
			}

			cq.where(cb.and(predicates.toArray(new Predicate[] {})));
		}
		return getResultList(cq);
	}

	@Override
	public List<Usuario> pesquisar(Usuario usuario) {
		return pesquisar(usuario, null);

	}

	@Override
	// TODO: refazer o pesquisar.
	public List<Usuario> pesquisar(Usuario usuario, List<StatusType> situacoes) {

		final CriteriaBuilder builder = getCriteriaBuilder();
		CriteriaQuery<Usuario> cq = builder.createQuery(Usuario.class);
		final Root<Usuario> usuarioRoot = cq.from(Usuario.class);
		cq.select(usuarioRoot);

		List<Predicate> predicateList = new ArrayList<Predicate>();

		Predicate login, predSituacao;

		if (!StringUtils.isBlank(usuario.getLogin())) {

			login = builder.like(builder.upper(usuarioRoot.get(Usuario_.login)),
					"%" + usuario.getLogin().toUpperCase() + "%");
			predicateList.add(login);
		}
		if (situacoes != null && (situacoes.size() > 0)) {

			predSituacao = usuarioRoot.get(Usuario_.situacao).in(situacoes.toArray());
			predicateList.add(predSituacao);
		}

		if (predicateList.size() > 0) {
			cq.where(predicateList.toArray(new Predicate[predicateList.size()]));
		}
		return getEntityManager().createQuery(cq).getResultList();

	}

	@Override
	/**
	 * Salva o usuário e cifra a senha caso seja um novo usuário.
	 */
	public void salvar(Usuario entity) throws BeanException {

		if (entity.getId() == null || entity.getId() == 0) {
			// entity.setSenha(PasswordHash.hashPassword(entity.getLogin(),
			// entity.getSenha()));
			setDataCadastro(entity);
		}
		super.salvar(entity);
	}

	@Override
	public void saveDataUltimoAcesso(Usuario entity) throws BeanException {
		if (getEntityManager().contains(entity)) {
			renovar(entity);
		} else {
			entity = buscar(entity);
		}

		entity.setDataUltimoAcesso(DataUtils.hoje());
		salvar(entity);

	}

	@Override
	public void trocarSenha(Usuario entity, String senha) throws BeanException {
		String pwd = cifrarSenha(entity.getLogin(), senha);

		if (getEntityManager().contains(entity)) {
			renovar(entity);
		} else {
			entity = buscar(entity);
		}
		entity.setSenha(pwd);
		salvar(entity);
	}

	@Override
	public String cifrarSenha(String login, String senha) {
		return null; // PasswordHash.hashPassword(login, senha);
	}

	/*
	 * @Override public void removePerfil(SistemaPerfil perfil, Usuario entity)
	 * throws ControllerException { renovar(entity);
	 * 
	 * if (entity.getPerfis().contains(perfil)) { entity.getPerfis().remove(perfil);
	 * salvar(entity); } else { throw new
	 * ControllerException("Não foi possível adicionar. Usuário não possui este perfil."
	 * ); } }
	 * 
	 * @Override public void addPerfil(SistemaPerfil perfil, Usuario entity) throws
	 * ControllerException { if (!entity.getPerfis().contains(perfil)) {
	 * entity.getPerfis().add(perfil); salvar(entity); } else { throw new
	 * ControllerException("Não foi possível adicionar. Usuário já possui este perfil."
	 * ); }
	 * 
	 * }
	 */

	@Override
	public Usuario buscar(Integer id, Boolean fetchSistemas, Boolean fetchPessoa) throws BeanException {
		Usuario usuario = getEntityManager().find(entityClass, id);
		if (fetchSistemas) {
			usuario.getSistemas().size();
		}
		if (fetchPessoa) {
			usuario.getTipoUsuario().getPessoa();
		}

		return usuario;
	}

	@Override
	public Usuario buscarPorLogin(String login) throws BeanException {
		final CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<Usuario> cq = cb.createQuery(Usuario.class);
		final Root<Usuario> root = cq.from(Usuario.class);
		cq.select(root);
		Predicate cond = cb.or(cb.equal(cb.lower(root.get(Usuario_.login)), login.toLowerCase()),
				cb.equal(cb.lower(root.get(Usuario_.loginLDAP)), login.toLowerCase()));
		cq.where(cond);

		return getSingleResult(cq);

	}

	@Override
	public Boolean jaExiste(Usuario entidade) {
		return false;
	}

	@Override
	public void setDataCadastro(Usuario entity) throws BeanException {
		// TODO Auto-generated method stub

	}

	@Override
	public Usuario buscar(PessoaFisica pessoa) throws BeanException {
		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<Usuario> cq = createCriteriaQuery();
		Root<Usuario> root = cq.from(Usuario.class);
		cq.select(root);

		Predicate cond = null; // cb.equal(root.get(Usuario_.pessoa), pessoa);
		cq.where(cond);
		return getSingleResult(cq);
	}

	@Override
	public Usuario buscar(ServidorPublico servidor) throws BeanException {
		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<Usuario> cq = createCriteriaQuery();
		Root<Usuario> root = cq.from(Usuario.class);
		cq.select(root);

		Predicate cond = null; // cb.equal(root.get(Usuario_.servidorPublico), servidor);
		cq.where(cond);
		return getSingleResult(cq);
	}

}

package br.leg.rr.al.seguranca.ejb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import br.leg.rr.al.core.dao.BeanException;
import br.leg.rr.al.core.domain.StatusType;
import br.leg.rr.al.core.jpa.BaseEntity_;
import br.leg.rr.al.core.jpa.Dominio_;
import br.leg.rr.al.seguranca.core.dao.SistemaBaseJPADao;
import br.leg.rr.al.seguranca.domain.UsuarioType;
import br.leg.rr.al.seguranca.jpa.GrupoPerfil;
import br.leg.rr.al.seguranca.jpa.Objeto;
import br.leg.rr.al.seguranca.jpa.Objeto_;
import br.leg.rr.al.seguranca.jpa.Perfil;
import br.leg.rr.al.seguranca.jpa.Perfil_;
import br.leg.rr.al.seguranca.jpa.Sistema;
import br.leg.rr.al.seguranca.jpa.SistemaPerfil;
import br.leg.rr.al.seguranca.jpa.SistemaPerfil_;
import br.leg.rr.al.seguranca.jpa.SistemaPermissao;
import br.leg.rr.al.seguranca.jpa.SistemaPermissao_;

@Stateless
@Named
public class SistemaPerfilService extends SistemaBaseJPADao<SistemaPerfil, Integer> implements SistemaPerfilLocal {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7641470890274793076L;

	/*
	 * @Override public SistemaPerfil buscar(Sistema sistema, Perfil perfil) {
	 * return buscar(sistema, perfil, false); }
	 */

	@SuppressWarnings("unchecked")
	@Override
	public SistemaPerfil buscar(SistemaPerfil entidade, Boolean fetchSistema, Boolean fetchPerfil,
			Boolean fetchPermissoes) {

		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<SistemaPerfil> cq = createCriteriaQuery();
		Root<SistemaPerfil> root = cq.from(SistemaPerfil.class);
		cq.select(root).distinct(true);

		if (fetchSistema) {
			root.fetch(SistemaPerfil_.sistema);
		}

		if (fetchPerfil) {
			root.fetch(SistemaPerfil_.perfil);
		}

		if (fetchPermissoes) {

			Join<SistemaPerfil, SistemaPermissao> permissaoJoin = (Join<SistemaPerfil, SistemaPermissao>) root
					.fetch(SistemaPerfil_.permissoes, JoinType.LEFT);
			Join<SistemaPermissao, Objeto> objJoin = (Join<SistemaPermissao, Objeto>) permissaoJoin
					.fetch(SistemaPermissao_.objeto, JoinType.LEFT);
			objJoin.fetch(Objeto_.modulo, JoinType.LEFT);
			permissaoJoin.fetch(SistemaPermissao_.operacao, JoinType.LEFT);

		}

		Predicate cond = cb.equal(root.get(BaseEntity_.id), entidade.getId());

		cq.where(cond);
		TypedQuery<SistemaPerfil> q = getEntityManager().createQuery(cq);

		return q.getSingleResult();

	}

	/*
	 * @Override public SistemaPerfil buscar(SistemaPerfil entidade, Boolean
	 * carregarPermissoes) { return buscar(entidade.getSistema(),
	 * entidade.getPerfil(), carregarPermissoes);
	 * 
	 * }
	 */

	@Override
	public void trocarSituacao(SistemaPerfil entitade) throws BeanException {

		renovar(entitade);
		if (entitade.getSituacao() == StatusType.ATIVO) {
			entitade.setSituacao(StatusType.INATIVO);
		} else {
			entitade.setSituacao(StatusType.ATIVO);
		}
		salvar(entitade);
	}

	@Override
	public Boolean jaExiste(SistemaPerfil entidade) {

		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<SistemaPerfil> cq = createCriteriaQuery();
		Root<SistemaPerfil> root = cq.from(SistemaPerfil.class);
		cq.select(root);
		List<Predicate> predicates = new ArrayList<Predicate>();

		Predicate cond = cb.equal(root.get(SistemaPerfil_.perfil), entidade.getPerfil());
		predicates.add(cond);
		Predicate cond1 = cb.equal(root.get(SistemaPerfil_.sistema), entidade.getSistema());
		predicates.add(cond1);

		if (entidade.getId() != null) {
			Predicate cond2 = cb.notEqual(root.get(BaseEntity_.id), entidade.getId());
			predicates.add(cond2);
		}

		cq.where(cb.and(predicates.toArray(new Predicate[] {})));

		return (!

		getResultList(cq).isEmpty());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SistemaPerfil> pesquisar(Map<String, Object> params) {

		StatusType situacao = null;
		List<Predicate> predicates = new ArrayList<Predicate>();
		List<GrupoPerfil> grupos = null;
		List<UsuarioType> tiposUsuario = null;
		List<Perfil> perfis = null;

		Sistema sistema = null;
		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<SistemaPerfil> cq = getCriteriaBuilder().createQuery(SistemaPerfil.class);

		Root<SistemaPerfil> root = cq.from(SistemaPerfil.class);
		Join<SistemaPerfil, Perfil> perfilJoin = (Join<SistemaPerfil, Perfil>) root.fetch(SistemaPerfil_.perfil);
		perfilJoin.fetch(Perfil_.grupoPerfil);
		cq.select(root);

		if (params.size() > 0) {

			if (params.containsKey(PESQUISAR_PARAM_SISTEMA)) {
				sistema = (Sistema) params.get(PESQUISAR_PARAM_SISTEMA);
				if (sistema != null) {
					Predicate cond = cb.equal(root.get(SistemaPerfil_.sistema), sistema);
					predicates.add(cond);
				}
			}

			if (params.containsKey(PESQUISAR_PARAM_GRUPOS_PERFIS)) {
				grupos = (List<GrupoPerfil>) params.get(PESQUISAR_PARAM_GRUPOS_PERFIS);
				if (grupos != null && grupos.size() > 0) {
					Expression<GrupoPerfil> exp = perfilJoin.get(Perfil_.grupoPerfil);
					Predicate cond = exp.in(grupos);
					predicates.add(cond);
				}
			}

			if (params.containsKey(PESQUISAR_PARAM_TIPOS_USUARIO)) {
				tiposUsuario = (List<UsuarioType>) params.get(PESQUISAR_PARAM_TIPOS_USUARIO);
				if (tiposUsuario != null && tiposUsuario.size() > 0) {
					Expression<UsuarioType> exp = perfilJoin.get(Perfil_.tipoUsuario);
					Predicate cond = exp.in(tiposUsuario);
					predicates.add(cond);
				}
			}

			if (params.containsKey(SistemaPerfilLocal.PESQUISAR_PARAM_PERFIS)) {
				perfis = (List<Perfil>) params.get(SistemaPerfilLocal.PESQUISAR_PARAM_PERFIS);
				if (perfis != null && perfis.size() > 0) {
					Expression<Perfil> exp = root.get(SistemaPerfil_.perfil);
					Predicate cond = exp.in(perfis);
					predicates.add(cond);

				}
			}

			if (params.containsKey(SistemaPerfilLocal.PESQUISAR_PARAM_SITUACAO)) {
				situacao = (StatusType) params.get(SistemaPerfilLocal.PESQUISAR_PARAM_SITUACAO);
				if (situacao != null) {
					Predicate cond = cb.equal(root.get(SistemaPerfil_.situacao), situacao);
					predicates.add(cond);

				}
			}

			cq.where(cb.and(predicates.toArray(new Predicate[] {})));
			return getResultList(cq);
		}

		return null;

	}

	@Override
	public List<GrupoPerfil> buscarGrupos(Sistema sistema) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(SistemaPerfilLocal.PESQUISAR_PARAM_SISTEMA, sistema);

		return buscarGruposPerfis(params);
	}

	@Override
	public List<GrupoPerfil> buscarGruposPerfis(Sistema sistema, List<GrupoPerfil> gruposExcluidos) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(SistemaPerfilLocal.PESQUISAR_PARAM_SISTEMA, sistema);
		params.put("gruposExcluidos", gruposExcluidos);

		return buscarGruposPerfis(params);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<GrupoPerfil> buscarGruposPerfis(Map<String, Object> params) {

		if (params.size() > 0) {

			String nome = null;
			Sistema sistema = null;
			StatusType situacao = null;
			List<GrupoPerfil> gruposExcluidos;
			List<Predicate> predicates = new ArrayList<Predicate>();

			CriteriaBuilder cb = getCriteriaBuilder();
			CriteriaQuery<GrupoPerfil> cq = cb.createQuery(GrupoPerfil.class);
			Root<GrupoPerfil> root = cq.from(GrupoPerfil.class);
			cq.select(root).distinct(true);

			Root<SistemaPerfil> fromSistemaPerfil = cq.from(SistemaPerfil.class);
			Join<SistemaPerfil, Perfil> sistemaPerfilJoin = fromSistemaPerfil.join(SistemaPerfil_.perfil);
			fromSistemaPerfil.join(SistemaPerfil_.sistema);
			sistemaPerfilJoin.join(Perfil_.grupoPerfil);

			// sistema
			if (params.containsKey(SistemaPerfilLocal.PESQUISAR_PARAM_SISTEMA)) {
				sistema = (Sistema) params.get(SistemaPerfilLocal.PESQUISAR_PARAM_SISTEMA);
				if (sistema != null) {
					Predicate cond = cb.equal(fromSistemaPerfil.get(SistemaPerfil_.sistema), sistema);
					predicates.add(cond);
				}
			}
			// gruposExcluidos
			if (params.containsKey("gruposExcluidos")) {
				gruposExcluidos = (List<GrupoPerfil>) params.get("gruposExcluidos");
				if (gruposExcluidos != null && gruposExcluidos.size() > 0) {
					Predicate cond1 = cb.not(root.in(gruposExcluidos));
					predicates.add(cond1);
				}
			}

			// nome do perfil
			if (params.containsKey(SistemaPerfilLocal.PESQUISAR_PARAM_NOME)) {
				nome = (String) params.get(SistemaPerfilLocal.PESQUISAR_PARAM_NOME);
				if (StringUtils.isNotEmpty(nome)) {
					Predicate cond = cb.like(cb.lower(root.get(Dominio_.nome)), "%" + nome.toLowerCase() + "%");
					predicates.add(cond);
				}
			}

			// situacao do SistemaPerfil
			if (params.containsKey(SistemaPerfilLocal.PESQUISAR_PARAM_SITUACAO)) {
				situacao = (StatusType) params.get(SistemaPerfilLocal.PESQUISAR_PARAM_SITUACAO);
				if (situacao != null) {
					Expression<StatusType> exp = fromSistemaPerfil.get(SistemaPerfil_.situacao);
					Predicate cond = cb.equal(exp, situacao);
					predicates.add(cond);
				}
			}

			cq.where(cb.and(predicates.toArray(new Predicate[] {})));

			TypedQuery<GrupoPerfil> q = getEntityManager().createQuery(cq);
			return q.getResultList();
		}
		return null;
	}

	@Override
	public List<GrupoPerfil> buscarGruposPerfisPorNome(Sistema sistema, String nome,
			List<GrupoPerfil> gruposExcluidos) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put(SistemaPerfilLocal.PESQUISAR_PARAM_SISTEMA, sistema);
		params.put(SistemaPerfilLocal.PESQUISAR_PARAM_NOME, nome);
		params.put("gruposExcluidos", gruposExcluidos);

		return buscarGruposPerfis(params);
	}

	@Override
	public List<Perfil> buscarPerfis(Sistema sistema) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(SistemaPerfilLocal.PESQUISAR_PARAM_SISTEMA, sistema);

		return buscarPerfis(params);
	}

	@Override
	public List<Perfil> buscarPerfis(Sistema sistema, UsuarioType tipoDeUsuario) {

		List<Predicate> predicates = new ArrayList<Predicate>();

		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<Perfil> cq = cb.createQuery(Perfil.class);
		Root<Perfil> root = cq.from(Perfil.class);
		cq.select(root).distinct(true);

		Root<SistemaPerfil> fromSistemaPerfil = cq.from(SistemaPerfil.class);
		Join<SistemaPerfil, Perfil> sistemaPerfilJoin = fromSistemaPerfil.join(SistemaPerfil_.perfil);

		Predicate cond = cb.equal(sistemaPerfilJoin.get(Perfil_.tipoUsuario), tipoDeUsuario);
		predicates.add(cond);

		Predicate cond1 = cb.equal(fromSistemaPerfil.get(SistemaPerfil_.sistema), sistema);
		predicates.add(cond1);

		cq.where(cb.and(predicates.toArray(new Predicate[] {})));

		TypedQuery<Perfil> q = getEntityManager().createQuery(cq);
		return q.getResultList();

		/*
		 * List<Predicate> predicates = new ArrayList<Predicate>();
		 * 
		 * CriteriaBuilder cb = getCriteriaBuilder(); CriteriaQuery<Perfil> cq =
		 * cb.createQuery(Perfil.class);
		 * 
		 * Root<Perfil> root = cq.from(Perfil.class); Root<SistemaPerfil>
		 * fromSistemaPerfil = cq.from(SistemaPerfil.class);
		 * cq.select(root).distinct(true);
		 * 
		 * Join<SistemaPerfil, Perfil> sistemaPerfilJoin =
		 * fromSistemaPerfil.join(SistemaPerfil_.perfil);
		 * 
		 * Predicate cond = cb.equal(root.get(Perfil_.tipoUsuario), tipoDeUsuario);
		 * predicates.add(cond);
		 * 
		 * Predicate cond1 = cb.equal(fromSistemaPerfil.get(SistemaPerfil_.sistema),
		 * sistema); predicates.add(cond1);
		 * 
		 * cq.where(cb.and(predicates.toArray(new Predicate[] {})));
		 * 
		 * TypedQuery<Perfil> q = getEntityManager().createQuery(cq); return
		 * q.getResultList();
		 */
	}

	@Override
	public List<Perfil> buscarPerfis(Sistema sistema, List<Perfil> perfisExcluidos) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(SistemaPerfilLocal.PESQUISAR_PARAM_SISTEMA, sistema);
		params.put("perfisExcluidos", perfisExcluidos);

		return buscarPerfis(params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Perfil> buscarPerfis(Map<String, Object> params) {

		if (params.size() > 0) {

			String nome = null;
			Sistema sistema = null;
			GrupoPerfil grupo = null;
			StatusType situacao = null;
			Boolean fetchGrupoPerfil = false;
			List<Perfil> perfisExcluidos;
			List<Predicate> predicates = new ArrayList<Predicate>();

			CriteriaBuilder cb = getCriteriaBuilder();
			CriteriaQuery<Perfil> cq = cb.createQuery(Perfil.class);

			Root<Perfil> root = cq.from(Perfil.class);
			Root<SistemaPerfil> fromSistemaPerfil = cq.from(SistemaPerfil.class);

			cq.select(root).distinct(true);

			if (params.containsKey(PESQUISAR_PARAM_FETCH_GRUPO_PERFIL)) {
				fetchGrupoPerfil = (Boolean) params.get(PESQUISAR_PARAM_FETCH_GRUPO_PERFIL);
				if (fetchGrupoPerfil) {
					root.fetch(Perfil_.grupoPerfil);
				}
			}

			// sistema
			if (params.containsKey(PESQUISAR_PARAM_SISTEMA)) {
				sistema = (Sistema) params.get(PESQUISAR_PARAM_SISTEMA);
				if (sistema != null) {
					Predicate cond = cb.equal(fromSistemaPerfil.get(SistemaPerfil_.sistema), sistema);
					predicates.add(cond);
				}
			}

			// nome do perfil
			if (params.containsKey(SistemaPerfilLocal.PESQUISAR_PARAM_NOME)) {
				nome = (String) params.get(SistemaPerfilLocal.PESQUISAR_PARAM_NOME);
				if (StringUtils.isNotEmpty(nome)) {
					Predicate cond = cb.like(cb.lower(root.get(Dominio_.nome)), "%" + nome.toLowerCase() + "%");
					predicates.add(cond);
				}
			}

			// perfisExcluidos
			if (params.containsKey(PESQUISAR_PARAM_PERFIS_EXCLUIDOS)) {
				perfisExcluidos = (List<Perfil>) params.get(PESQUISAR_PARAM_PERFIS_EXCLUIDOS);
				if (perfisExcluidos != null && perfisExcluidos.size() > 0) {
					Predicate cond = cb.not(root.in(perfisExcluidos));
					predicates.add(cond);
				}
			}

			if (params.containsKey(PESQUISAR_PARAM_GRUPO_PERFIL)) {

				grupo = (GrupoPerfil) params.get(PESQUISAR_PARAM_GRUPO_PERFIL);
				Join<SistemaPerfil, Perfil> sistemaPerfilJoin = fromSistemaPerfil.join(SistemaPerfil_.perfil);
				sistemaPerfilJoin.join(Perfil_.grupoPerfil);

				if (grupo != null) {
					Predicate cond = cb.equal(sistemaPerfilJoin.get(Perfil_.grupoPerfil), grupo);
					predicates.add(cond);
				}
			}

			if (params.containsKey(PESQUISAR_PARAM_SITUACAO)) {
				situacao = (StatusType) params.get(PESQUISAR_PARAM_SITUACAO);
				if (situacao != null) {
					Expression<StatusType> exp = fromSistemaPerfil.get(SistemaPerfil_.situacao);
					Predicate cond = cb.equal(exp, situacao);
					predicates.add(cond);
				}
			}

			cq.where(cb.and(predicates.toArray(new Predicate[] {})));

			TypedQuery<Perfil> q = getEntityManager().createQuery(cq);
			return q.getResultList();
		}

		return null;
	}

	@Override
	public List<Perfil> buscarPerfisPorNome(Sistema sistema, String nome, List<Perfil> perfisExcluidos) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(PESQUISAR_PARAM_SISTEMA, sistema);
		params.put(SistemaPerfilLocal.PESQUISAR_PARAM_NOME, nome);
		params.put(PESQUISAR_PARAM_PERFIS_EXCLUIDOS, perfisExcluidos);
		return buscarPerfis(params);
	}

	@Override
	public List<Perfil> buscarPerfisPorNome(Sistema sistema, String nome, List<Perfil> perfisExcluidos,
			Boolean fetchGrupoPerfil) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(PESQUISAR_PARAM_SISTEMA, sistema);
		params.put(SistemaPerfilLocal.PESQUISAR_PARAM_NOME, nome);
		params.put(PESQUISAR_PARAM_PERFIS_EXCLUIDOS, perfisExcluidos);
		params.put(PESQUISAR_PARAM_FETCH_GRUPO_PERFIL, fetchGrupoPerfil);
		return buscarPerfis(params);
	}

	@Override
	public List<Perfil> buscarPerfisPorGrupo(Sistema sistema, String nome, GrupoPerfil grupoSelecionado) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(SistemaPerfilLocal.PESQUISAR_PARAM_SISTEMA, sistema);
		params.put(PESQUISAR_PARAM_GRUPO_PERFIL, grupoSelecionado);
		params.put(SistemaPerfilLocal.PESQUISAR_PARAM_NOME, nome);

		return buscarPerfis(params);
	}

	@Override
	public SistemaPerfil buscar(Sistema sistema, Perfil perfil, Boolean fetchPerfil) {

		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<SistemaPerfil> cq = createCriteriaQuery();
		Root<SistemaPerfil> root = cq.from(SistemaPerfil.class);
		cq.select(root);
		if (fetchPerfil) {
			root.fetch(SistemaPerfil_.perfil);
		}
		List<Predicate> predicates = new ArrayList<Predicate>();

		Predicate cond = cb.equal(root.get(SistemaPerfil_.perfil), perfil);
		predicates.add(cond);
		Predicate cond1 = cb.equal(root.get(SistemaPerfil_.sistema), sistema);
		predicates.add(cond1);

		cq.where(predicates.toArray(new Predicate[] {}));

		return getSingleResult(cq);
	}

	@Override
	public List<SistemaPerfil> buscarTodos(Sistema sistema) {
		return buscarTodos(sistema, null, false);
	}

	@Override
	public List<SistemaPerfil> buscarTodos(Sistema sistema, Boolean carregarEager) {
		return buscarTodos(sistema, null, carregarEager);
	}

	@Override
	public List<SistemaPerfil> buscarTodos(Sistema sistema, UsuarioType grupoUsuario, Boolean carregarEager) {
		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<SistemaPerfil> cq = createCriteriaQuery();

		Root<SistemaPerfil> root = cq.from(SistemaPerfil.class);

		List<Predicate> predicates = new ArrayList<Predicate>();

		cq.select(root).distinct(true);
		if (sistema != null) {
			root.join(SistemaPerfil_.sistema);
			Predicate cond = cb.equal(root.get(SistemaPerfil_.sistema), sistema);
			predicates.add(cond);
		}

		if (carregarEager == null) {
			carregarEager = false;
		}

		if (carregarEager) {
			@SuppressWarnings("unchecked")
			Join<SistemaPerfil, Perfil> perfilJoin = (Join<SistemaPerfil, Perfil>) root.fetch(SistemaPerfil_.perfil);
			perfilJoin.fetch(Perfil_.grupoPerfil);
			root.fetch(SistemaPerfil_.sistema);
			if (grupoUsuario != null) {
				Predicate cond1 = cb.equal(perfilJoin.get(Perfil_.tipoUsuario), grupoUsuario);
				predicates.add(cond1);
			}

		}
		cq.where(predicates.toArray(new Predicate[] {}));

		return getResultList(cq);
	}

	@Override
	public List<SistemaPerfil> getAtivos(Sistema sistema, UsuarioType grupoUsuario, Boolean fetchSistema,
			Boolean fetchPerfil, Boolean fetchGrupo) {

		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<SistemaPerfil> cq = createCriteriaQuery();

		Root<SistemaPerfil> root = cq.from(SistemaPerfil.class);
		cq.select(root).distinct(true);

		List<Predicate> predicates = new ArrayList<Predicate>();

		root.join(SistemaPerfil_.sistema);
		Predicate cond = cb.equal(root.get(SistemaPerfil_.sistema), sistema);
		predicates.add(cond);

		if (fetchSistema) {
			root.fetch(SistemaPerfil_.sistema);
		}

		if (fetchPerfil) {
			@SuppressWarnings("unchecked")
			Join<SistemaPerfil, Perfil> perfilJoin = (Join<SistemaPerfil, Perfil>) root.fetch(SistemaPerfil_.perfil);
			Predicate cond1 = cb.equal(perfilJoin.get(Perfil_.tipoUsuario), grupoUsuario);
			predicates.add(cond1);

			if (fetchGrupo) {
				perfilJoin.fetch(Perfil_.grupoPerfil);
			}
		}

		cq.where(predicates.toArray(new Predicate[] {}));

		return getResultList(cq);
	}

}

package br.leg.rr.al.seguranca.autorizacao.ejb;

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
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import br.leg.rr.al.core.dao.BaseDominioIndexadoJPADao;
import br.leg.rr.al.core.dao.BeanException;
import br.leg.rr.al.core.domain.StatusType;
import br.leg.rr.al.core.jpa.BaseEntity_;
import br.leg.rr.al.core.jpa.DominioIndexado_;
import br.leg.rr.al.core.jpa.Dominio_;
import br.leg.rr.al.seguranca.autorizacao.jpa.GrupoPerfil;
import br.leg.rr.al.seguranca.autorizacao.jpa.Perfil;
import br.leg.rr.al.seguranca.autorizacao.jpa.Perfil_;

@Stateless
@Named
public class PerfilService extends BaseDominioIndexadoJPADao<Perfil> implements PerfilLocal {

	private static final long serialVersionUID = -9162032858310691064L;

	@Override
	public List<Perfil> getAtivos(GrupoPerfil grupo) {

		Map<String, Object> params = new HashMap<String, Object>();

		params.put(PESQUISAR_PARAM_SITUACAO, StatusType.ATIVO);
		params.put(PESQUISAR_PARAM_GRUPO_PERFIL, grupo);

		return pesquisar(params);

	}

	@Override
	public List<Perfil> getAtivos(GrupoPerfil grupo, List<Perfil> perfis) {
		Map<String, Object> params = new HashMap<String, Object>();

		params.put(PESQUISAR_PARAM_SITUACAO, StatusType.ATIVO);
		params.put(PESQUISAR_PARAM_GRUPO_PERFIL, grupo);

		// TODO falta implementar essa lista de exclusão.
		params.put("perfilList", perfis);

		return pesquisar(params);

	}

	@Override
	public Boolean jaExiste(Perfil entidade) {

		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<Perfil> root = cq.from(Perfil.class);
		cq.select(cb.count(root));
		
		List<Predicate> predicates = new ArrayList<Predicate>();

		Predicate cond = cb.equal(cb.lower(root.get(DominioIndexado_.nome)), entidade.getNome().toLowerCase());
		predicates.add(cond);

		if (entidade.getGrupoPerfil() != null) {
			cond = cb.equal(root.get(Perfil_.grupoPerfil), entidade.getGrupoPerfil());
			predicates.add(cond);
		} else {
			cond = root.get(Perfil_.grupoPerfil).isNull();
			predicates.add(cond);
		}

		if (entidade.getId() != null && entidade.getId().intValue() > 0) {
			cond = cb.notEqual(root.get(BaseEntity_.id), entidade.getId());
			predicates.add(cond);
		}

		cq.where(predicates.toArray(new Predicate[] {}));
		TypedQuery<Long> q = getEntityManager().createQuery(cq);

		if (q.getSingleResult() > 0) {
			if (entidade.getGrupoPerfil()!= null) {
				throw new BeanException("Perfil com este Nome e Grupo já existe. Informe outro valor.");
			} else {
				throw new BeanException("Perfil com este Nome já existe. Informe outro valor.");
			}
		}

		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Perfil> pesquisar(Map<String, Object> params) {

		String nome = null;
		StatusType situacao = null;
		List<GrupoPerfil> grupos = null;

		List<Predicate> predicates = new ArrayList<Predicate>();

		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<Perfil> cq = getCriteriaBuilder().createQuery(entityClass);
		Root<Perfil> root = cq.from(entityClass);
		root.fetch(Perfil_.grupoPerfil, JoinType.LEFT);
		cq.select(root);

		if (params.size() > 0) {

			if (params.containsKey(PESQUISAR_PARAM_NOME)) {
				nome = (String) params.get(PESQUISAR_PARAM_NOME);
				if (StringUtils.isNotBlank(nome)) {
					Predicate cond = cb.like(cb.lower(root.get(PESQUISAR_PARAM_NOME)), "%" + nome.toLowerCase() + "%");
					predicates.add(cond);
				}
			}

			if (params.containsKey(PESQUISAR_PARAM_GRUPOS_PERFIL)) {
				grupos = (List<GrupoPerfil>) params.get(PESQUISAR_PARAM_GRUPOS_PERFIL);
				if (grupos != null && grupos.size() > 0) {
					Expression<GrupoPerfil> exp = root.get(Perfil_.grupoPerfil);
					Predicate cond = exp.in(grupos);
					predicates.add(cond);

				}
			}

			if (params.containsKey(PESQUISAR_PARAM_SITUACAO)) {
				situacao = (StatusType) params.get(PESQUISAR_PARAM_SITUACAO);
				if (situacao != null) {
					Predicate cond = cb.equal(root.get(Dominio_.situacao), situacao);
					predicates.add(cond);
				}
			}

			cq.where(cb.and(predicates.toArray(new Predicate[] {})));
			return getResultList(cq);
		}

		return null;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.leg.al.rr.core.dao.BaseJPADao#carregarEntidade(br.leg.al.rr.core.jpa.
	 * EntityStatus)
	 */
	@Override
	public Perfil carregar(Perfil entidade) {
		Perfil perfil = buscar(entidade);
		perfil.getGrupoPerfil().getId();

		return perfil;
	}

}

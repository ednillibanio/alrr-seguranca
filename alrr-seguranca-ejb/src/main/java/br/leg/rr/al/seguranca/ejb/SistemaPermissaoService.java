/**
 * 
 */
package br.leg.rr.al.seguranca.ejb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.leg.rr.al.core.dao.BaseJPADaoStatus;
import br.leg.rr.al.core.jpa.BaseEntity_;
import br.leg.rr.al.seguranca.jpa.Objeto;
import br.leg.rr.al.seguranca.jpa.Objeto_;
import br.leg.rr.al.seguranca.jpa.Operacao;
import br.leg.rr.al.seguranca.jpa.Operacao_;
import br.leg.rr.al.seguranca.jpa.Sistema;
import br.leg.rr.al.seguranca.jpa.SistemaBaseEntity_;
import br.leg.rr.al.seguranca.jpa.SistemaDominio_;
import br.leg.rr.al.seguranca.jpa.SistemaPermissao;
import br.leg.rr.al.seguranca.jpa.SistemaPermissao_;

/**
 * @author ednil
 *
 */
@Named
@Stateless
public class SistemaPermissaoService extends BaseJPADaoStatus<SistemaPermissao, Integer>
		implements SistemaPermissaoLocal {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6663848552426218704L;

	@Override
	public Boolean jaExiste(SistemaPermissao entidade) {

		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<SistemaPermissao> cq = createCriteriaQuery();
		Root<SistemaPermissao> root = cq.from(SistemaPermissao.class);
		cq.select(root);
		List<Predicate> predicates = new ArrayList<Predicate>();

		Predicate cond = cb.equal(cb.lower(root.get(SistemaPermissao_.valor)), entidade.getValor().toLowerCase());
		predicates.add(cond);

		Predicate cond1 = cb.equal(root.get("sistema"), entidade.getSistema());
		predicates.add(cond1);

		if (entidade.getId() != null && entidade.getId().intValue() > 0) {
			Predicate cond3 = cb.notEqual(root.get(BaseEntity_.id), entidade.getId());
			predicates.add(cond3);
		}

		cq.where(cb.and(predicates.toArray(new Predicate[] {})));

		return (!getResultList(cq).isEmpty());
	}

	// TODO: passar esse método para super class. mas para isso precisa criar
	// uma classe só para o framework d controle de sistemas. por exemplo
	// RbacBaseJPADao
	@Override
	public List<SistemaPermissao> buscarTodos(Sistema sistema) {
		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<SistemaPermissao> cq = createCriteriaQuery();
		Root<SistemaPermissao> root = cq.from(SistemaPermissao.class);
		cq.select(root);
		Predicate cond = cb.equal(root.get("sistema"), sistema);
		cq.where(cond);

		return getResultList(cq);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SistemaPermissao> pesquisar(Map<String, Object> params) {

		Sistema sistema = null;
		Boolean fetchSistema = false;
		Boolean fetchModulo = false;
		Boolean fetchOperacao = false;
		Boolean fetchObjeto = false;

		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<SistemaPermissao> cq = createCriteriaQuery();
		Root<SistemaPermissao> root = cq.from(SistemaPermissao.class);

		if (params.size() > 0) {

			if (params.containsKey(SistemaPermissaoLocal.PESQUISAR_PARAM_FETCH_SISTEMA)) {
				fetchSistema = (Boolean) params.get(SistemaPermissaoLocal.PESQUISAR_PARAM_FETCH_SISTEMA);
			}
			if (params.containsKey(SistemaPermissaoLocal.PESQUISAR_PARAM_FETCH_OPERACAO)) {
				fetchOperacao = (Boolean) params.get(SistemaPermissaoLocal.PESQUISAR_PARAM_FETCH_OPERACAO);
			}

			if (params.containsKey(SistemaPermissaoLocal.PESQUISAR_PARAM_FETCH_OBJETO)) {
				fetchObjeto = (Boolean) params.get(SistemaPermissaoLocal.PESQUISAR_PARAM_FETCH_OBJETO);
			}
			if (params.containsKey(SistemaPermissaoLocal.PESQUISAR_PARAM_FETCH_MODULO)) {
				fetchModulo = (Boolean) params.get(SistemaPermissaoLocal.PESQUISAR_PARAM_FETCH_MODULO);
			}

			if (fetchSistema) {
				root.fetch("sistema");
			}

			if (fetchOperacao) {
				root.fetch(SistemaPermissao_.operacao);
			}

			if (fetchObjeto) {
				Join<SistemaPermissao, Objeto> permJoin = (Join<SistemaPermissao, Objeto>) root
						.fetch(SistemaPermissao_.objeto);
				if (fetchModulo) {
					permJoin.fetch(Objeto_.modulo);
				}
			}

			List<Predicate> predicates = new ArrayList<Predicate>();
			cq.select(root);

			if (params.containsKey(SistemaPermissaoLocal.PESQUISAR_PARAM_SISTEMA)) {
				sistema = (Sistema) params.get(SistemaPermissaoLocal.PESQUISAR_PARAM_SISTEMA);
				if (sistema != null) {
					Predicate cond = cb.equal(root.get("sistema"), sistema);
					predicates.add(cond);
				}
			}
			cq.where(cb.and(predicates.toArray(new Predicate[] {})));
			return getResultList(cq);
		}

		return null;
	}

	@Override
	public List<Operacao> buscarOperacoesPorObjeto(Sistema sistema, Objeto objeto) {

		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<Operacao> cq = cb.createQuery(Operacao.class);
		Root<Operacao> root = cq.from(Operacao.class);
		cq.select(root);

		Join<Operacao, SistemaPermissao> joinPermissao = root.join(Operacao_.permissoes);

		Predicate cond1 = cb.equal(root.get(SistemaDominio_.sistema), sistema);
		Predicate cond2 = cb.equal(joinPermissao.get("sistema"), sistema);
		Predicate cond3 = cb.equal(joinPermissao.get(SistemaPermissao_.objeto), objeto);

		cq.where(cond1, cond2, cond3);
		TypedQuery<Operacao> q = getEntityManager().createQuery(cq);

		return q.getResultList();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.leg.al.rr.core.dao.BaseJPADao#carregarEntidade(br.leg.al.rr.core.jpa.
	 * EntityStatus)
	 */
	@Override
	public SistemaPermissao carregar(SistemaPermissao entidade) {
		SistemaPermissao perm = buscar(entidade);

		// fetch
		perm.getSistema().getId();
		perm.getObjeto().getId();
		perm.getObjeto().getModulo().getId();
		perm.getObjeto().getSistema().getId();
		perm.getOperacao().getId();
		perm.getOperacao().getSistema().getId();

		return perm;
	}

}

/**
 * 
 */
package br.leg.rr.al.seguranca.autorizacao.ejb;

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
import br.leg.rr.al.seguranca.autorizacao.jpa.Modulo;
import br.leg.rr.al.seguranca.autorizacao.jpa.Objeto;
import br.leg.rr.al.seguranca.autorizacao.jpa.Objeto_;
import br.leg.rr.al.seguranca.autorizacao.jpa.Operacao;
import br.leg.rr.al.seguranca.autorizacao.jpa.Operacao_;
import br.leg.rr.al.seguranca.autorizacao.jpa.Permissao;
import br.leg.rr.al.seguranca.autorizacao.jpa.Permissao_;

/**
 * @author ednil
 *
 */
@Named
@Stateless
public class PermissaoService extends BaseJPADaoStatus<Permissao, Integer> implements PermissaoLocal {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6663848552426218704L;

	@Override
	public Boolean jaExiste(Permissao entidade) {

		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<Permissao> cq = createCriteriaQuery();
		Root<Permissao> root = cq.from(Permissao.class);
		cq.select(root);
		List<Predicate> predicates = new ArrayList<Predicate>();

		Predicate cond = cb.equal(cb.lower(root.get(Permissao_.valor)), entidade.getValor().toLowerCase());
		predicates.add(cond);

		if (entidade.getId() != null && entidade.getId().intValue() > 0) {
			Predicate cond3 = cb.notEqual(root.get(BaseEntity_.id), entidade.getId());
			predicates.add(cond3);
		}

		cq.where(cb.and(predicates.toArray(new Predicate[] {})));

		return (!getResultList(cq).isEmpty());
	}

	@Override
	public List<Permissao> buscarTodos(Modulo modulo) {
		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<Permissao> cq = createCriteriaQuery();
		Root<Permissao> root = cq.from(Permissao.class);
		cq.select(root);
		Predicate cond = cb.equal(root.get("modulo"), modulo);
		cq.where(cond);

		return getResultList(cq);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Permissao> pesquisar(Map<String, Object> params) {

		Boolean fetchModulo = false;
		Boolean fetchOperacao = false;
		Boolean fetchObjeto = false;

		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<Permissao> cq = createCriteriaQuery();
		Root<Permissao> root = cq.from(Permissao.class);

		if (params.size() > 0) {

			if (params.containsKey(PermissaoLocal.PESQUISAR_PARAM_FETCH_OPERACAO)) {
				fetchOperacao = (Boolean) params.get(PermissaoLocal.PESQUISAR_PARAM_FETCH_OPERACAO);
			}

			if (params.containsKey(PermissaoLocal.PESQUISAR_PARAM_FETCH_OBJETO)) {
				fetchObjeto = (Boolean) params.get(PermissaoLocal.PESQUISAR_PARAM_FETCH_OBJETO);
			}
			if (params.containsKey(PermissaoLocal.PESQUISAR_PARAM_FETCH_MODULO)) {
				fetchModulo = (Boolean) params.get(PermissaoLocal.PESQUISAR_PARAM_FETCH_MODULO);
			}

			if (fetchOperacao) {
				root.fetch(Permissao_.operacao);
			}

			if (fetchObjeto) {
				Join<Permissao, Objeto> permJoin = (Join<Permissao, Objeto>) root.fetch(Permissao_.objeto);
				if (fetchModulo) {
					permJoin.fetch(Objeto_.modulo);
				}
			}

			List<Predicate> predicates = new ArrayList<Predicate>();
			cq.select(root);

			cq.where(cb.and(predicates.toArray(new Predicate[] {})));
			return getResultList(cq);
		}

		return null;
	}

	@Override
	public List<Operacao> buscarOperacoesPorObjeto(Objeto objeto) {

		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<Operacao> cq = cb.createQuery(Operacao.class);
		Root<Operacao> root = cq.from(Operacao.class);
		cq.select(root);

		Join<Operacao, Permissao> joinPermissao = root.join(Operacao_.permissoes);

		Predicate cond3 = cb.equal(joinPermissao.get(Permissao_.objeto), objeto);

		cq.where(cond3);
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
	public Permissao carregar(Permissao entidade) {
		Permissao perm = buscar(entidade);

		// fetch
		perm.getObjeto().getId();
		perm.getObjeto().getModulo().getId();
		perm.getOperacao().getId();

		return perm;
	}

}

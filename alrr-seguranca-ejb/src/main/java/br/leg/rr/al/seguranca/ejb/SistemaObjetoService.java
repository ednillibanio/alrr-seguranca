/**
 * 
 */
package br.leg.rr.al.seguranca.ejb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import br.leg.rr.al.core.domain.StatusType;
import br.leg.rr.al.core.jpa.Dominio_;
import br.leg.rr.al.seguranca.core.dao.SistemaDominioJPADao;
import br.leg.rr.al.seguranca.jpa.Modulo;
import br.leg.rr.al.seguranca.jpa.Objeto;
import br.leg.rr.al.seguranca.jpa.Objeto_;
import br.leg.rr.al.seguranca.jpa.Sistema;
import br.leg.rr.al.seguranca.jpa.SistemaDominio_;

/**
 * @author ednil
 *
 */
@Named
@Stateless
public class ObjetoService extends SistemaDominioJPADao<Objeto> implements ObjetoLocal {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2997144954919921005L;

	@Override
	public List<Objeto> buscarPorModulo(Modulo modulo, StatusType situacao) {
		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<Objeto> cq = createCriteriaQuery();
		Root<Objeto> root = cq.from(Objeto.class);
		cq.select(root);
		List<Predicate> predicates = new ArrayList<Predicate>();

		if (modulo != null) {
			Predicate cond = cb.equal(root.get(Objeto_.modulo), modulo);
			predicates.add(cond);
		}

		if (situacao != null) {
			Predicate cond = cb.equal(root.get(Dominio_.situacao), situacao);
			predicates.add(cond);
		}

		cq.where(cb.and(predicates.toArray(new Predicate[] {})));
		return getResultList(cq);
	}

	@Override
	public Boolean jaExiste(Objeto entidade) {

		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<Objeto> cq = createCriteriaQuery();
		Root<Objeto> root = cq.from(Objeto.class);
		cq.select(root);
		List<Predicate> predicates = new ArrayList<Predicate>();

		Predicate cond = cb.equal(cb.lower(root.get(Dominio_.nome)), entidade.getNome().toLowerCase());
		predicates.add(cond);

		Predicate cond1 = cb.equal(root.get(SistemaDominio_.sistema), entidade.getSistema());
		predicates.add(cond1);

		Predicate cond2 = cb.equal(root.get(Objeto_.modulo), entidade.getModulo());
		predicates.add(cond2);

		cq.where(cb.and(predicates.toArray(new Predicate[] {})));
		return (!getResultList(cq).isEmpty());

	}

	@Override
	public List<Objeto> getAtivos(Modulo modulo) {
		return buscarPorModulo(modulo, StatusType.ATIVO);
	}

	@Override
	public List<Objeto> getAtivos(Sistema sistema, Boolean fetchModulo) {
		Map<String, Object> params = new HashMap<String, Object>();

		params.put(PESQUISAR_PARAM_SISTEMA, sistema);
		params.put(PESQUISAR_PARAM_FETCH_MODULO, fetchModulo);
		return pesquisar(params);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.leg.al.rr.seguranca.core.dao.SistemaDominioJPADao#pesquisar(java.util.
	 * Map)
	 */
	@Override
	public List<Objeto> pesquisar(Map<String, Object> params) {
		// TODO Auto-generated method stub
		String nome = null;
		StatusType situacao = null;
		Sistema sistema = null;
		Boolean fetchModulo = false;
		Boolean fetchPermissoes = false;

		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<Objeto> cq = createCriteriaQuery();
		Root<Objeto> root = cq.from(Objeto.class);
		cq.select(root).distinct(true);

		List<Predicate> predicates = new ArrayList<Predicate>();

		if (params.size() > 0) {

			if (params.containsKey(PESQUISAR_PARAM_FETCH_MODULO)) {
				fetchModulo = (Boolean) params.get(PESQUISAR_PARAM_FETCH_MODULO);
				if (fetchModulo) {
					root.fetch(Objeto_.modulo);
				}
			}
			if (params.containsKey(PESQUISAR_PARAM_FETCH_PERMISSOES)) {
				fetchPermissoes = (Boolean) params.get(PESQUISAR_PARAM_FETCH_PERMISSOES);

				if (fetchPermissoes) {
					root.fetch(Objeto_.permissoes, JoinType.LEFT);
				}

			}

			if (params.containsKey(PESQUISAR_PARAM_SISTEMA)) {
				sistema = (Sistema) params.get(PESQUISAR_PARAM_SISTEMA);

				if (sistema != null) {
					Predicate cond = cb.equal(root.get(SistemaDominio_.sistema), sistema);
					predicates.add(cond);
				}

			}

			if (params.containsKey(PESQUISAR_PARAM_NOME)) {
				nome = (String) params.get(PESQUISAR_PARAM_NOME);

				if (StringUtils.isNotBlank(nome)) {
					Predicate cond = cb.like(cb.lower(root.get(Dominio_.nome)), "%" + nome.toLowerCase() + "%");
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
		}
		return getResultList(cq);

	}

}

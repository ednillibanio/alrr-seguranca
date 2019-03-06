/**
 * 
 */
package br.leg.rr.al.seguranca.autorizacao.ejb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import br.leg.rr.al.core.dao.BaseDominioJPADao;
import br.leg.rr.al.core.dao.BeanException;
import br.leg.rr.al.core.domain.StatusType;
import br.leg.rr.al.core.jpa.BaseEntityStatus_;
import br.leg.rr.al.core.jpa.Dominio_;
import br.leg.rr.al.seguranca.autorizacao.jpa.Modulo;
import br.leg.rr.al.seguranca.autorizacao.jpa.Objeto;
import br.leg.rr.al.seguranca.autorizacao.jpa.Objeto_;

/**
 * @author ednil
 *
 */
@Named
@Stateless
public class ObjetoService extends BaseDominioJPADao<Objeto> implements ObjetoLocal {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3928130159531799836L;

	@Override
	public List<Objeto> buscarPorModulo(Modulo modulo, StatusType situacao) {
		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<Objeto> cq = createCriteriaQuery();
		Root<Objeto> root = cq.from(Objeto.class);
		cq.select(root);
		List<Predicate> predicates = new ArrayList<Predicate>();
		root.fetch(Objeto_.modulo, JoinType.INNER);

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
	public List<Objeto> buscarAtivosPorNome(String nome) {

		Map<String, Object> params = new HashMap<String, Object>();

		params.put(PESQUISAR_PARAM_NOME, nome);
		params.put(PESQUISAR_PARAM_SITUACAO, StatusType.ATIVO);
		params.put(PESQUISAR_PARAM_FETCH_MODULO, true);
		return pesquisar(params);

	}

	@Override
	public List<Objeto> buscarPorNome(String nome) {

		Map<String, Object> params = new HashMap<String, Object>();

		params.put(PESQUISAR_PARAM_NOME, nome);
		params.put(PESQUISAR_PARAM_FETCH_MODULO, true);
		return pesquisar(params);

	}

	@Override
	public Boolean jaExiste(Objeto entidade) {

		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<Objeto> root = cq.from(Objeto.class);
		cq.select(cb.count(root));

		List<Predicate> predicates = new ArrayList<Predicate>();

		Predicate cond = cb.equal(cb.lower(root.get(Objeto_.nome)), entidade.getNome().toLowerCase());
		predicates.add(cond);

		Predicate cond2 = cb.equal(root.get(Objeto_.modulo), entidade.getModulo());
		predicates.add(cond2);

		// condição para não verificar a mesma entidade se já existir.
		if (entidade.getId() != null) {
			Predicate id = cb.notEqual(root.get(BaseEntityStatus_.id), entidade.getId());
			predicates.add(id);
		}

		cq.where(predicates.toArray(new Predicate[predicates.size()]));
		TypedQuery<Long> q = getEntityManager().createQuery(cq);

		if (q.getSingleResult() > 0) {
			throw new BeanException("Objeto com este Nome e Módulo já existe. Informe outro valor.");
		}

		return false;

	}

	@Override
	public List<Objeto> getAtivos(Boolean fetchModulo) {
		Map<String, Object> params = new HashMap<String, Object>();

		params.put(PESQUISAR_PARAM_SITUACAO, StatusType.ATIVO);
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
	@SuppressWarnings("unchecked")
	@Override
	public List<Objeto> pesquisar(Map<String, Object> params) {

		String nome = null;
		StatusType situacao = null;
		List<Modulo> modulos = null;
		Boolean fetchModulo = false;
		Boolean fetchPermissoes = false;

		Predicate cond = null;

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

			if (params.containsKey(PESQUISAR_PARAM_MODULOS)) {

				modulos = (List<Modulo>) params.get(PESQUISAR_PARAM_MODULOS);

				if (modulos != null && modulos.size() > 0) {

					cond = root.get(Objeto_.modulo).in(Arrays.asList(modulos));
					predicates.add(cond);
				}
			}

			if (params.containsKey(PESQUISAR_PARAM_FETCH_PERMISSOES)) {
				fetchPermissoes = (Boolean) params.get(PESQUISAR_PARAM_FETCH_PERMISSOES);

				if (fetchPermissoes) {
					root.fetch(Objeto_.permissoes, JoinType.LEFT);
				}

			}

			if (params.containsKey(PESQUISAR_PARAM_NOME)) {
				nome = (String) params.get(PESQUISAR_PARAM_NOME);

				if (StringUtils.isNotBlank(nome)) {
					cond = cb.like(cb.lower(root.get(Dominio_.nome)), "%" + nome.toLowerCase() + "%");
					predicates.add(cond);
				}

			}

			if (params.containsKey(PESQUISAR_PARAM_SITUACAO)) {
				situacao = (StatusType) params.get(PESQUISAR_PARAM_SITUACAO);

				if (situacao != null) {
					cond = cb.equal(root.get(Dominio_.situacao), situacao);
					predicates.add(cond);
				}

			}

			cq.where(cb.and(predicates.toArray(new Predicate[] {})));
		}
		return getResultList(cq);

	}

}

/**
 * 
 */
package br.leg.rr.al.seguranca.autorizacao.ejb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
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
import br.leg.rr.al.seguranca.autorizacao.jpa.Operacao;
import br.leg.rr.al.seguranca.autorizacao.jpa.Operacao_;

/**
 * @author ednil
 *
 */
@Named
@Stateless
public class OperacaoService extends BaseDominioJPADao<Operacao> implements OperacaoLocal {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2088862408811864866L;

	@Override
	public Boolean jaExiste(Operacao entidade) {

		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<Operacao> root = cq.from(Operacao.class);
		cq.select(cb.count(root));
		List<Predicate> predicates = new ArrayList<Predicate>();

		Predicate cond = cb.equal(cb.lower(root.get(Operacao_.nome)), entidade.getNome().toLowerCase());
		predicates.add(cond);

		if (entidade.getObjeto() != null) {
			cond = cb.equal(root.get(Operacao_.objeto), entidade.getObjeto());
			predicates.add(cond);
		} else {
			cond = root.get(Operacao_.objeto).isNull();
			predicates.add(cond);
		}

		// condição para não verificar a mesma entidade se já existir.
		if (entidade.getId() != null) {
			Predicate id = cb.notEqual(root.get(BaseEntityStatus_.id), entidade.getId());
			predicates.add(id);
		}

		cq.where(predicates.toArray(new Predicate[predicates.size()]));
		TypedQuery<Long> q = getEntityManager().createQuery(cq);

		if (q.getSingleResult() > 0) {
			if (entidade.getObjeto() != null) {
				throw new BeanException("Operação com este Nome e Objeto já existe. Informe outro valor.");
			} else {
				throw new BeanException("Operação com este Nome já existe. Informe outro valor.");
			}
		}

		return false;

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
	public List<Operacao> pesquisar(Map<String, Object> params) {

		String nome = null;
		StatusType situacao = null;
		List<Objeto> objetos = null;
		List<Modulo> modulos = null;
		Boolean fetchModulo = false;
		Boolean fetchPermissoes = false;
		Join<Operacao, Objeto> objJoin = null;
		Predicate cond = null;

		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<Operacao> cq = createCriteriaQuery();
		Root<Operacao> root = cq.from(Operacao.class);
		cq.select(root).distinct(true);

		List<Predicate> predicates = new ArrayList<Predicate>();

		if (params.size() > 0) {

			if (params.containsKey(PESQUISAR_PARAM_OBJETOS)) {

				objetos = (List<Objeto>) params.get(PESQUISAR_PARAM_OBJETOS);

				if (objetos != null && objetos.size() > 0) {
					root.fetch(Operacao_.objeto, JoinType.LEFT);
					cond = root.get(Operacao_.objeto).in(Arrays.asList(objetos));
					predicates.add(cond);

				}
			}

			if (params.containsKey(PESQUISAR_PARAM_FETCH_MODULO)) {
				fetchModulo = (Boolean) params.get(PESQUISAR_PARAM_FETCH_MODULO);
				if (fetchModulo) {
					objJoin = (Join<Operacao, Objeto>) root.fetch(Operacao_.objeto, JoinType.LEFT);
					objJoin.fetch(Objeto_.modulo, JoinType.LEFT);
				}
			}

			if (params.containsKey(PESQUISAR_PARAM_MODULOS)) {

				modulos = (List<Modulo>) params.get(PESQUISAR_PARAM_MODULOS);

				if (modulos != null && modulos.size() > 0) {
					if (objJoin == null) {
						objJoin = (Join<Operacao, Objeto>) root.fetch(Operacao_.objeto, JoinType.LEFT);
						objJoin.fetch(Objeto_.modulo, JoinType.LEFT);
					}
					cond = objJoin.get(Objeto_.modulo).in(Arrays.asList(modulos));
					predicates.add(cond);
				}
			}

			if (params.containsKey(PESQUISAR_PARAM_FETCH_PERMISSOES)) {
				fetchPermissoes = (Boolean) params.get(PESQUISAR_PARAM_FETCH_PERMISSOES);

				if (fetchPermissoes) {
					root.fetch(Operacao_.permissoes, JoinType.LEFT);
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

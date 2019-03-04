/**
 * 
 */
package br.leg.rr.al.seguranca.autorizacao.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.leg.rr.al.core.dao.BaseDominioJPADao;
import br.leg.rr.al.core.jpa.Dominio_;
import br.leg.rr.al.seguranca.autorizacao.jpa.Operacao;

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
	public Boolean jaExiste(Operacao operacao) {

		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<Operacao> cq = createCriteriaQuery();
		Root<Operacao> root = cq.from(Operacao.class);
		cq.select(root);
		List<Predicate> predicates = new ArrayList<Predicate>();

		Predicate cond = cb.equal(cb.lower(root.get(Dominio_.nome)), operacao.getNome().toLowerCase());
		predicates.add(cond);

		cq.where(cb.and(predicates.toArray(new Predicate[] {})));
		return (!getResultList(cq).isEmpty());

	}

}

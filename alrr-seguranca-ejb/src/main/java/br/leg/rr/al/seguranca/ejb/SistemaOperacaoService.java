/**
 * 
 */
package br.leg.rr.al.seguranca.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.leg.rr.al.core.jpa.Dominio_;
import br.leg.rr.al.seguranca.core.dao.SistemaDominioJPADao;
import br.leg.rr.al.seguranca.jpa.Operacao;
import br.leg.rr.al.seguranca.jpa.SistemaDominio_;

/**
 * @author ednil
 *
 */
@Named
@Stateless
public class OperacaoService extends SistemaDominioJPADao<Operacao> implements OperacaoLocal {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2259164411643341574L;

	@Override
	public Boolean jaExiste(Operacao operacao) {

		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<Operacao> cq = createCriteriaQuery();
		Root<Operacao> root = cq.from(Operacao.class);
		cq.select(root);
		List<Predicate> predicates = new ArrayList<Predicate>();

		Predicate cond = cb.equal(cb.lower(root.get(Dominio_.nome)), operacao.getNome().toLowerCase());
		predicates.add(cond);

		Predicate cond1 = cb.equal(root.get(SistemaDominio_.sistema), operacao.getSistema());
		predicates.add(cond1);
		cq.where(cb.and(predicates.toArray(new Predicate[] {})));
		return (!getResultList(cq).isEmpty());

	}

}

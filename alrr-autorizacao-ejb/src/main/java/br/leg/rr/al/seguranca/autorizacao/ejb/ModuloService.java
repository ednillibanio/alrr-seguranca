package br.leg.rr.al.seguranca.autorizacao.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.leg.rr.al.core.dao.BaseDominioIndexadoJPADao;
import br.leg.rr.al.core.jpa.BaseEntityStatus_;
import br.leg.rr.al.seguranca.autorizacao.jpa.Modulo;
import br.leg.rr.al.seguranca.autorizacao.jpa.Modulo_;

@Named
@Stateless
public class ModuloService extends BaseDominioIndexadoJPADao<Modulo> implements ModuloLocal {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4912515139124040498L;

	@Override
	public Boolean jaExiste(Modulo entidade) {

		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<Modulo> cq = createCriteriaQuery();
		Root<Modulo> root = cq.from(Modulo.class);
		cq.select(root);
		List<Predicate> predicates = new ArrayList<Predicate>();

		Predicate cond = cb.equal(cb.lower(root.get(Modulo_.nome)), entidade.getNome().toLowerCase());
		predicates.add(cond);

		// condição para não verificar a mesma entidade se já existir.
		if (entidade.getId() != null) {
			Predicate id = cb.notEqual(root.get(BaseEntityStatus_.id), entidade.getId());
			predicates.add(id);
		}

		cq.where(predicates.toArray(new Predicate[predicates.size()]));

		return (!getResultList(cq).isEmpty());
	}

}

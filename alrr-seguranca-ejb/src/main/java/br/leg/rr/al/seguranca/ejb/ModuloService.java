package br.leg.rr.al.seguranca.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.leg.rr.al.core.jpa.Dominio_;
import br.leg.rr.al.seguranca.core.dao.SistemaDominioJPADao;
import br.leg.rr.al.seguranca.jpa.Modulo;
import br.leg.rr.al.seguranca.jpa.Modulo_;
import br.leg.rr.al.seguranca.jpa.Objeto;
import br.leg.rr.al.seguranca.jpa.Objeto_;
import br.leg.rr.al.seguranca.jpa.Sistema;
import br.leg.rr.al.seguranca.jpa.SistemaDominio_;
import br.leg.rr.al.seguranca.jpa.SistemaPermissao;
import br.leg.rr.al.seguranca.jpa.SistemaPermissao_;

@Named
@Stateless
public class ModuloService extends SistemaDominioJPADao<Modulo> implements ModuloLocal {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5235390235156315392L;

	@Override
	public Boolean jaExiste(Modulo entidade) {

		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<Modulo> cq = createCriteriaQuery();
		Root<Modulo> root = cq.from(Modulo.class);
		cq.select(root);
		List<Predicate> predicates = new ArrayList<Predicate>();

		Predicate cond = cb.equal(cb.lower(root.get(Dominio_.nome)), entidade.getNome().toLowerCase());
		predicates.add(cond);

		Predicate cond1 = cb.equal(root.get(SistemaDominio_.sistema), entidade.getSistema());
		predicates.add(cond1);

		cq.where(cb.and(predicates.toArray(new Predicate[] {})));
		return (!getResultList(cq).isEmpty());

	}

	@Override
	public List<Modulo> buscarPermissoes(Sistema sistema) {

		CriteriaBuilder cb = getCriteriaBuilder();

		CriteriaQuery<Modulo> cq = createCriteriaQuery();
		Root<Modulo> root = cq.from(Modulo.class);
		cq.select(root).distinct(true);
		@SuppressWarnings("unchecked")
		Join<Modulo, Objeto> objetoJoin = (Join<Modulo, Objeto>) root.fetch(Modulo_.objetos);

		@SuppressWarnings("unchecked")
		Join<Objeto, SistemaPermissao> permissaoJoin = (Join<Objeto, SistemaPermissao>) objetoJoin
				.fetch(Objeto_.permissoes);
		permissaoJoin.fetch(SistemaPermissao_.operacao);

		Predicate cond = cb.equal(root.get(SistemaDominio_.sistema), sistema);

		cq.where(cond);
		return getResultList(cq);

	}

}

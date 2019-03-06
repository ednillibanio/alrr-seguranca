package br.leg.rr.al.seguranca.autorizacao.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.leg.rr.al.core.dao.BaseDominioIndexadoJPADao;
import br.leg.rr.al.core.dao.BeanException;
import br.leg.rr.al.core.jpa.BaseEntityStatus_;
import br.leg.rr.al.seguranca.autorizacao.jpa.GrupoPerfil;
import br.leg.rr.al.seguranca.autorizacao.jpa.GrupoPerfil_;

@Named
@Stateless
public class GrupoPerfilService extends BaseDominioIndexadoJPADao<GrupoPerfil> implements GrupoPerfilLocal {

	private static final long serialVersionUID = -1009861115440115454L;

	@Override
	public Boolean jaExiste(GrupoPerfil entidade) {
		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<GrupoPerfil> root = cq.from(GrupoPerfil.class);
		cq.select(cb.count(root));
		
		List<Predicate> predicates = new ArrayList<Predicate>();

		Predicate cond = cb.equal(cb.lower(root.get(GrupoPerfil_.nome)), entidade.getNome().toLowerCase());
		predicates.add(cond);

		// condição para não verificar a mesma entidade se já existir.
		if (entidade.getId() != null) {
			Predicate id = cb.notEqual(root.get(BaseEntityStatus_.id), entidade.getId());
			predicates.add(id);
		}

		cq.where(predicates.toArray(new Predicate[predicates.size()]));
		TypedQuery<Long> q = getEntityManager().createQuery(cq);

		if (q.getSingleResult() > 0) {
			throw new BeanException("Grupo de Perfil com este Nome já existe. Informe outro valor.");
		}

		return false;
	}

}

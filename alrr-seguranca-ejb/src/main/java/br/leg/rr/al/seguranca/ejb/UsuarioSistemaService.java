package br.leg.rr.al.seguranca.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.leg.rr.al.core.dao.BeanException;
import br.leg.rr.al.core.domain.StatusType;
import br.leg.rr.al.core.jpa.BaseEntity_;
import br.leg.rr.al.seguranca.core.dao.SistemaBaseJPADao;
import br.leg.rr.al.seguranca.jpa.Perfil;
import br.leg.rr.al.seguranca.jpa.Perfil_;
import br.leg.rr.al.seguranca.jpa.Sistema;
import br.leg.rr.al.seguranca.jpa.SistemaBaseEntity_;
import br.leg.rr.al.seguranca.jpa.SistemaPerfil;
import br.leg.rr.al.seguranca.jpa.SistemaPerfil_;
import br.leg.rr.al.seguranca.jpa.SistemaPermissao;
import br.leg.rr.al.seguranca.jpa.Usuario;
import br.leg.rr.al.seguranca.jpa.UsuarioSistema;
import br.leg.rr.al.seguranca.jpa.UsuarioSistema_;

@Stateless
@Named
public class UsuarioSistemaService extends SistemaBaseJPADao<UsuarioSistema, Integer> implements UsuarioSistemaLocal {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8402741681596418643L;

	@Override
	public Boolean jaExiste(UsuarioSistema entidade) {
		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<UsuarioSistema> cq = createCriteriaQuery();
		Root<UsuarioSistema> root = cq.from(UsuarioSistema.class);
		cq.select(root);
		List<Predicate> predicates = new ArrayList<Predicate>();

		Predicate cond = cb.equal(root.get(UsuarioSistema_.sistema), entidade.getSistema());
		predicates.add(cond);

		Predicate cond1 = cb.equal(root.get(UsuarioSistema_.usuario), entidade.getUsuario());
		predicates.add(cond1);

		cq.where(cb.and(predicates.toArray(new Predicate[] {})));

		return (!getResultList(cq).isEmpty());
	}

	@Override
	public List<UsuarioSistema> buscar(Usuario usuario, Boolean fetch) {
		return buscar(usuario, fetch, fetch, fetch, fetch);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UsuarioSistema> buscar(Usuario usuario, Boolean fetchUsuario, Boolean fetchSistema, Boolean fetchPerfis,
			Boolean fetchPermissoes) {
		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<UsuarioSistema> cq = createCriteriaQuery();
		Root<UsuarioSistema> root = cq.from(UsuarioSistema.class);
		cq.select(root).distinct(true);

		Predicate cond = cb.equal(root.get(UsuarioSistema_.usuario), usuario);

		// recupera todos os perfis associado ao usuario-sistema.
		if (fetchPerfis) {
			Join<UsuarioSistema, SistemaPerfil> sistemaPerfilJoin = (Join<UsuarioSistema, SistemaPerfil>) root
					.fetch(UsuarioSistema_.perfis, JoinType.LEFT);
			Join<SistemaPerfil, Perfil> perfilJoin = (Join<SistemaPerfil, Perfil>) sistemaPerfilJoin
					.fetch(SistemaPerfil_.perfil, JoinType.LEFT);
			perfilJoin.fetch(Perfil_.grupoPerfil, JoinType.LEFT);
			// recupera todas as permissoes por perfil.
			if (fetchPermissoes) {
				// todo: ver se vai funcionar. Ainda não testamos.
				sistemaPerfilJoin.fetch(SistemaPerfil_.permissoes, JoinType.LEFT);
			}
		}

		if (fetchUsuario) {
			root.fetch(UsuarioSistema_.usuario);
		}
		if (fetchSistema) {
			root.fetch(UsuarioSistema_.sistema);
		}

		cq.where(cond);

		return getResultList(cq);
	}

	@Override
	public UsuarioSistema buscar(Usuario usuario, Sistema sistema) {
		return buscar(usuario, sistema, false, false, false, false);
	}

	@SuppressWarnings("unchecked")
	@Override
	public UsuarioSistema buscar(Usuario usuario, Sistema sistema, Boolean fetchUsuario, Boolean fetchSistema,
			Boolean fetchPerfis, Boolean fetchPermissoes) {
		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<UsuarioSistema> cq = createCriteriaQuery();
		Root<UsuarioSistema> root = cq.from(UsuarioSistema.class);
		cq.select(root);

		Predicate cond = cb.equal(root.get(UsuarioSistema_.usuario), usuario);
		Predicate cond1 = cb.equal(root.get(UsuarioSistema_.sistema), sistema);

		if (fetchUsuario) {
			root.fetch(UsuarioSistema_.usuario);
		}

		if (fetchSistema) {
			root.fetch(UsuarioSistema_.sistema);
		}

		// recupera todos os perfis associado ao usuario-sistema.
		if (fetchPerfis) {

			Join<UsuarioSistema, SistemaPerfil> sistemaPerfilJoin = (Join<UsuarioSistema, SistemaPerfil>) root
					.fetch(UsuarioSistema_.perfis, JoinType.LEFT);

			Join<SistemaPerfil, Perfil> perfilJoin = (Join<SistemaPerfil, Perfil>) sistemaPerfilJoin
					.fetch(SistemaPerfil_.perfil, JoinType.LEFT);

			perfilJoin.fetch(Perfil_.grupoPerfil, JoinType.LEFT);

			// recupera todas as permissoes por perfil.
			if (fetchPermissoes) {
				sistemaPerfilJoin.fetch(SistemaPerfil_.permissoes, JoinType.LEFT);
			}
		}

		cq.where(cond, cond1);

		return getSingleResult(cq);
	}

	@SuppressWarnings("unchecked")
	@Override
	public UsuarioSistema buscar(UsuarioSistema entidade, Boolean fetchUsuario, Boolean fetchSistema,
			Boolean fetchPerfis, Boolean fetchPermissoes) {
		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<UsuarioSistema> cq = createCriteriaQuery();
		Root<UsuarioSistema> root = cq.from(UsuarioSistema.class);
		cq.select(root).distinct(true);

		Predicate cond = cb.equal(root.get(BaseEntity_.id), entidade.getId());

		// recupera todos os perfis associado ao usuario-sistema.
		if (fetchPerfis) {
			Join<UsuarioSistema, SistemaPerfil> sistemaPerfilJoin = (Join<UsuarioSistema, SistemaPerfil>) root
					.fetch(UsuarioSistema_.perfis, JoinType.LEFT);
			Join<SistemaPerfil, Perfil> perfilJoin = (Join<SistemaPerfil, Perfil>) sistemaPerfilJoin
					.fetch(SistemaPerfil_.perfil, JoinType.LEFT);
			perfilJoin.fetch(Perfil_.grupoPerfil, JoinType.LEFT);
			// recupera todas as permissoes por perfil.
			if (fetchPermissoes) {
				sistemaPerfilJoin.fetch(SistemaPerfil_.permissoes, JoinType.LEFT);
			}
		}

		if (fetchUsuario) {
			root.fetch(UsuarioSistema_.usuario);
		}
		if (fetchSistema) {
			root.fetch(UsuarioSistema_.sistema);
		}

		cq.where(cond);

		return getSingleResult(cq);
	}

	@Override
	public List<SistemaPermissao> buscarPermissoes(UsuarioSistema entidade) {

		entidade = buscar(entidade.getId());
		entidade.getSistema().getId();
		entidade.getPerfis().size();

		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<SistemaPermissao> cq = cb.createQuery(SistemaPermissao.class);
		Root<SistemaPermissao> root = cq.from(SistemaPermissao.class);
		cq.select(root).distinct(true);

		Root<UsuarioSistema> fromUsuarioSistema = cq.from(UsuarioSistema.class);
		Join<UsuarioSistema, SistemaPerfil> join1 = fromUsuarioSistema.join(UsuarioSistema_.perfis);
		Join<SistemaPerfil, SistemaPermissao> join2 = join1.join(SistemaPerfil_.permissoes, JoinType.LEFT);

		Predicate cond = cb.equal(fromUsuarioSistema.get(BaseEntity_.id), entidade.getId());
		Predicate cond1 = cb.equal(root.get("sistema"), entidade.getSistema().getId());
		cq.where(cond, cond1);

		TypedQuery<SistemaPermissao> q = getEntityManager().createQuery(cq);
		return q.getResultList();

	}

	@Override
	public List<SistemaPermissao> buscarPermissoes(Sistema sistema, Usuario usuario) {
		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<SistemaPermissao> cq = cb.createQuery(SistemaPermissao.class);
		Root<SistemaPermissao> root = cq.from(SistemaPermissao.class);
		List<Predicate> predicates = new ArrayList<Predicate>();

		cq.select(root).distinct(true);

		Root<UsuarioSistema> fromUsuarioSistema = cq.from(UsuarioSistema.class);
		Join<UsuarioSistema, SistemaPerfil> join1 = fromUsuarioSistema.join(UsuarioSistema_.perfis);
		Join<SistemaPerfil, SistemaPermissao> join2 = join1.join(SistemaPerfil_.permissoes, JoinType.LEFT);

		Predicate cond = cb.equal(fromUsuarioSistema.get(UsuarioSistema_.sistema), sistema);
		Predicate cond1 = cb.equal(fromUsuarioSistema.get(UsuarioSistema_.usuario), usuario);
		Predicate cond2 = cb.equal(root.get("sistema"), sistema);

		predicates.add(cond);
		predicates.add(cond1);
		predicates.add(cond2);

		cq.where(cb.and(predicates.toArray(new Predicate[] {})));

		TypedQuery<SistemaPermissao> q = getEntityManager().createQuery(cq);
		return q.getResultList();

	}

	@Override
	public void trocarSituacao(UsuarioSistema entity) throws BeanException {
		StatusType sit = entity.getSituacao();
		UsuarioSistema entidade = buscar(entity);
		if (!entidade.getSituacao().equals(sit)) {
			if (entity.getSituacao() == StatusType.ATIVO) {
				entity.setSituacao(StatusType.INATIVO);
			} else {
				entity.setSituacao(StatusType.ATIVO);
			}
			salvar(entity);
		}
	}

}

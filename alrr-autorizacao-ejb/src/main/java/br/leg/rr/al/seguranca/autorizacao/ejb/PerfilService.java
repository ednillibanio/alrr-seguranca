package br.leg.rr.al.seguranca.ejb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import br.leg.rr.al.core.dao.BaseDominioJPADao;
import br.leg.rr.al.core.dao.BeanException;
import br.leg.rr.al.core.domain.StatusType;
import br.leg.rr.al.core.jpa.BaseEntity_;
import br.leg.rr.al.core.jpa.Dominio_;
import br.leg.rr.al.seguranca.domain.UsuarioType;
import br.leg.rr.al.seguranca.jpa.GrupoPerfil;
import br.leg.rr.al.seguranca.jpa.Perfil;
import br.leg.rr.al.seguranca.jpa.Perfil_;

@Stateless
@Named
public class PerfilService extends BaseDominioJPADao<Perfil> implements PerfilLocal {

	private static final long serialVersionUID = -9162032858310691064L;

	@Override
	public List<Perfil> getAtivos(GrupoPerfil grupo) {

		Map<String, Object> params = new HashMap<String, Object>();

		params.put(PESQUISAR_PARAM_SITUACAO, StatusType.ATIVO);
		params.put(PESQUISAR_PARAM_GRUPO_PERFIL, grupo);

		return pesquisar(params);

	}

	@Override
	public List<Perfil> getAtivos(UsuarioType grupoUsuarios) {

		Map<String, Object> params = new HashMap<String, Object>();
		List<UsuarioType> gruposUsuarios = new ArrayList<>();

		gruposUsuarios.add(grupoUsuarios);
		params.put(PESQUISAR_PARAM_SITUACAO, StatusType.ATIVO);
		params.put(PESQUISAR_PARAM_TIPOS_USUARIO, gruposUsuarios);

		return pesquisar(params);

	}

	@Override
	public List<Perfil> getAtivos(GrupoPerfil grupo, List<Perfil> perfis) {
		Map<String, Object> params = new HashMap<String, Object>();

		params.put(PESQUISAR_PARAM_SITUACAO, StatusType.ATIVO);
		params.put(PESQUISAR_PARAM_GRUPO_PERFIL, grupo);

		// TODO falta implementar essa lista de exclusão.
		params.put("perfilList", perfis);

		return pesquisar(params);

	}

	@Override
	public List<Perfil> getAtivosPorNome(UsuarioType grupoUsuarios, String nome) {
		Map<String, Object> params = new HashMap<String, Object>();
		List<UsuarioType> gruposUsuarios = new ArrayList<>();

		gruposUsuarios.add(grupoUsuarios);
		params.put(PESQUISAR_PARAM_SITUACAO, StatusType.ATIVO);
		params.put(PESQUISAR_PARAM_TIPOS_USUARIO, gruposUsuarios);
		params.put(PESQUISAR_PARAM_NOME, nome);

		return pesquisar(params);
	}

	@Override
	public void trocarSituacao(Perfil entity) throws BeanException {

		renovar(entity);
		if (entity.getSituacao() == StatusType.ATIVO) {
			entity.setSituacao(StatusType.INATIVO);
		} else {
			entity.setSituacao(StatusType.ATIVO);
		}
		salvar(entity);
	}

	@Override
	public Boolean jaExiste(Perfil entidade) {

		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<Perfil> cq = createCriteriaQuery();
		Root<Perfil> root = cq.from(Perfil.class);
		cq.select(root);
		List<Predicate> predicates = new ArrayList<Predicate>();

		Predicate cond = cb.equal(cb.lower(root.get(Dominio_.nome)), entidade.getNome().toLowerCase());
		predicates.add(cond);

		Predicate cond1 = cb.equal(root.get(Perfil_.grupoPerfil), entidade.getGrupoPerfil());
		predicates.add(cond1);

		Predicate cond2 = cb.equal(root.get(Perfil_.tipoUsuario), entidade.getTipoUsuario());
		predicates.add(cond2);

		if (entidade.getId() != null && entidade.getId().intValue() > 0) {
			Predicate cond3 = cb.notEqual(root.get(BaseEntity_.id), entidade.getId());
			predicates.add(cond3);
		}

		cq.where(cb.and(predicates.toArray(new Predicate[] {})));

		return (!getResultList(cq).isEmpty());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Perfil> pesquisar(Map<String, Object> params) {

		String nome = null;
		StatusType situacao = null;
		List<GrupoPerfil> grupos = null;
		List<UsuarioType> gruposUsuario = null;

		List<Predicate> predicates = new ArrayList<Predicate>();

		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<Perfil> cq = getCriteriaBuilder().createQuery(entityClass);
		Root<Perfil> root = cq.from(entityClass);
		root.fetch(Perfil_.grupoPerfil);
		cq.select(root);

		if (params.size() > 0) {

			if (params.containsKey(PESQUISAR_PARAM_NOME)) {
				nome = (String) params.get(PESQUISAR_PARAM_NOME);
				if (StringUtils.isNotBlank(nome)) {
					Predicate cond = cb.like(cb.lower(root.get(PESQUISAR_PARAM_NOME)), "%" + nome.toLowerCase() + "%");
					predicates.add(cond);
				}
			}

			if (params.containsKey(PESQUISAR_PARAM_GRUPOS_PERFIL)) {
				grupos = (List<GrupoPerfil>) params.get(PESQUISAR_PARAM_GRUPOS_PERFIL);
				if (grupos != null && grupos.size() > 0) {
					Expression<GrupoPerfil> exp = root.get(Perfil_.grupoPerfil);
					Predicate cond = exp.in(grupos);
					predicates.add(cond);

				}
			}

			if (params.containsKey(PESQUISAR_PARAM_TIPOS_USUARIO)) {
				gruposUsuario = (List<UsuarioType>) params.get(PESQUISAR_PARAM_TIPOS_USUARIO);
				if (gruposUsuario != null && gruposUsuario.size() > 0) {
					Expression<UsuarioType> exp = root.get(Perfil_.tipoUsuario);
					Predicate cond = exp.in(gruposUsuario);
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
			return getResultList(cq);
		}

		return null;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.leg.al.rr.core.dao.BaseJPADao#carregarEntidade(br.leg.al.rr.core.jpa.
	 * EntityStatus)
	 */
	@Override
	public Perfil carregar(Perfil entidade) {
		Perfil perfil = buscar(entidade);
		perfil.getGrupoPerfil().getId();

		return perfil;
	}

}

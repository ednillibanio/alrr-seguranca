package br.leg.rr.al.seguranca.core.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import br.leg.rr.al.core.dao.BaseDominioJPADao;
import br.leg.rr.al.core.domain.StatusType;
import br.leg.rr.al.seguranca.jpa.Sistema;
import br.leg.rr.al.seguranca.jpa.SistemaDominio;

/**
 * FIXME: depois tem que alinhar as duas classes BaseJPADaoStatus com essa
 * daqui, pois havia metodos duplicados nas duas e eu resolvi fazer um extend.
 * ver certinho se é isso mesmo que tem que fazer
 * 
 * @author ednil
 *
 * @param <T>
 */
public abstract class SistemaDominioJPADao<T extends SistemaDominio> extends BaseDominioJPADao<T>
		implements SistemaDominioDao<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8054203563918294954L;

	@Override
	public List<T> buscarPorNome(Sistema sistema, String nome) {

		Map<String, Object> params = new HashMap<String, Object>();

		params.put(PESQUISAR_PARAM_SISTEMA, sistema);
		params.put(PESQUISAR_PARAM_NOME, nome);

		return pesquisar(params);

	}

	@Override
	public List<T> buscarPorSituacao(Sistema sistema, StatusType situacao) {
		Map<String, Object> params = new HashMap<String, Object>();

		params.put("situacoes", situacao);
		params.put(PESQUISAR_PARAM_SISTEMA, sistema);

		return pesquisar(params);
	}

	@Override
	public List<T> getAtivos(Sistema sistema) {
		return buscarPorSituacao(sistema, StatusType.ATIVO);
	}

	@Override
	public List<T> getAtivosPorNome(Sistema sistema, String nome) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("situacoes", StatusType.ATIVO);
		params.put(PESQUISAR_PARAM_SISTEMA, sistema);
		params.put(PESQUISAR_PARAM_NOME, nome);

		return pesquisar(params);

	}

	@Override
	public List<T> pesquisar(Map<String, Object> params) {

		String nome = null;
		StatusType situacao = null;
		List<Predicate> predicates = new ArrayList<Predicate>();

		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<T> cq = getCriteriaBuilder().createQuery(entityClass);
		Root<T> root = cq.from(entityClass);
		cq.select(root);

		if (params.size() > 0) {

			// faz o relacionamento de todas as tabelas que são dependentes de
			// sistema.
			if (params.containsKey(PESQUISAR_PARAM_SISTEMA)) {
				Sistema sistema = (Sistema) params.get(PESQUISAR_PARAM_SISTEMA);
				Predicate cond = cb.equal(root.get(PESQUISAR_PARAM_SISTEMA), sistema);
				predicates.add(cond);
			}

			if (params.containsKey(PESQUISAR_PARAM_NOME)) {
				nome = (String) params.get(PESQUISAR_PARAM_NOME);
				if (StringUtils.isNotBlank(nome)) {
					Predicate cond = cb.like(cb.lower(root.get(PESQUISAR_PARAM_NOME)), "%" + nome.toLowerCase() + "%");
					predicates.add(cond);
				}
			}

			if (params.containsKey(PESQUISAR_PARAM_SITUACAO)) {
				situacao = (StatusType) params.get(PESQUISAR_PARAM_SITUACAO);
				if (situacao != null) {
					Predicate cond = cb.equal(root.get("situacao"), situacao);
					predicates.add(cond);
				}
			}

			cq.where(cb.and(predicates.toArray(new Predicate[] {})));
			return getEntityManager().createQuery(cq).getResultList();
		}

		return null;

	}

	@Override
	public List<T> getInativos(Sistema sistema) {
		return buscarPorSituacao(sistema, StatusType.INATIVO);
	}

}

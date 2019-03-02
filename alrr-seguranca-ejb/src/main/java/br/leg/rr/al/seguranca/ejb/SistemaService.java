package br.leg.rr.al.seguranca.ejb;

import java.sql.Date;
import java.util.Locale;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.leg.rr.al.core.dao.BaseDominioJPADao;
import br.leg.rr.al.core.utils.DataUtils;
import br.leg.rr.al.seguranca.domain.Sistemas;
import br.leg.rr.al.seguranca.jpa.Sistema;
import br.leg.rr.al.seguranca.jpa.Sistema_;

/**
 * Bean que carrega inicialmente as configurações do sistema.
 * 
 * @author Ednil Libanio
 * @date 30/07/2012
 * 
 * 
 */

@Named
@Stateless
public class SistemaService extends BaseDominioJPADao<Sistema> implements SistemaLocal {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6246705796435419310L;

	private Sistema sistema;

	private Locale locale;

	/**
	 * Retorna
	 */
	@Override
	public Date getDataExpiraSenha() {

		java.util.Date dataExpira = null;

		// TODO: Colocar um try e cacth para ver se o objeto sistema está vazio.
		// Interessante para notificar o usuário que ainda não tem configuração.
		// Alem disso, tem que tratar pq retorna zero também e ai da erro.
		// FIXME tem que tirar isso daqui o método. Ver depois.
		int periodo = 60; // Integer.parseInt(sistema.getSeguranca().getPeriodoExpiraSenha().toString());
		dataExpira = DataUtils.trocarData(DataUtils.hoje(), periodo);
		return new java.sql.Date(dataExpira.getTime());

	}

	@Override
	public Sistema buscar(Sistemas sistema) {
		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<Sistema> cq = createCriteriaQuery();
		Root<Sistema> root = cq.from(Sistema.class);

		Predicate cond = cb.equal(root.get(Sistema_.valor), sistema);
		cq.where(cond);

		return getSingleResult(cq);

	}

	@Override
	public Sistema getSistema() {
		return sistema;
	}

	@Override
	public void setSistema(Sistema sistema) {
		this.sistema = sistema;
	}

	@Override
	public Locale getLocale() {
		return locale;
	}

	@Override
	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	@Override
	public Boolean jaExiste(Sistema entidade) {
		return false;
	}
}

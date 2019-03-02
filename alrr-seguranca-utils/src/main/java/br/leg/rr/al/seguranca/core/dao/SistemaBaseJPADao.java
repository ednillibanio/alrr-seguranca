package br.leg.rr.al.seguranca.core.dao;

import java.io.Serializable;

import br.leg.rr.al.core.dao.BaseJPADaoStatus;
import br.leg.rr.al.core.jpa.EntityStatus;

public abstract class SistemaBaseJPADao<T extends EntityStatus<ID>, ID extends Serializable> extends BaseJPADaoStatus<T, ID>
		implements SistemaJPADao<T, ID> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6116652878491718866L;

}

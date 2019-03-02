package br.leg.rr.al.seguranca.core.dao;

import java.io.Serializable;

import br.leg.rr.al.core.dao.JPADaoStatus;
import br.leg.rr.al.core.jpa.EntityStatus;

public interface SistemaJPADao<T extends EntityStatus<ID>, ID extends Serializable> extends JPADaoStatus<T, ID> {

}

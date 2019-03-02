package br.leg.rr.al.seguranca.ejb;

import javax.ejb.Local;

import br.leg.rr.al.core.dao.DominioJPADao;
import br.leg.rr.al.seguranca.jpa.Menu;

@Local
public interface MenuLocal extends DominioJPADao<Menu> {

}

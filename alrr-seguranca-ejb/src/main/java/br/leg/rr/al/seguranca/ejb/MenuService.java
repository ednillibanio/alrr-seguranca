package br.leg.rr.al.seguranca.ejb;

import javax.ejb.Stateless;
import javax.inject.Named;

import br.leg.rr.al.core.dao.BaseDominioJPADao;
import br.leg.rr.al.seguranca.jpa.Menu;

/**
 * Bean que carrega inicialmente as configurações do menu.
 * 
 * @author Ednil Libanio
 * @date 18/07/2018
 * 
 * 
 */
@Named
@Stateless
public class MenuService extends BaseDominioJPADao<Menu> implements MenuLocal {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3082810248905050723L;

	@Override
	public Boolean jaExiste(Menu entidade) {
		// TODO Auto-generated method stub
		return false;
	}

}

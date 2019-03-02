package br.leg.rr.al.seguranca.ejb;

import javax.ejb.Stateless;
import javax.inject.Named;

import br.leg.rr.al.core.dao.BaseDominioJPADao;
import br.leg.rr.al.seguranca.jpa.GrupoPerfil;

@Named
@Stateless
public class GrupoService extends BaseDominioJPADao<GrupoPerfil> implements GrupoLocal {

	private static final long serialVersionUID = -1009861115440115454L;

	@Override
	public Boolean jaExiste(GrupoPerfil entidade) {
		return false;
	}

}

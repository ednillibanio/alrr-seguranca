package br.leg.rr.al.seguranca.autorizacao.ejb;

import javax.ejb.Stateless;
import javax.inject.Named;

import br.leg.rr.al.core.dao.BaseDominioIndexadoJPADao;
import br.leg.rr.al.seguranca.autorizacao.jpa.GrupoPerfil;

@Named
@Stateless
public class GrupoPerfilService extends BaseDominioIndexadoJPADao<GrupoPerfil> implements GrupoPerfilLocal {

	private static final long serialVersionUID = -1009861115440115454L;

	@Override
	public Boolean jaExiste(GrupoPerfil entidade) {
		return false;
	}

}

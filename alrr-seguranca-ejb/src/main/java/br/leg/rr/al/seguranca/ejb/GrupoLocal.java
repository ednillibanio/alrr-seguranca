package br.leg.rr.al.seguranca.ejb;

import javax.ejb.Local;

import br.leg.rr.al.core.dao.DominioJPADao;
import br.leg.rr.al.seguranca.jpa.GrupoPerfil;

@Local
public interface GrupoLocal extends DominioJPADao<GrupoPerfil> {

}

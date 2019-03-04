package br.leg.rr.al.seguranca.autorizacao.ejb;

import javax.ejb.Local;

import br.leg.rr.al.core.dao.DominioIndexadoJPADao;
import br.leg.rr.al.seguranca.autorizacao.jpa.GrupoPerfil;

@Local
public interface GrupoPerfilLocal extends DominioIndexadoJPADao<GrupoPerfil> {

}

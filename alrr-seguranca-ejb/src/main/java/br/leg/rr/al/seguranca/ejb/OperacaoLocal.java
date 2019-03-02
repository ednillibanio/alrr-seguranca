package br.leg.rr.al.seguranca.ejb;

import javax.ejb.Local;

import br.leg.rr.al.seguranca.core.dao.SistemaDominioDao;
import br.leg.rr.al.seguranca.jpa.Operacao;

@Local
public interface OperacaoLocal extends SistemaDominioDao<Operacao> {

}

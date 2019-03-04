package br.leg.rr.al.seguranca.autorizacao.ejb;

import javax.ejb.Local;

import br.leg.rr.al.core.dao.DominioJPADao;
import br.leg.rr.al.seguranca.autorizacao.jpa.Operacao;

@Local
public interface OperacaoLocal extends DominioJPADao<Operacao> {

}

package br.leg.rr.al.seguranca.autorizacao.utils;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import br.leg.rr.al.core.domain.StatusType;

/**
 * @author Ednil Libanio da Costa Junior
 * @date 19-04-2012
 */
@Named
@RequestScoped
public class AutorizacaoEnumFactory {

	public StatusType[] getStatusType() {
		return StatusType.values();
	}

}

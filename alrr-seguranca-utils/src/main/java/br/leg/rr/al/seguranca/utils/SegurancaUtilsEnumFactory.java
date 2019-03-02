/**
 *
 */
package br.leg.rr.al.seguranca.utils;

import javax.inject.Named;

import br.leg.rr.al.seguranca.domain.Sistemas;

/**
 * @author <a href="mailto:ednil.libanio@gmail.com"> Ednil Libanio da Costa
 *         Junior</a>
 * @since 1.0.0
 */
@Named
// @RequestScoped
public class SegurancaUtilsEnumFactory {

	public Sistemas[] getSistemas() {
		return Sistemas.values();
	}
}

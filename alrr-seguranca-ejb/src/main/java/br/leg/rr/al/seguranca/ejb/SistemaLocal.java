package br.leg.rr.al.seguranca.ejb;

import java.sql.Date;
import java.util.Locale;

import javax.ejb.Local;

import br.leg.rr.al.core.dao.DominioJPADao;
import br.leg.rr.al.seguranca.domain.Sistemas;
import br.leg.rr.al.seguranca.jpa.Sistema;

@Local
public interface SistemaLocal extends DominioJPADao<Sistema> {

	/**
	 * Calcula a data que a senha do usuário deve expirar conforme regra no sistema.
	 * O método calcula a data de acordo com o campo
	 * sistema.getSeguranca().getPeriodoExpiraSenha.
	 * 
	 * @return retorna a data que a senha irá expirar ou <code>null</code> caso não
	 *         exista restrição para expirar senha.
	 */
	Date getDataExpiraSenha();

	/**
	 * @return
	 */
	Sistema getSistema();

	/**
	 * @param sistema
	 */
	void setSistema(Sistema sistema);

	/**
	 * 
	 * @return retorna o Locale que está definido no sistema.
	 */
	Locale getLocale();

	/**
	 * Define o Locale para o sistema.
	 * 
	 * @param locale que será definido como padrão.
	 */
	void setLocale(Locale locale);

	/**
	 * 
	 * @param sistema
	 * @return
	 */
	Sistema buscar(Sistemas sistema);
}

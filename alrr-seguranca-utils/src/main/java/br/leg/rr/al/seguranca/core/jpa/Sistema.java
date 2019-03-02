package br.leg.rr.al.seguranca.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.leg.rr.al.core.jpa.Dominio;

/**
 * FIXME: Essa classe não deve ficar aqui. Não faz parte de seguranca.
 * 
 * @author ednil
 *
 */
@Entity
@Table
public class Sistema extends Dominio {

	private static final long serialVersionUID = 4300113608223792099L;

	/**
	 * Política segurança é o mesmo caso do tema. Na verdade a política de segurança
	 * é única para todos os sistemas e deve ser aplicado na autenticação do
	 * usuário. Provavelmente um modulo só disso.
	 */
	// @Embedded private PolicitaSenha seguranca;

	@NotNull(message = "Valor: preenchimento obrigatório")
	@Column(length = 8, nullable = false)
	private String valor;

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

}
